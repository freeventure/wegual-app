package com.wegual.pledgeservice.service;




import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegual.pledgeservice.ElasticSearchConfig;
import com.wegual.pledgeservice.GenericItemBeneficiaryUtils;
import com.wegual.pledgeservice.GenericItemGiveUpUtils;
import com.wegual.pledgeservice.GenericItemUserUtils;
import com.wegual.pledgeservice.message.PledgeTimelineItemBuilder;
import com.wegual.pledgeservice.message.TimelineMessageSender;

import app.wegual.common.asynch.SenderRunnable;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.GiveUpTimelineItem;
import app.wegual.common.model.Pledge;
import app.wegual.common.model.RegisterPledge;
import app.wegual.common.model.UserTimelineItem;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.xcontent.XContentFactory;

@Slf4j
@Service
public class PledgeService {

	@Autowired
	private ElasticSearchConfig esConfig;
	@Autowired
	private TimelineMessageSender utms;
	@Autowired
	@Qualifier("executorMessageSender")
	private TaskExecutor te;
	
	//TODO : Modify function to return list of pledges
	public List<Map<String, Object>> getAllPledgeForUser(String userId){
		log.info("Inside pledge service");
		List<Map<String, Object>> pledges = new ArrayList<>();
		try {
			SearchRequest searchRequest = new SearchRequest("pledge_idx");
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.nestedQuery("pledged_by",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("pledged_by.id", userId)), ScoreMode.None));
			searchRequest.source(sourceBuilder);
			
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value>0L) {
				Map<String, Object> src = null;
				for (SearchHit hit: searchResponse.getHits()) {
					src = hit.getSourceAsMap();
					//Pledge pledge = new ObjectMapper().readValue(hit.getSourceAsString(), Pledge.class);
					pledges.add(src);
				}
			}
			return pledges;
		} catch (Exception e) {
			log.error("Error getting pledges user: " + userId , e);
		}
		return pledges;
	}
	
	public void createPledge(RegisterPledge pledge) throws JsonProcessingException {
		Map<String, Object> giveup = null;
		Map<String, Object> user = null;
		Map<String, Object> beneficiary = null;
		
		SearchRequest userRequest = new SearchRequest("user_idx");
		SearchSourceBuilder userBuilder = new SearchSourceBuilder(); 
		userBuilder.query(QueryBuilders.termQuery("user_id", pledge.getUserId())); 		
		userRequest.source(userBuilder);
		RestHighLevelClient client = esConfig.getElastcsearchClient();
		try {
			
			SearchResponse userResponse = client.search(userRequest, RequestOptions.DEFAULT);
			SearchHit[] hits = userResponse.getHits().getHits();
			user = hits[0].getSourceAsMap();
		} 
		catch (IOException e) {
			log.error("Error getting user from ES: ", e);
		}
		
		SearchRequest benRequest = new SearchRequest("beneficiary_idx");
		SearchSourceBuilder benBuilder = new SearchSourceBuilder(); 
		benBuilder.query(QueryBuilders.termQuery("beneficiary_id", pledge.getBeneficiaryId())); 		
		benRequest.source(benBuilder);
		
		try {
			
			SearchResponse benResponse = client.search(benRequest, RequestOptions.DEFAULT);
			SearchHit[] hits = benResponse.getHits().getHits();
			beneficiary = hits[0].getSourceAsMap();
		} 
		catch (IOException e) {
			log.error("Error getting beneficiary from ES: ", e);
		}
		
		SearchRequest givupRequest = new SearchRequest("giveup_idx");
		SearchSourceBuilder giveupBuilder = new SearchSourceBuilder(); 
		giveupBuilder.query(QueryBuilders.termQuery("giveup_id", pledge.getGiveupId())); 		
		givupRequest.source(giveupBuilder);
		
		try {
			
			SearchResponse giveupResponse = client.search(givupRequest, RequestOptions.DEFAULT);
			SearchHit[] hits = giveupResponse.getHits().getHits();
			giveup = hits[0].getSourceAsMap();
		} 
		catch (IOException e) {
			log.error("Error getting giveup from ES: ", e);
		}
		
		GenericItem<String> genericUser = GenericItemUserUtils.userGenericItemFromEsDocument(user);
		GenericItem<String> genericBeneficiary = GenericItemBeneficiaryUtils.beneficiaryGenericItemFromEsDocument(beneficiary);
		GenericItem<String> genericGiveUp = GenericItemGiveUpUtils.giveupGenericItemFromEsDocument(giveup);
		
		Pledge pledgeObject = new Pledge();
		pledgeObject.setAmount(Double.parseDouble(pledge.getAmount()));
		pledgeObject.setCurrency(Currency.getInstance(pledge.getCurrency()));
		pledgeObject.setPledgedDate(System.currentTimeMillis());
		pledgeObject.setPledgedBy(genericUser);
		pledgeObject.setBeneficiary(genericBeneficiary);
		pledgeObject.setGiveUp(genericGiveUp);
		pledgeObject.setBaseCurrency(Currency.getInstance(pledge.getBaseCurrency()));
		pledgeObject.setBaseCurrencyAmount(Double.parseDouble(pledge.getBaseAmount()));
		
		IndexRequest indexRequest = new IndexRequest("pledge_idx").source(new ObjectMapper().writeValueAsString(pledgeObject),XContentType.JSON);
		indexRequest.opType(DocWriteRequest.OpType.INDEX);
		try {
			IndexResponse indexResponse = esConfig.getElastcsearchClient().index(
					indexRequest, RequestOptions.DEFAULT);
			if (indexResponse.getResult() != DocWriteResponse.Result.CREATED)
				throw new IllegalStateException("Pledge document was not created for id: " + indexResponse.getId());
			else
			{
				log.info("Pledge document created successfully for id: " + indexResponse.getId());
				UpdateRequest updateRequest = new UpdateRequest();
				updateRequest.index("pledge_idx");
				updateRequest.id(indexResponse.getId());
				updateRequest.doc( XContentFactory.jsonBuilder()
				        .startObject()
				            .field("pledge_id",indexResponse.getId() )
				        .endObject());
				UpdateResponse update = esConfig.getElastcsearchClient().update(updateRequest, RequestOptions.DEFAULT);
				log.info("Pledge Document update with id " + update.toString());
				pledgeObject.setId(indexResponse.getId());
				UserTimelineItem uti = new PledgeTimelineItemBuilder().pledge(pledgeObject)
						.build();
				te.execute(new SenderRunnable<TimelineMessageSender, UserTimelineItem>(utms, uti));
				BeneficiaryTimelineItem bti = new PledgeTimelineItemBuilder().pledge(pledgeObject)
						.buildForBeneficiary();
				te.execute(new SenderRunnable<TimelineMessageSender, BeneficiaryTimelineItem>(utms, bti));
			}
		} catch (IOException e) {
			
			log.error("Unable to create pledge document in ES", e);
		}
	}
}

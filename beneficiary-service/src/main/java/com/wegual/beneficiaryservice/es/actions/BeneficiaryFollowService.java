package com.wegual.beneficiaryservice.es.actions;

import java.io.IOException;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegual.beneficiaryservice.ElasticSearchConfig;
import com.wegual.beneficiaryservice.message.BeneficiaryFollowTimelineItemBuilder;
import com.wegual.beneficiaryservice.message.UserTimelineMessageSender;

import app.wegual.common.asynch.SenderRunnable;
import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.BeneficiaryFollowItem;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.User;
import app.wegual.common.model.UserActionItem;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.util.ESIndices;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeneficiaryFollowService {
	
	@Autowired
	private ElasticSearchConfig esConfig;
	
	@Autowired
	@Qualifier("executorMessageSender")
	private TaskExecutor te;
	
	@Autowired
	private UserTimelineMessageSender utms;

	public void followBeneficiary(UserActionItem uai) {
		String userId = uai.getActorId();
		String benId = uai.getTargetId();
		
		GenericItem<String> userFollower = getUserGenericItem(userId);
		GenericItem<String> benFollowee = getBeneficiaryGenericItem(benId);
		
		BeneficiaryFollowItem followItem = new BeneficiaryFollowItem();
		String id = benFollowee.getId() + "_" +userFollower.getId();
		followItem.setId(id);
		followItem.setUserFollower(userFollower);
		followItem.setBeneficiaryFollowee(benFollowee);
		followItem.setFollowDate(System.currentTimeMillis());
		
		RestHighLevelClient client = esConfig.getElasticsearchClient();
		try {
			IndexRequest indexRequest = new IndexRequest(ESIndices.BENEFICIARY_FOLLOW_INDEX).id(followItem.getId()).source(new ObjectMapper().writeValueAsString(followItem), XContentType.JSON);
			indexRequest.opType(DocWriteRequest.OpType.INDEX);
			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			if ( (indexResponse.getResult() != DocWriteResponse.Result.CREATED) && (indexResponse.getResult() != DocWriteResponse.Result.UPDATED) )
				throw new IllegalStateException("Beneficiary was not followed for user id: " + userId);
			else if(indexResponse.getResult() == DocWriteResponse.Result.UPDATED)
			{
				log.info("Beneficiary " + benId + " was already followed successfully for userId: " + userId);
				return;
			}
			{	
				log.info("Beneficiary " + benId + " followed successfully for userId: " + userId);
				UserTimelineItem uti = new BeneficiaryFollowTimelineItemBuilder(UserActionType.FOLLOW_BENEFICIARY)
						.time(System.currentTimeMillis())
						.user(userFollower)
						.beneficiary(benFollowee)
						.build();
				te.execute(new SenderRunnable<UserTimelineMessageSender, UserTimelineItem>(utms, uti));
			}
		} catch (Exception e) {
			log.error("Unable to follow a ben for given userId in ES", benFollowee.getName(), userFollower.getId(), e);
		}
	}
	
	public void unfollowBeneficiary(UserActionItem uai) {
		String id = uai.getTargetId() + "_" + uai.getActorId();
		
		DeleteByQueryRequest request = new DeleteByQueryRequest(ESIndices.BENEFICIARY_FOLLOW_INDEX); 
		
		TermQueryBuilder termQuery = QueryBuilders.termQuery("id", id);
				
		request.setQuery(termQuery);
		request.setMaxDocs(1);
		request.setBatchSize(1);
		request.setRefresh(true);
		
		try {
			BulkByScrollResponse bulkResponse = esConfig.getElasticsearchClient().deleteByQuery(request, RequestOptions.DEFAULT);
			log.info("bulk response for delete: " + bulkResponse.getDeleted());
			if(bulkResponse.getDeleted() != 1)
				throw new IllegalStateException("exactly one document was not deleted");
			else {
				log.info("Beneficiary " + uai.getTargetId() + " unfollowed successfully for userId: " + uai.getActorId());
				GenericItem<String> userFollower = new GenericItem<String>();
				GenericItem<String> benFollowee = new GenericItem<String>();
				
				userFollower = getUserGenericItem(uai.getActorId());
				benFollowee = getBeneficiaryGenericItem(uai.getTargetId());
				UserTimelineItem uti = new BeneficiaryFollowTimelineItemBuilder(UserActionType.FOLLOW_BENEFICIARY)
						.time(System.currentTimeMillis())
						.user(userFollower)
						.beneficiary(benFollowee)
						.build();
				te.execute(new SenderRunnable<UserTimelineMessageSender, UserTimelineItem>(utms, uti));
			}
		} catch (IOException e) {
			
			log.error("Unable to unlike a giveup document by query: " + id, e);
		}
	}
	
	public GenericItem<String> getUserGenericItem(String userId) {
		GenericItem<String> user = new GenericItem<String>();
		try {
			SearchRequest searchRequest = new SearchRequest(ESIndices.USER_INDEX);
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.termQuery("user_id", userId));
			searchRequest.source(sourceBuilder);
			
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			User u = null;
			if(searchResponse.getHits().getTotalHits().value>0L) {
				for(SearchHit hit : searchResponse.getHits()) {
					u = new ObjectMapper().readValue(hit.getSourceAsString(), User.class);
				}
				user.setId(u.getId());
				String name = u.getFirstName() + " " + u.getLastName();
				user.setName(name);
				user.setPictureLink(u.getPictureLink());
				String permalink = "/user/" + u.getId();
				user.setPermalink(permalink);
				return user;
			}
			
		} catch (Exception e) {
			log.info("User not found", e);
		}
		return user;
	}
	
	public GenericItem<String> getBeneficiaryGenericItem(String benId) {
		GenericItem<String> ben = new GenericItem<String>();
		try {
			SearchRequest searchRequest = new SearchRequest(ESIndices.BENEFICIARY_INDEX);
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.termQuery("beneficiary_id", benId));
			searchRequest.source(sourceBuilder);
			
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			Beneficiary b = null;
			if(searchResponse.getHits().getTotalHits().value>0L) {
				for(SearchHit hit : searchResponse.getHits()) {
					b = new ObjectMapper().readValue(hit.getSourceAsString(), Beneficiary.class);
				}
				ben.setId(b.getId());
				ben.setName(b.getName());
				ben.setPictureLink(b.getPictureLink());
				String permalink = "/beneficiary/" + b.getId();
				ben.setPermalink(permalink);
				return ben;
			}
			
		} catch (Exception e) {
			log.info("Beneficiary not found", e);
		}
		return ben;
	}
}

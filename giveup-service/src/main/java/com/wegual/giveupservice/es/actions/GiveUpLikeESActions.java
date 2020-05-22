package com.wegual.giveupservice.es.actions;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.giveupservice.message.GiveUpUserAction;



@Component
public class GiveUpLikeESActions {
	
	@Autowired
	private RestHighLevelClient client;
	
	protected void processAsynch(GiveUpBeneficiaryAction action, GiveUpUserAction bua) {
		Thread th = new Thread(new ESAsyncCommandRunner(action, bua));
		th.start();
	}
	
	public void userFollowed(GiveUpUserAction bua) {
		
		GiveUpBeneficiaryAction action = this::asynchUserFollowed;
		processAsynch(action, bua);
		
	}
	
	protected void asynchUserFollowed(GiveUpUserAction guua)
	{
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			IndexRequest indexRequest = new IndexRequest("giveup_follow_idx", "_doc", getActionId(guua))
			        .source(jsonBuilder()
			                .startObject()
			                .field("giveup", guua.getGiveUp().getId().toString())
			                .field("date_stamp", df.format(guua.getActionDate()))
			                .field("follower_user", guua.getUser().getId().toString())
			            .endObject());
			client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void userUnfollowed(GiveUpUserAction bua) {
		GiveUpBeneficiaryAction action = this::asynchUserUnfollowed;
		processAsynch(action, bua);
	}

	public void asynchUserUnfollowed(GiveUpUserAction bua) {
		DeleteRequest request = new DeleteRequest("giveup_follow_idx", "_doc", getActionId(bua));
		try {
			DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	protected String getActionId(GiveUpUserAction guua) {
		return guua.getGiveUp().getId() + "-" + guua.getUser().getId();
	}

	
	private static class ESAsyncCommandRunner implements Runnable {
		
		private GiveUpBeneficiaryAction action;
		private GiveUpUserAction data;
		
		public ESAsyncCommandRunner(GiveUpBeneficiaryAction action, GiveUpUserAction bua) {
			this.action = action;
			data = bua;
		}
		
		public void run() {
			action.theAction(data);
		}
	}
	
	@FunctionalInterface
    public static interface GiveUpBeneficiaryAction {
        void theAction(GiveUpUserAction bua);
    }
}

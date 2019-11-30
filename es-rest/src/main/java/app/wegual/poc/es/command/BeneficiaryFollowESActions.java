package app.wegual.poc.es.command;

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

import app.wegual.poc.common.message.BeneficiaryUserAction;

@Component
public class BeneficiaryFollowESActions {
	
	@Autowired
	private RestHighLevelClient client;
	
	protected void processAsynch(ESBeneficiaryAction action, BeneficiaryUserAction bua) {
		Thread th = new Thread(new ESAsyncCommandRunner(action, bua));
		th.start();
	}
	
	public void userFollowed(BeneficiaryUserAction bua) {
		
		ESBeneficiaryAction action = this::asynchUserFollowed;
		processAsynch(action, bua);
		
	}
	
	protected void asynchUserFollowed(BeneficiaryUserAction bua)
	{
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			IndexRequest indexRequest = new IndexRequest("beneficiary_follow_idx", "_doc", getActionId(bua))
			        .source(jsonBuilder()
			                .startObject()
			                .field("beneficiary", bua.getBeneficiary().getId().toString())
			                .field("date_stamp", df.format(bua.getActionDate()))
			                .field("follower_user", bua.getUser().getId().toString())
			            .endObject());
			client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void userUnfollowed(BeneficiaryUserAction bua) {
		ESBeneficiaryAction action = this::asynchUserUnfollowed;
		processAsynch(action, bua);
	}

	public void asynchUserUnfollowed(BeneficiaryUserAction bua) {
		DeleteRequest request = new DeleteRequest("beneficiary_follow_idx", "_doc", getActionId(bua));
		try {
			DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	protected String getActionId(BeneficiaryUserAction bua) {
		return bua.getBeneficiary().getId() + "-" + bua.getUser().getId();
	}

	
	private static class ESAsyncCommandRunner implements Runnable {
		
		private ESBeneficiaryAction action;
		private BeneficiaryUserAction data;
		
		public ESAsyncCommandRunner(ESBeneficiaryAction action, BeneficiaryUserAction bua) {
			this.action = action;
			data = bua;
		}
		
		public void run() {
			action.theAction(data);
		}
	}
	
	@FunctionalInterface
    public static interface ESBeneficiaryAction {
        void theAction(BeneficiaryUserAction bua);
    }
}

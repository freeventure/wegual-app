package app.wegual.poc.springdatamysql.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.wegual.poc.common.model.BeneficiaryFollowers;
import app.wegual.poc.springdatamysql.messaging.BeneficiaryFollowersMessageSender;

@RepositoryEventHandler(BeneficiaryFollowers.class)
public class BeneficiaryFollowersEventHandler {
	
	@Autowired
	  BeneficiaryFollowersMessageSender bfms;
	  
	  @HandleAfterCreate
	  public void handleBeneficiaryFollowersCreate(BeneficiaryFollowers ben) {
	    // … you can now deal with Person in a type-safe way
		  System.out.println("BeneficiaryFollowers create called");
		  sendMessageAsynch(ben);
	  }
	  
	  @HandleAfterDelete
	  public void handleBeneficiaryFollowersDelete(BeneficiaryFollowers pl) {

		  System.out.println("Beneficiary delete called");
		  sendMessageAsynch(pl);
	  }
		
	  protected void sendMessageAsynch(BeneficiaryFollowers pl) {
		  Thread th = new Thread(new SenderRunnable(bfms, pl));
			
		  th.start();
		}
		
	  private static class SenderRunnable implements Runnable {

		  private BeneficiaryFollowersMessageSender sender;
		  private BeneficiaryFollowers ben;
		  public SenderRunnable(final BeneficiaryFollowersMessageSender sender, BeneficiaryFollowers ben) {
			  this.sender = sender;
			  this.ben = ben;
		  }
		  @Override
		  public void run() {
			  System.out.println("I am running in thread: " +  Thread.currentThread().getName());
			  sender.sendMessage(ben);
		  }

	  }
}
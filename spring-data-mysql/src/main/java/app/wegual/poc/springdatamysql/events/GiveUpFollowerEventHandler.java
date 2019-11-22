package app.wegual.poc.springdatamysql.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.wegual.poc.springdatamysql.messaging.GiveUpFollowerMessageSender;
import app.wegual.poc.common.model.GiveUpFollower;

@RepositoryEventHandler(GiveUpFollower.class)
public class GiveUpFollowerEventHandler {
	
	@Autowired
	  GiveUpFollowerMessageSender bfms;
	  
	  @HandleAfterCreate
	  public void handleGiveUpFollowerCreate(GiveUpFollower ben) {
	    // … you can now deal with Person in a type-safe way
		  System.out.println("BeneficiaryFollowers create called");
		  sendMessageAsynch(ben);
	  }
	  
	  @HandleAfterDelete
	  public void handleGiveUpFollowerDelete(GiveUpFollower pl) {

		  System.out.println("Beneficiary delete called");
		  sendMessageAsynch(pl);
	  }
		
	  protected void sendMessageAsynch(GiveUpFollower pl) {
		  Thread th = new Thread(new SenderRunnable(bfms, pl));
			
		  th.start();
		}
		
	  private static class SenderRunnable implements Runnable {

		  private GiveUpFollowerMessageSender sender;
		  private GiveUpFollower ben;
		  public SenderRunnable(final GiveUpFollowerMessageSender sender, GiveUpFollower ben) {
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
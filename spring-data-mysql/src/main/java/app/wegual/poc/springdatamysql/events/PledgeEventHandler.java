package app.wegual.poc.springdatamysql.events;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.wegual.poc.common.model.BeneficiaryTimeline;
import app.wegual.poc.common.model.Pledge;
import app.wegual.poc.common.model.UserTimeline;
import app.wegual.poc.springdatamysql.messaging.PledgeMessageSender;

@RepositoryEventHandler(Pledge.class)
public class PledgeEventHandler {

  @Autowired
  PledgeMessageSender pms;
  
  UserTimeline userTimeline;
  BeneficiaryTimeline beneficiaryTimeline;
  
  @HandleAfterCreate
  public void handlePledgeCreate(Pledge pl) {
	  // … you can now deal with Person in a type-safe way
	  System.out.println("Pledge create called");
	  Timestamp ts = new Timestamp(new Date().getTime());
	  userTimeline = new UserTimeline().withId("usr" + pl.getPledgedBy().getId().toString())
			  .withObjectId("plg" + pl.getId().toString())
			  .withOperationType("Create")
			  .withTimestamp(ts);
	  beneficiaryTimeline = new BeneficiaryTimeline().withId("ben" + pl.getBeneficiary().getId().toString())
			  .withOperationType("plg" + pl.getId())
			  .withTimestamp(ts);
	  sendMessageAsynch(userTimeline);
	  sendMessageAsynch(beneficiaryTimeline);
  }

  @HandleAfterSave
  public void handlePledgeSave(Pledge pl) {
	  System.out.println("Pledge save called");
	  Timestamp ts = new Timestamp(new Date().getTime());
	  userTimeline = new UserTimeline().withId("usr" + pl.getPledgedBy().getId().toString())
			  .withObjectId("plg" + pl.getId().toString())
			  .withOperationType("Update")
			  .withTimestamp(ts);
	  beneficiaryTimeline = new BeneficiaryTimeline().withId("ben" + pl.getBeneficiary().getId().toString())
			  .withOperationType("plg" + pl.getId())
			  .withTimestamp(ts);
	  sendMessageAsynch(userTimeline);
  }

  protected void sendMessageAsynch(UserTimeline userTimeline) {
	  Thread th = new Thread(new SenderRunnable(pms, userTimeline));

	  th.start();
  }
  
  protected void sendMessageAsynch(BeneficiaryTimeline beneficiaryTimeline) {
	  Thread th = new Thread(new SenderRunnable(pms, beneficiaryTimeline));

	  th.start();
  }

  private static class SenderRunnable implements Runnable {

	  private PledgeMessageSender sender;
	  private UserTimeline userTimeline;
	  private BeneficiaryTimeline beneficiaryTimeline;
	  
	  public SenderRunnable(final PledgeMessageSender sender, UserTimeline ut) {
		  this.sender = sender;
		  this.userTimeline = ut;
	  }
	  
	  public SenderRunnable(final PledgeMessageSender sender, BeneficiaryTimeline bt) {
		  this.sender = sender;
		  this.beneficiaryTimeline = bt;
	  }
	  
	  @Override
	  public void run() {
		  System.out.println("I am running in thread: " +  Thread.currentThread().getName());
		  sender.sendMessage(userTimeline);
		  sender.sendMessage(beneficiaryTimeline);

	  }

  }
}
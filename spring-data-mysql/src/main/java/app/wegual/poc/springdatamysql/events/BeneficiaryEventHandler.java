package app.wegual.poc.springdatamysql.events;

import java.util.Date;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.wegual.poc.common.model.Beneficiary;
import app.wegual.poc.common.model.BeneficiaryTimeline;
import app.wegual.poc.springdatamysql.messaging.BeneficiaryMessageSender;


@RepositoryEventHandler(Beneficiary.class)
public class BeneficiaryEventHandler {

  @Autowired
  BeneficiaryMessageSender bms;
  
  BeneficiaryTimeline beneficiaryTimeline;
  
  @HandleAfterCreate
  public void handleBeneficiaryCreate(Beneficiary ben) {
	  // … you can now deal with Person in a type-safe way
	  System.out.println("Beneficiary create called");
	  Timestamp ts = new Timestamp(new Date().getTime());
	  beneficiaryTimeline = new BeneficiaryTimeline().withId("ben" + ben.getId().toString())
			  .withOperationType("Create")
			  .withTimestamp(ts);
	  sendMessageAsynch(beneficiaryTimeline);
  }

  @HandleAfterSave
  public void handleBeneficiarySave(Beneficiary ben) {
	  System.out.println("Beneficiary save called");
	  Timestamp ts = new Timestamp(new Date().getTime());
	  beneficiaryTimeline = new BeneficiaryTimeline().withId("ben" + ben.getId().toString())
			  .withOperationType("Update")
			  .withTimestamp(ts);
	  sendMessageAsynch(beneficiaryTimeline);
  }

  @HandleAfterDelete
  public void handleBeneficiaryDelete(Beneficiary ben) {

	  System.out.println("Beneficiary delete called");
	  Timestamp ts = new Timestamp(new Date().getTime());
	  beneficiaryTimeline = new BeneficiaryTimeline().withId("ben" + ben.getId().toString())
			  .withOperationType("Delete")
			  .withTimestamp(ts);
	  sendMessageAsynch(beneficiaryTimeline);
  }

  protected void sendMessageAsynch(BeneficiaryTimeline payload) {
	  Thread th = new Thread(new SenderRunnable(bms, payload));
	  th.start();

  }

  private static class SenderRunnable implements Runnable {

	  private BeneficiaryMessageSender sender;

	  private BeneficiaryTimeline benTimeline;
	  public SenderRunnable(final BeneficiaryMessageSender sender, BeneficiaryTimeline benTimeline) {
		  this.sender = sender;
		  this.benTimeline = benTimeline;
	  }

	  @Override
	  public void run() {
		  System.out.println("I am running in thread: " +  Thread.currentThread().getName());
		  sender.sendMessage(benTimeline);
	  }

  }

}
package app.wegual.poc.springdatamysql.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.wegual.poc.common.model.Beneficiary;
import app.wegual.poc.springdatamysql.messaging.BeneficiaryMessageSender;

@RepositoryEventHandler(Beneficiary.class)
public class BeneficiaryEventHandler {

  @Autowired
  BeneficiaryMessageSender bms;
  
  @HandleAfterCreate
  public void handleBeneficiaryCreate(Beneficiary ben) {
    // … you can now deal with Person in a type-safe way
	  System.out.println("Beneficiary create called");
	  sendMessageAsynch(ben);
  }

	@HandleAfterSave
	public void handleBeneficiarySave(Beneficiary pl) {
		System.out.println("Beneficiary save called");
		sendMessageAsynch(pl);
	}

	@HandleAfterDelete
	public void handleBeneficiaryDelete(Beneficiary pl) {

		System.out.println("Beneficiary delete called");
		sendMessageAsynch(pl);
	}
	
	protected void sendMessageAsynch(Beneficiary pl) {
		Thread th = new Thread(new SenderRunnable(bms, pl));
		
		th.start();
	}
	
	private static class SenderRunnable implements Runnable {

		private BeneficiaryMessageSender sender;
		private Beneficiary ben;
		public SenderRunnable(final BeneficiaryMessageSender sender, Beneficiary ben) {
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
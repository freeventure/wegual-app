package app.wegual.poc.springdatamysql.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.wegual.poc.common.model.Pledge;
import app.wegual.poc.springdatamysql.messaging.PledgeMessageSender;

@RepositoryEventHandler(Pledge.class)
public class PledgeEventHandler {

  @Autowired
  PledgeMessageSender pms;
  
  @HandleAfterCreate
  public void handlePledgeCreate(Pledge pl) {
    // … you can now deal with Person in a type-safe way
	  System.out.println("Pledge create called");
	  sendMessageAsynch(pl);
  }

	@HandleAfterSave
	public void handlePledgeSave(Pledge pl) {
		System.out.println("Pledge save called");
		sendMessageAsynch(pl);
	}

	@HandleAfterDelete
	public void handlePledgeDelete(Pledge pl) {

		System.out.println("Pledge delete called");
		sendMessageAsynch(pl);
	}
	
	protected void sendMessageAsynch(Pledge pl) {
		Thread th = new Thread(new SenderRunnable(pms, pl));
		
		th.start();
	}
	
	private static class SenderRunnable implements Runnable {

		private PledgeMessageSender sender;
		private Pledge pledge;
		public SenderRunnable(final PledgeMessageSender sender, Pledge pl) {
			this.sender = sender;
			this.pledge = pl;
		}
		@Override
		public void run() {
			System.out.println("I am running in thread: " +  Thread.currentThread().getName());
			sender.sendMessage(pledge);
			
		}
		
	}
}
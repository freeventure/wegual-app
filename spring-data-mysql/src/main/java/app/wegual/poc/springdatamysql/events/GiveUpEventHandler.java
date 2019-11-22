package app.wegual.poc.springdatamysql.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.wegual.poc.common.model.GiveUp;

import app.wegual.poc.springdatamysql.messaging.GiveUpMessageSender;

@RepositoryEventHandler(GiveUp.class)
public class GiveUpEventHandler {

  @Autowired
  GiveUpMessageSender gms;
  
  @HandleAfterCreate
  public void handleGiveUpCreate(GiveUp gu) {
	  System.out.println("GiveUp create called");
	  sendMessageAsynch(gu);
  }

	@HandleAfterSave
	public void handleGiveUpSave(GiveUp gu) {
		System.out.println("GiveUp save called");
		sendMessageAsynch(gu);
	}

	@HandleAfterDelete
	public void handleGiveUpDelete(GiveUp gu) {

		System.out.println("GiveUp delete called");
		sendMessageAsynch(gu);
	}
	
	protected void sendMessageAsynch(GiveUp gu) {
		Thread th = new Thread(new SenderRunnable(gms, gu));
		
		th.start();
	}
	
	private static class SenderRunnable implements Runnable {

		private GiveUpMessageSender sender;
		private GiveUp gu;
		public SenderRunnable(final GiveUpMessageSender sender, GiveUp gu) {
			this.sender = sender;
			this.gu = gu;
		}
		@Override
		public void run() {
			System.out.println("I am running in thread: " +  Thread.currentThread().getName());
			sender.sendMessage(gu);
			
		}
		
	}
}

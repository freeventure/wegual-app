package app.wegual.poc.springdatamysql.events;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.wegual.poc.common.model.GiveUpFollower;
import app.wegual.poc.common.model.UserTimeline;
import app.wegual.poc.springdatamysql.messaging.GiveUpFollowerMessageSender;

@RepositoryEventHandler(GiveUpFollower.class)
public class GiveUpFollowerEventHandler {

  @Autowired
  GiveUpFollowerMessageSender gms;
  
  UserTimeline userTimeline;
  
  @HandleAfterCreate
  public void handleGiveUpFollowerCreate(GiveUpFollower guf) {
	  System.out.println("GiveUp Follower create called");
	  Timestamp ts = new Timestamp(new Date().getTime());
	  userTimeline = new UserTimeline().withId("usr" + guf.getFollower().getId().toString())
			  .withObjectId("gup" + guf.getId().toString())
			  .withOperationType("Follow")
			  .withTimestamp(ts);
	  sendMessageAsynch(userTimeline);
  }

	@HandleAfterDelete
	public void handleGiveUpFollowerDelete(GiveUpFollower guf) {

		System.out.println("GiveUp Follower delete called");
		Timestamp ts = new Timestamp(new Date().getTime());
		userTimeline = new UserTimeline().withId("usr" + userTimeline.getUserId().toString())
				  .withObjectId("gup" + guf.getId().toString())
				  .withOperationType("Unfollow")
				  .withTimestamp(ts);
		sendMessageAsynch(userTimeline);
	}
	
	protected void sendMessageAsynch(UserTimeline user) {
		Thread th = new Thread(new SenderRunnable(gms, user));
		
		th.start();
	}
	
	private static class SenderRunnable implements Runnable {

		private GiveUpFollowerMessageSender sender;
		private UserTimeline user;
		public SenderRunnable(final GiveUpFollowerMessageSender sender, UserTimeline user) {
			this.sender = sender;
			this.user = user;
		}
		@Override
		public void run() {
			System.out.println("I am running in thread: " +  Thread.currentThread().getName());
			sender.sendMessage(user);
			
		}
		
	}
}

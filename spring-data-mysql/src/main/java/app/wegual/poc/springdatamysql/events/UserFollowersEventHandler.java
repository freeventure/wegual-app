package app.wegual.poc.springdatamysql.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.wegual.poc.common.model.UserFollowers;
import app.wegual.poc.springdatamysql.messaging.UserFollowersMessageSender;

@RepositoryEventHandler(UserFollowers.class)
public class UserFollowersEventHandler {
	
	@Autowired
	  UserFollowersMessageSender ufms;
	  
	  @HandleAfterCreate
	  public void handleUserFollowersCreate(UserFollowers user) {
	    // … you can now deal with Person in a type-safe way
		  System.out.println("UserFollowers create called");
		  sendMessageAsynch(user);
	  }
	  
	  @HandleAfterDelete
	  public void handleUserFollowersDelete(UserFollowers pl) {

		  System.out.println("User unfollow called");
		  sendMessageAsynch(pl);
	  }
		
	  protected void sendMessageAsynch(UserFollowers pl) {
		  Thread th = new Thread(new SenderRunnable(ufms, pl));
			
		  th.start();
		}
		
	  private static class SenderRunnable implements Runnable {

		  private UserFollowersMessageSender sender;
		  private UserFollowers user;
		  public SenderRunnable(final UserFollowersMessageSender sender, UserFollowers user) {
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
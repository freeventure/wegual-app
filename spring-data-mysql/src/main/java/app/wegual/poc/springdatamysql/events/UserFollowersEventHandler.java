package app.wegual.poc.springdatamysql.events;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.wegual.poc.common.model.UserFollowers;
import app.wegual.poc.common.model.UserTimeline;

import app.wegual.poc.springdatamysql.messaging.UserFollowersMessageSender;

@RepositoryEventHandler(UserFollowers.class)
public class UserFollowersEventHandler {

	@Autowired
	UserFollowersMessageSender ums;

	UserTimeline userTimeline;

	@HandleAfterCreate
	public void handleUserFollowersCreate(UserFollowers user) {
		// … you can now deal with Person in a type-safe way
		System.out.println("User follower create called");
		Timestamp ts = new Timestamp(new Date().getTime());
		userTimeline = new UserTimeline().withId("usr"+user.getFollower().getId().toString())
				.withObjectId("usr"+user.getFollowee().getId().toString())
				.withOperationType("Follow")
				.withTimestamp(ts);
		sendMessageAsynch(userTimeline);
	}

	@HandleAfterDelete
	public void handleUserDelete(UserFollowers user) {

		System.out.println("User delete called");
		Timestamp ts = new Timestamp(new Date().getTime());
		userTimeline = new UserTimeline().withId("usr"+user.getFollower().getId().toString())
				.withObjectId("usr"+user.getFollowee().getId().toString())
				.withOperationType("Unfollow")
				.withTimestamp(ts);
		sendMessageAsynch(userTimeline);
	}

	protected void sendMessageAsynch(UserTimeline payload) {
		Thread th = new Thread(new SenderRunnable(ums, payload));
		th.start();

	}

	private static class SenderRunnable implements Runnable {

		private UserFollowersMessageSender sender;

		private UserTimeline userTimeline;
		public SenderRunnable(final UserFollowersMessageSender sender, UserTimeline userTimeline) {
			this.sender = sender;
			this.userTimeline = userTimeline;
		}

		@Override
		public void run() {
			System.out.println("I am running in thread: " +  Thread.currentThread().getName());
			sender.sendMessage(userTimeline);
		}
	}
}
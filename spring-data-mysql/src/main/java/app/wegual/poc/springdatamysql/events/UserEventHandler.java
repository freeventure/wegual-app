package app.wegual.poc.springdatamysql.events;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.wegual.poc.common.model.UserTimeline;
import app.wegual.poc.common.model.User;
import app.wegual.poc.springdatamysql.messaging.UserMessageSender;

@RepositoryEventHandler(User.class)
public class UserEventHandler {
	
	@Autowired
	UserMessageSender ums;

	UserTimeline userTimeline;

	@HandleAfterCreate
	public void handleUserCreate(User user) {
		// … you can now deal with Person in a type-safe way
		System.out.println("User create called");
		Timestamp ts = new Timestamp(new Date().getTime());
		userTimeline = new UserTimeline().withId("usr" + user.getId().toString())
				.withOperationType("Create")
				.withTimestamp(ts);
		sendMessageAsynch(userTimeline);
	}

	@HandleAfterSave
	public void handleBeneficiarySave(User user) {
		System.out.println("User save called");
		Timestamp ts = new Timestamp(new Date().getTime());
		userTimeline = new UserTimeline().withId("usr" + user.getId().toString())
				.withOperationType("Update")
				.withTimestamp(ts);
		sendMessageAsynch(userTimeline);
	}

	@HandleAfterDelete
	public void handleUserDelete(User user) {

		System.out.println("User delete called");
		Timestamp ts = new Timestamp(new Date().getTime());
		userTimeline = new UserTimeline().withId("usr" + user.getId().toString())
				.withOperationType("Delete")
				.withTimestamp(ts);
		sendMessageAsynch(userTimeline);
	}

	protected void sendMessageAsynch(UserTimeline payload) {
		Thread th = new Thread(new SenderRunnable(ums, payload));
		th.start();

	}

	private static class SenderRunnable implements Runnable {

		private UserMessageSender sender;

		private UserTimeline userTimeline;
		public SenderRunnable(final UserMessageSender sender, UserTimeline userTimeline) {
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

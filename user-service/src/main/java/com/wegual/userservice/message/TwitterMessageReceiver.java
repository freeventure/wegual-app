package com.wegual.userservice.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wegual.userservice.twitter.TwitterService;

import app.wegual.common.util.MessagingConstants;

@Service
public class TwitterMessageReceiver {
	
	@Autowired
	private TwitterService ts;
	
	@RabbitListener(queues = MessagingConstants.TWITTER_ACTION_QUEUE_NAME)
	public void receiveTwitterActionMessage(String userId) {
		System.out.println("Fetch Twitter Feed message received");
		ts.ingestTweetsFromTwitter(userId);
		return;
	}
	

}

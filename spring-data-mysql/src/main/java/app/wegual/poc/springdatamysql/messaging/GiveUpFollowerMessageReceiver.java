package app.wegual.poc.springdatamysql.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.poc.common.model.UserTimeline;
import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.springdatamysql.UserTimelineRepository;

@Service
public class GiveUpFollowerMessageReceiver {
	@Autowired
	private UserTimelineRepository u;
	
	@RabbitListener(queues = MessagingConstants.queueNameUserTimeline)
	public void receiveMessageForGiveUpFollower(UserTimeline userTimeline) {
		System.out.print(userTimeline.toString());
		UserTimeline bt = u.save(userTimeline);
		System.out.println(bt.getUserId());
	}

	
}

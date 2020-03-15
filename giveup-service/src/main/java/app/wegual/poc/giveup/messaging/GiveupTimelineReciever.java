package app.wegual.poc.giveup.messaging;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.poc.common.model.Timeline;
import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.giveup.service.GiveupTimelineService;

@Service
public class GiveupTimelineReciever {
	@Autowired
	private GiveupTimelineService ts;
	
	@RabbitListener(queues = MessagingConstants.queueNameGiveUpTimeline)
	public void receiveMessageForGiveUpTimeline(Timeline timeline) throws IOException {
		System.out.println("received at giveup timeline");
		ts.save(timeline);
	}
}

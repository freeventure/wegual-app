package app.wegual.poc.es.messaging;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.es.model.Timeline;
import app.wegual.poc.es.service.GiveUpTimelineService;

@Service
public class GiveUpTimelineReciever {
	@Autowired
	private GiveUpTimelineService ts;
	
	@RabbitListener(queues = MessagingConstants.queueNameGiveUpTimeline)
	public void receiveMessageForGiveUpTimeline(Timeline timeline) throws IOException {
		System.out.println("received at giveup timeline");
		ts.save(timeline);
	}
}

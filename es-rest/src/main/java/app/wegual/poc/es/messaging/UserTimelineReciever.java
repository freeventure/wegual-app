package app.wegual.poc.es.messaging;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.es.model.Timeline;
import app.wegual.poc.es.service.UserTimelineService;

@Service
public class UserTimelineReciever {
	
	@Autowired
	private UserTimelineService ts;
	
	@RabbitListener(queues = MessagingConstants.queueNameUserTimeline)
	public void receiveMessageForUserTimeline(Timeline timeline) throws IOException {
		//System.out.print(benTimeline.toString());
		//benTimeline.setBeneficiaryId("ben"+benTimeline.getBeneficiaryId());
		System.out.println("received");
		ts.save(timeline);
	}
}

package app.wegual.poc.es.messaging;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.es.model.Timeline;
import app.wegual.poc.es.service.BeneficiaryTimelineService;

@Service
public class BeneficiaryTimelineReciever {
	@Autowired
	private BeneficiaryTimelineService ts;
	
	@RabbitListener(queues = MessagingConstants.queueNameBeneficiaryTimeline)
	public void receiveMessageForBeneficiaryTimeline(Timeline timeline) throws IOException {
		System.out.println("received");
		ts.save(timeline);
	}
}

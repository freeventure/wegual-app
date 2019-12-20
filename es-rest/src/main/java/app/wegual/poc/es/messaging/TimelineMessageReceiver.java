package app.wegual.poc.es.messaging;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.poc.es.model.Timeline;
import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.es.service.TimelineService;

@Service
public class TimelineMessageReceiver {
	
	@Autowired
	private TimelineService bts;
	
	@RabbitListener(queues = MessagingConstants.queueNameTimeline)
	public void receiveMessageForBeneficiaryTimeline(Timeline benTimeline) throws IOException {
		//System.out.print(benTimeline.toString());
		//benTimeline.setBeneficiaryId("ben"+benTimeline.getBeneficiaryId());
		System.out.println("received");
		bts.save(benTimeline);
	} 
}

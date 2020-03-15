package app.wegual.poc.beneficiary.messaging;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.poc.beneficiary.service.BeneficiaryTimelineService;
import app.wegual.poc.common.model.Timeline;
import app.wegual.poc.common.util.MessagingConstants;

@Service
public class BeneficiaryTimelineReceiver {
	
	@Autowired
	private BeneficiaryTimelineService ts;
	
	@RabbitListener(queues = MessagingConstants.queueNameBeneficiaryTimeline)
	public void receiveMessageForBeneficiaryTimeline(Timeline timeline) throws IOException {
		System.out.println("received");
		ts.save(timeline);
	}

}

package app.wegual.poc.springdatamysql.messaging;



import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.poc.common.model.Beneficiary;
import app.wegual.poc.common.model.BeneficiaryTimeline;
import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.springdatamysql.BeneficaryTimelineRepository;

@Service
public class BeneficiaryMessageReceiver {
	
	@Autowired
	private BeneficaryTimelineRepository b;
	
	@RabbitListener(queues = MessagingConstants.queueNameBeneficiaryTimeline)
	public void receiveMessageForBeneficiary(BeneficiaryTimeline benTimeline) {
		System.out.print(benTimeline.toString());
		//benTimeline.setBeneficiaryId("ben"+benTimeline.getBeneficiaryId());
		BeneficiaryTimeline bt = b.save(benTimeline);
		System.out.println(bt.getBeneficiaryId());
	}

}

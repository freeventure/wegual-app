package app.wegual.poc.springdatamysql.messaging;



import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import app.wegual.poc.common.model.Beneficiary;
import app.wegual.poc.common.model.BeneficiaryTimeline;
import app.wegual.poc.common.util.MessagingConstants;

@Service
public class BeneficiaryMessageReceiver {
	
	@RabbitListener(queues = MessagingConstants.queueNameBeneficiaryTimeline)
	public void receiveMessageForBeneficiary(BeneficiaryTimeline benTimeline) {
		System.out.println("abc");
		System.out.print(benTimeline.toString());
	}

}

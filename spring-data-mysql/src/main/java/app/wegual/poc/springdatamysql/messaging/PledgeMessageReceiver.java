package app.wegual.poc.springdatamysql.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.poc.common.model.BeneficiaryTimeline;
import app.wegual.poc.common.model.UserTimeline;
import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.springdatamysql.BeneficaryTimelineRepository;
import app.wegual.poc.springdatamysql.UserTimelineRepository;

@Service
public class PledgeMessageReceiver {
	@Autowired
	private UserTimelineRepository u;
	private BeneficaryTimelineRepository b;
	
	@RabbitListener(queues = MessagingConstants.queueNameUserTimeline)
	public void receiveMessageForUser(UserTimeline userTimeline) {
		System.out.print(userTimeline.toString());
		UserTimeline bt = u.save(userTimeline);
		System.out.println(bt.getUserId());
	}
	
	@RabbitListener(queues = MessagingConstants.queueNameBeneficiaryTimeline)
	public void receiveMessageForBeneficiary(BeneficiaryTimeline beneficiaryTimeline) {
		System.out.print(beneficiaryTimeline.toString());
		BeneficiaryTimeline bt = b.save(beneficiaryTimeline);
		System.out.println(bt.getBeneficiaryId());
	}
}

package com.wegual.beneficiaryservice.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.beneficiaryservice.es.actions.BeneficiaryFollowService;

import app.wegual.common.model.UserActionItem;
import app.wegual.common.model.UserActionType;
import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BeneficiaryMessageReceiver {
	
	@Autowired
	private BeneficiaryFollowService bfs;

	@RabbitListener(queues = MessagingConstants.BENEFICIARY_ACTION_QUEUE_NAME)
	public void receiveMessage(UserActionItem uai) {
		log.info("ES Received user action message to follow beneficiary");
		if(UserActionType.FOLLOW_BENEFICIARY.equals(uai.getActionType())) {
			bfs.followBeneficiary(uai);
		}
		if(UserActionType.UNFOLLOW_BENEFICIARY.equals(uai.getActionType())) {
			bfs.unfollowBeneficiary(uai);
		}
	}
}

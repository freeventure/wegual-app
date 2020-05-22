package com.wegual.giveupservice.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.giveupservice.es.actions.GiveUpLikeService;

import app.wegual.common.model.UserActionItem;
import app.wegual.common.model.UserActionType;
import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GiveUpLiked {

	@Autowired
	private GiveUpLikeService guls;
	
	@RabbitListener(queues = MessagingConstants.GIVEUP_ACTION_QUEUE_NAME)
	public void receiveObjectMessage(UserActionItem uat) {
		log.info("ES Received GiveUp user action message");
		if(UserActionType.LIKE.equals(uat.getActionType())) {
			guls.likeGiveUp(uat);
		}
		if(UserActionType.UNLIKE.equals(uat.getActionType())){
			guls.unlikeGiveUp(uat);
		}
	}
}

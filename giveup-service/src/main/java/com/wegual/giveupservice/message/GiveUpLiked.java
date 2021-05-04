package com.wegual.giveupservice.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.giveupservice.es.actions.GiveUpLikeService;
import com.wegual.giveupservice.service.GiveUpFollowService;

import app.wegual.common.model.UserActionItem;
import app.wegual.common.model.UserActionType;
import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GiveUpLiked {

	@Autowired
	private GiveUpLikeService guls;
	
	@Autowired
	private GiveUpFollowService gufs;
	
	@RabbitListener(queues = MessagingConstants.GIVEUP_ACTION_QUEUE_NAME)
	public void receiveObjectMessage(UserActionItem uai) {
		log.info("ES Received GiveUp user action message");
		if(UserActionType.LIKE.equals(uai.getActionType())) {
			guls.likeGiveUp(uai);
		}
		if(UserActionType.UNLIKE.equals(uai.getActionType())){
			guls.unlikeGiveUp(uai);
		}
		if(UserActionType.FOLLOW_GIVEUP.equals(uai.getActionType())){
			gufs.followGiveUp(uai);
		}
		if(UserActionType.UNFOLLOW_GIVEUP.equals(uai.getActionType())){
			gufs.unfollowGiveup(uai);
		}
	}
}

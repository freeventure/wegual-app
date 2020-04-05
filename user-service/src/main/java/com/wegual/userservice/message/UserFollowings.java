package com.wegual.userservice.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.userservice.es.actions.UserFollowActions;

import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserFollowings {
	@Autowired
	UserFollowActions actions;

	@RabbitListener(queues = "user-followings")
    public void receiveObjectMessage(UserTimelineItem uti) {
        log.info("ES Received user action message");
        
        if(UserActionType.FOLLOW.equals(uti.getUserActionType()))
        {
        	// add to ES index
        	actions.userFollowed(uti);
        }

        if(UserActionType.UNFOLLOW.equals(uti.getUserActionType()))
        {
        	// remove from ES index
        	actions.userUnfollowed(uti);
        }
    }
}

package com.wegual.userservice.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.userservice.es.actions.UserActions;

import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserActionMessages {
	@Autowired
	UserActions actions;

	@RabbitListener(queues = "user-actions")
    public void receiveObjectMessage(UserTimelineItem uti) {
        log.info("ES Received user action message");
        
        if(UserActionType.UPDATE.equals(uti.getUserActionType()))
        {
        	if(uti.getActionObject() != null &&
        			UserActionTargetType.IMAGE.equals(uti.getActionObject().getActionType()))
        	// update the user link to profile image in the ES index
        	actions.userUpdateProfileImage(uti);
        }
    }
}

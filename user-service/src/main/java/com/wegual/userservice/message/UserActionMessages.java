package com.wegual.userservice.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.MessagingMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.userservice.es.actions.UserActions;

import app.wegual.common.model.UserActionItem;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserActionMessages {
	@Autowired
	UserActions actions;
	
//	@RabbitListener(queues = "user-actions")
//    public void receiveObjectMessage(UserTimelineItem uti) {
//        log.info("ES Received user action message");
//        
//        if(UserActionType.UPDATE.equals(uti.getUserActionType()))
//        {
//        	if(uti.getActionObject() != null &&
//        			UserActionTargetType.IMAGE.equals(uti.getActionObject().getActionType()))
//        	// update the user link to profile image in the ES index
//        	actions.userUpdateProfileImage(uti);
//        }
//    }
	
	@RabbitListener(queues = "user-actions")
    public void receiveObjectMessages(Message message) {
        log.info("Received user action message");
        MessageConverter mc = new SimpleMessageConverter();
        Object obj = mc.fromMessage(message);
        if(obj instanceof UserTimelineItem) {
        	UserTimelineItem uti = (UserTimelineItem) obj;
        	if(UserActionType.UPDATE.equals(uti.getUserActionType()))
        	{
        		if(uti.getActionObject() != null &&
        				UserActionTargetType.IMAGE.equals(uti.getActionObject().getActionType()))
        			// update the user link to profile image in the ES index
        			actions.userUpdateProfileImage(uti);
        	}
        }
        if(obj instanceof UserActionItem) {
        	UserActionItem uat = (UserActionItem) obj;
        	if(UserActionType.LIKE_POST.equals(uat.getActionType())){
        		actions.likePost(uat);
        	}
        	if(UserActionType.UNLIKE_POST.equals(uat.getActionType())){
        		actions.unlikePost(uat);
        	}
        	if(UserActionType.VIEW_POST.equals(uat.getActionType())){
        		actions.viewPost(uat);
        	}
        }
    }
}

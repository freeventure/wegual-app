//package com.wegual.giveupservice.message;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.wegual.giveupservice.es.actions.GiveUpFollowESActions;
//
//import app.wegual.common.model.UserActionType;
//
//@Component
//public class GiveUpFollowed {
//	@Autowired
//	GiveUpFollowESActions actions;
//
//	@RabbitListener(queues = "giveup-actions")
//    public void receiveObjectMessage(GiveUpUserAction bua) {
//        System.out.println("ES Received GiveUp user action message");
//        
//        if(UserActionType.FOLLOW.equals(bua.getUserActionType()))
//        {
//        	// add to ES index
//        	actions.userFollowed(bua);
//        }
//
//        if(UserActionType.UNFOLLOW.equals(bua.getUserActionType()))
//        {
//        	// remove from ES index
//        	actions.userUnfollowed(bua);
//        }
//    }
//}

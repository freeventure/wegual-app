//package com.wegual.beneficiaryservice.message;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.wegual.beneficiaryservice.es.actions.BeneficiaryFollowESActions;
//
//import app.wegual.common.model.UserActionType;
//
//@Component
//public class BeneficiaryFollowed {
//	@Autowired
//	BeneficiaryFollowESActions actions;
//
//	@RabbitListener(queues = "beneficiary-actions")
//    public void receiveObjectMessage(BeneficiaryUserAction bua) {
//        System.out.println("ES Received beneficiary user action message");
//        
//        if(UserActionType.FOLLOW_BENEFICIARY.equals(bua.getUserActionType()))
//        {
//        	// add to ES index
//        	actions.userFollowed(bua);
//        }
//
//        if(UserActionType.UNFOLLOW_BENEFICIARY.equals(bua.getUserActionType()))
//        {
//        	// remove from ES index
//        	actions.userUnfollowed(bua);
//        }
//    }
//}

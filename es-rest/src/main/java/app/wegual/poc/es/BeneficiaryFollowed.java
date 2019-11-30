package app.wegual.poc.es;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.message.BeneficiaryUserAction;
import app.wegual.poc.common.model.UserActionType;
import app.wegual.poc.es.command.BeneficiaryFollowESActions;

@Component
public class BeneficiaryFollowed {
	@Autowired
	BeneficiaryFollowESActions actions;

	@RabbitListener(queues = "beneficiary-actions")
    public void receiveObjectMessage(BeneficiaryUserAction bua) {
        System.out.println("ES Received beneficiary user action message");
        
        if(UserActionType.FOLLOW.equals(bua.getUserActionType()))
        {
        	// add to ES index
        	actions.userFollowed(bua);
        }

        if(UserActionType.UNFOLLOW.equals(bua.getUserActionType()))
        {
        	// remove from ES index
        	actions.userUnfollowed(bua);
        }
    }
}

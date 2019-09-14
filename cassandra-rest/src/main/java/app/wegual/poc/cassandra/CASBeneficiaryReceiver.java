package app.wegual.poc.cassandra;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.Beneficiary;

@Component
public class CASBeneficiaryReceiver {


    @RabbitListener(queues = "spring-cas-beneficiaries")
    public void receiveObjectMessage(Beneficiary ben) {
        System.out.println("Cassandra Received beneficiary message");
        System.out.println("Name: " + ben.getName() + " desc: " + ben.getDescription());
    }
}

package app.wegual.poc.es;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.Beneficiary;

@Component
public class ESBeneficiaryReceiver {


    @RabbitListener(queues = "spring-es-beneficiaries")
    public void receiveObjectMessage(Beneficiary ben) {
        System.out.println("ES Received beneficiary message");
        System.out.println("Name: " + ben.getName() + " desc: " + ben.getDescription());
    }
}

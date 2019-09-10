package app.wegual.poc.cassandra;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.Pledge;

@Component
public class CASPledgeReceiver {


    @RabbitListener(queues = "spring-cas-pledges")
    public void receiveObjectMessage(Pledge pledge) {
        System.out.println("Cassandra Received pledge message");
        System.out.println("amout: " + pledge.getAmount() + " currency: " + pledge.getCurrency());
    }
}

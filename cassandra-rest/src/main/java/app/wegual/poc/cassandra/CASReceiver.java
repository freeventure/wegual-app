package app.wegual.poc.cassandra;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.ObjectCreate;

@Component
public class CASReceiver {


    @RabbitListener(queues = "spring-cas")
    public void receiveObjectMessage(ObjectCreate oc) {
        System.out.println("Cassandra Received object create message");
        System.out.println("name: " + oc.getName() + " creator: " + oc.getCreator());
    }
}

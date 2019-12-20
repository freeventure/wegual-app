//package app.wegual.poc.es;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//
//import app.wegual.poc.common.model.ObjectCreate;
//
//
//public class ESReceiver {
//
//    @RabbitListener(queues = "spring-es")
//    public void receiveObjectMessage(ObjectCreate oc) {
//        System.out.println("ES Received object create message");
//        System.out.println("name: " + oc.getName() + " creator: " + oc.getCreator());
//    }
//    
//}

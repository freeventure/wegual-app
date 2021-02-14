package com.wegual.userservice.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.userservice.es.actions.UserFeedActions;

import app.wegual.common.model.PledgeFeedItem;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserFeedMessages {

	@Autowired
	UserFeedActions actions;


	@RabbitListener(queues = "user-feed")
	public void receiveObjectMessage(Message message) {
		MessageConverter mc =  new SimpleMessageConverter();
		Object obj = mc.fromMessage(message);
		if(obj instanceof PledgeFeedItem) {
			log.info("ES Timeline Received user action message at user-timeline inside user-service");
			actions.userFeedGenericEvent((PledgeFeedItem)obj);
		}
	}
}
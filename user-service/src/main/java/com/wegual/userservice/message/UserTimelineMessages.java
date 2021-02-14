package com.wegual.userservice.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.userservice.es.actions.UserTimelineActions;

import app.wegual.common.model.UserTimelineItem;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserTimelineMessages {

	@Autowired
	private UserTimelineActions actions;

	@RabbitListener(queues = "user-timeline")
	public void receiveObjectMessage(Message message) {
		MessageConverter mc = new SimpleMessageConverter();
		log.info("ES Timeline Received user action message at user-timeline inside user-service");
		Object obj = mc.fromMessage(message);
		if(obj instanceof UserTimelineItem) {
			System.out.println("ES Timeline Received user action message at user-timeline inside user-service");
			actions.userTimelineGenericEvent((UserTimelineItem)obj);
		}
	}
}

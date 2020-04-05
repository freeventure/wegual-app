package com.wegual.userservice.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.userservice.es.actions.UserTimelineActions;

import app.wegual.common.model.UserTimelineItem;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserTimelineMessages {
	@Autowired
	UserTimelineActions actions;

	@RabbitListener(queues = "user-timeline")
    public void receiveObjectMessage(UserTimelineItem uti) {
        log.info("ES Timeline Received user action message");
        actions.userTimelineGenericEvent(uti);
    }
}

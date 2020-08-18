package com.wegual.giveupservice.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.giveupservice.es.actions.GiveUpTimelineActions;

import app.wegual.common.model.GiveUpTimelineItem;
import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GiveUpTimelineMessageReceiver {
	
	@Autowired
	private GiveUpTimelineActions gta;
	
	@RabbitListener(queues = MessagingConstants.GIVEUP_TIMELINE_QUEUE_NAME)
	public void receiveMessage(GiveUpTimelineItem gti) {
		log.info("ES Received timeline message for giveup");
		gta.giveUpTimelineGenericEvent(gti);
	}
}

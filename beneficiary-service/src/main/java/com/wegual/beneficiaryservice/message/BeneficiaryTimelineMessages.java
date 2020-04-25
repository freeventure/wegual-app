package com.wegual.beneficiaryservice.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.beneficiaryservice.es.actions.BeneficiaryTimelineActions;

import app.wegual.common.model.BeneficiaryTimelineItem;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BeneficiaryTimelineMessages {
	@Autowired
	BeneficiaryTimelineActions actions;

	@RabbitListener(queues = "beneficiary-timeline")
    public void receiveObjectMessage(BeneficiaryTimelineItem bti) {
        log.info("ES Timeline Received beneficiary action message");
        actions.beneficiaryTimelineGenericEvent(bti);
    }
}

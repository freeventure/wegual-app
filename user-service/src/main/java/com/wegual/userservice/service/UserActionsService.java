package com.wegual.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.wegual.userservice.message.UserActionsAsynchMessageSender;

import app.wegual.common.asynch.SenderRunnable;
import app.wegual.common.model.UserTimelineItem;

@Service
public class UserActionsService {

	@Autowired
	@Qualifier("executorMessageSender")
	TaskExecutor te;
	
	@Autowired
	private UserActionsAsynchMessageSender uaam;
	
	public void followUser(String userId, String actorId) {
		
	}
	
	public void unfollowUser(String userId, String actorId) {
		
	}
	
	public void updateProfile() {
		
	}

	protected void sendMessageAsynch(UserTimelineItem uti) {
		te.execute(new SenderRunnable<UserActionsAsynchMessageSender, UserTimelineItem>(uaam, uti));
	}
	
}

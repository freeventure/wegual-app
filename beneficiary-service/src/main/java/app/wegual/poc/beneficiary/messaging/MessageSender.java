package app.wegual.poc.beneficiary.messaging;

import org.springframework.stereotype.Component;

@Component
public interface MessageSender<O>{
	
	public void sendMessage(O message);
	
}
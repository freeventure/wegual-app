package app.wegual.poc.giveup.messaging;

import org.springframework.stereotype.Component;

@Component
public interface MessageSender<O>{
	
	public void sendMessage(O message);
	
}

package app.wegual.poc.giveup.messaging;


public class SenderRunnable<T extends MessageSender<O>, O> implements Runnable{
	
	private T sender;
	private O message;
	
	public SenderRunnable(T sender,O message) {
		this.sender = sender;
		this.message = message;
	}
	
	@Override
	public void run() {
		System.out.println("Currently running thread :" + Thread.currentThread().getName());
		sender.sendMessage(message);
	}
}
package app.wegual.common.asynch;

import java.io.Serializable;

public class SenderRunnable<T extends MessageSender, O extends Serializable> implements Runnable {

	private T sender;
	private O object;
	public SenderRunnable(final T sender, O object) {
		this.sender = sender;
		this.object = object;
	}
	@Override
	public void run() {
		System.out.println("I am running in thread: " +  Thread.currentThread().getName());
		sender.sendMessage(object);
	}
}

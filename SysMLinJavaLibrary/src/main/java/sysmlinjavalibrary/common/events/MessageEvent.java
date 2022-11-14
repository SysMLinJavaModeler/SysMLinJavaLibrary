package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjavalibrary.common.messages.Message;
import sysmlinjavalibrary.common.signals.MessageSignal;

public class MessageEvent extends SysMLSignalEvent
{
	public MessageEvent(Message message)
	{
		super("Message");
		((MessageSignal)signal).message = message;
	}

	public Message getMessage()
	{
		return ((MessageSignal)signal).message;
	}

	@Override
	public void createSignal()
	{
		signal = new MessageSignal(new Message());
	}
}

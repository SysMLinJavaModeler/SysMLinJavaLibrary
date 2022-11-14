package sysmlinjavalibrary.common.signals;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;
import sysmlinjavalibrary.common.messages.Message;

public class MessageSignal extends SysMLSignal
{
	@Attribute
	public Message message;

	public MessageSignal(Message message)
	{
		super();
		this.message = message;
	}

	@Override
	public String stackNamesString()
	{
		return message.stackNamesString();
	}

	@Override
	public String toString()
	{
		return String.format("MessageSignal [message=%s]", message);
	}
}

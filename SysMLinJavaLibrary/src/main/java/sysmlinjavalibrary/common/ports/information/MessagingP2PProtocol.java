package sysmlinjavalibrary.common.ports.information;

import java.util.Optional;
import sysmlinjava.annotations.comments.Hyperlink;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.comments.SysMLHyperlink;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjavalibrary.common.events.MessageEvent;
import sysmlinjavalibrary.common.messages.Message;
import sysmlinjavalibrary.common.signals.MessageSignal;

public class MessagingP2PProtocol extends SysMLFullPort
{	
	public MessagingP2PProtocol(SysMLBlock contextBlock, Long id, String name)
	{
		super(contextBlock, Optional.of(contextBlock), id, name);
	}

	public MessagingP2PProtocol(SysMLBlock contextBlock, Long id)
	{
		super(contextBlock, Optional.of(contextBlock), id);
	}

	@Hyperlink
	public SysMLHyperlink protocolStandard;
	
	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if(signal instanceof MessageSignal)
		{
			MessageSignal messageSignal = (MessageSignal)signal;
			Message c4s2Message = messageSignal.message;
			result = new MessageEvent(c4s2Message);
		}
		else
			logger.severe("unrecognized signal type: " + signal.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
	{
		SysMLSignal result = null;
		if(object instanceof Message)
		{
			Message message = (Message)object;
			result = new MessageSignal(message);
		}
		else
			logger.severe("unrecognized object type: " + object.getClass().getSimpleName());
		return result;
	}

	@Override
	protected void createHyperlinks()
	{
		protocolStandard = new SysMLHyperlink("Interface Requirements Specification for the TBP", "file://IRS for TBP");
	}
}

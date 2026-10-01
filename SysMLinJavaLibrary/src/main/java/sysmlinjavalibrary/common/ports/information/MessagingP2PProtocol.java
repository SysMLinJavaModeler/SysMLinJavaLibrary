package sysmlinjavalibrary.common.ports.information;

import java.util.Optional;
import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.ports.SysMLPort;
import sysmlinjava.states.StateBehaviorContext;
import sysmlinjavalibrary.common.messages.Message;
import sysmlinjavalibrary.common.signals.MessageSignal;

public class MessagingP2PProtocol extends SysMLPort
{	
	public MessagingP2PProtocol(StateBehaviorContext contextBlock, Long id, String name)
	{
		super(contextBlock, Optional.of(contextBlock), id, name);
	}

	public MessagingP2PProtocol(StateBehaviorContext contextBlock, Long id)
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
			result = new SysMLSignalEvent(signal, "MessageEvent", 0L);
		else
			logger.severe("unrecognized signal type: " + signal.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLSignal signalFor(SysMLAnything object)
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
	protected void createSupportingInformationLinks()
	{
		protocolStandard = new SysMLHyperlink("Interface Requirements Specification for the TBP", "file://IRS for TBP");
	}
}

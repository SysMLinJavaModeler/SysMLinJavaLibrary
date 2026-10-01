package sysmlinjavalibrary.common.ports.information;

import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.actions.Action;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.javaannotations.requirements.RequirementReference;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.ports.SysMLPort;
import sysmlinjava.states.StateBehaviorContext;
import sysmlinjavalibrary.common.objects.information.EthernetPacket;
import sysmlinjavalibrary.common.objects.information.IPPacket;
import sysmlinjavalibrary.common.signals.EthernetPacketSignal;

public class EthernetProtocol extends SysMLPort
{
	public EthernetProtocol(StateBehaviorContext contextBlock, SysMLPart eventContextBlock, Long id)
	{
		super(contextBlock, Optional.of(eventContextBlock), id);
	}

	public EthernetProtocol(StateBehaviorContext contextBlock, Long id)
	{
		super(contextBlock, id);
		this.eventContext = Optional.of(this);
	}

	@RequirementReference
	@Hyperlink
	public SysMLHyperlink protocolStandard;

	@Action
	public void onEthernetPacketReceived(EthernetPacket packet)
	{
		receive(packet);
	}

	@Override
	public ScheduledThreadPoolExecutor getExecutionThreads()
	{
		return context.get().getExecutionThreads();
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof EthernetPacketSignal)
			result = new SysMLSignalEvent(signal, "EthernetPacketEvent", 0L);
		else
			logger.warning("unexpected signal type: " + signal.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLAnything clientObjectFor(SysMLAnything object)
	{
		SysMLAnything result = null;
		if (object instanceof EthernetPacket)
		{
			EthernetPacket ethernetPacket = (EthernetPacket)object;
			result = ethernetPacket.frame;
		}
		else
			logger.warning("unexpected signal type: " + object.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLSignal signalFor(SysMLAnything object)
	{
		SysMLSignal result = null;
		if (object instanceof IPPacket)
		{
			IPPacket ipPacket = (IPPacket)object;
			EthernetPacket packet = new EthernetPacket(id, connectedPortsPeers.get(0).id, ipPacket);
			result = new EthernetPacketSignal(packet);
		}
		else
			logger.warning("unexpected object type: " + object.getClass().getSimpleName());
		return result;
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new EthernetProtocolStateMachine(this));
	}

	@Override
	protected void createSupportingInformationLinks()
	{
		protocolStandard = new SysMLHyperlink("IEEE 802.3 Ethernet", "https://en.wikipedia.org/wiki/IEEE_802.3");
	}
}

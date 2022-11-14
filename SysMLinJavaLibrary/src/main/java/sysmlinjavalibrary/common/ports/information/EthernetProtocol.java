package sysmlinjavalibrary.common.ports.information;

import java.util.Optional;
import sysmlinjava.annotations.Reception;
import sysmlinjava.annotations.comments.Hyperlink;
import sysmlinjava.annotations.requirements.RequirementReference;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.comments.SysMLHyperlink;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjavalibrary.common.events.EthernetPacketEvent;
import sysmlinjavalibrary.common.objects.information.EthernetPacket;
import sysmlinjavalibrary.common.objects.information.IPPacket;
import sysmlinjavalibrary.common.signals.EthernetPacketSignal;

public class EthernetProtocol extends SysMLFullPort
{
	public EthernetProtocol(SysMLBlock contextBlock, SysMLBlock eventContextBlock, Long id)
	{
		super(contextBlock, Optional.of(eventContextBlock), id);
	}

	public EthernetProtocol(SysMLBlock contextBlock, Long id)
	{
		super(contextBlock, id);
		this.eventContextBlock = Optional.of(this);
	}

	@RequirementReference
	@Hyperlink
	public SysMLHyperlink protocolStandard;

	@Reception
	public void onEthernetPacketReceived(EthernetPacket packet)
	{
		receive(packet);
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof EthernetPacketSignal)
		{
			EthernetPacketSignal ethernetPacketSignal = (EthernetPacketSignal)signal;
			EthernetPacket ethernetPacket = (EthernetPacket)ethernetPacketSignal.packet;
			result = new EthernetPacketEvent(ethernetPacket);
		}
		else
			logger.warning("unexpected signal type: " + signal.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLClass clientObjectFor(SysMLClass object)
	{
		SysMLClass result = null;
		if (object instanceof EthernetPacket)
		{
			EthernetPacket ethernetPacket = (EthernetPacket)object;
			result = (SysMLClass)ethernetPacket.frame;
		}
		else
			logger.warning("unexpected signal type: " + object.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
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
		this.stateMachine = Optional.of(new EthernetProtocolStateMachine(this));
	}

	@Override
	protected void createHyperlinks()
	{
		protocolStandard = new SysMLHyperlink("IEEE 802.3 Ethernet", "https://en.wikipedia.org/wiki/IEEE_802.3");
	}
}

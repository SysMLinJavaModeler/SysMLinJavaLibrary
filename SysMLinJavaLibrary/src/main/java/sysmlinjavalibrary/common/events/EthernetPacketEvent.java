package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjavalibrary.common.objects.information.EthernetPacket;
import sysmlinjavalibrary.common.signals.EthernetPacketSignal;

public class EthernetPacketEvent extends SysMLSignalEvent
{
	public EthernetPacketEvent(EthernetPacket packet)
	{
		super("EthernetPacket");
		((EthernetPacketSignal)signal).packet.sourceAddress = packet.sourceAddress;
		((EthernetPacketSignal)signal).packet.destinationAddress = packet.destinationAddress;
		((EthernetPacketSignal)signal).packet.frame = packet.frame;
	}

	public EthernetPacket getPacket()
	{
		return ((EthernetPacketSignal)signal).packet;
	}

	@Override
	public void createSignal()
	{
		signal = new EthernetPacketSignal(new EthernetPacket());
	}
}

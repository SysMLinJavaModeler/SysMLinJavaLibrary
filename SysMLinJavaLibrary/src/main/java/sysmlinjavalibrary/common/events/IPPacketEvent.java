package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjavalibrary.common.objects.information.IPPacket;
import sysmlinjavalibrary.common.signals.IPPacketSignal;

public class IPPacketEvent extends SysMLSignalEvent
{
	public IPPacketEvent(IPPacket packet)
	{
		super("IPPacket");
		((IPPacketSignal)signal).packet.sourceAddress = packet.sourceAddress;
		((IPPacketSignal)signal).packet.destinationAddress = packet.destinationAddress;
		((IPPacketSignal)signal).packet.data = packet.data;
	}

	public IPPacketEvent(IPPacketSignal signal)
	{
		super("IPPacket");
		((IPPacketSignal)signal).packet.sourceAddress = signal.packet.sourceAddress;
		((IPPacketSignal)signal).packet.destinationAddress = signal.packet.destinationAddress;
		((IPPacketSignal)signal).packet.data = signal.packet.data;
	}

	public IPPacket getPacket()
	{
		return ((IPPacketSignal)signal).packet;
	}

	@Override
	public String toString()
	{
		return String.format("IPPacketEvent [signal=%s]", signal);
	}

	@Override
	public void createSignal()
	{
		signal = new IPPacketSignal(new IPPacket());
	}
}

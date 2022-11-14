package sysmlinjavalibrary.common.signals;

import sysmlinjava.analysis.common.StackedProtocolObject;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;
import sysmlinjavalibrary.common.objects.information.EthernetPacket;

public class EthernetPacketSignal extends SysMLSignal  implements StackedProtocolObject
{
	@Attribute
	public EthernetPacket packet;

	public EthernetPacketSignal(EthernetPacket packet)
	{
		super("EthernetPacket", 0L);
		this.packet = packet;
	}

	@Override
	public String stackNamesString()
	{
		return packet.stackNamesString();
	}
}

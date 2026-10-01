package sysmlinjavalibrary.common.signals;

import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.views.common.StackedProtocolObject;
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

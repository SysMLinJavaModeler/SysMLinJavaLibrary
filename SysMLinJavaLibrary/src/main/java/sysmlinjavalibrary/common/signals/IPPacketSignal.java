package sysmlinjavalibrary.common.signals;

import java.util.Optional;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjavalibrary.common.objects.information.IPPacket;

public class IPPacketSignal extends SysMLSignal
{
	@Attribute
	public IPPacket packet;

	public IPPacketSignal(IPPacket packet)
	{
		super();
		this.packet = packet;
	}

	public IPPacketSignal(IPPacket packet, Long id)
	{
		this(packet);
		this.id = id;
	}

	@Override
	public String stackNamesString()
	{
		return stackNamesString(this, Optional.empty());
	}
}

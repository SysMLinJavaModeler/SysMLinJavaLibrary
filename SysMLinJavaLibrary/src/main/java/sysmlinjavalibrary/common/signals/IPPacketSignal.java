package sysmlinjavalibrary.common.signals;

import java.util.Optional;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;
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

	@Override
	public String stackNamesString()
	{
		return stackNamesString(this, Optional.empty());
	}
}

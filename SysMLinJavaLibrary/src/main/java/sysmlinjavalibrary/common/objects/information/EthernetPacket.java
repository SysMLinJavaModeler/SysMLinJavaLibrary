package sysmlinjavalibrary.common.objects.information;

import java.util.Optional;

import sysmlinjava.common.SysMLAnything;
import sysmlinjava.views.common.StackedProtocolObject;

/**
 * The {@code EthernetPacket} is the SysMLinJava model of a packet of the
 * ethernet protocol. It contains only a source and destination address
 * (simulating the MAC address) and a frame object (simulating the ethernet
 * frame binary). The {@code EthernetPacket} also implements the
 * {@code StackedProtocolObject} interface.
 * 
 * @author ModelerOne
 *
 */
public class EthernetPacket extends SysMLAnything implements StackedProtocolObject
{
	public Long sourceAddress;
	public Long destinationAddress;
	public SysMLAnything frame;


	public EthernetPacket(Long sourceAddress, Long destinationAddress, SysMLAnything frame)
	{
		super();
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
		this.frame = frame;
	}

	public EthernetPacket()
	{
		super();
		this.sourceAddress = 0L;
		this.destinationAddress = 0L;
		this.frame = null;
	}

	@Override
	public String toString()
	{
		return String.format("EthernetPacket [sourceAddress=%s, destinationAddress=%s, frame=%s]", sourceAddress, destinationAddress, frame);
	}

	@Override
	public String stackNamesString()
	{
		if (frame instanceof IPPacket)
			return stackNamesString(this, Optional.of((IPPacket)frame));
		else
			return "<none>";
	}
}

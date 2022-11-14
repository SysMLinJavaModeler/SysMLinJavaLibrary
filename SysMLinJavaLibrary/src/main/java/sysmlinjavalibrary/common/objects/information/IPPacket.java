package sysmlinjavalibrary.common.objects.information;

import java.util.Optional;
import sysmlinjava.analysis.common.StackedProtocolObject;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;

/**
 * The {@code IPPacket} is the SysMLinJava model of a packet for use in an
 * Internet Protocol (IP) for internetwork communications. It contains an IP
 * source and destination address pair and a data class object simulating the
 * packet data of the IP packet. The {@code IPPacket} also implements the
 * {@code StackedProtocolObject} interface.
 * 
 * @author ModelerOne
 *
 */
public class IPPacket extends SysMLClass implements StackedProtocolObject
{
	@Attribute
	public Integer sourceAddress;
	@Attribute
	public Integer destinationAddress;
	@Attribute
	public SysMLClass data;

	public IPPacket(Integer sourceAddress, Integer destinationAddress, SysMLClass data)
	{
		super();
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
		this.data = data;
	}

	public IPPacket(IPPacket packet)
	{
		super();
		this.sourceAddress = packet.sourceAddress;
		this.destinationAddress = packet.destinationAddress;
		this.data = packet.data;
	}

	public IPPacket()
	{
		super();
		this.sourceAddress = 0;
		this.destinationAddress = 0;
		this.data = null;
	}

	@Override
	public String stackNamesString()
	{
		String result = "<none>";
		if (data instanceof UDPDatagram)
			result = stackNamesString(this, Optional.of((UDPDatagram)data));
		return result;
	}

	@Override
	public String toString()
	{
		return String.format("IPPacket [sourceAddress=%s, destinationAddress=%s, data=%s]", sourceAddress, destinationAddress, data);
	}

}

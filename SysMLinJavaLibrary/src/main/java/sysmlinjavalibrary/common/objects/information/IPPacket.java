package sysmlinjavalibrary.common.objects.information;

import java.util.Optional;

import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.views.common.StackedProtocolObject;

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
public class IPPacket extends SysMLAnything implements StackedProtocolObject
{
	@Attribute
	public Integer sourceAddress;
	@Attribute
	public Integer destinationAddress;
	@Attribute
	public Boolean isEncrypted;
	@Attribute
	public SysMLAnything data;

	public IPPacket(Integer sourceAddress, Integer destinationAddress, Boolean isEncrypted, SysMLAnything data)
	{
		super();
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
		this.isEncrypted = isEncrypted;
		this.data = data;
	}

	public IPPacket(Integer sourceAddress, Integer destinationAddress, SysMLAnything data)
	{
		super();
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
		this.isEncrypted = false;
		this.data = data;
	}

	public IPPacket(IPPacket packet, Boolean isEncrypted)
	{
		super();
		this.sourceAddress = packet.sourceAddress;
		this.destinationAddress = packet.destinationAddress;
		this.isEncrypted = isEncrypted;
		this.data = packet.data;
	}

	public IPPacket()
	{
		super();
		this.sourceAddress = 0;
		this.destinationAddress = 0;
		this.isEncrypted = false;
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
		StringBuilder builder = new StringBuilder();
		builder.append("IPPacket [sourceAddress=");
		builder.append(sourceAddress);
		builder.append(", destinationAddress=");
		builder.append(destinationAddress);
		builder.append(", isEncrypted=");
		builder.append(isEncrypted);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}
}

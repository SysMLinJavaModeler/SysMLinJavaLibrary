package sysmlinjavalibrary.common.objects.information;

import java.util.Optional;

import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.views.common.StackedProtocolObject;

/**
 * The {@code UDPDatagram} is the SysMLinJava model of a datagram packet for use
 * in a User Datagram Protocol (UDP) for internetwork communications. It
 * contains an IP source and destination address pair and a UDP source and
 * destination port number. It also contains a data class object simulating the
 * packet data of the UDP dataqgram. The {@code UDPDatagram} also implements the
 * {@code StackedProtocolObject} interface.
 * 
 * @author ModelerOne
 *
 */
public class UDPDatagram extends SysMLAnything implements StackedProtocolObject
{
	@Attribute
	public Integer sourcePort;
	@Attribute
	public Integer destinationPort;
	@Attribute
	public Integer sourceIPAddress;
	@Attribute
	public Integer destinationIPAddress;
	@Attribute
	public SysMLAnything data;

	public UDPDatagram(Integer sourcePort, Integer destinationPort, Integer sourceIPAddress, Integer destinationIPAddress, SysMLAnything data)
	{
		super();
		this.sourcePort = sourcePort;
		this.destinationPort = destinationPort;
		this.sourceIPAddress = sourceIPAddress;
		this.destinationIPAddress = destinationIPAddress;
		this.data = data;
	}

	@Override
	public String stackNamesString()
	{
		if (data instanceof SNMPRequest)
			return stackNamesString(this, Optional.of((SNMPRequest)data));
		else if (data instanceof SNMPResponse)
			return stackNamesString(this, Optional.of((SNMPResponse)data));
		else
			return "<none>";
	}

	@Override
	public String toString()
	{
		return String.format("UDPDatagram [sourcePort=%s, destinationPort=%s, sourceIPAddress=%s, destinationIPAddress=%s, data=%s]", sourcePort, destinationPort, sourceIPAddress, destinationIPAddress, data);
	}
}

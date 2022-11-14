package sysmlinjavalibrary.common.objects.information;

import java.util.Optional;
import sysmlinjava.analysis.common.StackedProtocolObject;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;

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
public class UDPDatagram extends SysMLClass implements StackedProtocolObject
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
	public SysMLClass data;

	public UDPDatagram(Integer sourcePort, Integer destinationPort, Integer sourceIPAddress, Integer destinationIPAddress, SysMLClass data)
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

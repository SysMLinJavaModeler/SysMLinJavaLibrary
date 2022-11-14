package sysmlinjavalibrary.common.objects.information;

import java.util.Optional;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;

/**
 * The {@code HDLCFrame} is the SysMLinJava model of a frame of the High-Level
 * Data Link Control network protocol used for data-link communications. It
 * contains only a data Object simulating the HDLC frame data. The
 * {@code HDLCFrame} extends the {@code SysMLSignal} enabling it to be
 * transmitted between ports as a physical-level protocol. This implementation
 * assumes the frame contains an IPPacket (typical for IP router communications)
 * so this implementation cannot be used for other protocol stacks.
 * 
 * @author ModelerOne
 *
 */
public class HDLCFrame extends SysMLSignal
{
	@Attribute
	public Integer sourceID;
	@Attribute
	public Integer destinationID;
	@Attribute
	public IPPacket data;

	public HDLCFrame(Integer source, Integer destination, IPPacket data)
	{
		super();
		this.sourceID = source;
		this.destinationID = destination;
		this.data = data;
	}

	public HDLCFrame()
	{
		super();
		this.sourceID = 0;
		this.destinationID = 0;
		this.data = new IPPacket();
	}

	@Override
	public String stackNamesString()
	{
		if (data instanceof IPPacket)
			return stackNamesString(this, Optional.of((IPPacket)data));
		else
			return "<none>";
	}

	@Override
	public String toString()
	{
		return String.format("HDLCFrame [data=%s, sourceID=%s, destinationID=%s, id=%s]", data, sourceID, destinationID, id);
	}

}

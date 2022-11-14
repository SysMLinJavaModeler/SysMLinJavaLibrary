package sysmlinjavalibrary.common.ports.information;

import java.util.Optional;
import sysmlinjava.annotations.comments.Hyperlink;
import sysmlinjava.annotations.requirements.RequirementReference;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.comments.SysMLHyperlink;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjavalibrary.common.events.IPPacketEvent;
import sysmlinjavalibrary.common.objects.information.IPPacket;
import sysmlinjavalibrary.common.objects.information.UDPDatagram;

public class InternetProtocol extends SysMLFullPort
{
	public InternetProtocol(SysMLBlock contextBlock, SysMLBlock eventContextBlock, Long id)
	{
		super(contextBlock, Optional.of(eventContextBlock), id);
	}

	public InternetProtocol(SysMLBlock contextBlock, Long id)
	{
		super(contextBlock, id);
	}

	@RequirementReference
	@Hyperlink
	public SysMLHyperlink protocolStandard;

	@Override
	protected void createHyperlinks()
	{
		protocolStandard = new SysMLHyperlink("IETF RFC-791 Internet Protocol", "https://tools.ietf.org/html/rfc791");
	}

	@Override
	protected SysMLClass serverObjectFor(SysMLClass clientObject)
	{
		SysMLClass result = null;
		if (clientObject instanceof UDPDatagram)
			result = new IPPacket(((UDPDatagram)clientObject).sourceIPAddress, ((UDPDatagram)clientObject).destinationIPAddress, clientObject);
		else
			logger.warning("unexpected client object type: " + clientObject.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLClass clientObjectFor(SysMLClass serverObject)
	{
		SysMLClass result = null;
		if (serverObject instanceof IPPacket)
			result = ((IPPacket)serverObject).data;
		else
			logger.warning("unexpected serverObject type: " + serverObject.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLClass object)
	{
		SysMLSignalEvent result = null;
		if (object instanceof IPPacket)
			result = new IPPacketEvent((IPPacket)object);
		else
			logger.warning("unexpected object type: " + object.getClass().getSimpleName());
		return result;
	}
}

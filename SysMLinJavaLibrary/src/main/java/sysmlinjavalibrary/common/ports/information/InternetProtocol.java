package sysmlinjavalibrary.common.ports.information;

import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.javaannotations.requirements.RequirementReference;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.ports.SysMLPort;
import sysmlinjavalibrary.common.objects.information.IPPacket;
import sysmlinjavalibrary.common.objects.information.UDPDatagram;
import sysmlinjavalibrary.common.signals.IPPacketSignal;

public class InternetProtocol extends SysMLPort
{
	@RequirementReference
	@Hyperlink
	public SysMLHyperlink protocolStandard;

	public InternetProtocol(SysMLPart contextPart, SysMLPart eventContextPart, Long id)
	{
		super(contextPart, Optional.of(eventContextPart), id);
	}

	public InternetProtocol(SysMLPart contextPart, Long id)
	{
		super(contextPart, id);
	}

	public InternetProtocol(SysMLPort contextPart, Long id)
	{
		super(contextPart, id);
	}

	@Override
	public ScheduledThreadPoolExecutor getExecutionThreads()
	{
		return context.get().getExecutionThreads();
	}

	@Override
	protected void createSupportingInformationLinks()
	{
		protocolStandard = new SysMLHyperlink("IETF RFC-791 Internet Protocol", "https://tools.ietf.org/html/rfc791");
	}

	@Override
	protected SysMLAnything serverObjectFor(SysMLAnything clientObject)
	{
		SysMLAnything result = null;
		if (clientObject instanceof UDPDatagram)
			result = new IPPacket(((UDPDatagram)clientObject).sourceIPAddress, ((UDPDatagram)clientObject).destinationIPAddress, clientObject);
		else
			logger.warning("unexpected client object type: " + clientObject.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLAnything clientObjectFor(SysMLAnything serverObject)
	{
		SysMLAnything result = null;
		if (serverObject instanceof IPPacket)
			result = ((IPPacket)serverObject).data;
		else
			logger.warning("unexpected serverObject type: " + serverObject.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLAnything object)
	{
		SysMLSignalEvent result = null;
		if (object instanceof IPPacket)
			result = new SysMLSignalEvent(new IPPacketSignal((IPPacket)object, id), "IPPacket", 0L);
		else
			logger.warning("unexpected object type: " + object.getClass().getSimpleName());
		return result;
	}
}

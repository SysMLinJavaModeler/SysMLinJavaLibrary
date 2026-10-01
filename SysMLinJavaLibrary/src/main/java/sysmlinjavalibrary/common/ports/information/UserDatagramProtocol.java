package sysmlinjavalibrary.common.ports.information;

import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.javaannotations.requirements.RequirementReference;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.ports.SysMLPort;
import sysmlinjavalibrary.common.objects.information.HTTPRequest;
import sysmlinjavalibrary.common.objects.information.HTTPResponse;
import sysmlinjavalibrary.common.objects.information.UDPDatagram;

public class UserDatagramProtocol extends SysMLPort
{
	public UserDatagramProtocol(SysMLPart contextBlock, Long id)
	{
		super(contextBlock, id);
	}

	@RequirementReference
	@Hyperlink
	public SysMLHyperlink protocolStandard;

	@Override
	protected SysMLAnything serverObjectFor(SysMLAnything clientObject)
	{
		SysMLAnything result = null;
		if (clientObject instanceof HTTPRequest)
			result = new UDPDatagram(0, 0, ((HTTPRequest)clientObject).ipSource, ((HTTPRequest)clientObject).ipDestination, clientObject);
		else if (clientObject instanceof HTTPResponse)
			result = new UDPDatagram(0, 0, ((HTTPResponse)clientObject).ipSource, ((HTTPResponse)clientObject).ipDestination, clientObject);
		else
			logger.warning("unexpected client object type: " + clientObject.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLAnything clientObjectFor(SysMLAnything serverObject)
	{
		SysMLAnything result = null;
		if (serverObject instanceof UDPDatagram)
			result = ((UDPDatagram)serverObject).data;
		else
			logger.warning("unexpected serverObject type: " + serverObject.getClass().getSimpleName());
		return result;
	}

	@Override
	protected void createSupportingInformationLinks()
	{
		protocolStandard = new SysMLHyperlink("IETF RFC-768 User Datagram Protocol", "https://tools.ietf.org/html/rfc768");
	}
}

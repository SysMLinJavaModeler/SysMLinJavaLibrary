package sysmlinjavalibrary.common.ports.information;

import sysmlinjava.annotations.comments.Hyperlink;
import sysmlinjava.annotations.requirements.RequirementReference;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.comments.SysMLHyperlink;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjavalibrary.common.objects.information.HTTPRequest;
import sysmlinjavalibrary.common.objects.information.HTTPResponse;
import sysmlinjavalibrary.common.objects.information.UDPDatagram;

public class UserDatagramProtocol extends SysMLFullPort
{
	public UserDatagramProtocol(SysMLBlock contextBlock, Long id)
	{
		super(contextBlock, id);
	}

	@RequirementReference
	@Hyperlink
	public SysMLHyperlink protocolStandard;

	@Override
	protected SysMLClass serverObjectFor(SysMLClass clientObject)
	{
		SysMLClass result = null;
		if (clientObject instanceof HTTPRequest)
			result = new UDPDatagram(0, 0, ((HTTPRequest)clientObject).ipSource, ((HTTPRequest)clientObject).ipDestination, clientObject);
		else if (clientObject instanceof HTTPResponse)
			result = new UDPDatagram(0, 0, ((HTTPResponse)clientObject).ipSource, ((HTTPResponse)clientObject).ipDestination, clientObject);
		else
			logger.warning("unexpected client object type: " + clientObject.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLClass clientObjectFor(SysMLClass serverObject)
	{
		SysMLClass result = null;
		if (serverObject instanceof UDPDatagram)
			result = ((UDPDatagram)serverObject).data;
		else
			logger.warning("unexpected serverObject type: " + serverObject.getClass().getSimpleName());
		return result;
	}

	@Override
	protected void createHyperlinks()
	{
		protocolStandard = new SysMLHyperlink("IETF RFC-768 User Datagram Protocol", "https://tools.ietf.org/html/rfc768");
	}
}

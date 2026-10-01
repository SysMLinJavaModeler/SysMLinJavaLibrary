package sysmlinjavalibrary.common.ports.information;

import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.javaannotations.requirements.RequirementReference;
import sysmlinjava.ports.SysMLPort;
import sysmlinjavalibrary.common.objects.information.IPPacket;
import sysmlinjavalibrary.components.communications.internet.EthernetSwitchIPRouter;

public class InternetRoutingProtocol extends SysMLPort
{
	public InternetRoutingProtocol(EthernetSwitchIPRouter contextBlock, Long id)
	{
		super(contextBlock, id);
	}

	@RequirementReference
	@Hyperlink
	public SysMLHyperlink protocolStandard;

	@Override
	protected void createSupportingInformationLinks()
	{
		protocolStandard = new SysMLHyperlink("IETF RFC-1723 Routing Information Protocol", "https://tools.ietf.org/html/rfc1723");
	}

	public void transmit(SysMLAnything object)
	{
		if (object instanceof IPPacket)
		{
			IPPacket packet = (IPPacket)object;
			EthernetSwitchIPRouter switchRouter = (EthernetSwitchIPRouter)context.get();
			Integer ethernetPortIndex = switchRouter.ipToEthernetMap.ethernetPortFor(packet.destinationAddress);
			EthernetProtocol ethernetPort = (EthernetProtocol)connectedPortsServers.get(ethernetPortIndex);
			ethernetPort.transmit(packet);
		}
		else
			logger.severe("unrecognized object type: " + object.getClass().getName());
	}
}

package sysmlinjavalibrary.common.ports.information;

import sysmlinjava.annotations.comments.Hyperlink;
import sysmlinjava.annotations.requirements.RequirementReference;
import sysmlinjava.comments.SysMLHyperlink;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjavalibrary.common.objects.information.IPPacket;
import sysmlinjavalibrary.components.communications.internet.EthernetSwitchIPRouter;

public class InternetRoutingProtocol extends SysMLFullPort
{
	public InternetRoutingProtocol(EthernetSwitchIPRouter contextBlock, Long id)
	{
		super(contextBlock, id);
	}

	@RequirementReference
	@Hyperlink
	public SysMLHyperlink protocolStandard;

	@Override
	protected void createHyperlinks()
	{
		protocolStandard = new SysMLHyperlink("IETF RFC-1723 Routing Information Protocol", "https://tools.ietf.org/html/rfc1723");
	}

	public void transmit(SysMLClass object)
	{
		if (object instanceof IPPacket)
		{
			IPPacket packet = (IPPacket)object;
			EthernetSwitchIPRouter switchRouter = (EthernetSwitchIPRouter)contextBlock.get();
			Integer ethernetPortIndex = switchRouter.ipToEthernetMap.ethernetPortFor(packet.destinationAddress);
			EthernetProtocol ethernetPort = (EthernetProtocol)connectedPortsServers.get(ethernetPortIndex);
			ethernetPort.transmit(packet);
		}
		else
			logger.severe("unrecognized object type: " + object.getClass().getName());
	}
}

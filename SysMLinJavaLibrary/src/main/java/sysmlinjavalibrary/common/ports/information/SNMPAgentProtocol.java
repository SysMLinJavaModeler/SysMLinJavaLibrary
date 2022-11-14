package sysmlinjavalibrary.common.ports.information;

import java.util.Optional;
import sysmlinjava.annotations.comments.Hyperlink;
import sysmlinjava.annotations.requirements.RequirementReference;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.comments.SysMLHyperlink;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjavalibrary.common.events.SNMPRequestEvent;
import sysmlinjavalibrary.common.objects.information.SNMPResponse;
import sysmlinjavalibrary.common.signals.SNMPRequestSignal;
import sysmlinjavalibrary.common.signals.SNMPResponseSignal;

public class SNMPAgentProtocol extends SysMLFullPort
{
	public SNMPAgentProtocol(SysMLBlock contextBlock, Long id)
	{
		super(contextBlock, Optional.of(contextBlock), id);
	}

	@RequirementReference
	@Hyperlink
	public SysMLHyperlink protocolStandard;

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if(signal instanceof SNMPRequestSignal)
		{
			SNMPRequestSignal snmpRequestSignal = (SNMPRequestSignal)signal;
			result = new SNMPRequestEvent(snmpRequestSignal.request);
		}
		else
			logger.warning("unexpected signal type: " + signal.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
	{
		SysMLSignal result = null;
		if(object instanceof SNMPResponse)
		{
			SNMPResponse snmpResponse = (SNMPResponse)object;
			result = new SNMPResponseSignal(snmpResponse);
		}
		else
			logger.severe("unexpected object type: " + object.getClass().getSimpleName());
		return result;
	}

	@Override
	protected void createHyperlinks()
	{
		protocolStandard = new SysMLHyperlink("Simple Network Management Protocol", "https://tools.ietf.org/html/rfc3411");
	}
}

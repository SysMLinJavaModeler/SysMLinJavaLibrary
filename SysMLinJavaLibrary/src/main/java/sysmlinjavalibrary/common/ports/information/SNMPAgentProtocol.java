package sysmlinjavalibrary.common.ports.information;

import java.util.Optional;
import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.javaannotations.requirements.RequirementReference;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.ports.SysMLPort;
import sysmlinjavalibrary.common.objects.information.SNMPResponse;
import sysmlinjavalibrary.common.signals.SNMPRequestSignal;
import sysmlinjavalibrary.common.signals.SNMPResponseSignal;

public class SNMPAgentProtocol extends SysMLPort
{
	public SNMPAgentProtocol(SysMLPart contextBlock, Long id)
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
			result = new SysMLSignalEvent(signal, "SNMPRequestEvent", 0L);
		else
			logger.warning("unexpected signal type: " + signal.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLSignal signalFor(SysMLAnything object)
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
	protected void createSupportingInformationLinks()
	{
		protocolStandard = new SysMLHyperlink("Simple Network Management Protocol", "https://tools.ietf.org/html/rfc3411");
	}
}

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
import sysmlinjavalibrary.common.events.SNMPResponseEvent;
import sysmlinjavalibrary.common.objects.information.SNMPRequest;
import sysmlinjavalibrary.common.signals.SNMPRequestSignal;
import sysmlinjavalibrary.common.signals.SNMPResponseSignal;

public class SNMPManagerProtocol extends SysMLFullPort
{
	public SNMPManagerProtocol(SysMLBlock contextBlock, Long id)
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
		if(signal instanceof SNMPResponseSignal)
		{
			SNMPResponseSignal snmpResponseSignal = (SNMPResponseSignal)signal;
			result = new SNMPResponseEvent(snmpResponseSignal.response);
		}
		else
			logger.warning("unexpected signal type: " + signal.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
	{
		SysMLSignal result = null;
		if(object instanceof SNMPRequest)
		{
			SNMPRequest snmpRequest = (SNMPRequest)object;
			result = new SNMPRequestSignal(snmpRequest);
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

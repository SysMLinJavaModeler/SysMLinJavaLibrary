package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjavalibrary.common.objects.information.SNMPRequest;
import sysmlinjavalibrary.common.signals.SNMPRequestSignal;

public class SNMPRequestEvent extends SysMLSignalEvent
{
	public SNMPRequestEvent(SNMPRequest request)
	{
		super("SNMPRequest");
		((SNMPRequestSignal)signal).request.mib.time = request.mib.time;
		((SNMPRequestSignal)signal).request.mib.dataString = request.mib.dataString;
		((SNMPRequestSignal)signal).request.time = request.time;
	}

	public SNMPRequest getRequest()
	{
		return ((SNMPRequestSignal)signal).request;
	}

	@Override
	public void createSignal()
	{
		signal = new SNMPRequestSignal(new SNMPRequest());
	}
}

package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjavalibrary.common.objects.information.SNMPResponse;
import sysmlinjavalibrary.common.signals.SNMPResponseSignal;

public class SNMPResponseEvent extends SysMLSignalEvent
{
	public SNMPResponseEvent(SNMPResponse response)
	{
		super("SNMPResponse");
		((SNMPResponseSignal)signal).response.mib.time = response.mib.time;
		((SNMPResponseSignal)signal).response.mib.dataString = response.mib.dataString;
		((SNMPResponseSignal)signal).response.time = response.time;
	}

	public SNMPResponse getResponse()
	{
		return ((SNMPResponseSignal)signal).response;
	}

	@Override
	public void createSignal()
	{
		signal = new SNMPResponseSignal(new SNMPResponse());
	}
}

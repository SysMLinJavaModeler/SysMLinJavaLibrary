package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.valuetypes.HeatWatts;
import sysmlinjavalibrary.common.signals.HeatSignal;

public class HeatEvent extends SysMLSignalEvent
{
	public HeatEvent(HeatWatts heat)
	{
		super("Heat");
		((HeatSignal)signal).heat.value = heat.value;
	}

	@Override
	public void createSignal()
	{
		signal = new HeatSignal(new HeatWatts());
	}
}

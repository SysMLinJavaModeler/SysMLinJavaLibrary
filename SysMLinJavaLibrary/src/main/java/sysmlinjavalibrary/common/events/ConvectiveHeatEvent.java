package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjavalibrary.common.objects.energy.thermal.ConvectiveHeat;
import sysmlinjavalibrary.common.signals.ConvectiveHeatSignal;

public class ConvectiveHeatEvent extends SysMLSignalEvent
{
	public ConvectiveHeatEvent(ConvectiveHeat convective)
	{
		super("ConvectiveHeat");
		((ConvectiveHeatSignal)signal).heat.heat.value = convective.heat.value;
		((ConvectiveHeatSignal)signal).heat.heat.value = convective.heat.value;
	}

	public ConvectiveHeat getHeat()
	{
		return ((ConvectiveHeatSignal)signal).heat;
	}

	@Override
	public void createSignal()
	{
		signal = new ConvectiveHeatSignal(new ConvectiveHeat());
	}
}

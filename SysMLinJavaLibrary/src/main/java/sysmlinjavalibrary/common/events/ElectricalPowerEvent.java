package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.valuetypes.ElectricalPower;
import sysmlinjavalibrary.common.signals.ElectricalPowerSignal;

public class ElectricalPowerEvent extends SysMLSignalEvent
{
	public ElectricalPowerEvent(ElectricalPower power)
	{
		super("ElectricalPower");
		((ElectricalPowerSignal)signal).power = new ElectricalPower(power);
	}

	public ElectricalPower getPower()
	{
		return ((ElectricalPowerSignal)signal).power;
	}

	@Override
	public void createSignal()
	{
		signal = new ElectricalPowerSignal(new ElectricalPower());
	}
}

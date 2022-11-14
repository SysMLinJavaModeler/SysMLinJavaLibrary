package sysmlinjavalibrary.common.signals;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.valuetypes.ElectricalPower;

public class ElectricalPowerSignal extends SysMLSignal
{
	@Attribute
	public ElectricalPower power;

	public ElectricalPowerSignal(ElectricalPower power)
	{
		super();
		this.power = power;
	}

	@Override
	public String stackNamesString()
	{
		return power.stackNamesString();
	}

	@Override
	public String toString()
	{
		return String.format("ElectricalPowerSignal [name=%s, id=%s, power=%s]", name, id, power);
	}
}

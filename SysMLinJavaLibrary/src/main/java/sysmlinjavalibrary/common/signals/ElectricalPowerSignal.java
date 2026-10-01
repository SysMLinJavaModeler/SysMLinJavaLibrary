package sysmlinjavalibrary.common.signals;

import sysmlinjava.attributetypes.ElectricalPower;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;

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

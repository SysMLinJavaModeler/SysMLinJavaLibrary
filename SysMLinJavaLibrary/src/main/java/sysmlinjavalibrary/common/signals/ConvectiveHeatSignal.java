package sysmlinjavalibrary.common.signals;

import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjavalibrary.common.objects.energy.thermal.ConvectiveHeat;

public class ConvectiveHeatSignal extends SysMLSignal
{
	@Attribute
	public ConvectiveHeat heat;

	public ConvectiveHeatSignal(ConvectiveHeat heat)
	{
		super();
		this.heat = heat;
	}

	@Override
	public String stackNamesString()
	{
		return heat.stackNamesString();
	}

	@Override
	public String toString()
	{
		return String.format("ConvectiveHeatSignal [heat=%s]", heat);
	}
}

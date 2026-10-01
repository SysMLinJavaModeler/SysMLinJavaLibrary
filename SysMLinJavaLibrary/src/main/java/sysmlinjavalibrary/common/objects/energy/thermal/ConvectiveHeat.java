package sysmlinjavalibrary.common.objects.energy.thermal;

import sysmlinjava.attributetypes.HeatWatts;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.views.common.StackedProtocolObject;

/**
 * {@code ConvectiveHeat} is a SysMLinJava model class that represents heat
 * transfered by convection, e.g. air flow, in terms of its attribute heat
 * watts. It can be used to specify flows of heat into and out of the
 * {@code ConvectiveThermalPortSink} and {@code ConvectiveThermalPortSource},
 * respectively. The class implements the {@code StackedProtocolObject}
 * interface.
 **/
public class ConvectiveHeat extends SysMLAnything implements StackedProtocolObject
{
	@Attribute
	public HeatWatts heat;

	public ConvectiveHeat(HeatWatts heat)
	{
		super();
		this.heat = heat;
	}

	public ConvectiveHeat()
	{
		super();
		this.heat = new HeatWatts(0);
	}

	@Override
	public String stackNamesString()
	{
		return String.format("%s(watts=%8.4f)", getClass().getSimpleName(), heat.value);
	}

	@Override
	public String toString()
	{
		return String.format("ConvectiveHeat [heat=%s]", heat);
	}
}

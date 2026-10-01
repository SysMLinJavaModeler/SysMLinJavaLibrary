package sysmlinjavalibrary.common.signals;

import sysmlinjava.attributetypes.HeatWatts;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;

public class HeatSignal extends SysMLSignal
{
	@Attribute
	public HeatWatts heat;


	public HeatSignal(HeatWatts heat)
	{
		super();
		this.heat = heat;
	}

	@Override
	public String stackNamesString()
	{
		return heat.identityString();
	}
}

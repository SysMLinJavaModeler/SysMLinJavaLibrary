package sysmlinjavalibrary.common.signals;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.valuetypes.HeatWatts;

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

package sysmlinjavalibrary.common.signals;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;
import sysmlinjavalibrary.common.objects.information.OnOffSwitch;

public class OnOffSwitchSignal extends SysMLSignal
{
	@Attribute
	public OnOffSwitch onOffSwitch;

	public OnOffSwitchSignal(OnOffSwitch onOffSwitch)
	{
		super();
		this.onOffSwitch = onOffSwitch;
	}

	@Override
	public String stackNamesString()
	{
		return onOffSwitch.stackNamesString();
	}

	@Override
	public String toString()
	{
		return String.format("OnOffSwitchSignal [onOffSwitch=%s]", onOffSwitch);
	}
}

package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjavalibrary.common.objects.information.OnOffSwitch;
import sysmlinjavalibrary.common.signals.OnOffSwitchSignal;

public class OnOffSwitchEvent extends SysMLSignalEvent
{
	public OnOffSwitchEvent(OnOffSwitch onOffSwitch)
	{
		super("OnOffSwitch");
		((OnOffSwitchSignal)signal).onOffSwitch.isOn = onOffSwitch.isOn;
	}

	public OnOffSwitch getOnOffSwitch()
	{
		return ((OnOffSwitchSignal)signal).onOffSwitch;
	}

	@Override
	public void createSignal()
	{
		signal = new OnOffSwitchSignal(new OnOffSwitch());
	}
}

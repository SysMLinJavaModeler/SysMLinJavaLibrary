package sysmlinjavalibrary.common.objects.information;

import sysmlinjava.analysis.common.StackedProtocolObject;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;

/**
 * The {@code OnOffSwitch} is a SysMLinJava model of a simple on/off switch. It
 * has a single attribute of a boolean indication of the switch is on. The
 * {@code OnOffSwitch} also implements the {@code StackedProtocolObject}
 * interface.
 * 
 * @author ModelerOne
 *
 */
public class OnOffSwitch extends SysMLClass implements StackedProtocolObject
{
	@Attribute
	public Boolean isOn;

	public OnOffSwitch(Boolean state)
	{
		super();
		this.isOn = state;
	}

	public OnOffSwitch()
	{
		isOn = false;
	}

	@Override
	public String stackNamesString()
	{
		return String.format("%s(isOn=%s", getClass().getSimpleName(), isOn);
	}

	@Override
	public String toString()
	{
		return String.format("OnOffSwitch [isOn=%s]", isOn);
	}
}

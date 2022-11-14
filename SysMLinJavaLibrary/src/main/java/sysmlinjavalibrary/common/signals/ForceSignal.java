package sysmlinjavalibrary.common.signals;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.valuetypes.ForceNewtons;

public class ForceSignal extends SysMLSignal
{
	@Attribute
	public ForceNewtons force;

	public ForceSignal(ForceNewtons force)
	{
		super();
		this.force = force;
	}

	@Override
	public String stackNamesString()
	{
		return force.identityString();
	}

	@Override
	public String toString()
	{
		return String.format("ForceSignal [force=%s]", force);
	}
}

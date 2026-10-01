package sysmlinjavalibrary.common.signals;

import sysmlinjava.attributetypes.ForceNewtons;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;

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

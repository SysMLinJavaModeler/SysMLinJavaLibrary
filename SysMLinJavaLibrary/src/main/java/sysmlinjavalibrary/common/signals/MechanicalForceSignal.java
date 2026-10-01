package sysmlinjavalibrary.common.signals;

import sysmlinjava.attributetypes.ForceNewtons;
import sysmlinjava.attributetypes.IInteger;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;

public class MechanicalForceSignal extends SysMLSignal
{
	@Attribute
	public ForceNewtons force;
	@Attribute
	public IInteger id;

	public MechanicalForceSignal(ForceNewtons force, IInteger id)
	{
		super();
		this.force = force;
		this.id = id;
	}

	@Override
	public String stackNamesString()
	{
		return force.stackNamesString();
	}

	@Override
	public String toString()
	{
		return String.format("MechanicalForceSignal [force=%s, id=%s]", force, id);
	}
}

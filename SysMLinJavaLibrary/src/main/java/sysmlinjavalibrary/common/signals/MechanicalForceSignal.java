package sysmlinjavalibrary.common.signals;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.valuetypes.ForceNewtons;
import sysmlinjava.valuetypes.IInteger;

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

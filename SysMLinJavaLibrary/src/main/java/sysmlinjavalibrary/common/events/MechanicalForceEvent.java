package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.valuetypes.ForceNewtons;
import sysmlinjava.valuetypes.IInteger;
import sysmlinjavalibrary.common.signals.MechanicalForceSignal;

/**
 * Event for the occurence of a value of mechanical force for an identified object
 * 
 * @author ModelerOne
 *
 */
public class MechanicalForceEvent extends SysMLSignalEvent
{
	public MechanicalForceEvent(ForceNewtons force, IInteger id)
	{
		super("MechanicalForce");
		((MechanicalForceSignal)signal).id = id;
		((MechanicalForceSignal)signal).force = new ForceNewtons(force);
	}

	public ForceNewtons getForce()
	{
		return ((MechanicalForceSignal)signal).force;
	}

	public IInteger getID()
	{
		return ((MechanicalForceSignal)signal).id;
	}

	@Override
	public void createSignal()
	{
		signal = new MechanicalForceSignal(new ForceNewtons(), IInteger.zero);
	}
}

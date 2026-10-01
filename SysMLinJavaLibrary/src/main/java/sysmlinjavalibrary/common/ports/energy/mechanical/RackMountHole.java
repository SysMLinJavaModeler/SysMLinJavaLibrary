package sysmlinjavalibrary.common.ports.energy.mechanical;

import java.util.Optional;
import sysmlinjava.attributetypes.DistanceMillimeters;
import sysmlinjava.attributetypes.ForceNewtons;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.ports.SysMLPort;
import sysmlinjava.states.StateBehaviorContext;
import sysmlinjavalibrary.common.signals.MechanicalForceSignal;

/**
 * The {@code RackMountHole} is a SysMLinJava model of a port for a hole in a
 * rack structure for mounting equipment with a {@code ComponentMountFastener}.
 * The {@code RackMountHole} has a flow value of the weight of the equipment
 * transmitted to the hole through the fastener It also has values for its
 * physical size, i.e. diameter, thickness, and offset from an adjacent hole.
 * The {@code RackMountHole} overrides the {@code SysMLFullPort} operation to
 * translate a receive {@code MechanicalForceSignal} into a
 * {@code MechanicalForceEvent} and to set the weight that flows into the hole.
 * 
 * @author ModelerOne
 *
 */
public class RackMountHole extends SysMLPort
{
	@Attribute
	public DistanceMillimeters diameter;
	@Attribute
	public DistanceMillimeters thickness;
	@Attribute
	public DistanceMillimeters offsetFromNext;

	@Attribute
	public ForceNewtons weight;

	public RackMountHole(StateBehaviorContext eventDriven, Long id)
	{
		super(eventDriven, Optional.of(eventDriven), id);
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof MechanicalForceSignal)
		{
			MechanicalForceSignal forceSignal = (MechanicalForceSignal)signal;
			weight.value = forceSignal.force.value;
			weight.direction.value = forceSignal.force.direction.value;
			result = new SysMLSignalEvent(forceSignal, "MechanicalForceEvent", 0L);
		}
		return result;
	}

	@Override
	protected void createAttributes()
	{
		diameter = new DistanceMillimeters(5.4864);
		thickness = new DistanceMillimeters(2.0);
		offsetFromNext = new DistanceMillimeters(12.7);
		weight = new ForceNewtons(0);
	}
}

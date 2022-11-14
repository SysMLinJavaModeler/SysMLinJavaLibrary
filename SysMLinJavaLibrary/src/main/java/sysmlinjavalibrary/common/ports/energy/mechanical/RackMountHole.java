package sysmlinjavalibrary.common.ports.energy.mechanical;

import java.util.Optional;
import sysmlinjava.annotations.Flow;
import sysmlinjava.annotations.Value;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.kinds.SysMLFlowDirectionKind;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjava.valuetypes.DistanceMillimeters;
import sysmlinjava.valuetypes.ForceNewtons;
import sysmlinjavalibrary.common.events.MechanicalForceEvent;
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
public class RackMountHole extends SysMLFullPort
{
	@Value
	public DistanceMillimeters diameter;
	@Value
	public DistanceMillimeters thickness;
	@Value
	public DistanceMillimeters offsetFromNext;

	@Flow(direction = SysMLFlowDirectionKind.in)
	public ForceNewtons weight;

	public RackMountHole(SysMLBlock contextBlock, Long id)
	{
		super(contextBlock, Optional.of(contextBlock), id);
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
			result = new MechanicalForceEvent(forceSignal.force, forceSignal.id);
		}
		return result;
	}

	@Override
	protected void createValues()
	{
		diameter = new DistanceMillimeters(5.4864);
		thickness = new DistanceMillimeters(2.0);
		offsetFromNext = new DistanceMillimeters(12.7);
	}

	@Override
	protected void createFlows()
	{
		weight = new ForceNewtons(0);
	}
}

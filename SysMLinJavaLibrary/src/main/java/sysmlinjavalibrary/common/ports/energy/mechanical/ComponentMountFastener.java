package sysmlinjavalibrary.common.ports.energy.mechanical;

import sysmlinjava.annotations.Flow;
import sysmlinjava.annotations.Value;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.kinds.SysMLFlowDirectionKind;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjava.valuetypes.DistanceMillimeters;
import sysmlinjava.valuetypes.ForceNewtons;
import sysmlinjava.valuetypes.IInteger;
import sysmlinjavalibrary.common.signals.MechanicalForceSignal;

/**
 * The {@code ComponentMountFastener} is a SysMLinJava model of a port
 * representing a fastener on an item of equipment to be mounted in a rack,
 * specifically a hole in a rack structure for mounting equipment with a
 * {@code ComponentMountFastener}. The {@code ComponentMountFastener} has a flow
 * value of the weight of the equipment transmitted by the fastener to the hole.
 * It also has values for its physical size, i.e. diameter and length which can
 * be used to verify compatibility with {@code RackMountHole}. The
 * {@code ComponentMountFastener} overrides the {@code SysMLFullPort} operation
 * to insert a {@code ForceNewtons} object into a {@code MechanicalForceSignal}
 * before signal transmission and to set the weight that flows from (is
 * transferred by) the fastener.
 * 
 * @author ModelerOne
 *
 */
public class ComponentMountFastener extends SysMLFullPort
{
	@Value
	public DistanceMillimeters diameter;
	@Value
	public DistanceMillimeters length;

	@Flow(direction = SysMLFlowDirectionKind.out)
	public ForceNewtons weight;

	public ComponentMountFastener(SysMLBlock contextBlock, Long id)
	{
		super(contextBlock, id);
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
	{
		SysMLSignal result = null;
		if (object instanceof ForceNewtons)
		{
			ForceNewtons forceObject = (ForceNewtons)object;
			weight.value = forceObject.value;
			result = new MechanicalForceSignal(forceObject, new IInteger(forceObject.id));
		}
		else
			logger.severe("unrecognized object type: " + object.getClass().getSimpleName());
		return result;
	}

	@Override
	protected void createValues()
	{
		super.createValues();
		diameter = new DistanceMillimeters(5);
		length = new DistanceMillimeters(20);
	}

	@Override
	protected void createFlows()
	{
		weight = new ForceNewtons(0);
	}
}

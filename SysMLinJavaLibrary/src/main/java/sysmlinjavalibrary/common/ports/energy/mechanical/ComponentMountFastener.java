package sysmlinjavalibrary.common.ports.energy.mechanical;

import sysmlinjava.attributetypes.DistanceMillimeters;
import sysmlinjava.attributetypes.ForceNewtons;
import sysmlinjava.attributetypes.IInteger;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.ports.SysMLPort;
import sysmlinjava.states.StateBehaviorContext;
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
public class ComponentMountFastener extends SysMLPort
{
	@Attribute
	public DistanceMillimeters diameter;
	@Attribute
	public DistanceMillimeters length;

	@Attribute
	public ForceNewtons weight;

	public ComponentMountFastener(StateBehaviorContext eventDriven, Long id)
	{
		super(eventDriven, id);
	}

	@Override
	protected SysMLSignal signalFor(SysMLAnything object)
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
	protected void createAttributes()
	{
		super.createAttributes();
		diameter = new DistanceMillimeters(5);
		length = new DistanceMillimeters(20);
		weight = new ForceNewtons(0);
	}
}

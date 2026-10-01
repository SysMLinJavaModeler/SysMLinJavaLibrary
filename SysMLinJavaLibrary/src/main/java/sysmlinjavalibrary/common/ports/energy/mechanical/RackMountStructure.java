package sysmlinjavalibrary.common.ports.energy.mechanical;

import java.util.ArrayList;
import java.util.List;

import sysmlinjava.attributetypes.ForceNewtons;
import sysmlinjava.attributetypes.IInteger;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.javaannotations.ports.Port;
import sysmlinjava.ports.SysMLPort;
import sysmlinjava.states.StateBehaviorContext;

/**
 * The {@code RackMountStructure} is a SysMLinJava model of a typical equipment
 * rack. It is an extension of the {@code SysMLFullPort} that has four sets
 * (lists) of {@code RackMountHole}s which are nested ports that represent the
 * actual mount holes that equipment is mounted on with weight-transfering
 * {@code ComponentMountFaster}s. The {@code RackMountStructure} is simply a
 * container of nested ports. It performs no transmissions nor receptions.
 * 
 * @author ModelerOne
 *
 */
public class RackMountStructure extends SysMLPort
{
	@Port
	public List<RackMountHole> railLeftFront;
	@Port
	public List<RackMountHole> railRightFront;
	@Port
	public List<RackMountHole> railLeftRear;
	@Port
	public List<RackMountHole> railRightRear;

	@Attribute
	public List<ForceNewtons> weightOnRailLeftFront;
	@Attribute
	public List<ForceNewtons> weightOnRailRightFront;
	@Attribute
	public List<ForceNewtons> weightOnRailLeftRear;
	@Attribute
	public List<ForceNewtons> weightOnRailRightRear;

	@Attribute
	public IInteger numberHolesPerRail;

	public RackMountStructure(StateBehaviorContext contextBlock, Long id)
	{
		super(contextBlock, id);
	}

	@Override
	protected void createAttributes()
	{
		numberHolesPerRail = new IInteger(32);
		weightOnRailLeftFront = new ArrayList<>();
		weightOnRailRightFront = new ArrayList<>();
		weightOnRailLeftRear = new ArrayList<>();
		weightOnRailRightRear = new ArrayList<>();
		for (int weightIndex = 0; weightIndex < numberHolesPerRail.value; weightIndex++)
		{
			weightOnRailLeftFront.add(weightIndex, new ForceNewtons(0));
			weightOnRailRightFront.add(weightIndex, new ForceNewtons(0));
			weightOnRailLeftRear.add(weightIndex, new ForceNewtons(0));
			weightOnRailRightRear.add(weightIndex, new ForceNewtons(0));
		}
	}

	@Override
	protected void createPorts()
	{
		railLeftFront = new ArrayList<>();
		railRightFront = new ArrayList<>();
		railLeftRear = new ArrayList<>();
		railRightRear = new ArrayList<>();
		for (int holeIndex = 0; holeIndex < numberHolesPerRail.value; holeIndex++)
		{
			railLeftFront.add(holeIndex, new RackMountHole(context.get(), (long)holeIndex));
			railRightFront.add(holeIndex, new RackMountHole(context.get(), (long)holeIndex));
			railLeftRear.add(holeIndex, new RackMountHole(context.get(), (long)holeIndex));
			railRightRear.add(holeIndex, new RackMountHole(context.get(), (long)holeIndex));
		}
	}
}

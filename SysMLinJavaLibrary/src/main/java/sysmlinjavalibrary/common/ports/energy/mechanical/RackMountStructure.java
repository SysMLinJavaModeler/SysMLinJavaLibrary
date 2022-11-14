package sysmlinjavalibrary.common.ports.energy.mechanical;

import java.util.ArrayList;
import java.util.List;
import sysmlinjava.annotations.Flow;
import sysmlinjava.annotations.FullPort;
import sysmlinjava.annotations.Value;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.kinds.SysMLFlowDirectionKind;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjava.valuetypes.ForceNewtons;
import sysmlinjava.valuetypes.IInteger;

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
public class RackMountStructure extends SysMLFullPort
{
	@FullPort
	public List<RackMountHole> railLeftFront;
	@FullPort
	public List<RackMountHole> railRightFront;
	@FullPort
	public List<RackMountHole> railLeftRear;
	@FullPort
	public List<RackMountHole> railRightRear;

	@Flow(direction = SysMLFlowDirectionKind.in)
	public List<ForceNewtons> weightOnRailLeftFront;
	@Flow(direction = SysMLFlowDirectionKind.in)
	public List<ForceNewtons> weightOnRailRightFront;
	@Flow(direction = SysMLFlowDirectionKind.in)
	public List<ForceNewtons> weightOnRailLeftRear;
	@Flow(direction = SysMLFlowDirectionKind.in)
	public List<ForceNewtons> weightOnRailRightRear;

	@Value
	public IInteger numberHolesPerRail;

	public RackMountStructure(SysMLBlock contextBlock, Long id)
	{
		super(contextBlock, id);
	}

	@Override
	protected void createValues()
	{
		numberHolesPerRail = new IInteger(32);
	}

	@Override
	protected void createFlows()
	{
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
	protected void createFullPorts()
	{
		super.createFullPorts();
		railLeftFront = new ArrayList<>();
		railRightFront = new ArrayList<>();
		railLeftRear = new ArrayList<>();
		railRightRear = new ArrayList<>();
		for (int holeIndex = 0; holeIndex < numberHolesPerRail.value; holeIndex++)
		{
			railLeftFront.add(holeIndex, new RackMountHole(contextBlock.get(), (long)holeIndex));
			railRightFront.add(holeIndex, new RackMountHole(contextBlock.get(), (long)holeIndex));
			railLeftRear.add(holeIndex, new RackMountHole(contextBlock.get(), (long)holeIndex));
			railRightRear.add(holeIndex, new RackMountHole(contextBlock.get(), (long)holeIndex));
		}
	}
}

package sysmlinjavalibrary.common.ports.energy.mechanical;

import sysmlinjava.annotations.FullPort;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.ports.SysMLFullPort;

/**
 * The {@code ComponentMountStructure} is a SysMLinJava model of a typical item
 * of equipment mounted in a rack. It is an extension of the
 * {@code SysMLFullPort} that has four nested ports that represent the four
 * mount points with fasteners that are inserted into {@code RackMountHole}s.
 * The four nested {@code ComponentMountFastener} ports represent the actual
 * fasteners that transmit (transfer) the equipment's weight to
 * {@code RackMountHole}s. The {@code ComponentMountStructure} is simply a
 * container of nested ports. It has no flows and performs no transmissions nor
 * receptions.
 * 
 * @author ModelerOne
 *
 */
public class ComponentMountStructure extends SysMLFullPort
{
	@FullPort
	public ComponentMountFastener mountLeftFront;
	@FullPort
	public ComponentMountFastener mountRightFront;
	@FullPort
	public ComponentMountFastener mountLeftRear;
	@FullPort
	public ComponentMountFastener mountRightRear;

	public ComponentMountStructure(SysMLBlock contextBlock, Long id)
	{
		super(contextBlock, id);
	}

	@Override
	protected void createFullPorts()
	{
		mountLeftFront = new ComponentMountFastener(contextBlock.get(), 0L);
		mountRightFront = new ComponentMountFastener(contextBlock.get(), 1L);
		mountLeftRear = new ComponentMountFastener(contextBlock.get(), 2L);
		mountRightRear = new ComponentMountFastener(contextBlock.get(), 3L);
	}
}

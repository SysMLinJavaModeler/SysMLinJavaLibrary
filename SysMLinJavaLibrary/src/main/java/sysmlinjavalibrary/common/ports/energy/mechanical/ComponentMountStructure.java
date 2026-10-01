package sysmlinjavalibrary.common.ports.energy.mechanical;

import sysmlinjava.javaannotations.ports.Port;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.ports.SysMLPort;

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
public class ComponentMountStructure extends SysMLPort
{
	@Port
	public ComponentMountFastener mountLeftFront;
	@Port
	public ComponentMountFastener mountRightFront;
	@Port
	public ComponentMountFastener mountLeftRear;
	@Port
	public ComponentMountFastener mountRightRear;

	public ComponentMountStructure(SysMLPart context, Long id)
	{
		super(context, id);
	}

	@Override
	protected void createPorts()
	{
		mountLeftFront = new ComponentMountFastener(context.get(), 0L);
		mountRightFront = new ComponentMountFastener(context.get(), 1L);
		mountLeftRear = new ComponentMountFastener(context.get(), 2L);
		mountRightRear = new ComponentMountFastener(context.get(), 3L);
	}
}

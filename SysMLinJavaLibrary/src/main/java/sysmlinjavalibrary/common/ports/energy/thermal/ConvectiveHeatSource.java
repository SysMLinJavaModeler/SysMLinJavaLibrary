package sysmlinjavalibrary.common.ports.energy.thermal;

import sysmlinjava.attributetypes.HeatWatts;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.ports.SysMLPort;
import sysmlinjavalibrary.common.objects.energy.thermal.ConvectiveHeat;
import sysmlinjavalibrary.common.signals.ConvectiveHeatSignal;

/**
 * The {@code ConvectiveHeatSource} is the SysMLinJava model of a port that
 * transmits heat to a convective heat sink, i.e. from a
 * {@code ConvectiveHeatSink} port. The port supports the flow of heat out of
 * the port and is typically connected to a {@code ConvectiveHeatSink} port that
 * provides a flow of heat into its port. The heat that flows is as specified by
 * the {@code ConvectiveHeat} flow value.
 * <p>
 * As a minimal extension of the basic {@code SysMLFullPort} the
 * {@code ConvectiveHeatSource} provides an overridden implementation of the
 * method translating a received {@code ConvectiveHeat} object to a
 * {@code SysMLSignal} that contains the {@code ConvectiveHeat}. This method is
 * called by the {@code SysMLFullPort} after it is called to transmit a
 * {@code ConvectiveHeat} object to create the associated
 * {@code ConvectiveHeatSignal} that is to be transmitted the connected
 * {@code ConvectiveHeatSink}.
 * 
 * @author ModelerOne
 *
 */
public class ConvectiveHeatSource extends SysMLPort
{
	@Attribute
	public ConvectiveHeat heat;

	public ConvectiveHeatSource(SysMLPart parent, Long id)
	{
		super(parent, id);
	}

	@Override
	protected SysMLSignal signalFor(SysMLAnything object)
	{
		SysMLSignal result = null;
		if (object instanceof ConvectiveHeat)
		{
			ConvectiveHeat heatObject = (ConvectiveHeat)object;
			heat.heat.value = heatObject.heat.value; 
			result = new ConvectiveHeatSignal(heatObject);
		}
		else
			logger.severe("unrecognized object type: " + object.getClass().getSimpleName());
		return result;
	}

	@Override
	protected void createAttributes()
	{
		heat = new ConvectiveHeat(new HeatWatts(0));
	}
}

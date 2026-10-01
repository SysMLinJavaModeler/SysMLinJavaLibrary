package sysmlinjavalibrary.common.ports.energy.thermal;

import java.util.Optional;
import sysmlinjava.attributetypes.HeatWatts;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.ports.SysMLPort;
import sysmlinjava.states.StateBehaviorContext;
import sysmlinjavalibrary.common.objects.energy.thermal.ConvectiveHeat;
import sysmlinjavalibrary.common.signals.ConvectiveHeatSignal;

/**
 * The {@code ConvectiveHeatSink} is the SysMLinJava model of a port that
 * receives heat from a convective heat source, i.e. from a
 * {@code ConvectiveHeatSource} port. The port supports the flow of heat into
 * the port and is typically connected from a {@code ConvectiveHeatSource} port
 * that provides a flow of heat into its port. The heat that flows is as
 * specified by the {@code ConvectiveHeat} flow value.
 * <p>
 * As a minimal extension of the basic {@code SysMLFullPort} the
 * {@code ConvectiveHeatSink} provides an overridden implementation of the
 * method translating a received {@code SysMLSignal} to a {@code SysMLEvent}
 * that contains the {@code ConvectiveHeat}. This method is called by the
 * {@code SysMLFullPort} after it receives a {@code ConvectiveHeatSignal} to
 * create the associated {@code ConvectiveHeatEvent} that is to be submitted to
 * the event context block.
 * 
 * @author ModelerOne
 *
 */
public class ConvectiveHeatSink extends SysMLPort
{
	@Attribute
	public ConvectiveHeat heat;

	public ConvectiveHeatSink(StateBehaviorContext contextBlock, Long id)
	{
		super(contextBlock, Optional.of(contextBlock), id);
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof ConvectiveHeatSignal)
		{
			ConvectiveHeatSignal heatSignal = (ConvectiveHeatSignal)signal;
			heat.heat.value = heatSignal.heat.heat.value;
			result = new SysMLSignalEvent(heatSignal, "ConvectiveHeatEvent", 0L);
		}
		else
			logger.severe("unrecognized signal type: " + signal.getClass().getSimpleName());
		return result;
	}

	@Override
	protected void createAttributes()
	{
		heat = new ConvectiveHeat(new HeatWatts(0));
	}
}

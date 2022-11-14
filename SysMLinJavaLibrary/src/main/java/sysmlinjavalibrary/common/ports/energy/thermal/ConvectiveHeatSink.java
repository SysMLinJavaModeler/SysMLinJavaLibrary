package sysmlinjavalibrary.common.ports.energy.thermal;

import java.util.Optional;
import sysmlinjava.annotations.Flow;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.kinds.SysMLFlowDirectionKind;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjava.valuetypes.HeatWatts;
import sysmlinjavalibrary.common.events.ConvectiveHeatEvent;
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
public class ConvectiveHeatSink extends SysMLFullPort
{
	@Flow(direction = SysMLFlowDirectionKind.in)
	public ConvectiveHeat heat;

	public ConvectiveHeatSink(SysMLBlock contextBlock, Long id)
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
			result = new ConvectiveHeatEvent(heatSignal.heat);
		}
		else
			logger.severe("unrecognized signal type: " + signal.getClass().getSimpleName());
		return result;
	}

	@Override
	protected void createFlows()
	{
		heat = new ConvectiveHeat(new HeatWatts(0));
	}
}

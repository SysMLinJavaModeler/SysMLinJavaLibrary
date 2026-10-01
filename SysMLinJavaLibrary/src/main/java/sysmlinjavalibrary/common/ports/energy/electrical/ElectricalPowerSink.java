package sysmlinjavalibrary.common.ports.energy.electrical;

import java.util.Optional;
import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.attributetypes.CurrentAmps;
import sysmlinjava.attributetypes.ElectricalPower;
import sysmlinjava.attributetypes.FrequencyHertz;
import sysmlinjava.attributetypes.PotentialElectricalVolts;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.ports.SysMLPort;
import sysmlinjava.states.StateBehaviorContext;
import sysmlinjavalibrary.common.signals.ElectricalPowerSignal;

/**
 * The {@code ElectricalPowerSink} is the SysMLinJava model of a port that
 * transmits a power sink and receives power from a power source, i.e. from a
 * {@code ElectricalPowerSource} port. The port supports the flow of power into
 * the port and is typically connected from a {@code ElectricalPowerSource} port
 * that supports the flow of power into its port. The power can be of any
 * arbitrary type of electrical power, i.e. any frequency, voltage, or current
 * as specified by the {@code ElectricalPower} flow value.
 * <p>
 * As a minimal extension of the basic {@code SysMLFullPort} the
 * {@code ElectricalPowerSink} provides overridden implementations of the
 * methods for:<br>
 * 1) translating a received {@code SysMLSignal} to a {@code SysMLEvent} that
 * contains the {@code ElectricalPower}. This method is called by the
 * {@code SysMLFullPort} after it receives a {@code ConvectiveHeatSignal} to
 * create the associated {@code ConvectiveHeatEvent} that is to be submitted to
 * the event context block. and for:<br>
 * 2) constructing a {@code SysMLSignal} for a specified {@code ElectricalPower}
 * object. This method is called by the {@code SysMLFullPort} after it receives
 * a {@code ConvectiveHeat} object to create the associated
 * {@code ConvectiveHeatSignal} that is to be transmitted to a connected
 * {@code ElectricalPowerSource}.
 * 
 * @author ModelerOne
 *
 */
public class ElectricalPowerSink extends SysMLPort
{
	/**
	 * Electrical power flowing into the power sink
	 */
	@Attribute
	public ElectricalPower power;

	/**
	 * Hyperlink to the standard for the power sink
	 */
	@Hyperlink
	public SysMLHyperlink standard;

	/**
	 * Constructor
	 * 
	 * @param contextBlock block that contains the power sink
	 * @param id           unique identifier
	 */
	public ElectricalPowerSink(StateBehaviorContext contextBlock, Long id)
	{
		super(contextBlock, Optional.of(contextBlock), id);
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof ElectricalPowerSignal)
		{
			ElectricalPowerSignal powerSignal = (ElectricalPowerSignal)signal;
			power.frequency.value = powerSignal.power.frequency.value;
			power.potential.value = powerSignal.power.potential.value;
			power.current.value = powerSignal.power.current.value;
			result = new SysMLSignalEvent(signal, "ElectricalPowerEvent", 0L);
		}
		else
			logger.severe("unrecognized signal type: " + signal.getClass().getSimpleName());
		return result;
	}

	@Override
	protected SysMLSignal signalFor(SysMLAnything object)
	{
		SysMLSignal result = null;
		if (object instanceof ElectricalPower)
		{
			ElectricalPower powerObject = (ElectricalPower)object;
			power.frequency.value = powerObject.frequency.value;
			power.potential.value = powerObject.potential.value;
			power.current.value = powerObject.current.value;
			result = new ElectricalPowerSignal(powerObject);
		}
		else
			logger.severe("unrecognized object type: " + object.getClass().getSimpleName());
		return result;
	}

	@Override
	protected void createAttributes()
	{
		power = new ElectricalPower(new FrequencyHertz(0), new PotentialElectricalVolts(0), new CurrentAmps(0));
	}

	@Override
	protected void createSupportingInformationLinks()
	{
		standard = new SysMLHyperlink("", "http://tbd");
	}
}
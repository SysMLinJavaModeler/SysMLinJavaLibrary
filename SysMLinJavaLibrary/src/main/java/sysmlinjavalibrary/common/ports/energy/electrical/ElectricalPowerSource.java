package sysmlinjavalibrary.common.ports.energy.electrical;

import java.util.Optional;
import sysmlinjava.attributetypes.CurrentAmps;
import sysmlinjava.attributetypes.ElectricalPower;
import sysmlinjava.attributetypes.FrequencyHertz;
import sysmlinjava.attributetypes.PotentialElectricalVolts;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.ports.SysMLPort;
import sysmlinjavalibrary.common.signals.ElectricalPowerSignal;

/**
 * The {@code ElectricalPowerSource} is the SysMLinJava model of a port that
 * provides electrical power. The port supports the flow of power out of the
 * port and is typically connected to a {@code ElectricalPowerSink} port that
 * receives the flow of power into its port. The power can be of any arbitrary
 * type of electrical power, i.e. any frequency, voltage, or current as
 * specified by the {@code ElectricalPower} flow value.
 * <p>
 * As a minimal extension of the basic {@code SysMLFullPort} the
 * {@code ElecticalPowerPortSource} provides overridden implementations of the
 * methods for 1) translating a received {@code SysMLSignal} to a
 * {@code SysMLEvent} that contains the {@code ElectricalPower} and 2) for
 * constructing a {@code SysMLSignal} for a specified {@code ElectricalPower}
 * object. Typically, the 1st method would be used during the reception of power
 * as a source or sink and the 2nd method would be used during the transmission
 * of power as a source or sink, with the two connected ports recognizing which
 * is the source and which is the sink. This enables the power sink port to send
 * a power sink flow and the power source port to consequently send the actual
 * power source flow.
 * 
 * @author ModelerOne
 *
 */
public class ElectricalPowerSource extends SysMLPort
{
	@Attribute
	public ElectricalPower power;

	public ElectricalPowerSource(SysMLPart contextBlock, Long id)
	{
		super(contextBlock, Optional.of(contextBlock), id);
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
			result = new ElectricalPowerSignal(power);
		}
		else
			logger.severe("unrecognized object type: " + object.getClass().getSimpleName());
		return result;
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
			result = new SysMLSignalEvent(powerSignal, "ElectricalPowerEvent", 0L);
		}
		return result;
	}

	@Override
	protected void createAttributes()
	{
		power = new ElectricalPower(new FrequencyHertz(0), new PotentialElectricalVolts(0), new CurrentAmps(0));
	}
}

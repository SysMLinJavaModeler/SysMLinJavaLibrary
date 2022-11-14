package sysmlinjavalibrary.common.ports.energy.electrical;

import java.util.Optional;
import sysmlinjava.annotations.Flow;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.kinds.SysMLFlowDirectionKind;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjava.valuetypes.CurrentAmps;
import sysmlinjava.valuetypes.ElectricalPower;
import sysmlinjava.valuetypes.FrequencyHertz;
import sysmlinjava.valuetypes.PotentialElectricalVolts;
import sysmlinjavalibrary.common.events.ElectricalPowerEvent;
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
public class ElectricalPowerSource extends SysMLFullPort
{
	@Flow(direction = SysMLFlowDirectionKind.inout)
	public ElectricalPower power;

	public ElectricalPowerSource(SysMLBlock contextBlock, Long id)
	{
		super(contextBlock, Optional.of(contextBlock), id);
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
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
			result = new ElectricalPowerEvent(powerSignal.power);
		}
		return result;
	}

	@Override
	protected void createFlows()
	{
		power = new ElectricalPower(new FrequencyHertz(0), new PotentialElectricalVolts(0), new CurrentAmps(0));
	}
}

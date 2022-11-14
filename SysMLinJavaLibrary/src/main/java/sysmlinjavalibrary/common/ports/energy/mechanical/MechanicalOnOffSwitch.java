package sysmlinjavalibrary.common.ports.energy.mechanical;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjavalibrary.common.events.OnOffSwitchEvent;
import sysmlinjavalibrary.common.signals.OnOffSwitchSignal;

/**
 * The {@code MechanicalOnOffSwitch} is a SysMLinJava model of a port that
 * represents a mechanical on/off switch. The port receives
 * {@code OnOffSwitchSignals} that contain a on/off indication for the switch
 * simulating the switch being switched on or off.
 * <p>
 * * As a minimal extension of the basic {@code SysMLFullPort} the
 * {@code MechanicalOnOffSwitch} provides an overridden implementation of the
 * method for translating a received {@code SysMLSignal} to a {@code SysMLEvent}
 * that contains the {@code OnOffSwitch}. This method is called by the
 * {@code SysMLFullPort} after it receives a {@code OnOffSwitchSignal} to create
 * the associated {@code OnOffSwitchEvent} that is to be submitted to the event
 * context block.
 * 
 * @author ModelerOne
 *
 */
public class MechanicalOnOffSwitch extends SysMLFullPort
{
	public MechanicalOnOffSwitch(SysMLBlock contextBlock, Long id)
	{
		super(contextBlock, Optional.of(contextBlock), id);
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof OnOffSwitchSignal)
		{
			OnOffSwitchSignal switchSignal = (OnOffSwitchSignal)signal;
			result = new OnOffSwitchEvent(switchSignal.onOffSwitch);
		}
		return result;
	}

}

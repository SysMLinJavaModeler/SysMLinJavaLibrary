package sysmlinjavalibrary.common.ports.energy.mechanical;

import sysmlinjava.common.SysMLAnything;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.ports.SysMLPort;
import sysmlinjavalibrary.common.objects.information.OnOffSwitch;
import sysmlinjavalibrary.common.signals.OnOffSwitchSignal;

/**
 * The {@code MechanicalOnOffContact} is a SysMLinJava model of a port that
 * represents an object that makes contact with a mechanical on/off switch for
 * the purposes of toggling the switch between on and off states. The port
 * transmits {@code OnOffSwitchSignal}s that contain an on/off indication for the
 * switch simulating the contact switching the switch on or off.
 * <p>
 * * As a minimal extension of the basic {@code SysMLFullPort} the
 * {@code MechanicalOnOffSwitchContact} provides an overridden implementation of the
 * method for constructing a {@code OnOffSwitch} object into a {@code SysMLSignal}. This method is called by the
 * {@code SysMLFullPort} after it receives a {@code OnOffSwitch} that is to be transmitted to
 * the associated {@code MechanicalOnOffSwitch}.
 * 
 * @author ModelerOne
 *
 */
public class MechanicalOnOffSwitchContact extends SysMLPort
{
	public MechanicalOnOffSwitchContact(SysMLPart contextBlock, Long id)
	{
		super(contextBlock, id);
	}

	@Override
	protected SysMLSignal signalFor(SysMLAnything object)
	{
		SysMLSignal result = null;
		if (object instanceof OnOffSwitch)
		{
			OnOffSwitch onOffSwitch = (OnOffSwitch)object;
			result = new OnOffSwitchSignal(onOffSwitch);
		}
		return result;
	}
}

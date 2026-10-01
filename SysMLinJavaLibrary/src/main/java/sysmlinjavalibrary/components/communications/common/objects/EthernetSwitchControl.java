package sysmlinjavalibrary.components.communications.common.objects;

import java.util.Optional;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.attributes.Attribute;

public class EthernetSwitchControl extends SysMLAnything
{	
	@Attribute
	public Optional<EthernetSwitchStatesEnum> state;

	public EthernetSwitchControl(Optional<EthernetSwitchStatesEnum> toState)
	{
		super();
		this.state = toState;
	}
}

package sysmlinjavalibrary.components.communications.common.objects;

import java.util.Optional;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;

public class EthernetSwitchControl extends SysMLClass
{	
	@Attribute
	public Optional<EthernetSwitchStatesEnum> state;

	public EthernetSwitchControl(Optional<EthernetSwitchStatesEnum> toState)
	{
		super();
		this.state = toState;
	}
}

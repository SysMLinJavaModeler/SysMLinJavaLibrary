package sysmlinjavalibrary.components.communications.common.objects;

import java.util.Optional;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;

public class HAIPEControl extends SysMLClass
{
	@Attribute
	public Optional<HAIPEStatesEnum> toState;

	public HAIPEControl(Optional<HAIPEStatesEnum> toState)
	{
		super();
		this.toState = toState;
	}
}

package sysmlinjavalibrary.components.communications.common.objects;

import java.util.Optional;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.attributes.Attribute;

public class HAIPEControl extends SysMLAnything
{
	@Attribute
	public Optional<HAIPEStatesEnum> toState;

	public HAIPEControl(Optional<HAIPEStatesEnum> toState)
	{
		super();
		this.toState = toState;
	}
}

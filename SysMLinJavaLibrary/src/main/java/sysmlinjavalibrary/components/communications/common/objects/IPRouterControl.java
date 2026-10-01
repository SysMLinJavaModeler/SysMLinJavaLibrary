package sysmlinjavalibrary.components.communications.common.objects;

import java.util.Optional;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.attributes.Attribute;

public class IPRouterControl extends SysMLAnything
{
	@Attribute
	public Optional<IPRouterStatesEnum> toState;

	public IPRouterControl(Optional<IPRouterStatesEnum> toState)
	{
		super();
		this.toState = toState;
	}
}

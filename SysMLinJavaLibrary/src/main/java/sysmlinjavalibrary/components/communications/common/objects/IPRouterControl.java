package sysmlinjavalibrary.components.communications.common.objects;

import java.util.Optional;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;

public class IPRouterControl extends SysMLClass
{
	@Attribute
	public Optional<IPRouterStatesEnum> toState;

	public IPRouterControl(Optional<IPRouterStatesEnum> toState)
	{
		super();
		this.toState = toState;
	}
}

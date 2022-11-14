package sysmlinjavalibrary.components.communications.common.objects;

import java.util.Optional;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;

public class SIPRNetRouterControl extends SysMLClass
{
	@Attribute
	public Optional<SIPRNetRouterStatesEnum> toState;

	public SIPRNetRouterControl(Optional<SIPRNetRouterStatesEnum> toState)
	{
		super();
		this.toState = toState;
	}
}

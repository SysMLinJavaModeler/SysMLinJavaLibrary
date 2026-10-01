package sysmlinjavalibrary.components.communications.common.objects;

import java.util.Optional;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.attributes.Attribute;

public class SIPRNetRouterControl extends SysMLAnything
{
	@Attribute
	public Optional<SIPRNetRouterStatesEnum> toState;

	public SIPRNetRouterControl(Optional<SIPRNetRouterStatesEnum> toState)
	{
		super();
		this.toState = toState;
	}
}

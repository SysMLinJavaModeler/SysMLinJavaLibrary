package sysmlinjavalibrary.components.communications.common.objects;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.valuetypes.SysMLEnumeration;

public class SIPRNetRouterStatesEnum extends SysMLEnumeration<SIPRNetRouterStatesEnum>
{
	@Attribute public static final SIPRNetRouterStatesEnum Initial = new SIPRNetRouterStatesEnum("Initial", 0);
	@Attribute public static final SIPRNetRouterStatesEnum PowerOff = new SIPRNetRouterStatesEnum("PowerOff", 1);
	@Attribute public static final SIPRNetRouterStatesEnum Initializing = new SIPRNetRouterStatesEnum("Initializing", 2);
	@Attribute public static final SIPRNetRouterStatesEnum Operational = new SIPRNetRouterStatesEnum("Operational", 3);
	@Attribute public static final SIPRNetRouterStatesEnum Final = new SIPRNetRouterStatesEnum("Final", 4);
	public static final SIPRNetRouterStatesEnum[] values = {Initial, PowerOff, Initializing, Operational, Final};

	private SIPRNetRouterStatesEnum(String name, int ordinal)
	{
		super(name, ordinal);
	}

	public static SIPRNetRouterStatesEnum valueOf(String name)
	{
		return SysMLEnumeration.valueOf(name, values);
	}

	public static SIPRNetRouterStatesEnum valueOf(int ordinal)
	{
		return SysMLEnumeration.valueOf(ordinal, values);
	}

	public static SIPRNetRouterStatesEnum[] values()
	{
		return values;
	}
}

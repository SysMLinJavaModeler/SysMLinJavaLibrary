package sysmlinjavalibrary.components.communications.common.objects;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.valuetypes.SysMLEnumeration;

public class IPRouterStatesEnum extends SysMLEnumeration<IPRouterStatesEnum>
{
	@Attribute public static final IPRouterStatesEnum Initial = new IPRouterStatesEnum("Initial", 0);
	@Attribute public static final IPRouterStatesEnum PowerOff = new IPRouterStatesEnum("PowerOff", 1);
	@Attribute public static final IPRouterStatesEnum Initializing = new IPRouterStatesEnum("Initializing", 2);
	@Attribute public static final IPRouterStatesEnum Operational = new IPRouterStatesEnum("Operational", 3);
	@Attribute public static final IPRouterStatesEnum Final = new IPRouterStatesEnum("Final", 4);
	public static final IPRouterStatesEnum[] values = {Initial, PowerOff, Initializing, Operational, Final};
	
	private IPRouterStatesEnum(String name, int ordinal)
	{
		super(name, ordinal);
	}

	public static IPRouterStatesEnum valueOf(String name)
	{
		return SysMLEnumeration.valueOf(name, values);
	}

	public static IPRouterStatesEnum valueOf(int ordinal)
	{
		return SysMLEnumeration.valueOf(ordinal, values);
	}

	public static IPRouterStatesEnum[] values()
	{
		return values;
	}
}
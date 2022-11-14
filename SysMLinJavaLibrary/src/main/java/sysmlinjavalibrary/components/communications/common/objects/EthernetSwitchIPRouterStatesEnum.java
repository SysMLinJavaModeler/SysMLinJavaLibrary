package sysmlinjavalibrary.components.communications.common.objects;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.valuetypes.SysMLEnumeration;

public class EthernetSwitchIPRouterStatesEnum extends SysMLEnumeration<EthernetSwitchIPRouterStatesEnum>
{
	@Attribute public static final EthernetSwitchIPRouterStatesEnum Initial      = new EthernetSwitchIPRouterStatesEnum("Initial", 0);
	@Attribute public static final EthernetSwitchIPRouterStatesEnum PowerOff     = new EthernetSwitchIPRouterStatesEnum("PowerOff", 1);
	@Attribute public static final EthernetSwitchIPRouterStatesEnum Initializing = new EthernetSwitchIPRouterStatesEnum("Initializing", 2);
	@Attribute public static final EthernetSwitchIPRouterStatesEnum Operational  = new EthernetSwitchIPRouterStatesEnum("Operational", 3);
	@Attribute public static final EthernetSwitchIPRouterStatesEnum Final        = new EthernetSwitchIPRouterStatesEnum("Final", 4);
	@Attribute public static final EthernetSwitchIPRouterStatesEnum[] values = {Initial, PowerOff, Initializing, Operational, Final};
	
	private EthernetSwitchIPRouterStatesEnum(String name, int ordinal)
	{
		super(name, ordinal);
	}

	public static EthernetSwitchIPRouterStatesEnum valueOf(String name)
	{
		return SysMLEnumeration.valueOf(name, values);
	}

	public static EthernetSwitchIPRouterStatesEnum valueOf(int ordinal)
	{
		return SysMLEnumeration.valueOf(ordinal, values);
	}

	public static EthernetSwitchIPRouterStatesEnum[] values()
	{
		return values;
	}
}
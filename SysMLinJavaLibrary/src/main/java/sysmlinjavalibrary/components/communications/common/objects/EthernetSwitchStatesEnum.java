package sysmlinjavalibrary.components.communications.common.objects;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.valuetypes.SysMLEnumeration;

public class EthernetSwitchStatesEnum extends SysMLEnumeration<EthernetSwitchStatesEnum>
{
	@Attribute public static final EthernetSwitchStatesEnum Initial = new EthernetSwitchStatesEnum("Initial", 0);
	@Attribute public static final EthernetSwitchStatesEnum PowerOff = new EthernetSwitchStatesEnum("PowerOff", 1);
	@Attribute public static final EthernetSwitchStatesEnum Initializing = new EthernetSwitchStatesEnum("Initializing", 2);
	@Attribute public static final EthernetSwitchStatesEnum Operational = new EthernetSwitchStatesEnum("Operational", 3);
	@Attribute public static final EthernetSwitchStatesEnum Final = new EthernetSwitchStatesEnum("Final", 4);
	public static final EthernetSwitchStatesEnum[] values = {Initial, PowerOff, Initializing, Operational, Final};
	
	private EthernetSwitchStatesEnum(String name, int ordinal)
	{
		super(name, ordinal);
	}

	public static EthernetSwitchStatesEnum valueOf(String name)
	{
		return SysMLEnumeration.valueOf(name, values);
	}

	public static EthernetSwitchStatesEnum valueOf(int ordinal)
	{
		return SysMLEnumeration.valueOf(ordinal, values);
	}

	public static EthernetSwitchStatesEnum[] values()
	{
		return values;
	}
}
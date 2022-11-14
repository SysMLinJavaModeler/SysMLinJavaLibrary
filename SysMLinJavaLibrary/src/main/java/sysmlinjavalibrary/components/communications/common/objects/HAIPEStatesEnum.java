package sysmlinjavalibrary.components.communications.common.objects;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.valuetypes.SysMLEnumeration;

public class HAIPEStatesEnum extends SysMLEnumeration<HAIPEStatesEnum>
{
	@Attribute public static final HAIPEStatesEnum Initial = new HAIPEStatesEnum("Initial", 0);
	@Attribute public static final HAIPEStatesEnum PowerOff = new HAIPEStatesEnum("PowerOff", 1);
	@Attribute public static final HAIPEStatesEnum Initializing = new HAIPEStatesEnum("Initializing", 2);
	@Attribute public static final HAIPEStatesEnum Operational = new HAIPEStatesEnum("Operational", 3);
	@Attribute public static final HAIPEStatesEnum Final = new HAIPEStatesEnum("Final", 4);
	public static final HAIPEStatesEnum[] values = {Initial, PowerOff, Initializing, Operational, Final};
	
	private HAIPEStatesEnum(String name, int ordinal)
	{
		super(name, ordinal);
	}

	public static HAIPEStatesEnum valueOf(String name)
	{
		return SysMLEnumeration.valueOf(name, values);
	}

	public static HAIPEStatesEnum valueOf(int ordinal)
	{
		return SysMLEnumeration.valueOf(ordinal, values);
	}

	public static HAIPEStatesEnum[] values()
	{
		return values;
	}
}
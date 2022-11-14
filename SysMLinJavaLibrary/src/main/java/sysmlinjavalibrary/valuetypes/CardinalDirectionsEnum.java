package sysmlinjavalibrary.valuetypes;

import static java.lang.Math.PI;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.valuetypes.DirectionRadians;
import sysmlinjava.valuetypes.SysMLEnumeration;

public class CardinalDirectionsEnum extends SysMLEnumeration<CardinalDirectionsEnum>
{
	@Attribute public static final CardinalDirectionsEnum north = new CardinalDirectionsEnum("north", 0, new DirectionRadians(0));
	@Attribute public static final CardinalDirectionsEnum east = new CardinalDirectionsEnum("east", 1, new DirectionRadians(PI / 2));
	@Attribute public static final CardinalDirectionsEnum south = new CardinalDirectionsEnum("south", 2, new DirectionRadians(PI));
	@Attribute public static final CardinalDirectionsEnum west = new CardinalDirectionsEnum("west", 3, new DirectionRadians(PI * 1.5));
	
	public static final CardinalDirectionsEnum[] values = {north, east, south, west};

	public DirectionRadians direction;

	private CardinalDirectionsEnum(String name, int ordinal, DirectionRadians direction)
	{
		super(name, ordinal);
		this.direction = direction;
	}

	public static CardinalDirectionsEnum valueOf(String name)
	{
		return SysMLEnumeration.valueOf(name, values);
	}

	public static CardinalDirectionsEnum valueOf(int ordinal)
	{
		return SysMLEnumeration.valueOf(ordinal, values);
	}

	public static CardinalDirectionsEnum[] values()
	{
		return values;
	}
}

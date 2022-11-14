package sysmlinjavalibrary.valuetypes;

public class CardinalDirectionsEnumMain
{

	public static void main(String[] args)
	{
		CardinalDirectionsEnum card = CardinalDirectionsEnum.south;
		System.out.println("toString(): " + card.toString());
		System.out.println("valueOf(north): " + CardinalDirectionsEnum.valueOf("north"));
		System.out.println("valueOf(3): " + CardinalDirectionsEnum.valueOf(3));
		System.out.println(String.format("values(i): %s %s %s %s", CardinalDirectionsEnum.values()[0], CardinalDirectionsEnum.values()[1], CardinalDirectionsEnum.values()[2], CardinalDirectionsEnum.values()[3]));
		System.out.println(String.format("values(i): %s %s %s %s",
			CardinalDirectionsEnum.values()[0].name(),
			CardinalDirectionsEnum.values()[1].name(),
			CardinalDirectionsEnum.values()[2].name(),
			CardinalDirectionsEnum.values()[3].name()));
		CardinalDirectionsEnum card0 = CardinalDirectionsEnum.valueOf(0);
		CardinalDirectionsEnum card1 = CardinalDirectionsEnum.valueOf(1);
		CardinalDirectionsEnum card2 = CardinalDirectionsEnum.valueOf(2);
		CardinalDirectionsEnum card3 = CardinalDirectionsEnum.valueOf(3);
		System.out.println(String.format("values(i): %s %s %s %s", card0, card1, card2, card3));
		CardinalDirectionsEnum[] vals = CardinalDirectionsEnum.values();
		System.out.println(String.format("values(i): %s %s %s %s", vals[0], vals[1], vals[2], vals[3]));
	}
}

package cards;

public interface ICard extends Comparable<Object> {
	public static final int SPADES = 0;
	public static final int HEARTS = 1;
	public static final int DIAMONDS = 2;
	public static final int CLUBS = 3;

	public int getSuit();
	public int getRank();
}

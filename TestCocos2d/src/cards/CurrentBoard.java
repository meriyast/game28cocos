package cards;

import java.util.ArrayList;
import java.util.List;

public class CurrentBoard {
	private List<Card> cardsPlayed;
	Player currentHolder;
	int totalPoints;
	private boolean wasCut;
	private Game gameRef;
	private Card holdingCard;
	private boolean debug=true;
	
	public Player getCurrentHolder() {
		return currentHolder;
	}
	public void setCurrentHolder(Player currentHolder) {
		this.currentHolder = currentHolder;
	}

	public int getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
	
	
	public CurrentBoard(Player currentHolder, boolean wasCut, int totalPoints) {
		this.currentHolder = currentHolder;
		this.totalPoints = totalPoints;
		wasCut = false;
		cardsPlayed = new ArrayList<Card>();
	}
	
	public CurrentBoard() {
		cardsPlayed = new ArrayList<Card>();
		this.currentHolder = null;
		this.totalPoints = 0;
		wasCut = false;
	}
	public List<Card> getCardsPlayed() {
		return cardsPlayed;
	}
	
	public int getNumPlaysDone(){
		return cardsPlayed.size();
	}
	
	public void setCardsPlayed(List<Card> cardsPlayed) {
		this.cardsPlayed = cardsPlayed;
	}
	
	public void addCardToBoard(Card card){
		this.cardsPlayed.add(card);
	}
	
	public int getRunningSuite(){
		return cardsPlayed.get(0).getSuit();
	}
	public boolean getWasCut() {
		return wasCut;
	}
	public void setWasCut(boolean wasCut) {
		this.wasCut = wasCut;
	}

	
	
	public void updateBoard(Player p, Card played) {
		cardsPlayed.add(played);
		totalPoints = totalPoints + played.getMyvalue();

		if (cardsPlayed.size() == 1) {
			getGameRef().setBoardSuite(played.getSuit());
			setCurrentHolder(p);
			setHoldingCard(played);
			if (debug)
				System.out.println("Now holdingCard:"
						+ played.getUniqueCardValue());
		} else if (played.getSuit() == getGameRef().getBoardSuite()
				&& played.getRank() < holdingCard.getRank()) {
			setCurrentHolder(p);
			setHoldingCard(played);
			if (debug)
				System.out.println("Now holdingCard:" + played.getUniqueCardValue());

		} else if (gameRef.getTrump().getTrumpCard().getSuit() == played
				.getSuit() && wasCut == false) {
			setWasCut(true);
			gameRef.getTrump().setOpen(true);
			setCurrentHolder(p);
			setHoldingCard(played);
			Card playersTrump = gameRef.getTrump().getBidOwner().getTrump();
			gameRef.getTrump().getBidOwner().getMyHand().addCard(playersTrump);
			if (debug)
				System.out.println("Now holdingCard:" + played.getUniqueCardValue());

		} else if (gameRef.getTrump().getTrumpCard().getSuit() == played
				.getSuit() && wasCut) {
			if (played.getRank() < getHoldingCard().getRank()) {
				setCurrentHolder(p);
				setHoldingCard(played);
				if (debug)
					System.out.println("Now holdingCard:" + played.getUniqueCardValue());
			}
			if (debug)
				System.out
						.println("#######################################################");
		}
	}
	
	
	public Game getGameRef() {
		return gameRef;
	}
	public void setGameRef(Game gameRef) {
		this.gameRef = gameRef;
	}
	public Card getHoldingCard() {
		return holdingCard;
	}
	public void setHoldingCard(Card holdingCard) {
		this.holdingCard = holdingCard;
	}
}

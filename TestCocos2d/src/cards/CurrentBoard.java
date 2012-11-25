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
		debug = false;
		cardsPlayed.add(played);
		Card trumpCardRef = gameRef.getTrump().getTrumpCard();
		totalPoints = totalPoints + played.getMyvalue();

		System.out.println("First cut: "+gameRef.isJustOpenedTrump());
		//First card. will have cardsPlayed.size() = 1.
		if (cardsPlayed.size() == 1) {
			getGameRef().setBoardSuite(played.getSuit());
			setCurrentHolder(p);
			setHoldingCard(played);
			if(debug) System.out.println("cardsPlayed.size() == 1");
			
		//Normal case. card belong to boardSuite, and is bigger.
		} else if (played.getSuit() == getGameRef().getBoardSuite()
				&& played.getRank() < holdingCard.getRank() &&
				!wasCut) {
			setCurrentHolder(p);
			setHoldingCard(played);
			if(debug) System.out.println("played.getSuit() == getGameRef().getBoardSuite() && played.getRank() < holdingCard.getRank()");
		
		//cardSuite != boardSuite. cardSuite = trumpSuite.
		} else if (trumpCardRef.getSuit() == played.getSuit() &&
				played.getSuit()!= gameRef.getBoardSuite() &&
				getGameRef().isJustOpenedTrump() ) {
			
			
			getGameRef().setJustOpenedTrump(false);
			setWasCut(true);
			setCurrentHolder(p);
			setHoldingCard(played);

			boolean alreadyIncluded = false;
			Player trumpOwner = gameRef.getTrump().getBidOwner();
			for(Card card: trumpOwner.getMyHand().getMyCards()){
				if(card.equals(trumpCardRef))
					alreadyIncluded = true;
			}
			
			if(!alreadyIncluded){
				gameRef.getTrump().getBidOwner().getMyHand().addCard(trumpCardRef);
			}
			
			if(debug) System.out.println("gameRef.getTrump().getTrumpCard().getSuit() == played .getSuit() && wasCut == false");

		} else if (trumpCardRef.getSuit() == played.getSuit() && 
					wasCut && gameRef.getTrump().isOpen() &&
					played.getRank() < getHoldingCard().getRank() ) {
			setCurrentHolder(p);
			setHoldingCard(played);
				if(debug) System.out.println("played.getRank() < getHoldingCard().getRank()");
				
		}
		else if (trumpCardRef.getSuit() == played.getSuit() && 
				!wasCut && gameRef.getTrump().isOpen() ) {
			setWasCut(true);
			setCurrentHolder(p);
			setHoldingCard(played);
				if(debug) System.out.println("played.getRank() < getHoldingCard().getRank()");
			
		}
		debug = true;
		if (debug){
			System.out.println("Now holdingCard:" + getHoldingCard().getUniqueCardValue());
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

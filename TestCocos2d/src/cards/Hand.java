package cards;

import java.util.ArrayList;

public class Hand {
	

	private ArrayList<Card> myHand;
	
	public Hand() {
		myHand = new ArrayList<Card>();
	}
	
	public void addCard(Card card) {
		myHand.add(card);
	}
	
	public void removeCard(Card card) {
		myHand.remove(card);
	}
	
	public ArrayList<Card> getMyCards() {
		return myHand;
	}
	
	public int numCardsInHand(){
		return myHand.size();
	}
	
	public int getTotalPointsinHand(){
		int totalPoints =0;
		for (Card card:myHand){
			totalPoints = totalPoints + card.getMyvalue();
		}
		return totalPoints;
	}
	
	
	
}

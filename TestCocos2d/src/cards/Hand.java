package cards;

import java.util.ArrayList;
import java.util.List;

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
	
	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Hand guest = (Hand) obj;

    	if(areSameCards(guest.getMyCards()) )
    		return true;
    	else
    		return false;
    }

	public boolean areSameCards(List<Card> guestCards){
		if(myHand.size() != guestCards.size())
			return false;
        for(int index=0;index<myHand.size();index++){
        	if(!myHand.get(index).equals(guestCards.get(index)))
        		return false;
        }
        return true;
	}
}

package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Deck implements Iterator<Object> {
	protected ArrayList<Card> myCardList;
	protected int myIndex;
	
	
	public Deck(){
		myCardList = new ArrayList<Card>();
		for(int suit = ICard.SPADES; suit <= ICard.CLUBS; suit++){
			for (int rank = 0; rank <8; rank++){
				myCardList.add(new Card(suit,rank));
			}
		}
		
		shuffle();
	}
	
	public String printAll(){
		Deck deck ;
		deck = this;
		String fullString = "";
		while(deck.hasNext()){
			Card card = (Card) deck.next();
			fullString = fullString+ " \n "+card.toString();
		}
		return fullString;
	}
	
	
	protected void shuffle(){
		Collections.shuffle(myCardList);
		myIndex = 0;
	}

	public boolean hasNext() {
		return myIndex < myCardList.size();
	}

	public Object next() {
		ICard card = (ICard) myCardList.get(myIndex);
		myIndex++;
		return card;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}




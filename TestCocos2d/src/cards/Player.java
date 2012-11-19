package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

	private String name;
	private Hand myHand;
	private int points;
	private Team team;
	private boolean isAI;
	private Game gameReference;
	
	public Player(String name, int points, Game game) {
		this.name = name;
		this.points = points;
		myHand = new Hand();
		gameReference = game;
		isAI = false;
		team = null;
	}
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Hand getMyHand() {
		return myHand;
	}

	public void setMyHand(Hand myHand) {
		this.myHand = myHand;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public boolean isMyTurn(Player player){
		if (this.getName() == player.getName()){
			return true;
		}
		else{
			return false;
		}
	}
	

	public void printPlayer() {
		System.out.println("\n\n");
		System.out.println(this.toString());
		System.out.println("******************");

		Hand hand = this.getMyHand();
		List<Card> cards = hand.getMyCards();
			for(Card card:cards){
				System.out.println(card.toString());
			}
	}

	public boolean getIsAI() {
		return isAI;
	}

	public void setIsAI(boolean isAI) {
		this.isAI = isAI;
	}
	
	public int getTotalPointsinHand(List<Card> cardList){
		int totalPoints =0;
		for (Card card:cardList){
			totalPoints = totalPoints + card.getMyvalue();
		}
		return totalPoints;
	}
	
	
//	public Card aiPlay(CurrentBoard board, GameStatus status){
	
	public TrumpCandidate aiPlayBid() {
		Map<Integer,Float> cardsSuiteCounted = new HashMap<Integer, Float>();
		float suiteValue = 0;
		int suiteSelected = 0;
		int bidValue=0;
		Card cardSelected;
		/*
		 * Groups the card by suite, put into map. key- suit value(int 0-3). value- x.y(float) 
		 * x= no of occurrence of suite(count), y= total value in the suite. 
		 * eg: (key: 3) -> (value:2.5) means for clubs, there are 2 cards, and the sum of values
		 * of all cards = 5(Should be a Jack and Nine. )
		 */
		
		for(Card card: this.getMyHand().getMyCards()){
			float value = card.getMyvalue();
//			System.out.print("\n"+carddx.getUniqueCardValue()+"=>"+card.getMyvalue());
			value = value /10;
			
			if(cardsSuiteCounted.containsKey(card.getSuit())){
				float mapValue = (float)cardsSuiteCounted.get(card.getSuit())+ ( 1f )+ (value);
				cardsSuiteCounted.put(card.getSuit(), mapValue);
			}
			else{
				cardsSuiteCounted.put(card.getSuit(), 1f + value);
			}
		}

		for (Integer key : cardsSuiteCounted.keySet()) {
			if(suiteValue < cardsSuiteCounted.get(key)){
				suiteSelected = key;
				suiteValue = cardsSuiteCounted.get(key);
			}
		}
		/* 
		 * After looping through all the suites in the loop, we will now know that
		 * The suite with highest number of cards(in the hand) will be saved at suiteSelected
		 * Now, If we have a tie, the suite with greater value will be selected.  
		 */
		List<Card> suiteSelectedCards = new ArrayList<Card>();
		for(Card card: this.getMyHand().getMyCards()){
			if(card.getSuit() == suiteSelected){
				suiteSelectedCards.add(card);
			}
		}
		Collections.sort(suiteSelectedCards);
		
		/*
		 *Select the second biggest card from the suiteSelected, to set as trump. 
		 * 
		 */
		
		if(suiteSelectedCards.size() == 1)
			cardSelected = suiteSelectedCards.get(0);
		else
			cardSelected = suiteSelectedCards.get(1);
		
		System.out.println("\nSuiteValue: "+suiteValue);
		if(suiteValue <=2 ) 
			bidValue = 14;
		else if(suiteValue <=3)
			bidValue = 15;
		else 
			bidValue = 16;
		
		return new TrumpCandidate(cardSelected, bidValue);
	}
	
	
	
	public Card aiPlayGame(Game game){
		if(!this.getName().equals(game.currentPlayer))
			return new Card();
		
		
		return null;
	}
	
	
	
	
	public void describePlayer(){
		System.out.println("Describing player: "+ this.getName());
		System.out.print("Hand: ");
		for(Card card:getMyHand().getMyCards()){
			System.out.print(card.getUniqueCardValue()+", ");
		}
	}

	public Game getGameReference() {
		return gameReference;
	}

	public void setGameReference(Game gameReference) {
		this.gameReference = gameReference;
	}
}


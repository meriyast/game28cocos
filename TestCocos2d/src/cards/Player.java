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
	private Card trump;
	
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

		cardsSuiteCounted = getSuiteMap();

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
		
		
		if(getMyHand().getMyCards().size() >= 7){
			/*
			 * defaulting sending bidValue to 13 if there are 7 or more cards.
			 * So that, if the suiteValue <5, a bid of 16 won't be made. 
			 */
			bidValue = 13;
			float pointTotal = (float) ((float)suiteValue - Math.floor(suiteValue));
			if(suiteValue > 5 && pointTotal >=.4){
				bidValue = 21;
			}
		}
		return new TrumpCandidate(cardSelected, bidValue);
	}
	
	
	public Card aiPlayGame(){
		Game game = gameReference;
		Card returnCard;
		boolean ourHold =false;
		boolean wasCut = false;
		if(!this.getName().equals(game.getPlayerTurn().getName()))
			return new Card();
		
		
		 //BoardSize = 0 means the board is empty, and I've to start the game. 
		if( game.getBoard().getCardsPlayed().size() ==0){
			
				returnCard = getBestCardToStart();
		}
		//BoardSize !=0. I am not the first player. 
		else{
			Team team = game.getBoard().getCurrentHolder().getTeam();
			wasCut = game.getBoard().getWasCut();
			//If my team holds the board, I should support. 
			if(this.getTeam().toString().equals(team.toString())){
				ourHold = true;
				
				
				int suiteId = game.getBoard().getCardsPlayed().get(0).getSuit();
				returnCard = getBiggestFromSuite(suiteId);
			}
			//If my team doesn't hold the board, I should attack. 
			else{
				ourHold = false;
			}
		}
		
		return null;
	}
	
	
	/**
	 * Returns the minimum card from the hand.
	 * if include:true, minCard from the specific suite.
	 * if include:false, minCard from everything outside suite.
	 * 
	 * @param suiteId
	 * @param includeSuite
	 * @return
	 */
	private Card getMinCardFromHand(int suiteId, boolean include){
		Card returnCard = null;
		int rank = -1;
		for(Card card: getMyHand().getMyCards()){
			if( card.getSuit() == suiteId && include){
				if(card.getRank()> rank ){
					rank = card.getRank();
					returnCard = card;
				}
			}
				
			if (card.getSuit() != suiteId && !include){
				if(card.getRank()> rank ){
					rank = card.getRank();
					returnCard = card;
				}				
			}
		}
		
		return returnCard;
	}
	

	/**
	 * Returns the biggest card of suite:suiteId. If no card present
	 * from suite:suiteId, returns null.
	 * 
	 * @param suiteId
	 * @return Card/null
	 */
	private Card getBiggestFromSuite(int suiteId) {
		Card returnCard = null;
		
		int rank = 8; 	//the highest rank in the deck is 7. So :)
		for(Card card: getMyHand().getMyCards()){
			if( card.getSuit() != suiteId)
				continue;
			if(card.getRank()< rank ){
				rank = card.getRank();
				returnCard = card;
			}
		}
		return returnCard;
	}
	
	/**
	 * Returns the biggest card from the hand. 
	 * Will never return suite:trump, if user=trumpOwner.
	 * 
	 * Also, won't return a 9. :)
	 * @return
	 */
	private Card getBestCardToStart() {
		int maxValue = 0;
		Card returnCard=null ;
		int suite;
		Game game = this.getGameReference();
		
		//If I'm the player who've placed the trump
		if(game.getTrump().getBidOwner().equals(getName()))
			suite = getPrivateTrump().getSuit();

		/* putting a random int value as suiteId. Since there is no 
		 * suite that starts with 9, if(card.getSuit()!=suite) will always 
		 * be true.
		 */
		else 
			suite = 9;
		
		/* Trying to find the highest value card in the hand. Assuming the minValue =0, 
		 * we will look for values bigger than that, once the hand is parsed, 
		 * we'll have the highest value
		 * 
		 */
		for (Card card: getMyHand().getMyCards()){
			if(card.getSuit()!=suite ){
				if( card.getMyvalue() > maxValue){
					if (card.getMyvalue() ==2)	// Chaek9 escape strategy. 
						continue;
					returnCard = card;
					maxValue = card.getMyvalue();
				}
			}
		}
		/*
		 * In case all the cards in the hand are of value = 0, the function will return
		 * the first card it finds that is not a trump. 
		 */
		if(returnCard ==null){
			for (Card card: getMyHand().getMyCards()){
				if(card.getSuit()!=suite ){
						returnCard = card;
						break;
					}
				}
			}
		return returnCard;
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

	public Card getPrivateTrump() {
		return trump;
	}

	public void setPrivateTrump(Card trump) {
		this.trump = trump;
	}
	
	/**
	 * Groups the card by suite, put into map. key- suit value(int 0-3). value- x.y(float) 
	 * x= no of occurrence of suite(count), y= total value in the suite. 
	 * eg: (key: 3) -> (value:2.5) means for clubs, there are 2 cards, and the sum of values
	 * of all cards = 5(Should be a Jack and Nine. )
	 */
	public Map<Integer,Float> getSuiteMap(){
		Map<Integer,Float> cardsSuiteCounted = new HashMap<Integer, Float>();

		for(Card card: this.getMyHand().getMyCards()){
			float value = card.getMyvalue();
			value = value /10;
			
			if(cardsSuiteCounted.containsKey(card.getSuit())){
				float mapValue = (float)cardsSuiteCounted.get(card.getSuit())+ ( 1f )+ (value);
				cardsSuiteCounted.put(card.getSuit(), mapValue);
			}
			else{
				cardsSuiteCounted.put(card.getSuit(), 1f + value);
			}
		}
		return cardsSuiteCounted;
	}
}


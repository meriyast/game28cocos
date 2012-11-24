package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

	boolean debug = true;
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
		trump = null;
	}
	
	public Team getTeam() {
		return team;
	}

	public void removeCard(Card card){
		myHand.removeCard(card);
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
		if(debug) System.out.println("\n\n");
		System.out.println(this.toString());
		if(debug) System.out.println("******************");

		Hand hand = this.getMyHand();
		List<Card> cards = hand.getMyCards();
		if(debug) for(Card card:cards){
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
		
		//System.out.println("\nSuiteValue: "+suiteValue);
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
			if((suiteValue >= 5 && pointTotal >=.4) ||(suiteValue >= 4 && pointTotal >=.5)){
				bidValue = 21;
			}
		}
		return new TrumpCandidate(cardSelected, bidValue);
	}
	
	
	public Card aiPlayGame(){
		Game game = gameReference;
		Card returnCard;
		debug = true;
		boolean wasCut = false;
		int trumpSuiteId;
		if(!this.getName().equals(game.getPlayerTurn().getName()))
			return new Card();
		
		if(debug) System.out.println("");
		 //BoardSize = 0 means the board is empty, and I've to start the game. 
		if( game.getBoard().getCardsPlayed().size() ==0){
				returnCard = getBestCardToStart();
				if(debug) System.out.println ("First card:"+ returnCard.getUniqueCardValue());
				if(returnCard == null)
					if(debug) System.out.println("ReturnCardNull at start");
		}
		//BoardSize !=0. I am not the first player. 
		else{
			int suiteId = game.getBoardSuite();
//			if(debug) System.out.println("SuiteId:"+suiteId);
			Team team = game.getBoard().getCurrentHolder().getTeam();
			System.out.println("I'm "+getName() +"@"+ getTeam().getTeamName() +",Hand now held by team: "+team.getTeamName());
			wasCut = game.getBoard().getWasCut();
			
			//If my team holds the board, I should support. 
			if(this.getTeam().equals(team)){
				//will first check if we have cards in suite=roundSuite.
				if(haveRoundSuite()){
					returnCard = getBiggestFromHand(suiteId,true);
					if(returnCard != null) if(debug) System.out.println("MyTeam's hand. I'll support with: "+returnCard.getUniqueCardValue());
				}
				else{
					//if no cards of the same suite, support with the best card from the hand
					returnCard = getBiggestFromHand(suiteId,false);
					if(returnCard != null)  if(debug) System.out.println("MyTeam's hand. I'll support with: "+returnCard.getUniqueCardValue());
				}
				
			}
			//If my team doesn't hold the board, I should attack. 
			else{
				if(debug) System.out.println("I must attack");
				//The hand was owned by the other team by cutting. 
				if(wasCut){
					//I have Card from suite:suiteId. Must play the MinCard from suite.
					if(debug) System.out.println("CurrentBoard was cut");
					if(haveRoundSuite()){
						returnCard = getMinCardFromHand(suiteId, true);
						if(debug) System.out.println("I have boardSuite. I'll give the lowest card: "+returnCard.getUniqueCardValue());
					}
					//I don't have Card form suite:suiteId. Must Cut if possible.
					else{
						
						boolean openedTrumpThisRound = !game.getTrump().isOpen();
						Card trumpInHand  = getTrumpCard();

						//I don't have a Trump card.
						if(trumpInHand == null){
							trumpSuiteId = game.getTrump().getTrumpCard().getSuit();
							returnCard = getMinCardFromHand();		
							if(debug) System.out.println("I don't have TrumpCard. I'll give lowest card: "+returnCard.getUniqueCardValue());

						}
						//I have a Trump Card.
						else{
							// my Trump Card's value is greater than the highest Trump in the CurrentBoard.
							if(trumpInHand.getRank()<getHoldingCard().getRank()){
								returnCard = trumpInHand;
								if(debug) System.out.println("I don't have boardSuite. I'll cut: "+trumpInHand.getUniqueCardValue());
							}
							//Or, if I ordered to open Trump in this round, I've got to play a trump.
							if(trumpInHand.getRank()>getHoldingCard().getRank() && openedTrumpThisRound){
								returnCard = getMinTrump();
								if(debug) System.out.println("I asked to open the trump. So I have to cut with this trump "+trumpInHand.getUniqueCardValue());
							}
							/*
							 * My highest trump is lesser than the highest Trump in the board. don't cut.
							 * Will be called only if I didn't order to open the Trump this round
							 */
							else{
								trumpSuiteId = game.getTrump().getTrumpCard().getSuit();
								returnCard = getMinCardFromHand(trumpSuiteId, false);
								if(debug) System.out.println("I do have a trump. But smaller. So not cutting "+trumpInHand.getUniqueCardValue());
							}
							
						}
					}
//					returnCard = null;
				}
				//The hand was owned by the other team by Heirarchy. 
				else{
					if(debug) System.out.println("CurrentBoard owned by heirarchy");
					//I have Card from suite: roundSuite.
					if(haveRoundSuite()){
						if(debug) System.out.println("I have round suite. Yay!!");
						Card bestCardInHand = getBiggestFromHand(suiteId, true);
						//my biggest Card of the roundSuite is lesser than holdingCard.
						if(bestCardInHand.getRank()>getHoldingCard().getRank()){
							returnCard = getMinCardFromHand(suiteId, true);
							System.out.println("My biggest won't take the board. putting in: "+returnCard.getUniqueCardValue());
						}
						//my biggest Card of the roundSuite is bigger than holdingCard.
						else{
							returnCard = bestCardInHand;
							System.out.println("Taking the board with : "+returnCard.getUniqueCardValue());
						}
					}
					//I don't have Card from suite:roundSuite. I must cut. 
					else{
						if(debug) System.out.println("I don't have roundSuite. I must cut.");
						trumpSuiteId = game.getTrump().getTrumpCard().getSuit();
						returnCard = getTrumpCard();
						
						/*
						 * Must consider the fact that I don't have any trump cards to cut with.
						 * will be returning null at returnCard.
						 * Since I can't cut, return the lowest card I have in hand.
						 */
						if(returnCard == null){
							returnCard = getMinCardFromHand(trumpSuiteId, false);
							if(debug)
								System.out.println("I don't have a trumpCard. Minimizing casuality with: "+returnCard.getUniqueCardValue());
						}
						else{
							if(debug)
								System.out.println("Cutting with trumpCard: "+returnCard.getUniqueCardValue());
						}
					}
				}
			}
		}
		if(returnCard == null)
			returnCard = getMinCardFromHand();
		
		System.out.println("##Player: "+this.getName()+" played:" +returnCard.getUniqueCardValue());
		return returnCard;
	}
	
	private Card getMinCardFromHand() {
		debug = false;
		if(debug) System.out.println("getMinCardFromHand() "); 
		Card returnCard = null;
		int rank = -1;
		for (Card card : getMyHand().getMyCards()) {

			if (card.getRank() > rank) {
				rank = card.getRank();
				returnCard = card;
			}
		}
		return returnCard;
	}

	private Card getMinTrump() {
		debug = false;
		if(debug) System.out.println(" getMinTrump() ");

		Game game = this.getGameReference();
		int trumpSuiteId;
		Card returnCard = null;
		//If Trump is open already.
		if(game.getTrump().isOpen()){
			trumpSuiteId = game.getTrump().getTrumpCard().getSuit();
			returnCard = getMinCardFromHand(trumpSuiteId, true);
		}
		//Open Trump, cut with the biggest Trump Value from hand.
		else{
			game.revealTrump();
			trumpSuiteId = game.getTrump().getTrumpCard().getSuit();
			returnCard = getMinCardFromHand(trumpSuiteId, true);
		}
		
		return returnCard;
	}

	private Card getTrumpCard(){
		debug = false;
		if(debug) System.out.println(" getTrumpCard() ");

		Game game = this.getGameReference();
		game.getTrump().describeTrump();
		int trumpSuiteId=8;
		Card returnCard = null;
		//If Trump is open already.
		if(game.getTrump().isOpen()){
			trumpSuiteId = game.getTrump().getTrumpCard().getSuit();
			if(debug) System.out.println("*trumpSuiteId: "+trumpSuiteId);
			returnCard = getBiggestFromHand(trumpSuiteId, true);
			if(returnCard == null)
				if(debug) System.out.println("*returnCard found null at pt1");
		}

		//Open Trump, cut with the biggest Trump Value from hand.
		else{
			game.revealTrump();
			trumpSuiteId = game.getTrump().getTrumpCard().getSuit();
			if(debug) System.out.println("*trumpSuiteId: "+trumpSuiteId);
			returnCard = getBiggestFromHand(trumpSuiteId, true);
			if(returnCard == null)
				if(debug) System.out.println("*returnCard found null at pt2");
		}
		if(returnCard !=null) 
			if(debug) System.out.println("The board was cut. I repeat, THE BOARD WAS CUT. Trump: "+ returnCard.getUniqueCardValue());
		return returnCard;
	}
	
	
	/**
	 * Will return the Card that is holding the board. 
	 * 
	 * @return
	 */
	private Card getHoldingCard() {
		return gameReference.getBoard().getHoldingCard();
	}

	/**
	 * Returns 
	 * True if contains the RoundSuite.
	 * False otherwise.
	 * @return
	 */
	private boolean haveRoundSuite() {
		int suiteId = getGameReference().getBoardSuite();
		for(Card card: myHand.getMyCards()){
			if(card.getSuit()  == suiteId)
				return true;
		}
			return false;
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
		debug = false;
		if(debug) System.out.println(" getMinCardFromHand(int suiteId, boolean include) ");

		Card returnCard = null;
		int rank = -1;
		
		int trumpSuite = getTrumpSuite();
		for(Card card: getMyHand().getMyCards()){

			if(card.getSuit() == trumpSuite && suiteId != trumpSuite){
				continue;
			}
			if( card.getSuit() == suiteId && include == true){
				if(card.getRank()> rank ){
					rank = card.getRank();
					returnCard = card;
				}
			}
				
			if (card.getSuit() != suiteId && include == false){
				if(card.getRank()> rank ){
					rank = card.getRank();
					returnCard = card;
				}				
			}
		}
		return returnCard;
	}
	

	/**
	 *  Returns the biggest card from the hand.
	 * if include:true, maxCard from the specific suite.
	 * if include:false, maxCard from everything outside suite.
	 * 
	 * @param suiteId
	 * @return Card/null
	 */
	private Card getBiggestFromHand(int suiteId, boolean include) {
		debug = false;
		if(debug) System.out.println(" getBiggestFromHand(int suiteId, boolean include)");

		Card returnCard = null;
		int rank = 8; 	//the highest rank in the deck is 7. So :)
		int trumpSuite = getTrumpSuite();
		if(debug) System.out.println("suite: "+suiteId+" include: "+include+" trump: "
		+trumpSuite +"Size of Hand:"+getMyHand().getMyCards().size());
		
		for(Card card: getMyHand().getMyCards()){
			if(debug) System.out.println("Now Card: "+card.getUniqueCardValue());
			
			
			 if(card.getSuit() == trumpSuite && suiteId != trumpSuite){
				 if(debug) System.out.println("Continue");
				continue;
			}
			if( card.getSuit() == suiteId && include){
				if(card.getRank()< rank ){
					rank = card.getRank();
					returnCard = card;
				}
			}
				
			if (card.getSuit() != suiteId && !include){
				if(card.getRank()< rank ){
					rank = card.getRank();
					returnCard = card;
				}				
			}
		}
		return returnCard;
	}
	

	/**
	 * Returns an integer value=8 if caller is not eligible to view the trump. 
	 * Eligibility:
	 * 1. Owner of the trump. or
	 * 2. Trump is already open.
	 * 
	 * @return
	 */
	private int getTrumpSuite(){
		int trumpSuite = 8;
		if(this.trump !=null){
			 trumpSuite = this.trump.getSuit();
		}
		else if(getGameReference().getTrump().isOpen()){
			trumpSuite = getGameReference().getTrump().getTrumpCard().getSuit();
		}
		
		return trumpSuite;
	}
	
	
	/**
	 * Returns the biggest card from the hand. 
	 * Will never return suite:trump, if user=trumpOwner.
	 * isValidBid
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
		return getTrump();
	}

	public void setPrivateTrump(Card trump) {
		this.setTrump(trump);
	}
	
	/**
	 * Groups the card by suite, put into map. key- suit value(int 0-3). value-
	 * x.y(float) x= no of occurrence of suite(count), y= total value in the
	 * suite. eg: (key: 3) -> (value:2.5) means for clubs, there are 2 cards,
	 * and the sum of values of all cards = 5(Should be a Jack and Nine. )
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
		//if a trump is set, the trump value should be added to the map as well.
		if(trump != null){
			int trumpSuite = trump.getSuit();
			float value = trump.getMyvalue();
			value = value/10;
			if(cardsSuiteCounted.containsKey(trumpSuite)){
				float mapValue = (float)cardsSuiteCounted.get(trumpSuite)+ ( 1f )+ (value);
				cardsSuiteCounted.put(trumpSuite, mapValue);
			}
		}
		return cardsSuiteCounted;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
    	
        Player guest = (Player) obj;
    	if(this.points == guest.getPoints()  &&
    		this.name.equals(guest.getName()) &&
    		this.myHand.equals(guest.getMyHand())&&
    		this.team.equals(guest.getTeam()) &&
    		this.isAI == guest.getIsAI() &&
    		this.gameReference == guest.getGameReference() &&
    		this.getTrump().equals(guest.getTrump())
    			)
    		return true;
    	else
    		return false;
    }

	public Card getTrump() {
		return trump;
	}

	public void setTrump(Card trump) {
		this.trump = trump;
	}
}


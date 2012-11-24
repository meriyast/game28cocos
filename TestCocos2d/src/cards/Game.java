package cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;




public class Game {
	boolean debug = false;
	private Queue<Player> playersInTheGame;
	Trump trump ;
	public  Player currentPlayer ;
	Deck deck;
	int gameId;
	Team team1;
	Team team2;
	GameStatus status;
	private CurrentBoard board;	
	private int boardSuite;

	
	
	public GameStatus getStatus() {
		return status;
	}


	public void setStatus(GameStatus status) {
		this.status = status;
	}


	public Game(){
		Random r = new Random();
		gameId = r.nextInt(1001);
		status = GameStatus.WAIT;
		playersInTheGame = new LinkedList<Player>();
		trump = new Trump();
		currentPlayer = null;
		deck= new Deck() ;
		team1= new Team("Team1", 0);
		team2= new Team("Team2", 0);
		setBoard(new CurrentBoard());
		board.setGameRef(this);
	}
	
	
	public Game(Player player) {
		Random r = new Random();
		gameId = r.nextInt(1001);
		status = GameStatus.WAIT;
		
		trump = new Trump();
		currentPlayer = player;
		
		playersInTheGame = new LinkedList<Player>();
		playersInTheGame.add(player);
		deck= new Deck() ;
		board.setGameRef(this);
	}
	
	
	public int joinGame(Player player) {
		if(playersInTheGame.size()<=4) {
			playersInTheGame.add(player);
			
			if (playersInTheGame.size() % 2 == 0) {
				player.setTeam(team2);
			} else {
				player.setTeam(team1);
			}
		} 
			return numPlayersInGame();
	}
	
	public void rotateOnce(){
		//so that the first player in the queue now- is the one next to player1. 
		Player player = playersInTheGame.remove();
		playersInTheGame.add(player);
	}
	
	public boolean canStartGame() {
		
		if(this.numPlayersInGame() != 4){
			return false;
		}		
		else 
			return true;
	}		  
	
	public void deal(){
		Player player;
		for(int i=0;i<4;i++){
			player = playersInTheGame.remove();
			for(int j =0; j<4;j++){
				Card card = (Card) deck.next();
				player.getMyHand().addCard(card);
			}
			playersInTheGame.add(player);			
		}
	}



	public int numPlayersInGame(){
		return playersInTheGame.size();
	}


	public Queue<Player> getPlayersInTheGame() {
		return playersInTheGame;
	}


	public void setPlayersInTheGame(Queue<Player> playersInTheGame) {
		this.playersInTheGame = playersInTheGame;
	}

	
	public Trump getTrump() {
		return trump;
	}


	public void setTrump(Trump trump) {
		this.trump = trump;
	}
	

	public Player getPlayerTurn() {
		return currentPlayer;
	}


	public void setPlayerTurn(Player playerTurn) {
		this.currentPlayer = playerTurn;
	}


	public boolean isValidBid(int inputTrumpValue,Player p){
		if(isFirstRoundDone()){
			
			if(inputTrumpValue > getTrump().getCurrentHightestBid() && 
					getTrump().getBidOwner().getTeam().equals(p.getTeam()) &&
					p.getMyHand().getMyCards().size()<=4 ){
				if(debug) System.out.println("Bid owner is from same team.");
				return false;
			}
			
			if(	
					inputTrumpValue > getTrump().getCurrentHightestBid() &&
					inputTrumpValue >  14 &&
					inputTrumpValue <= 28
					)
				return true;
			else
				return false;
		}
		else{
			if(inputTrumpValue <14)
				return false;
			else
				return true;
		}
	}



	public int getBoardSuite() {
		return boardSuite;
	}
	public void setBoardSuite(int boardSuite) {
		this.boardSuite = boardSuite;
	}
	
	public int getGameId() {
		return gameId;
	}


	public void setGameId(int gameId) {
		this.gameId = gameId;
	}


	public Team getTeam1() {
		return team1;
	}


	public void setTeam1(Team team1) {
		this.team1 = team1;
	}


	public Team getTeam2() {
		return team2;
	}


	public void setTeam2(Team team2) {
		this.team2 = team2;
	}


	public CurrentBoard getBoard() {
		return board;
	}


	public void setBoard(CurrentBoard board) {
		this.board = board;
	}


	public Card revealTrump() {
		board.setWasCut(true);
		this.getTrump().setOpen(true);
		return trump.getTrumpCard();
	}

	
	public void bid(Player p){
		debug = false;
		TrumpCandidate trumpCandidate = new TrumpCandidate();
		if(debug) System.out.println(" ");
		if(debug) p.describePlayer();
		
		//If the player is AI, call aiPlayBid.
		if (p.getIsAI()) {
			trumpCandidate = p.aiPlayBid();
			//if the trumpCandidate is not Valid, return. this would mean pass.
		} 
		//The bidder is alive. Get values from the bidder. 
		else {
			
			trumpCandidate =readTrump(p);
		}
		
		//Whether it is ai or not, if the bid is not valid, return.
		if  (!isValidBid(trumpCandidate.getBid(), p))
			return;
		
		//if a bid was already placed, give that card back to the owner.
		if(isFirstRoundDone()){
			trump.getBidOwner().getMyHand().addCard(trump.getTrumpCard());
		}
		
		trump.setBidOwner(p);
		trump.setCurrentHightestBid(trumpCandidate.getBid());
		trump.setTrumpCard(trumpCandidate.getCard());
		if(debug) System.out.println("Bid of " + trump.getCurrentHightestBid() + " by " + trump.getBidOwner().getName());
		
		//remove the card from player's hand, and place it in Trump placeholder.
		p.getMyHand().removeCard(trumpCandidate.getCard());
		p.setTrump(trumpCandidate.getCard());
	}
	

	public boolean isFirstRoundDone(){
		if(this.getTrump().getCurrentHightestBid() ==13)
			return false;
		else 
			return true;
			
	}
	
	
	public TrumpCandidate readTrump(Player p){
		int inputTrumpValue = 0;
		TrumpCandidate trumpCandidate = new TrumpCandidate();
		
		try {
			do {			
				System.out.println("Now bid:" + p.getName());
				System.out.println("Enter Bid: ");
				
				BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
				String input = "";
				input = br.readLine();
				if (input.length() == 0)
					continue;
				
//				String card = "";
//				input = br.readLine();

				inputTrumpValue = Integer.parseInt(input);
				trumpCandidate.setBid(inputTrumpValue);
			} while (isValidBid(inputTrumpValue, p));

		} catch (IOException e) {
			System.err.println("Error: " + e);
		}
		return trumpCandidate;
	}


	public void play(Player p) {
		Card played;
		played = p.aiPlayGame();
		p.getMyHand().removeCard(played);
		board.updateBoard(p,played);
	}


	public void updateProceedings() {
		debug = true;
		Player winner = board.getCurrentHolder();
		Team winningTeam = winner.getTeam();
		int totalPoints = winningTeam.getTotalPoints() + board.getTotalPoints();
		System.out.println("");
		if(debug) System.out.println("Winner was: "+winner.getName());
		if(debug) System.out.println("Total Points for Team: "+winningTeam.getTeamName()+" is: "+totalPoints);
		winningTeam.setTotalPoints(totalPoints);
		
		Player player1 = playersInTheGame.peek();
		while(!winner.equals(player1)){
			rotateOnce();
			player1 = playersInTheGame.peek();
			System.out.println("rotated once");
		}
		setPlayerTurn(winner);
		if(debug) System.out.println(playersInTheGame.peek().getName()+ " starts the next game");
		board = new CurrentBoard();
		board.setGameRef(this);
		board.setWasCut(false);
	}
	
}
package cards;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;




public class Game {

	private Queue<Player> playersInTheGame;
	Trump trump ;
	public  Player currentPlayer ;
	Deck deck;
	int gameId;
	Team team1;
	Team team2;
	GameStatus status;
	private CurrentBoard board;
	
	
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
	}
	
	
	public int joinGame(Player player) {
		if(playersInTheGame.size()<=4) {
			playersInTheGame.add(player);
			
			if (playersInTheGame.size() % 2 == 0) {
				player.setTeam(team1);
			} else {
				player.setTeam(team2);
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

	public void bid(Player p, int inputTrumpValue) {
		
		if (inputTrumpValue == 100) {
			System.out.println(p.getName() + " folded");
		} else {
			trump.setBidOwner(p);
			trump.setCurrentHightestBid(inputTrumpValue);
			System.out.println("Bid of " + trump.getCurrentHightestBid() + " by " + trump.getBidOwner().getName());
			
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


	public boolean isValidBid(int inputTrumpValue, boolean firstRoundDone){
		
		//during first round= mandatory bid
		if (!firstRoundDone) {
			if (inputTrumpValue >= 14 && inputTrumpValue <= 28)
				return true;
			else
				return false;
		} else{
			if (inputTrumpValue > this.getTrump().getCurrentHightestBid() )
				return true;
			else
				return false;
		}
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
		return trump.getTrumpCard();
	}

	
}
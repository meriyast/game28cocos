package cards;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Deck aDeck = new Deck();
		List<Team> teamList = new ArrayList<Team>();
		Team team1 = new Team("Team1", 0);
		Team team2 = new Team("Team2", 0);

		Game game = new Game();
		game.setStatus(GameStatus.WAIT);
		
		for (int i = 1; i <= 4; i++) {
			String player = "Player" + i;
			Player aPlayer = new Player(player, 0,game);
			aPlayer.setIsAI(true);
			game.joinGame(aPlayer);
		}
		
		do {
		} while (!game.canStartGame());

		game.deal();
		game.setStatus(GameStatus.BID);
		
//		if(true)
//		for(Player p: game.getPlayersInTheGame()){
//			System.out.println("\nPlayer: "+p.getName());
//			for(Card card: p.getMyHand().getMyCards())
//				System.out.print("   "+card.getUniqueCardValue());
//		}
//		
		game.rotateOnce();

		System.out.print("Min Bid:");

		System.out.println(game.getTrump().getCurrentHightestBid() + 1);
		System.out.println("********** ");

		LinkedList<Player> players = (LinkedList<Player>) game
				.getPlayersInTheGame();

		for (Player p : players) {
			game.bid(p);
		}

		game.deal();
		
		for (Player p : players) {
			game.bid(p);
		}
		
		for(int i = 0; i<8;i++){
			System.out.println("\n\n$$$$$$$$ ----Round :"+(i+1)+"---- $$$$$$$$");
			for(Player p:players){
				game.setPlayerTurn(p);
				game.play(p);
			}
			game.updateProceedings();
		}
		Team bidTeam =game.getTrump().getBidOwner().getTeam();
		Player bidPlayer = game.getTrump().getBidOwner();
		
		System.out.println("Bid was: "+game.getTrump().getCurrentHightestBid()+" by: "+
		bidPlayer.getName()+"@"+bidTeam.getTeamName());
		System.out.println(bidTeam.getTeamName()+" got "+bidTeam.getTotalPoints() +" points.");
		if(bidTeam.getTotalPoints() >= game.getTrump().getCurrentHightestBid())
			System.out.println(bidTeam.getTeamName() + " Won!!");
		else
			System.out.println(bidTeam.getTeamName() + " Lost :(");

	}

}

package cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
			
			if(game.getPlayersInTheGame().contains(aPlayer)){
				System.out.println("added player"+aPlayer.getName());
			}
		}
		
		do {
		} while (!game.canStartGame());

		game.deal();
		game.setStatus(GameStatus.BID);
		
		
		game.rotateOnce();

		System.out.print("Min Bid:");

		System.out.println(game.getTrump().getCurrentHightestBid() + 1);
		System.out.println("********** ");

		LinkedList<Player> players = (LinkedList<Player>) game
				.getPlayersInTheGame();

		boolean firstRoundDone = false;
		for (Player p : players) {
			TrumpCandidate trumpCandidate = new TrumpCandidate();
			System.out.println(" ");

			p.describePlayer();
			if (p.getIsAI()) {
				trumpCandidate = p.aiPlayBid();
				if  (!game.isValidBid(trumpCandidate.getBid(), firstRoundDone))
					continue;
			} else {
				System.out.println("Now bid:" + p.getName());
				System.out.println("Enter Bid: ");
				
				int inputTrumpValue = 0;
				try {
					do {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(System.in));
						String input = "";
						input = br.readLine();
						if (input.length() == 0)
							continue;

						inputTrumpValue = Integer.parseInt(input);
						trumpCandidate.setBid(inputTrumpValue);
					} while (!game.isValidBid(inputTrumpValue, firstRoundDone));

				} catch (IOException e) {
					System.err.println("Error: " + e);
				}

			}
			if (firstRoundDone) {
				if (game.getTrump().getBidOwner().getTeam().equals(p.getTeam())
						&& trumpCandidate.getBid() < 18) {
					System.out.println("Bid Placed by team member");
					continue;
				}
			}
			game.bid(p, trumpCandidate.getBid());
			if (!firstRoundDone)
				firstRoundDone = true;

		}

		game.deal();


	}

}

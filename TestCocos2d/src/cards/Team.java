package cards;

import java.util.ArrayList;

public class Team {

	private String teamName;
	private int totalPoints;
	
	public Team(String teamName, int totalPoints) {
		this.teamName = teamName;
		this.totalPoints = totalPoints;
	}


	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	
	public int getTotalPoints(ArrayList<Card> ourCards) {
		
		int total =0;
		for(Card c: ourCards) {
			total += c.getMyvalue();
		}
		
		return total;
	}	
	
	
	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Team guest = (Team) obj;
    	if(this.totalPoints == guest.getTotalPoints()  &&
    		this.getTeamName().equals(guest.getTeamName()) )
    		return true;
    	else
    		return false;
    }

}

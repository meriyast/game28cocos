package cards;

public enum GameStatus{
	WAIT("Waiting Phase",1),
	BID("Bidding Phase",2),
	PLAY("Play",3);
	
	  private int val;
	  private String description;
	  
	GameStatus(String action, int val) {
		this.description=action;
		this.val=val;
	}
	
	  public String getDescription() {
		    return description; 
	  }	
	  
	  public int getVal() {
		  return val;
	  }
}
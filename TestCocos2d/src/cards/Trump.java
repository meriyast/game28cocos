package cards;

public class Trump {
	int currentHightestBid ;
	Player bidOwner;
	Card trumpCard;
	
	
	public Card getTrumpCard() {
		return trumpCard;
	}

	public void setTrumpCard(Card trumpCard) {
		this.trumpCard = trumpCard;
	}

	public int getCurrentHightestBid() {
		return currentHightestBid;
	}
	
	public void setCurrentHightestBid(int currentHightestBid) {
		
		if(this.currentHightestBid<currentHightestBid){
			this.currentHightestBid = currentHightestBid;
		}
		else{
			return;
		}
	}
	
	public void returnTrumpToOwner(){
		bidOwner.getMyHand().addCard(trumpCard);
	}
	
	public Player getBidOwner() {
		return bidOwner;
	}
	
	public void setBidOwner(Player bitOwner) {
		this.bidOwner = bitOwner;
	}
	
	public Trump(int currentHightestBid, Player bitOwner) {
		super();
		this.currentHightestBid = currentHightestBid;
		this.bidOwner = bitOwner;
	}
	
	
	public Trump() {
		this.currentHightestBid =13;
		this.bidOwner = new Player("RandomPlayerGeneratedAtTrump", 0,null);
	}
	
	
	
	
	
}

package cards;

public class Trump {
	int currentHightestBid ;
	Player bidOwner;
	Card trumpCard;
	private boolean isOpen;
	
	
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
		this.currentHightestBid = currentHightestBid;
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
		setOpen(false);
	}
	
	
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Trump guest = (Trump) obj;
    	if(this.currentHightestBid == guest.getCurrentHightestBid()  &&
    		this.bidOwner.equals(guest.getBidOwner()) &&
    		this.trumpCard.equals(guest.getTrumpCard()) )
    		return true;
    	else
    		return false;
    }

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	public void describeTrump(){
		System.out.println("TrumpSuite: "+trumpCard.getUniqueCardValue() +" Owner: "+bidOwner.getName());
	}

	
	
}

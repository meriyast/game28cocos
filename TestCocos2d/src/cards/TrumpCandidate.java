package cards;

public class TrumpCandidate{
	Card card;
	int bid;
	
	
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	
	public TrumpCandidate(Card card, int bid) {
		super();
		this.card = card;
		this.bid = bid;
	}
	
	
	public TrumpCandidate() {
		super();
		this.card = null;
		this.bid = 0;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        TrumpCandidate guest = (TrumpCandidate) obj;
    	if(this.bid == guest.getBid()  &&
    		this.card.equals(guest.getCard()) )
    		return true;
    	else
    		return false;
    }
	
}
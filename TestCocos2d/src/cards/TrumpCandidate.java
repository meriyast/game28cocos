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
	
	
}
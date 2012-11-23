package cards;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card implements ICard{
	
	private final int mySuit;     
	private final int myRank;
	private final int myvalue;


	private static final String[] suitStrings = {
		"spades", "hearts", "diamonds", "clubs"	
	};
	
	private String cardDesc;
	
	//This weird ranking will result in Jack= rank0. :/
	private static final String[] rankStrings = {
		"jack","nine","ace","ten","king","queen","eight","seven"	
	};
	
	public static final Map<String, String> cardMap = 
	            Collections.unmodifiableMap(
	                    /* This is really an anonymous 
	                       inner class - a sub-class of j.u.HashMap */ 
	                    new HashMap<String, String>() {
	                        /**
							 * had to add a generated serial id
							 */
							private static final long serialVersionUID = 716384016034736171L;

							{
	                     	    put("jack of spades", "img/sj.png");
	                            put("nine of spades", "img/s9.png");
	                            put("ace of spades", "img/s1.png");
	                            put("ten of spades", "img/s10.png");
	                            put("king of spades", "img/sk.png");
	                            put("queen of spades", "img/sq.png");
	                            put("eight of spades", "img/s8.png");
	                            put("seven of spades", "img/s7.png");
	                            
	                     	    put("jack of hearts", "img/hj.png");
	                            put("nine of hearts", "img/h9.png");
	                            put("ace of hearts", "img/h1.png");
	                            put("ten of hearts", "img/h10.png");
	                            put("king of hearts", "img/hk.png");
	                            put("queen of hearts", "img/hq.png");
	                            put("eight of hearts", "img/h8.png");
	                            put("seven of hearts", "img/h7.png");
	                            
	                     	    put("jack of diamonds", "img/dj.png");
	                            put("nine of diamonds", "img/d9.png");
	                            put("ace of diamonds", "img/d1.png");
	                            put("ten of diamonds", "img/d10.png");
	                            put("king of diamonds", "img/dk.png");
	                            put("queen of diamonds", "img/dq.png");
	                            put("eight of diamonds", "img/d8.png");
	                            put("seven of diamonds", "img/d7.png");
	                            
	                     	    put("jack of clubs", "img/cj.png");
	                            put("nine of clubs", "img/c9.png");
	                            put("ace of clubs", "img/c1.png");
	                            put("ten of clubs", "img/c10.png");
	                            put("king of clubs", "img/ck.png");
	                            put("queen of clubs", "img/cq.png");
	                            put("eight of clubs", "img/c8.png");
	                            put("seven of clubs", "img/c7.png");
	 
							}

	                    });

	
	public Card() {
		this.myvalue= 0;
		this.mySuit = 0;
		this.myRank = 0;
		this.cardDesc = "";
	}

	public Card(int suit, int rank){
		Map<String, Integer> valueMap = new HashMap<String, Integer>();
		valueMap.put(rankStrings[0], 3);
		valueMap.put(rankStrings[1], 2);
		valueMap.put(rankStrings[2], 1);
		valueMap.put(rankStrings[3], 1);
		valueMap.put(rankStrings[4], 0);
		valueMap.put(rankStrings[5], 0);
		valueMap.put(rankStrings[6], 0);
		valueMap.put(rankStrings[7], 0);
		
		mySuit = suit;
		myRank = rank;
		myvalue = valueMap.get(rankStrings[(rank)]);
		
		cardDesc = rankStrings[getRank()]+" of "+
	             suitStrings[getSuit()];
	}

	public int getMyvalue() {
		return myvalue;
	}
	
	public int getSuit() {
		return mySuit;
	}
	
	public int getRank() {
		return myRank;
	}
	public String toString(){
		return cardDesc;
	}
	
	public String getUniqueCardValue(){
		return rankStrings[getRank()]+"@"+ suitStrings[getSuit()];
	}
	
	public int compareTo(Object o) {
		ICard other = (ICard) o;
		int rdiff =  other.getRank() - getRank();
		if (rdiff == 0) {
			return  other.getSuit() - getSuit() ;
		}
		else {
			return rdiff;
		}
	}
	
	@Override
	public boolean equals(Object o){
	    return compareTo(o) == 0;
	}
	
	
}
package com.example.testcocos2d;

import java.util.ArrayList;

import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import cards.Card;

public class CardUtilities extends CardGame {

	public CardUtilities(ccColor4B ccColor4B) {
		super(ccColor4B);
	}

	public static void rearrangeCards(ArrayList<CCSprite> pCards, int whoseTurn) {
		if(whoseTurn==1) {
			rearrangeCardsP1(pCards);
		} else if(whoseTurn==2) {
			rearrangeCardsP2(pCards);
		} else if(whoseTurn==3) {
			rearrangeCardsP3(pCards);
		} else if(whoseTurn==4) {
			rearrangeCardsP4(pCards);
		}

		
	}
	
	private static void rearrangeCardsP4(ArrayList<CCSprite> pCards) {
		float pos;
		int angle;
		float rotateBy;
		//Player4
		pos = winSize.height/2.5f;
		if(winSize.width<700) {
			pos = pos+25;
		}
		angle = 10;
		rotateBy = 0;	
		int index = 0;
		for(CCSprite card: p4Cards) {
			scene.removeChild(card, true);
			if((currentBidOwner==4 && index!=trumpCardIndex) || currentBidOwner!=4) {
				if(winSize.width > 700) { //if it's a tab
					card.setPosition(winSize.width/1.07f,pos);
				} else {
					card.setScale(0.6f);
					card.setPosition(winSize.width/1.2f,pos);
				}
				CCRotateBy rotate = CCRotateBy.action(1, angle+rotateBy);
				rotateBy = 5;
				card.runAction(rotate);
				pos = pos+20;
				scene.addChild(card);
			}
			index++;
		}	
	}

	private static void rearrangeCardsP3(ArrayList<CCSprite> pCards) {
		float pos;
		int angle;
		float rotateBy;
		//Player3
		pos = winSize.width/2.5f;
		if(winSize.width<700) {
			pos = pos-20;
		}
		angle = 20;
		rotateBy = 0;	
		int index = 0;
		for(CCSprite card: p3Cards) {
			scene.removeChild(card, true);
			if((currentBidOwner ==3 && index!=trumpCardIndex) || currentBidOwner!=3) {
				if(winSize.width > 700) { //if it's a tab
					card.setPosition(pos,winSize.height/1.15f);
				} else {
					card.setScale(0.6f);
					card.setPosition(pos,winSize.height/1.25f);
				}
				CCRotateBy rotate = CCRotateBy.action(1, angle-rotateBy);
				rotateBy = 5;
				card.runAction(rotate);
				pos = pos+20;
				scene.addChild(card);
			} 
			index++;
		}	
	}

	private static void rearrangeCardsP2(ArrayList<CCSprite> pCards) {
		float pos;
		int angle;
		float rotateBy;
		//Player2
		pos =winSize.height/2;
		if(winSize.width<700) {
			pos = pos-20;
		}
		angle = 20;
		rotateBy = 0;	
		int index = 0;
		for(CCSprite card: p2Cards) {
			scene.removeChild(card, true);
			if((currentBidOwner==2 && index!=trumpCardIndex) || currentBidOwner!=2) {
				if(winSize.width > 700) { //if it's a tab
					card.setPosition(winSize.width/11,pos);
				} else {
					card.setScale(0.6f);
					card.setPosition(winSize.width/7,pos);
				}
				CCRotateBy rotate = CCRotateBy.action(1, angle-rotateBy);
				rotateBy = 5;
				card.runAction(rotate);
				pos = pos+20;
				scene.addChild(card);
			} 
			index++;
		}
	}

	private static void rearrangeCardsP1(ArrayList<CCSprite> pCards) {
		float pos =winSize.width/2;
		if(winSize.width<700) {
			pos = pos+45;
		}
		int angle = 5;
		float rotateBy = 0;
		int index=0;
		if(firstRoundBiddingOver) {
			pos = winSize.width/2.5f;
			angle=-3;
		}
		for(CCSprite card:pCards) {
			scene.removeChild(card, true);
			if(currentBidOwner==1 && (index!=trumpCardIndex)) {
				if(winSize.width > 700) { //if it's a tab
					card.setScale(2f);
					card.setPosition(pos,winSize.height/5);
				} else {
					card.setScale(0.8f);
					card.setPosition(pos,winSize.height/6);
				}
				CCRotateBy rotate = CCRotateBy.action(2, angle+rotateBy);
				card.runAction(rotate);
				
				if(winSize.width<700) {
					pos = pos+40;
				} else {
					pos = pos+80;
				}
				rotateBy = 0;
				scene.addChild(card);
			} 
			index++;
		}		
	}
	

	public static void returnTrumpPlayer4(ArrayList<CCSprite> pCards) {
		float pos;
		int angle;
		float rotateBy;
		//Player4


		pos =winSize.height/2.5f;
		angle = -40;
		rotateBy = 0;	
		if(winSize.width<700) {
			pos = pos-20;
		}
		int noOfCards=4;
		for(CCSprite card:pCards) {
			scene.removeChild(card, true);
		}
		pos =winSize.height/2.5f;
		rotateBy = 0;	
		if(firstRoundBiddingOver) {
			noOfCards=8;
			if(currentBidOwner==4) {
				noOfCards=7;
			}
			if(winSize.width>700) {
				angle=30;
				pos =winSize.height/2f;
			} else {
				angle=-50;
			}
		}
		for(CCSprite card:p4Cards) {
			scene.removeChild(card, true);
			if(winSize.width > 700) { //if it's a tab
				card.setPosition(winSize.width/1.07f,pos);
			} else {
				card.setScale(0.6f);
				card.setPosition(winSize.width/1.16f,pos);
			}
			
			CCRotateBy rotate = CCRotateBy.action(1, angle-rotateBy);
			card.runAction(rotate);
			if(winSize.width>700) {
				pos = pos+20;
				rotateBy = rotateBy+15;
			} else {
				pos = pos+15;
				rotateBy = rotateBy+5;
			}
			scene.addChild(card);
		}			
		CCSprite cardInHand = CCSprite.sprite("img/b1fv.png");
		cardInHand.setRotation(180);
		if(winSize.width > 700) { //if it's a tab
			cardInHand.setScale(2f);
			cardInHand.setPosition(winSize.width/1.07f,pos);
		} else {
			cardInHand.setScale(0.6f);
			cardInHand.setPosition(winSize.width/1.16f,pos);
		}
		CCRotateBy rotate = CCRotateBy.action(1, angle-rotateBy);
		cardInHand.runAction(rotate);
		scene.addChild(cardInHand);
		p2Cards.add(cardInHand);
	}
	public static void returnTrumpPlayer3(ArrayList<CCSprite> pCards) {
		float pos;
		int angle;
		float rotateBy;
		//Player3

		pos =winSize.width/2.5f;
		if(winSize.width<700) {
			pos = pos-20;
		}
		angle = 20;
		rotateBy = 0;	
		int noOfCards=4;
		if(firstRoundBiddingOver) {
			noOfCards=8;
			if(winSize.width>700) {
				angle=60;
				pos=winSize.width/2f;
			}
		}
		for(CCSprite card:p3Cards) {
			scene.removeChild(card, true);
			if(winSize.width > 700) { //if it's a tab
				card.setPosition(pos,winSize.height/1.15f);
			} else {
				card.setScale(0.6f);
				card.setPosition(pos,winSize.height/1.25f);
			}
			
			CCRotateBy rotate = CCRotateBy.action(1, angle-rotateBy);
			card.runAction(rotate);
			if(winSize.width>700) {
				pos = pos+20;
				rotateBy = rotateBy+20;
			} else {
				pos = pos+15;
				rotateBy = rotateBy+15;
			}
			scene.addChild(card);
		}
		CCSprite cardInHand = CCSprite.sprite("img/b1fv.png");
		cardInHand.setRotation(180);
		if(winSize.width > 700) { //if it's a tab
			cardInHand.setScale(2f);
			cardInHand.setPosition(pos,winSize.height/1.15f);
		} else {
			cardInHand.setScale(0.6f);
			cardInHand.setPosition(pos,winSize.height/1.25f);
		}
		CCRotateBy rotate = CCRotateBy.action(1, angle-rotateBy);
		cardInHand.runAction(rotate);
		scene.addChild(cardInHand);
		p2Cards.add(cardInHand);
		
	}
	public static void returnTrumpPlayer2(ArrayList<CCSprite> pCards) {
		float pos;
		int angle;
		float rotateBy;
		//Player2

		pos =winSize.height/2;
		if(winSize.width<700) {
			pos = pos-20;
		}
		angle = 20;
		rotateBy = 0;	
		int noOfCards=4;
		if(firstRoundBiddingOver) {
			noOfCards=8;
			angle=40;
		}
		for(CCSprite card:p2Cards) {
			scene.removeChild(card, true);
			card.setRotation(90);
			if(winSize.width > 700) { //if it's a tab
				card.setPosition(winSize.width/11,pos);
			} else {
				card.setScale(0.6f);
				card.setPosition(winSize.width/11,pos);
			}
			
			CCRotateBy rotate = CCRotateBy.action(1, angle-rotateBy);
			card.runAction(rotate);
			if(winSize.width>700) {
				pos = pos+20;
				rotateBy = rotateBy+20;
			} else {
				pos = pos+10;
				rotateBy = rotateBy+20;
			}
			scene.addChild(card);
		}
		CCSprite cardInHand = CCSprite.sprite("img/b1fv.png");
		cardInHand.setRotation(90);
		if(winSize.width > 700) { //if it's a tab
			cardInHand.setScale(2f);
			cardInHand.setPosition(winSize.width/11,pos);
		} else {
			cardInHand.setScale(0.6f);
			cardInHand.setPosition(winSize.width/11,pos);
		}
		CCRotateBy rotate = CCRotateBy.action(1, angle-rotateBy);
		cardInHand.runAction(rotate);
		scene.addChild(cardInHand);
		p2Cards.add(cardInHand);
	}
	public static void returnTrumpPlayer1(ArrayList<CCSprite> pCards) {
		float pos =winSize.width/2;
		int angle = -10;
		float rotateBy = 0;
		if(winSize.width<700) {
			pos = pos+45;
		}
		if(firstRoundBiddingOver) {
			pos = winSize.width/2.5f;
		}

		for(CCSprite card:pCards) {
			scene.removeChild(card, true);
			if(winSize.width > 700) { //if it's a tab
				card.setScale(2f);
				card.setPosition(pos,winSize.height/5);
			} else {
				card.setScale(0.8f);
				card.setPosition(pos,winSize.height/6);
			}
			CCRotateBy rotate = CCRotateBy.action(2, angle+rotateBy);
			card.runAction(rotate);
			
			if(winSize.width>700) {
				pos = pos+60;
				rotateBy = rotateBy+5;
			} else {
				pos = pos+40;
				rotateBy = rotateBy+5;
			}
			scene.addChild(card);
		}
	}
	
	public static void drawTrumpAndBidBoxes() {
		
		if(!player1SelectedTrump || firstRoundBiddingOver) {
			if(winSize.width>700) {
				currentBid = CCLabel.makeLabel(currentBidValue.toString(), "Times New Roman", 40);
				increase = CCLabel.makeLabel("+1", "Times New Roman", 40);
				decrease = CCLabel.makeLabel("-1", "Times New Roman", 40);
				setTrump = CCLabel.makeLabel("Set Trump!", "Times New Roman", 45);
				currentBid.setPosition(winSize.width/1.5f, winSize.height/3);
				increase.setPosition(currentBid.getPosition().x+60, currentBid.getPosition().y);
				decrease.setPosition(currentBid.getPosition().x+150, currentBid.getPosition().y);
				setTrump.setPosition(currentBid.getPosition().x+10, currentBid.getPosition().y+50);

			} else {
				currentBid = CCLabel.makeLabel(currentBidValue.toString(), "Times New Roman", 25);
				if(firstRoundBiddingOver && whoseTurn==1) {
					currentBid = CCLabel.makeLabel("20", "Times New Roman", 25);
				}
				increase = CCLabel.makeLabel("+1", "Times New Roman", 25);
				decrease = CCLabel.makeLabel("-1", "Times New Roman", 25);
				setTrump = CCLabel.makeLabel("Set Trump!", "Times New Roman", 20);
				currentBid.setPosition(winSize.width/2f, winSize.height/2f);
				increase.setPosition(currentBid.getPosition().x+30, currentBid.getPosition().y);
				decrease.setPosition(currentBid.getPosition().x+60, currentBid.getPosition().y);
				setTrump.setPosition(currentBid.getPosition().x+30, currentBid.getPosition().y+30);
			}
			currentBid.setColor(ccColor3B.ccYELLOW);
			scene.addChild(currentBid);
			
			increase.setColor(ccColor3B.ccGREEN);
			scene.addChild(increase);
			
			decrease.setColor(ccColor3B.ccORANGE);
			scene.addChild(decrease);
			
			setTrump.setColor(ccColor3B.ccWHITE);
			scene.addChild(setTrump);		
		}

	}
	
	public static void dealPlayer4() {
		float pos;
		int angle;
		float rotateBy;
		int noOfCards=4;
		if(winSize.width>700) {
			pos=winSize.height/2f;
		} else {
			pos =winSize.height/2.5f;
		}
		angle = -40;
		rotateBy = 0;	
		cardsPlayer4 = CCLayer.node();
		if(firstRoundBiddingOver) {
			for(CCSprite card:p4Cards) {
				scene.removeChild(card, true);
			}
			noOfCards=8;
			if(currentBidOwner==4) {
				noOfCards=7;
			}
			angle=-60;
		}
		p4Cards = new ArrayList<CCSprite>();
		for(int i =0;i<noOfCards;i++) {
			CCSprite cardInHand = CCSprite.sprite("img/b1fv.png");
			cardInHand.setRotation(270);
			
			CCRotateBy rotate = CCRotateBy.action(1, angle+rotateBy);
			cardInHand.runAction(rotate);
			if(winSize.width > 700) { //if it's a tab
				cardInHand.setPosition(winSize.width/1.07f,pos);
			} else {
				cardInHand.setScale(0.6f);
				cardInHand.setPosition(winSize.width/1.16f,pos);
			}
			p4Cards.add(cardInHand);
			cardsPlayer4.addChild(cardInHand);
			if(winSize.width>700) {
				pos = pos+20;
				rotateBy = rotateBy+20;
			} else {
				pos = pos+10;
				rotateBy = rotateBy+15;
			}
			scene.addChild(cardInHand);
		}
	}

	public static void dealPlayer3() {
		float pos;
		int angle;
		float rotateBy;
		int noOfCards=4;
		if(winSize.width>700) {
			pos = winSize.width/2f;
		} else {
			pos = winSize.width/2.5f;
		}
		angle = 40;
		rotateBy = 0;	
		cardsPlayer3 = CCLayer.node();
		if(firstRoundBiddingOver) {
			for(CCSprite card:p3Cards) {
				scene.removeChild(card, true);
			}
			noOfCards=8;
			if(currentBidOwner==3) {
				noOfCards=7;
			}
			angle=60;
		}
		p3Cards = new ArrayList<CCSprite>();


		for(int i=0;i<noOfCards;i++) {
			CCSprite cardInHand = CCSprite.sprite("img/b1fv.png");
			cardInHand.setRotation(180);
			
			CCRotateBy rotate = CCRotateBy.action(1, angle+rotateBy);
			cardInHand.runAction(rotate);
			if(winSize.width > 700) { //if it's a tab
				cardInHand.setPosition(pos,winSize.height/1.15f);
			} else {
				cardInHand.setScale(0.6f);
				cardInHand.setPosition(pos,winSize.height/1.25f);
			}
			
			p3Cards.add(cardInHand);
			cardsPlayer3.addChild(cardInHand);
			if(winSize.width>700) {
				pos = pos+20;
				rotateBy = rotateBy-20;
			} else {
				pos = pos+10;
				rotateBy = rotateBy-15;
			}
			scene.addChild(cardInHand);
		}
	}

	public static void dealPlayer2() {
		float pos;
		int angle;
		float rotateBy;
		int noOfCards=4;
		//Player2
		cardsPlayer2 = CCLayer.node();
		pos =winSize.height/2;
		angle = 20;
		rotateBy = 0;
		if(firstRoundBiddingOver) {
			for(CCSprite card:p2Cards) {
				scene.removeChild(card, true);
			}
			noOfCards=8;
			if(currentBidOwner==2) {
				noOfCards=7;
			}
			angle=60;
		}
		p2Cards = new ArrayList<CCSprite>();
		for(int i =0;i<noOfCards;i++) {
			CCSprite cardInHand = CCSprite.sprite("img/b1fv.png");
			cardInHand.setRotation(90);
			if(winSize.width > 700) { //if it's a tab
				cardInHand.setPosition(winSize.width/11,pos);
			} else {
				cardInHand.setScale(0.6f);
				cardInHand.setPosition(winSize.width/7,pos);
			}
			
			CCRotateBy rotate = CCRotateBy.action(1, angle-rotateBy);
			cardInHand.runAction(rotate);
			
			p2Cards.add(cardInHand);
			if(winSize.width>700) {
				pos = pos+20;
				rotateBy = rotateBy+20;
			} else {
				pos = pos+10;
				rotateBy = rotateBy+20;
			}
			scene.addChild(cardInHand);
		}
	}

	public static void dealPlayer1() {
		cardsPlayer1 = CCLayer.node();
		float pos =winSize.width/2;
		int angle;
		float rotateBy = 0;
		//show player 1 the 4 cards he got after dealing
		if(winSize.width<700) {
			pos = pos+45;
			angle=-20;
		} else {
			angle=-20;
		}
		int trumpIndex=10;
		if(firstRoundBiddingOver) {
			player1.setPosition(winSize.width/2.5f,15);
			if(currentBidOwner==1) {
				trumpCard.setPosition(winSize.width/4f, 15);
				player1.setPosition(winSize.width/3f,15);
				trumpIndex=trumpCardIndex;
			}
			for(CCSprite card:p1Cards) {
				scene.removeChild(card, true);
			}
			pos = winSize.width/2.5f;
			if(winSize.width<700) {
				angle=-30;
			} else {
				angle=-25;
			}
		}
		p1Cards = new ArrayList<CCSprite>();
		player1Cards = new ArrayList<Card>();
		int index=0;
		for(Card c: p1.getMyHand().getMyCards()) {
			CCSprite cardInHand = CCSprite.sprite(Card.cardMap.get(c.toString()));
			//if(index != trumpIndex) {
				if(winSize.width > 700) { //if it's a tab
					cardInHand.setScale(1.7f);
					cardInHand.setPosition(pos,winSize.height/5);
					if(!firstRoundBiddingOver) {
						cardInHand.setPosition(pos,winSize.height/6);
					} else {
						cardInHand.setPosition(pos,winSize.height/6.5f);
						if(p1.getMyHand().getMyCards().indexOf(c)==6) {
							cardInHand.setPosition(pos,winSize.height/7f);
						}
						if(p1.getMyHand().getMyCards().indexOf(c)==7) {
							cardInHand.setPosition(pos,winSize.height/8f);
						}
					}
				} else {
					cardInHand.setScale(0.8f);
					if(!firstRoundBiddingOver) {
						cardInHand.setPosition(pos,winSize.height/6);
					} else {
						cardInHand.setPosition(pos,winSize.height/6.5f);
						if(p1.getMyHand().getMyCards().indexOf(c)==6) {
							cardInHand.setPosition(pos,winSize.height/7f);
						}
						if(p1.getMyHand().getMyCards().indexOf(c)==7) {
							cardInHand.setPosition(pos-10,winSize.height/8f);
						}
					}
				}
				CCRotateBy rotate = CCRotateBy.action(1, angle+rotateBy);
				cardInHand.runAction(rotate);
				
				if(winSize.width>700) {
					if(firstRoundBiddingOver) {
						pos = pos+70;
					} else {
						pos = pos+55;
					}
					rotateBy = rotateBy+10;
				} else {
					pos = pos+40;
					rotateBy = rotateBy+10;
				}
				scene.addChild(cardInHand);
			//}
			p1Cards.add(cardInHand);
			player1Cards.add(c);
			index++;
		}
		if(firstRoundBiddingOver && currentBidOwner==1) {
			p1Cards.add(showTrumpCard);
		}
		card1OrigPosition = p1Cards.get(0).getPosition();
		card2OrigPosition = p1Cards.get(1).getPosition();
		card3OrigPosition = p1Cards.get(2).getPosition();
		card4OrigPosition = p1Cards.get(3).getPosition();
		if(p1.getMyHand().getMyCards().size()>4) {
			card5OrigPosition = p1Cards.get(4).getPosition();
			card6OrigPosition = p1Cards.get(5).getPosition();
			card7OrigPosition = p1Cards.get(6).getPosition();
			if(currentBidOwner !=1) {
				card8OrigPosition = p1Cards.get(7).getPosition();
			}
		}
	}
	
	public static void removeTrumpAndBidBoxes() {
		scene.removeChild(currentBid,true);
		scene.removeChild(increase,true);
		scene.removeChild(decrease,true);
		scene.removeChild(setTrump,true);
	}

	public static void checkCard1Selected() {
		if(p1Cards.get(0).getPosition().x != card1OrigPosition.x || p1Cards.get(0).getPosition().y != card1OrigPosition.y) {
			CCMoveTo moveTo = CCMoveTo.action(0.5f, card1OrigPosition);
			p1Cards.get(0).runAction(moveTo);
			toggle1=false;
			CardUtilities.removeTrumpAndBidBoxes();
		}
	}
	
	public static void checkCard2Selected() {
		if(p1Cards.get(1).getPosition().x != card2OrigPosition.x || p1Cards.get(1).getPosition().y != card2OrigPosition.y) {
			CCMoveTo moveTo = CCMoveTo.action(0.5f, card2OrigPosition);
			p1Cards.get(1).runAction(moveTo);
			toggle2=false;
			CardUtilities.removeTrumpAndBidBoxes();
		}
	}
	
	public static void checkCard3Selected() {
		if(p1Cards.get(2).getPosition().x != card3OrigPosition.x || p1Cards.get(2).getPosition().y != card3OrigPosition.y) {
			CCMoveTo moveTo = CCMoveTo.action(0.5f, card3OrigPosition);
			p1Cards.get(2).runAction(moveTo);
			toggle3=false;
			CardUtilities.removeTrumpAndBidBoxes();
		}
	}
	
	public static void checkCard4Selected() {
		if(p1Cards.get(3).getPosition().x != card4OrigPosition.x || p1Cards.get(3).getPosition().y != card4OrigPosition.y) {
			CCMoveTo moveTo = CCMoveTo.action(0.5f, card4OrigPosition);
			p1Cards.get(3).runAction(moveTo);
			toggle4=false;
			CardUtilities.removeTrumpAndBidBoxes();
		}
	}
	
	public static void checkCard5Selected() {
		if(p1Cards.get(4).getPosition().x != card5OrigPosition.x || p1Cards.get(4).getPosition().y != card5OrigPosition.y) {
			CCMoveTo moveTo = CCMoveTo.action(0.5f, card5OrigPosition);
			p1Cards.get(4).runAction(moveTo);
			toggle5=false;
			CardUtilities.removeTrumpAndBidBoxes();
		}
	}
	
	public static void checkCard6Selected() {
		if(p1Cards.get(5).getPosition().x != card6OrigPosition.x || p1Cards.get(5).getPosition().y != card6OrigPosition.y) {
			CCMoveTo moveTo = CCMoveTo.action(0.5f, card6OrigPosition);
			p1Cards.get(5).runAction(moveTo);
			toggle6=false;
			CardUtilities.removeTrumpAndBidBoxes();
		}
	}
	
	public static void checkCard7Selected() {
		if(p1Cards.get(6).getPosition().x != card7OrigPosition.x || p1Cards.get(6).getPosition().y != card7OrigPosition.y) {
			CCMoveTo moveTo = CCMoveTo.action(0.5f, card7OrigPosition);
			p1Cards.get(6).runAction(moveTo);
			toggle7=false;
			CardUtilities.removeTrumpAndBidBoxes();
		}
	}
	
	public static void checkCard8Selected() {
		if(currentBidOwner!=1 &&(p1Cards.get(7).getPosition().x != card8OrigPosition.x || p1Cards.get(7).getPosition().y != card8OrigPosition.y)) {
			CCMoveTo moveTo = CCMoveTo.action(0.5f, card8OrigPosition);
			p1Cards.get(7).runAction(moveTo);
			toggle8=false;
			CardUtilities.removeTrumpAndBidBoxes();
		}
	}
	
	public static void justRearrange(ArrayList<CCSprite> pCards,int player) {
		if(player==1) {
			for(CCSprite card:pCards) {
				scene.removeChild(card, true);
				//card.setPosition(card.getPosition().x-2,card.getPosition().y);
				scene.addChild(card);
			}
		} else 
		if(player==2) {
			for(CCSprite csprite:pCards) {
				csprite.setPosition(csprite.getPosition().x, csprite.getPosition().y-3);
			}
		} else if(player==3) {
			for(CCSprite csprite:pCards) {
				csprite.setPosition(csprite.getPosition().x-1, csprite.getPosition().y);
			}			
		} else if(player==4) {
			for(CCSprite csprite:pCards) {
				csprite.setPosition(csprite.getPosition().x, csprite.getPosition().y-3);
			}			
		}
	}
	
	public static void afterRevealingTrumpAI(ArrayList<CCSprite> pCards,int player) {
		if(player==2) {
			for(CCSprite card:pCards) {
				scene.removeChild(card, true);
				card.setPosition(card.getPosition().x, card.getPosition().y-3);
				scene.addChild(card);
			}

		} else if(player==3) {
			for(CCSprite card:pCards) {
				scene.removeChild(card, true);
				card.setPosition(card.getPosition().x-1, card.getPosition().y);
				scene.addChild(card);
			}
	
		} else if(player==4) {
			for(CCSprite card:pCards) {
				scene.removeChild(card, true);
				card.setPosition(card.getPosition().x, card.getPosition().y-3);
				scene.addChild(card);
			}
			
		}		
	}
}

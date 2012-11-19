package com.example.testcocos2d;

import java.util.ArrayList;

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

	public static void rearrangeCards(ArrayList<CCSprite> pCards) {
		float pos =winSize.width/2;
		if(winSize.width<700) {
			pos = pos+40;
		}
		int angle = 10;
		float rotateBy = 0;
		int index=0;
		for(CCSprite card:pCards) {
			scene.removeChild(card, true);
			if(index!=trumpCardIndex) {
				if(winSize.width > 700) { //if it's a tab
					card.setScale(2f);
					card.setPosition(pos,winSize.height/5);
				} else {
					card.setScale(0.8f);
					card.setPosition(pos,winSize.height/6);
				}
				CCRotateBy rotate = CCRotateBy.action(1, angle+rotateBy);
				card.runAction(rotate);
				
				pos = pos+20;
				rotateBy = 0;
				scene.addChild(card);
			}
			index++;
		}
		
	}
	
	public static void drawTrumpAndBidBoxes() {
		
		if(!player1SelectedTrump) {
			if(winSize.width>700) {
				currentBid = CCLabel.makeLabel(bid.toString(), "Times New Roman", 40);
				increase = CCLabel.makeLabel("+1", "Times New Roman", 40);
				decrease = CCLabel.makeLabel("-1", "Times New Roman", 40);
				setTrump = CCLabel.makeLabel("Set Trump!", "Times New Roman", 45);
				currentBid.setPosition(winSize.width/1.5f, winSize.height/3);
				increase.setPosition(currentBid.getPosition().x+30, currentBid.getPosition().y);
				decrease.setPosition(currentBid.getPosition().x+30, currentBid.getPosition().y-80);
				setTrump.setPosition(currentBid.getPosition().x+10, currentBid.getPosition().y+50);

			} else {
				currentBid = CCLabel.makeLabel(bid.toString(), "Times New Roman", 25);
				increase = CCLabel.makeLabel("+1", "Times New Roman", 20);
				decrease = CCLabel.makeLabel("-1", "Times New Roman", 25);
				setTrump = CCLabel.makeLabel("Set Trump!", "Times New Roman", 20);
				currentBid.setPosition(winSize.width/4f, winSize.height/4f);
				increase.setPosition(currentBid.getPosition().x+30, currentBid.getPosition().y);
				decrease.setPosition(currentBid.getPosition().x+60, currentBid.getPosition().y);
				setTrump.setPosition(currentBid.getPosition().x+30, currentBid.getPosition().y+20);
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
		cardsPlayer4 = CCLayer.node();
		p4Cards = new ArrayList<CCSprite>();

		pos =winSize.height/2;
		angle = -40;
		rotateBy = 0;	
		for(int i =0;i<4;i++) {
			CCSprite cardInHand = CCSprite.sprite("img/b1fv.png");
			cardInHand.setRotation(270);
			
			CCRotateBy rotate = CCRotateBy.action(1, angle+rotateBy);
			rotateBy = rotateBy+20;
			cardInHand.runAction(rotate);
			if(winSize.width > 700) { //if it's a tab
				cardInHand.setPosition(winSize.width/1.4f,pos);
			} else {
				cardInHand.setScale(0.6f);
				cardInHand.setPosition(winSize.width/1.2f,pos);
			}
			p4Cards.add(cardInHand);
			cardsPlayer4.addChild(cardInHand);
			pos = pos+20;
			scene.addChild(cardInHand);
		}
	}

	public static void dealPlayer3() {
		float pos;
		int angle;
		float rotateBy;
		cardsPlayer3 = CCLayer.node();
		p3Cards = new ArrayList<CCSprite>();

		pos = winSize.width/2;
		angle = 40;
		rotateBy = 0;	
		for(int i=0;i<4;i++) {
			CCSprite cardInHand = CCSprite.sprite("img/b1fv.png");
			cardInHand.setRotation(180);
			
			CCRotateBy rotate = CCRotateBy.action(1, angle+rotateBy);
			rotateBy = rotateBy-20;
			cardInHand.runAction(rotate);
			if(winSize.width > 700) { //if it's a tab
				cardInHand.setPosition(pos,winSize.height/1.15f);
			} else {
				cardInHand.setScale(0.6f);
				cardInHand.setPosition(pos,winSize.height/1.3f);
			}
			
			p3Cards.add(cardInHand);
			cardsPlayer3.addChild(cardInHand);
			pos = pos+20;
			scene.addChild(cardInHand);
		}
	}

	public static void dealPlayer2() {
		float pos;
		int angle;
		float rotateBy;
		//Player2
		cardsPlayer2 = CCLayer.node();
		p2Cards = new ArrayList<CCSprite>();

		pos =winSize.height/2;
		angle = 40;
		rotateBy = 0;	
		for(int i =0;i<4;i++) {
			CCSprite cardInHand = CCSprite.sprite("img/b1fv.png");
			cardInHand.setRotation(90);
			if(winSize.width > 700) { //if it's a tab
				cardInHand.setPosition(winSize.width/11,pos);
			} else {
				cardInHand.setScale(0.6f);
				cardInHand.setPosition(winSize.width/7,pos);
			}
			
			CCRotateBy rotate = CCRotateBy.action(1, angle-rotateBy);
			rotateBy = rotateBy+20;
			cardInHand.runAction(rotate);
			
			p2Cards.add(cardInHand);
			pos = pos+20;
			scene.addChild(cardInHand);
		}
	}

	public static void dealPlayer1() {
		cardsPlayer1 = CCLayer.node();
		//show player 1 the 4 cards he got after dealing
		p1Cards = new ArrayList<CCSprite>();
		float pos =winSize.width/2;
		if(winSize.width<700) {
			pos = pos+40;
		}
		int angle = -40;
		float rotateBy = 0;
		
		for(Card c: p1.getMyHand().getMyCards()) {
			CCSprite cardInHand = CCSprite.sprite(Card.cardMap.get(c.toString()));
			if(winSize.width > 700) { //if it's a tab
				cardInHand.setScale(2f);
				cardInHand.setPosition(pos,winSize.height/5);
			} else {
				cardInHand.setScale(0.8f);
				cardInHand.setPosition(pos,winSize.height/6);
			}
			CCRotateBy rotate = CCRotateBy.action(1, angle+rotateBy);
			cardInHand.runAction(rotate);
			
			p1Cards.add(cardInHand);
			pos = pos+30;
			rotateBy = rotateBy+25;
			scene.addChild(cardInHand);
		}
		
		card1OrigPosition = p1Cards.get(0).getPosition();
		card2OrigPosition = p1Cards.get(1).getPosition();
		card3OrigPosition = p1Cards.get(2).getPosition();
		card4OrigPosition = p1Cards.get(3).getPosition();
	}
}

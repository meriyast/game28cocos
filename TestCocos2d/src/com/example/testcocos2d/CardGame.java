package com.example.testcocos2d;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.actions.ease.CCEaseAction;
import org.cocos2d.actions.ease.CCEaseElastic;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.view.MotionEvent;
import cards.Card;
import cards.Game;
import cards.GameStatus;
import cards.Player;
import cards.Trump;

public class CardGame extends CCColorLayer {
	
	public static CGSize winSize;
	public static CCScene scene;
	public static CCLayer layer;
	
	static CCLayer cardsPlayer1;
	static CCLayer cardsPlayer2;
	static CCLayer cardsPlayer3;
	static CCLayer cardsPlayer4;
	
	static Player p1;
	static Player p2;
	static Player p3;
	static Player p4;

	CCSprite player1;
	CCSprite player2;
	CCSprite player3;
	CCSprite player4;
	
	Card selectedCard;
	
	static ArrayList<CCSprite> p1Cards;
	static ArrayList<CCSprite> p2Cards;
	static ArrayList<CCSprite> p3Cards;
	static ArrayList<CCSprite> p4Cards;
	
	boolean toggle4 = false;
	boolean toggle3 = false;
	boolean toggle2 = false;
	boolean toggle1 = false;

	boolean cardSelected = false;
	static int trumpCardIndex;
	
	static CGPoint card1OrigPosition;
	static CGPoint card2OrigPosition;
	static CGPoint card3OrigPosition;
	static CGPoint card4OrigPosition;
	
	static Integer bid = 14;
	static CCLabel currentBid;
	static CCLabel increase;
	static CCLabel decrease;
	static CCLabel setTrump;
	
	Game game;
	CCSprite message;
	CCSprite trumpCard;
	CCSprite showTrumpCard;
	boolean cardShownToHimself;
	
	boolean gameOver = false;
	int whoseTurn = 1;
	Trump trump = new Trump();
	static boolean player1SelectedTrump = false;
	boolean player2SelectedTrump = false;
	boolean player3SelectedTrump = false;
	boolean player4SelectedTrump = false;
	
	CGRect cardBox1;
	CGRect cardBox2;
	CGRect cardBox3;
	CGRect cardBox4;
	public static CCScene scene()
	{
	    scene = CCScene.node();
	    layer = new CardGame(ccColor4B.ccc4(0, 0, 0, 0));

	    scene.addChild(layer);
	 
	    return scene;
	}
	
	public CardGame(ccColor4B ccColor4B) {
		
		super(ccColor4B);
	    this.setIsTouchEnabled(true);

		winSize = CCDirector.sharedDirector().displaySize();

		CCSprite gameStatus;
		game = new Game();

		
		p1 = new Player("Jeevan", 0);
		player1 = CCLabel.makeLabel("Jeevan", "Arial", 15);
		player1.setColor(ccColor3B.ccGREEN);

		player1.setPosition(CGPoint.ccp(winSize.width/2,25));
		scene.addChild(player1);
		game.joinGame(p1);

    	p2 = new Player("Meriya", 0);
    	player2 = 	CCLabel.makeLabel("Meriya", "Arial", 15);
		player2.setColor(ccColor3B.ccMAGENTA);
		player2.setPosition(CGPoint.ccp(25,winSize.height/2));
		player2.setRotation(90);
		scene.addChild(player2);
		game.joinGame(p2);

		p3 = new Player("Janith", 0);
	    player3 = CCLabel.makeLabel("Janith", "Arial", 15);
		player3.setColor(ccColor3B.ccYELLOW);
		player3.setPosition(CGPoint.ccp(winSize.width/2,winSize.height-25));
		player3.setRotation(180);
		scene.addChild(player3);
		game.joinGame(p3);

		p4 = new Player("Cijo", 0);
		player4 = CCLabel.makeLabel("Cijo", "Arial", 15);
		player4.setColor(ccColor3B.ccWHITE);
		player4.setPosition(CGPoint.ccp(winSize.width-25,winSize.height/2));
		player4.setRotation(270);
		scene.addChild(player4);
		game.joinGame(p4);
		
		game.deal();
		winSize = CCDirector.sharedDirector().displaySize();

		CardUtilities.dealPlayer1();
		//show the back of cards for the rest of the players
		//Player2
		CardUtilities.dealPlayer2();

		//Player3
		CardUtilities.dealPlayer3();
		
		//Player4
		CardUtilities.dealPlayer4();

		game.setStatus(GameStatus.BID);
		if(winSize.width>700) {
			gameStatus = CCLabel.makeLabel(game.getStatus().getDescription(), "Arial", 50);
		} else {
			gameStatus = CCLabel.makeLabel(game.getStatus().getDescription(), "Arial", 20);
		}
		gameStatus.setColor(ccColor3B.ccWHITE);
		gameStatus.setPosition(winSize.width/2,winSize.height/1.5f);
		CCFadeOut fadeOut = CCFadeOut.action(15);
		gameStatus.runAction(fadeOut);
		scene.addChild(gameStatus);
		this.schedule("update");
	}


	
	public void update(float dt) {
		winSize = CCDirector.sharedDirector().displaySize();

		if(whoseTurn == 1) { //Player 1 chance
			game.getStatus();
			if(game.getStatus().getDescription().equals(GameStatus.BID.getDescription())) {
				if(winSize.width > 700) {
					message = CCLabel.makeLabel("Select a trump card and the bid value", "Times New Roman", 30);
				} else {
					message = CCLabel.makeLabel("Select a trump card and the bid value", "Times New Roman", 15);
				}
				message.setColor(ccColor3B.ccc3(255, 102, 102));
				message.setPosition(winSize.width/2,winSize.height/2);
				CCFadeOut fadeOut = CCFadeOut.action(6);
				message.runAction(fadeOut);
				scene.addChild(message);
			}

		} else if(whoseTurn == 2) { //Player 2 chance
			if(game.getStatus().getDescription().equals(GameStatus.BID.getDescription())) {

			}				
		} else if(whoseTurn ==3) { //Player 3 chance
			if(game.getStatus().getDescription().equals(GameStatus.BID.getDescription())) {

			}					
		} else if(whoseTurn ==4) { //Player 4 chance
			if(game.getStatus().getDescription().equals(GameStatus.BID.getDescription())) {

			}	
		}
	}
	
	private void removeTrumpAndBidBoxes() {
		scene.removeChild(currentBid,true);
		scene.removeChild(increase,true);
		scene.removeChild(decrease,true);
		scene.removeChild(setTrump,true);
	}
	
	private void setTrumpCard(Card selectedCard, int index) {
		if(whoseTurn ==1){
			trump.setTrumpCard(selectedCard);
			trump.setCurrentHightestBid(bid);
			trump.setBidOwner(p1);
			game.setTrump(trump);
			
			CCFadeOut fadeOut = CCFadeOut.action(1);
			p1Cards.get(index).runAction(fadeOut);
			scene.removeChild(p1Cards.get(index),true);
			
			trumpCard = CCSprite.sprite("img/b1fv.png");
			if(winSize.width > 700) {
				trumpCard.setScale(1f);
				trumpCard.setPosition(winSize.width/4, winSize.height/8);
			} else {
				trumpCard.setScale(0.6f);
				trumpCard.setPosition(winSize.width/3, winSize.height/15);				
			}
			CCFadeIn fadeIn = CCFadeIn.action(1);
			trumpCard.runAction(fadeIn);
			scene.addChild(trumpCard);
			player1SelectedTrump = true;
			if(player1SelectedTrump) {
				whoseTurn =2;
			}
			removeTrumpAndBidBoxes();
			CardUtilities.rearrangeCards(p1Cards);

		}
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {		
		
		CGPoint location = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(),event.getY()));

		if(player1SelectedTrump) {
			CGRect trumpBox = CGRect.make(trumpCard.getPosition().x - trumpCard.getContentSize().width/2, trumpCard.getPosition().y
					- trumpCard.getContentSize().height/2, trumpCard.getContentSize().width, trumpCard.getContentSize().height);
			if(CGRect.containsPoint(trumpBox, location)) {
				if(!cardShownToHimself) {
					CCFadeOut fadeOut = CCFadeOut.action(0.25f);
					trumpCard.runAction(fadeOut);
					scene.removeChild(trumpCard, true);		
					showTrumpCard = CCSprite.sprite(Card.cardMap.get(selectedCard.toString()));
					showTrumpCard.setPosition(trumpCard.getPosition().x, trumpCard.getPosition().y);
					CCEaseAction elastic = CCEaseElastic.action(CCIntervalAction.action(1));
					showTrumpCard.runAction(elastic);
					if(winSize.width<700) {
						showTrumpCard.setScale(0.6f);
					}
					scene.addChild(showTrumpCard);
					cardShownToHimself = true;
				} else {
					CCFadeOut fadeOut = CCFadeOut.action(0.25f);
					showTrumpCard.runAction(fadeOut);
					scene.removeChild(showTrumpCard, true);		
					trumpCard = CCSprite.sprite("img/b1fv.png");
					trumpCard.setPosition(showTrumpCard.getPosition().x, showTrumpCard.getPosition().y);
					CCEaseAction elastic = CCEaseElastic.action(CCIntervalAction.action(1));
					trumpCard.runAction(elastic);
					if(winSize.width<700) {
						trumpCard.setScale(0.6f);
					}
					scene.addChild(trumpCard);	
					cardShownToHimself = false;
				}


			}
		}
		if(!player1SelectedTrump && cardSelected) {
			CGRect increaseBox= CGRect.make(increase.getPosition().x - increase.getContentSize().width/2, increase.getPosition().y 
					- increase.getContentSize().height/2, increase.getContentSize().width,increase.getContentSize().height);
		
			if(CGRect.containsPoint(increaseBox,location)){
				bid = bid+1;
				scene.removeChild(currentBid,true);
				if(winSize.width>700) {
					currentBid = CCLabel.makeLabel(bid.toString(), "Times New Roman", 40);
				} else {
					currentBid = CCLabel.makeLabel(bid.toString(), "Times New Roman", 25);
				}
				currentBid.setColor(ccColor3B.ccYELLOW);
				if(winSize.width>700) { 
					currentBid.setPosition(winSize.width/1.5f, winSize.height/3);
				} else {
					currentBid.setPosition(winSize.width/4f, winSize.height/4f);
				}
				scene.addChild(currentBid);
			}
			
			CGRect decreaseBox= CGRect.make(decrease.getPosition().x - decrease.getContentSize().width/2, decrease.getPosition().y 
					- decrease.getContentSize().height/2, decrease.getContentSize().width,decrease.getContentSize().height);
		
			if(CGRect.containsPoint(decreaseBox,location)){
				scene.removeChild(currentBid,true);
				if(bid >14) {
					bid = bid-1;
				}
				if(winSize.width>700) {
					currentBid = CCLabel.makeLabel(bid.toString(), "Times New Roman", 40);
				} else {
					currentBid = CCLabel.makeLabel(bid.toString(), "Times New Roman", 25);
				}				
				currentBid.setColor(ccColor3B.ccYELLOW);
				if(winSize.width > 700) {
					currentBid.setPosition(winSize.width/1.5f, winSize.height/3);
				} else {
					currentBid.setPosition(winSize.width/4f, winSize.height/4f);
				}
				scene.addChild(currentBid);
			}
			
			CGRect setTrumpBox= CGRect.make(setTrump.getPosition().x - setTrump.getContentSize().width/2, setTrump.getPosition().y 
					- setTrump.getContentSize().height/2, setTrump.getContentSize().width,setTrump.getContentSize().height);
		
			if(CGRect.containsPoint(setTrumpBox,location)){
				setTrumpCard(selectedCard,trumpCardIndex);
				removeTrumpAndBidBoxes();
			}
		}
		if(!player1SelectedTrump) {
			cardBox4 = CGRect.make(p1Cards.get(3).getPosition().x-p1Cards.get(3).getContentSize().width/2,
					p1Cards.get(3).getPosition().y, 
					p1Cards.get(3).getContentSize().width,p1Cards.get(3).getContentSize().height);
			//CGPoint touchLocationRelative4 = p1Cards.get(3).convertToNodeSpace(location);
			if(CGRect.containsPoint(cardBox4, location)) {
				if(!toggle4) {
					CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
					p1Cards.get(3).runAction(moveBy);	
					toggle4=true;
					selectedCard = p1.getMyHand().getMyCards().get(3);
					trumpCardIndex = 3;
					if(p1Cards.get(0).getPosition() != card1OrigPosition) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card1OrigPosition);
						p1Cards.get(0).runAction(moveTo);
						toggle1=false;
						removeTrumpAndBidBoxes();
					}
					if(p1Cards.get(1).getPosition() != card2OrigPosition) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card2OrigPosition);
						p1Cards.get(1).runAction(moveTo);
						toggle2=false;
						removeTrumpAndBidBoxes();
					}
					if(p1Cards.get(2).getPosition() != card3OrigPosition) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card3OrigPosition);
						p1Cards.get(2).runAction(moveTo);
						toggle3=false;
						removeTrumpAndBidBoxes();
					}
					CardUtilities.drawTrumpAndBidBoxes();
					cardSelected=true;
				} else {
					CCMoveTo moveTo = CCMoveTo.action(0.5f, card4OrigPosition);
					p1Cards.get(3).runAction(moveTo);
					toggle4=false;
					cardSelected=false;
					removeTrumpAndBidBoxes();
				}
			}
			
			cardBox3 = CGRect.make(p1Cards.get(2).getPosition().x-p1Cards.get(2).getContentSize().width/2f,
					p1Cards.get(2).getPosition().y, 
					p1Cards.get(2).getContentSize().width/2,p1Cards.get(2).getContentSize().height);
			//CGPoint touchLocationRelative3 = p1Cards.get(2).convertToNodeSpace(location);
			if(CGRect.containsPoint(cardBox3, location)) {
				if(!toggle3) {
					CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
					p1Cards.get(2).runAction(moveBy);
					toggle3=true;
					selectedCard = p1.getMyHand().getMyCards().get(2);
					trumpCardIndex = 2;
					if(p1Cards.get(0).getPosition() != card1OrigPosition) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card1OrigPosition);
						p1Cards.get(0).runAction(moveTo);
						toggle1=false;
						removeTrumpAndBidBoxes();
					}
					if(p1Cards.get(1).getPosition() != card2OrigPosition) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card2OrigPosition);
						p1Cards.get(1).runAction(moveTo);
						toggle2=false;
						removeTrumpAndBidBoxes();
					}
					if(p1Cards.get(3).getPosition() != card4OrigPosition) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card4OrigPosition);
						p1Cards.get(3).runAction(moveTo);
						toggle4=false;
						removeTrumpAndBidBoxes();
					}
					CardUtilities.drawTrumpAndBidBoxes();
					cardSelected=true;
				} else {
					CCMoveTo moveTo = CCMoveTo.action(0.5f, card3OrigPosition);
					p1Cards.get(2).runAction(moveTo);
					toggle3=false;
					cardSelected=false;
					removeTrumpAndBidBoxes();
				}
			}
			if(winSize.width > 700) {
				cardBox2 = CGRect.make(p1Cards.get(1).getPosition().x-p1Cards.get(1).getContentSize().width,
						p1Cards.get(1).getPosition().y, 
						p1Cards.get(1).getContentSize().width/2.5f,p1Cards.get(1).getContentSize().height);
			} else {
				cardBox2 = CGRect.make(p1Cards.get(1).getPosition().x-p1Cards.get(1).getContentSize().width/2,
						p1Cards.get(1).getPosition().y, 
						p1Cards.get(1).getContentSize().width/2.5f,p1Cards.get(1).getContentSize().height);
			}
			//CGPoint touchLocationRelative2 = p1Cards.get(1).convertToNodeSpace(location);
			if(CGRect.containsPoint(cardBox2, location)) {
				if(!toggle2) {
					CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
					p1Cards.get(1).runAction(moveBy);	
					toggle2=true;
					selectedCard = p1.getMyHand().getMyCards().get(1);
					trumpCardIndex = 1;
					if(p1Cards.get(0).getPosition() != card1OrigPosition) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card1OrigPosition);
						p1Cards.get(0).runAction(moveTo);
						toggle1=false;
						removeTrumpAndBidBoxes();
					}
					if(p1Cards.get(2).getPosition() != card3OrigPosition) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card3OrigPosition);
						p1Cards.get(2).runAction(moveTo);
						toggle3=false;
						removeTrumpAndBidBoxes();
					}
					if(p1Cards.get(3).getPosition() != card4OrigPosition) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card4OrigPosition);
						p1Cards.get(3).runAction(moveTo);
						toggle4=false;
						removeTrumpAndBidBoxes();
					}
					CardUtilities.drawTrumpAndBidBoxes();
					cardSelected=true;
				} else {
					CCMoveTo moveTo = CCMoveTo.action(0.5f, card2OrigPosition);
					p1Cards.get(1).runAction(moveTo);
					toggle2=false;
					cardSelected=false;
					removeTrumpAndBidBoxes();
				}
			}
			
			if(winSize.width>700) {
				cardBox1 = CGRect.make(p1Cards.get(0).getPosition().x-1.5f*p1Cards.get(0).getContentSize().width,
					p1Cards.get(0).getPosition().y, 
					p1Cards.get(0).getContentSize().width/2,p1Cards.get(0).getContentSize().height);
			} else {
				cardBox1 = CGRect.make(p1Cards.get(0).getPosition().x-1.5f*p1Cards.get(0).getContentSize().width/2,
					p1Cards.get(0).getPosition().y, 
					p1Cards.get(0).getContentSize().width/2,p1Cards.get(0).getContentSize().height);
			}
			//CGPoint touchLocationRelative1 = p1Cards.get(0).convertToNodeSpace(location);
			if(CGRect.containsPoint(cardBox1, location)) {
				if(!toggle1) {
					CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
					p1Cards.get(0).runAction(moveBy);	
					toggle1=true;
					selectedCard = p1.getMyHand().getMyCards().get(0);
					trumpCardIndex = 0;
					if(p1Cards.get(2).getPosition() != card3OrigPosition) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card3OrigPosition);
						p1Cards.get(2).runAction(moveTo);
						toggle3=false;
						removeTrumpAndBidBoxes();
					}
					if(p1Cards.get(1).getPosition() != card2OrigPosition) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card2OrigPosition);
						p1Cards.get(1).runAction(moveTo);
						toggle2=false;
						removeTrumpAndBidBoxes();
					}
					if(p1Cards.get(3).getPosition() != card4OrigPosition) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card4OrigPosition);
						p1Cards.get(3).runAction(moveTo);
						toggle4=false;
						removeTrumpAndBidBoxes();
					}
					CardUtilities.drawTrumpAndBidBoxes();
					cardSelected=true;
				} else {
					CCMoveTo moveTo = CCMoveTo.action(0.5f, card1OrigPosition);
					p1Cards.get(0).runAction(moveTo);
					toggle1=false;
					cardSelected=false;
					removeTrumpAndBidBoxes();
				}
			}
		}
	  return CCTouchDispatcher.kEventHandled;
	}
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
	  return super.ccTouchesEnded(event);
	}
	
	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
	
	  return super.ccTouchesMoved(event);
	}
	
	@Override
	public boolean ccTouchesCancelled(MotionEvent event) {
	  return super.ccTouchesCancelled(event);
	}
	
    public void draw(GL10 gl) {

        gl.glEnable(GL10.GL_LINE_SMOOTH);

        // draw a green circle with 10 segments
        gl.glLineWidth(16);
        if(whoseTurn ==1) {
        	gl.glColor4f(player1.getColor().r, player1.getColor().g, player1.getColor().b, 1.0f);
        	
        	CCDrawingPrimitives.ccDrawCircle(gl, CGPoint.ccp(player1.getPosition().x, player1.getPosition().y), 
        			(player1.getContentSize().height/5)*(player1.getContentSize().width/5), 0, 100, false);
        } else if(whoseTurn ==2) {
        	gl.glColor4f(0,0,0, 1.0f);
        	CCDrawingPrimitives.ccDrawCircle(gl, CGPoint.ccp(player1.getPosition().x, player1.getPosition().y), 
        			(player1.getContentSize().height/5)*(player1.getContentSize().width/5), 0, 100, false);
        	
        	gl.glColor4f(player2.getColor().r, player2.getColor().g, player2.getColor().b, 1.0f);
        	CCDrawingPrimitives.ccDrawCircle(gl, CGPoint.ccp(player2.getPosition().x, player2.getPosition().y), 
        			(player2.getContentSize().height/5)*(player2.getContentSize().width/5), 0, 100, false);        	
        } else if(whoseTurn ==3) {
        	gl.glColor4f(player3.getColor().r, player3.getColor().g, player3.getColor().b, 1.0f);
        	CCDrawingPrimitives.ccDrawCircle(gl, CGPoint.ccp(player3.getPosition().x, player3.getPosition().y), 
        			(player3.getContentSize().height/5)*(player3.getContentSize().width/5), 0, 100, false);
        } else if(whoseTurn ==4) {
        	gl.glColor4f(player4.getColor().r, player4.getColor().g, player4.getColor().b, 1.0f);
        	CCDrawingPrimitives.ccDrawCircle(gl, CGPoint.ccp(player4.getPosition().x, player4.getPosition().y), 
        			(player4.getContentSize().height/5)*(player4.getContentSize().width/5), 0, 100, false);
        }
        // restore original values
        gl.glLineWidth(1);
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glPointSize(1);
    }
	
}

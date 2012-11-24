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
import cards.TrumpCandidate;

public class CardGame extends CCColorLayer {
	
	private static Integer lowestBidValue = 14;
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

	static CCSprite player1;
	static CCSprite player2;
	static CCSprite player3;
	static CCSprite player4;
	
	Card selectedCard;
    Card selectedTrumpCard;

	static ArrayList<CCSprite> p1Cards;
	static ArrayList<CCSprite> p2Cards;
	static ArrayList<CCSprite> p3Cards;
	static ArrayList<CCSprite> p4Cards;
	
	public static boolean toggle8 = false;
	public static boolean toggle7 = false;
	public static boolean toggle6 = false;
	public static boolean toggle5 = false;
	public static boolean toggle4 = false;
	public static boolean toggle3 = false;
	public static boolean toggle2 = false;
	public static boolean toggle1 = false;

	boolean cardSelected = false;
	static int trumpCardIndex;
	static int currentBidOwner;
	
	static CGPoint card1OrigPosition;
	static CGPoint card2OrigPosition;
	static CGPoint card3OrigPosition;
	static CGPoint card4OrigPosition;
	static CGPoint card5OrigPosition;
	static CGPoint card6OrigPosition;
	static CGPoint card7OrigPosition;
	static CGPoint card8OrigPosition;

	
	static Integer currentBidValue = 14;
	static Integer secondRoundMinimum = 20;
	static Integer trumpBidValue;
	
	static Integer player1Bid;
	static Integer player2Bid;
	static Integer player3Bid;
	static Integer player4Bid;
	
	static CCLabel currentBid;
	static CCLabel increase;
	static CCLabel decrease;
	static CCLabel setTrump;
	static CCLabel pass;
	
	CCSprite message1;
	CCSprite table;

	Game game;
	static CCSprite trumpCard;
	CCSprite showTrumpCard;
	static CCSprite selectedTrumpSprite;
	boolean cardShownToHimself;
	
	boolean gameOver = false;
	static int whoseTurn = 1;
	Trump trump = new Trump();
	static boolean player1SelectedTrump = false;
	static boolean player2SelectedTrump = false;
	static boolean player3SelectedTrump = false;
	static boolean player4SelectedTrump = false;
	
	CGRect cardBox1;
	CGRect cardBox2;
	CGRect cardBox3;
	CGRect cardBox4;
	CGRect cardBox5;
	CGRect cardBox6;
	CGRect cardBox7;
	CGRect cardBox8;
	CGRect tableBox;
	
	TrumpCandidate p2trumpCard;
	TrumpCandidate p3trumpCard;
	TrumpCandidate p4trumpCard;
	
	boolean player2pass=false;
    boolean player3pass=false;
    boolean player4pass=false;


	private boolean firstTimeM1=true;
	private boolean firstTimeM2=true;
	private boolean firstTimeM3=true;
	private boolean firstTimeM4=true;
	private boolean firstTimePlayM=true;

	private boolean secondDealOver;
	
	public static boolean firstRoundBiddingOver=false;
	CCSprite bidVale;
	CCFadeOut fadeOut;
	public static boolean secondRoundBiddingOver=false;
	private CCSprite movingCard;
	private int removeIndex;

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
		whoseTurn = 1;
		CCSprite gameStatus;
		game = new Game();

		table = CCSprite.sprite("img/table.jpg");
		if(winSize.width>700) {
			table.setScaleX(1.5f);
			table.setScaleY(1f);
			table.setPosition(winSize.width/2, winSize.height/1.7f);
		} else {
			table.setScale(0.4f);
			table.setPosition(winSize.width/2, winSize.height/2);
		}
		scene.addChild(table);
		tableBox = CGRect.make(table.getPosition().x-table.getContentSize().width/4.8f, table.getPosition().y-table.getContentSize().height/7f,
				table.getContentSize().width/2.5f,table.getContentSize().height/2f);

		
		p1 = new Player("Jeevan", 0, game);
		player1 = CCLabel.makeLabel("Jeevan", "Arial", 15);
		player1.setColor(ccColor3B.ccGREEN);

		player1.setPosition(CGPoint.ccp(winSize.width/2,25));
		scene.addChild(player1);
		game.joinGame(p1);

    	p2 = new Player("Meriya", 0, game);
    	player2 = 	CCLabel.makeLabel("Meriya", "Arial", 15);
		player2.setColor(ccColor3B.ccMAGENTA);
		player2.setPosition(CGPoint.ccp(25,winSize.height/2));
		player2.setRotation(90);
		scene.addChild(player2);
		game.joinGame(p2);

		p3 = new Player("Janith", 0, game);
	    player3 = CCLabel.makeLabel("Janith", "Arial", 15);
		player3.setColor(ccColor3B.ccYELLOW);
		player3.setPosition(CGPoint.ccp(winSize.width/2,winSize.height-25));
		player3.setRotation(180);
		scene.addChild(player3);
		game.joinGame(p3);

		p4 = new Player("Cijo", 0, game);
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
		fadeOut = CCFadeOut.action(15);
		gameStatus.runAction(fadeOut);
		scene.addChild(gameStatus);
		if(pass != null) {
			scene.addChild(pass);
		}
		if(trumpBidValue != null) {
			if(winSize.width > 700) {
				bidVale = CCLabel.makeLabel("Current Bid: "+trumpBidValue, "Times New Roman", 30);
			} else {
				bidVale = CCLabel.makeLabel("Current Bid: "+trumpBidValue, "Times New Roman", 15);
			}
			scene.addChild(bidVale);
		}	
		this.schedule("update",0.5f);
	}


	public void secondRoundCardDeal(float dt) {
		if(firstRoundBiddingOver && !secondDealOver) {
			this.unschedule("p2bid");
			this.unschedule("p3bid");
			this.unschedule("p4bid");
			game.deal();
			CCSprite gameStatus;
			if(winSize.width>700) {
				gameStatus = CCLabel.makeLabel("2nd Round Bidding", "Arial", 50);
			} else {
				gameStatus = CCLabel.makeLabel("2nd Round Bidding", "Arial", 20);
			}
			gameStatus.setColor(ccColor3B.ccWHITE);
			gameStatus.setPosition(winSize.width/2,winSize.height/1.5f);
			fadeOut = CCFadeOut.action(15);
			gameStatus.runAction(fadeOut);
			scene.addChild(gameStatus);
			CardUtilities.dealPlayer1();
			//show the back of cards for the rest of the players
			//Player2
			CardUtilities.dealPlayer2();
	
			//Player3
			CardUtilities.dealPlayer3();
			
			//Player4
			CardUtilities.dealPlayer4();
			secondDealOver=true;
			lowestBidValue=20;
			whoseTurn =1;
			currentBidValue=20;
			if(firstRoundBiddingOver) {
				pass = CCLabel.makeLabel("Pass",  "Times New Roman", 25);
				pass.setPosition(winSize.width/5f, winSize.height/3f);
				pass.setColor(ccColor3B.ccc3(127, 255, 212));
				scene.addChild(pass);
			}
			firstTimeM1=true;
			firstTimeM2=true;
			firstTimeM3=true;
			firstTimeM4=true;
		}
		
	}
	
	public void update(float dt) {
		winSize = CCDirector.sharedDirector().displaySize();
		if(secondRoundBiddingOver && firstTimePlayM && game.getStatus().getDescription().equals(GameStatus.PLAY.getDescription())) {
			CCLabel gameStatus;
			if(winSize.width>700) {
				gameStatus = CCLabel.makeLabel(game.getStatus().getDescription(), "Arial", 50);
			} else {
				gameStatus = CCLabel.makeLabel(game.getStatus().getDescription(), "Arial", 20);
			}
			gameStatus.setColor(ccColor3B.ccWHITE);
			gameStatus.setPosition(winSize.width/2,winSize.height/1.5f);
			fadeOut = CCFadeOut.action(15);
			gameStatus.runAction(fadeOut);
			scene.addChild(gameStatus);
			firstTimePlayM=false;
			firstTimeM1=true;
			firstTimeM2=true;
			firstTimeM3=true;
			firstTimeM4=true;
		}
		if(whoseTurn == 1) { //Player 1 chance
			if(game.getStatus().getDescription().equals(GameStatus.BID.getDescription())) {
				if(firstTimeM1) {
					if(winSize.width > 700) {
						message1 = CCLabel.makeLabel("Your turn to bid", "Times New Roman", 30);
					} else {
						message1 = CCLabel.makeLabel("Your turn to bid", "Times New Roman", 15);
					}
					message1.setColor(ccColor3B.ccc3(255, 102, 102));
					message1.setPosition(winSize.width/2,winSize.height/2);
					scene.addChild(message1);
					fadeOut = CCFadeOut.action(5);
					message1.runAction(fadeOut);
					firstTimeM1=false;
				}

			} else if(game.getStatus().getDescription().equals(GameStatus.PLAY.getDescription())) {
				if(firstTimeM1) {
					if(winSize.width > 700) {
						message1 = CCLabel.makeLabel("Your turn to play", "Times New Roman", 30);
					} else {
						message1 = CCLabel.makeLabel("Your turn to play", "Times New Roman", 15);
					}
					message1.setColor(ccColor3B.ccc3(25, 25, 112));
					message1.setPosition(winSize.width/2,winSize.height/2);
					scene.addChild(message1);
					fadeOut = CCFadeOut.action(5);
					message1.runAction(fadeOut);
					firstTimeM1=false;
				}
			}

		} else if(whoseTurn == 2) { //Player 2 chance
			CCSprite message2;
			if(game.getStatus().getDescription().equals(GameStatus.BID.getDescription())) {
				if(!player2SelectedTrump || firstRoundBiddingOver) {
					if(firstTimeM2) {
						fadeOut = CCFadeOut.action(5);
						if(winSize.width > 700) {
							message2 = CCLabel.makeLabel(p2.getName()+"'s turn to bid", "Times New Roman", 30);
						} else {
							message2 = CCLabel.makeLabel(p2.getName()+"'s turn to bid", "Times New Roman", 15);
						}
						message2.setColor(ccColor3B.ccc3(255, 102, 102));
						message2.setPosition(winSize.width/2,winSize.height/2);
						scene.addChild(message2);
	
						fadeOut = CCFadeOut.action(6);
						message2.runAction(fadeOut);
						firstTimeM2=false;
						this.schedule("p2bid", 3f);
					}
				} else if(!player2pass) {
					this.schedule("p2pass", 2f);

				}
			} else if(game.getStatus().getDescription().equals(GameStatus.PLAY.getDescription())) {
				if(firstTimeM2) {
					if(winSize.width > 700) {
						message2 = CCLabel.makeLabel(p2.getName()+"'s turn to play", "Times New Roman", 30);
					} else {
						message2 = CCLabel.makeLabel(p2.getName()+"'s turn to play", "Times New Roman", 15);
					}
					message2.setColor(ccColor3B.ccc3(25, 25, 112));
					message2.setPosition(winSize.width/2,winSize.height/2);
					scene.addChild(message2);
					fadeOut = CCFadeOut.action(5);
					message2.runAction(fadeOut);
					firstTimeM2=false;
				}
			}		
		} else if(whoseTurn ==3) { //Player 3 chance
			CCSprite message3;
			if(game.getStatus().getDescription().equals(GameStatus.BID.getDescription())) {
				if(!player3SelectedTrump || firstRoundBiddingOver) {
					if(firstTimeM3) {
						CCFadeOut fadeOut = CCFadeOut.action(5);
						if(winSize.width > 700) {
							message3 = CCLabel.makeLabel(p3.getName()+"'s turn to bid", "Times New Roman", 30);
						} else {
							message3 = CCLabel.makeLabel(p3.getName()+"'s turn to bid", "Times New Roman", 15);
						}
						message3.setColor(ccColor3B.ccc3(255, 102, 102));
						message3.setPosition(winSize.width/2,winSize.height/2);
						scene.addChild(message3);
	
						fadeOut = CCFadeOut.action(5);
						message3.runAction(fadeOut);
						firstTimeM3=false;
						this.schedule("p3bid", 2f);
					}
				} else if(!player3pass) {
					this.schedule("p3pass", 2f);
				}

			} else if(game.getStatus().getDescription().equals(GameStatus.PLAY.getDescription())) {
				if(firstTimeM3) {
					if(winSize.width > 700) {
						message3 = CCLabel.makeLabel(p3.getName()+"'s turn to play", "Times New Roman", 30);
					} else {
						message3 = CCLabel.makeLabel(p3.getName()+"'s turn to play", "Times New Roman", 15);
					}
					message3.setColor(ccColor3B.ccc3(25, 25, 112));
					message3.setPosition(winSize.width/2,winSize.height/2);
					scene.addChild(message3);
					fadeOut = CCFadeOut.action(5);
					message3.runAction(fadeOut);
					firstTimeM3=false;
				}
			}				
		} else if(whoseTurn ==4) { //Player 4 chance
			CCSprite message4;
			if(game.getStatus().getDescription().equals(GameStatus.BID.getDescription())) {
				if(!player4SelectedTrump || firstRoundBiddingOver) {
					if(firstTimeM4) {
					    fadeOut = CCFadeOut.action(5);
						if(winSize.width > 700) {
							message4 = CCLabel.makeLabel(p4.getName()+"'s turn to bid", "Times New Roman", 30);
						} else {
							message4 = CCLabel.makeLabel(p4.getName()+"'s turn to bid", "Times New Roman", 15);
						}
						message4.setColor(ccColor3B.ccc3(255, 102, 102));
						message4.setPosition(winSize.width/2,winSize.height/2);
						scene.addChild(message4);
	
						fadeOut = CCFadeOut.action(6);
						message4.runAction(fadeOut);
						firstTimeM4=false;
						this.unschedule("p3bid");
						this.unschedule("p2bid");
						this.schedule("p4bid", 2f);
					}
				} else if(!player4pass) {
					this.schedule("p4pass", 2f);
				}

			} else if(game.getStatus().getDescription().equals(GameStatus.PLAY.getDescription())) {
				if(firstTimeM3) {
					if(winSize.width > 700) {
						message4 = CCLabel.makeLabel(p4.getName()+"'s turn to play", "Times New Roman", 30);
					} else {
						message4 = CCLabel.makeLabel(p4.getName()+"'s turn to play", "Times New Roman", 15);
					}
					message4.setColor(ccColor3B.ccc3(25, 25, 112));
					message4.setPosition(winSize.width/2,winSize.height/2);
					scene.addChild(message4);
					fadeOut = CCFadeOut.action(5);
					message4.runAction(fadeOut);
					firstTimeM3=false;
				}
			}		
		}
	}

	public void showCurrentBid(float dt) {
		fadeOut = CCFadeOut.action(3);

		bidVale.setColor(ccColor3B.ccc3(135, 206, 250));
		bidVale.setPosition(winSize.width/6,winSize.height/4f);
		bidVale.runAction(fadeOut);
		if(trumpBidValue != null) {
			if(winSize.width > 700) {
				bidVale = CCLabel.makeLabel("Current Bid: "+trumpBidValue, "Times New Roman", 30);
			} else {
				bidVale = CCLabel.makeLabel("Current Bid: "+trumpBidValue, "Times New Roman", 15);
			}
			scene.addChild(bidVale);
		}	
	}
	
	public void p4pass(float dt) {
		if(!player4pass) {
			whoseTurn =1;
			player4pass=true;
		}
	}
	
	public void p4bid(float dt) {
		if(whoseTurn==4 && ((!player4SelectedTrump && !player4pass) || firstRoundBiddingOver)) {
			p4trumpCard=p4.aiPlayBid();
			if((!player2SelectedTrump || firstRoundBiddingOver) && p4trumpCard.getBid()>trump.getCurrentHightestBid()) { 
				
				/* player 4 gets to bid if player2 has not bid
				  and his bid value is greater than currentBidValue
				 */
				trumpBidValue = p4trumpCard.getBid();
				currentBidOwner = 4;
				player4Bid=trumpBidValue;
				//player 1 needs to take his trump card back
				if(player3SelectedTrump) {
					returnTrumpCard(p3Cards,3);
				} else if(player1SelectedTrump) {
					returnTrumpCard(p1Cards,1);
				}
				trumpCardIndex=1;
				setTrumpCard(p4trumpCard.getCard(),0,p4,p4Cards);
				fadeOut = CCFadeOut.action(3);
				if(trumpBidValue != null) {
					scene.removeChild(bidVale, true);
					if(winSize.width > 700) {
						bidVale = CCLabel.makeLabel("Current Bid: "+trumpBidValue, "Times New Roman", 30);
					} else {
						bidVale = CCLabel.makeLabel("Current Bid: "+trumpBidValue, "Times New Roman", 15);
					}
					bidVale.setColor(ccColor3B.ccc3(135, 206, 250));
					bidVale.setPosition(winSize.width/6,winSize.height/4f);
					scene.addChild(bidVale);
				}	
				whoseTurn =1;
				if(!firstRoundBiddingOver) {
					this.schedule("secondRoundCardDeal", 0.5f);
					firstRoundBiddingOver = true;
				} else {
					game.setStatus(GameStatus.PLAY);
					secondRoundBiddingOver = true;
					this.unschedule("secondRoundCardDeal");
				}
				this.unschedule("p2bid");
				this.unschedule("p3bid");
				this.unschedule("p4bid");

			} else {
				player4pass=true;
				whoseTurn = 1;
				if(!firstRoundBiddingOver) {
					this.schedule("secondRoundCardDeal", 0.5f);
					firstRoundBiddingOver = true;
				} else {
					game.setStatus(GameStatus.PLAY);
					secondRoundBiddingOver = true;
					scene.removeChild(pass, true);
					this.unschedule("secondRoundCardDeal");
				}
			}
		}
	}
	
	public void p3pass(float dt) {
		if(!player3pass) {
			whoseTurn =4;
			player3pass=true;
		}
	}
	
	public void p3bid(float dt) {
		if(whoseTurn==3 && ((!player3SelectedTrump && !player3pass)||firstRoundBiddingOver)) {
			p3trumpCard=p3.aiPlayBid();
			if((player2SelectedTrump || firstRoundBiddingOver) && p3trumpCard.getBid()>trump.getCurrentHightestBid()) {
				trumpBidValue = p3trumpCard.getBid();
				currentBidOwner = 3;
				player3Bid=trumpBidValue;
				//player 1 needs to take his trump card back
				if(player2SelectedTrump) {
					returnTrumpCard(p2Cards,2);
				} else if(player1SelectedTrump) {
					returnTrumpCard(p1Cards,1);
				}
				trumpCardIndex=1;
				setTrumpCard(p3trumpCard.getCard(),0,p3,p3Cards);
				fadeOut = CCFadeOut.action(3);

				if(trumpBidValue != null) {
					scene.removeChild(bidVale, true);
					if(winSize.width > 700) {
						bidVale = CCLabel.makeLabel("Current Bid: "+trumpBidValue, "Times New Roman", 30);
					} else {
						bidVale = CCLabel.makeLabel("Current Bid: "+trumpBidValue, "Times New Roman", 15);
					}
					bidVale.setColor(ccColor3B.ccc3(135, 206, 250));
					bidVale.setPosition(winSize.width/6,winSize.height/4f);
					scene.addChild(bidVale);
				}	
				whoseTurn =4;
			} else {
				player3pass=true;
				whoseTurn = 4;
			}
		}
	}
	
	public void p2pass(float dt) {
		if(!player2pass) {
			whoseTurn =3;
			player2pass=true;
		}
	}
	
	public void p2bid(float dt) {
		if(whoseTurn==2 && ((!player2SelectedTrump && !player2pass) || firstRoundBiddingOver)) {
			p2trumpCard=p2.aiPlayBid();
			if(p2trumpCard.getBid()>trump.getCurrentHightestBid()) {
				trumpBidValue = p2trumpCard.getBid();
				currentBidOwner = 2;
				player2Bid=trumpBidValue;
				//player 1 needs to take his trump card back
				returnTrumpCard(p1Cards,1);
				trumpCardIndex=1;
				setTrumpCard(p2trumpCard.getCard(),0,p2,p2Cards);
				fadeOut = CCFadeOut.action(3);
				if(trumpBidValue != null) {
					scene.removeChild(bidVale, true);
					if(winSize.width > 700) {
						bidVale = CCLabel.makeLabel("Current Bid: "+trumpBidValue, "Times New Roman", 30);
					} else {
						bidVale = CCLabel.makeLabel("Current Bid: "+trumpBidValue, "Times New Roman", 15);
					}
					bidVale.setColor(ccColor3B.ccc3(135, 206, 250));
					bidVale.setPosition(winSize.width/6,winSize.height/4f);
					scene.addChild(bidVale);
				}	
				whoseTurn =3;
			} else {
				player2pass=true;
				whoseTurn = 3;
			}
		}
	}

	private void returnTrumpCard(ArrayList<CCSprite> pCards,int player) {
		if(player==1) {
			fadeOut = CCFadeOut.action(2);
			trumpCard.runAction(fadeOut);
			scene.removeChild(trumpCard,true);	
			CardUtilities.returnTrumpPlayer1(pCards);
		} else if(player==2) {
			fadeOut = CCFadeOut.action(2);
			trumpCard.runAction(fadeOut);
			scene.removeChild(trumpCard,true);	
			CardUtilities.returnTrumpPlayer2(pCards);
		} else if(player==3) {
			fadeOut = CCFadeOut.action(2);
			trumpCard.runAction(fadeOut);
			scene.removeChild(trumpCard,true);	
			CardUtilities.returnTrumpPlayer3(pCards);
		}
	}
	private void setTrumpCard(Card selectedCard, int index, Player p, ArrayList<CCSprite> pCards) {
		//setting the trump class
		trump.setTrumpCard(selectedCard);
		trump.setCurrentHightestBid(trumpBidValue);
		trump.setBidOwner(p);
		game.setTrump(trump);	
	
		fadeOut = CCFadeOut.action(2);
		if(!firstRoundBiddingOver && whoseTurn!=1 && currentBidOwner!=1) {
			pCards.get(index).runAction(fadeOut);
			scene.removeChild(pCards.get(index),true);
		}
		
		if(whoseTurn ==1){

			player1SelectedTrump = true;
			player1Bid=trumpBidValue;
			CCFadeIn fadeIn = CCFadeIn.action(2);

			if(!firstRoundBiddingOver && currentBidOwner !=1) {
				trumpCard = CCSprite.sprite("img/b1fv.png");
				if(winSize.width > 700) {
					trumpCard.setScale(1.5f);
					trumpCard.setPosition(winSize.width/4, winSize.height/8);
				} else {
					trumpCard.setScale(0.6f);
					trumpCard.setPosition(winSize.width/3, winSize.height/15);				
				}
				trumpCard.runAction(fadeIn);
				scene.addChild(trumpCard);
				selectedTrumpCard = selectedCard;
			} else if(firstRoundBiddingOver) {
				if(currentBidOwner !=1) {
					if(player2SelectedTrump) {
						returnTrumpCard(p2Cards, 2);
					} else if(player3SelectedTrump) {
						returnTrumpCard(p3Cards, 3);
					} else if(player4SelectedTrump) {
						returnTrumpCard(p4Cards, 4);
						fadeOut = CCFadeOut.action(2);
						trumpCard.runAction(fadeOut);
						scene.removeChild(trumpCard,true);	
						CardUtilities.returnTrumpPlayer4(pCards);
					}
					player2SelectedTrump=false;
					player3SelectedTrump=false;
					player4SelectedTrump=false;
					trumpCard.setRotation(0);
					trumpCard = CCSprite.sprite("img/b1fv.png");
					trumpCard.setPosition(winSize.width/5.5f, 15);
					if(winSize.width<700) {
						trumpCard.setScale(0.6f);
					} else {
						trumpCard.setScale(1.5f);
					}
					trumpCard.runAction(fadeIn);
					scene.addChild(trumpCard);
					selectedTrumpCard = selectedCard;
				} else {
					if(!selectedTrumpCard.toString().equals(selectedCard.toString())) {
						pCards.get(index).runAction(fadeOut);
						returnTrumpCard(p1Cards, 1);
						trumpCard.setRotation(0);
						trumpCard = CCSprite.sprite("img/b1fv.png");
						trumpCard.setPosition(winSize.width/5.5f, 15);
						trumpCard.setScale(0.6f);
						trumpCard.runAction(fadeIn);
						scene.addChild(trumpCard);
					}
					selectedTrumpCard = selectedCard;
				}
			} 
			currentBidOwner=1;
			CardUtilities.removeTrumpAndBidBoxes();
			CardUtilities.rearrangeCards(pCards,1);
			if(trumpBidValue != null) {
				scene.removeChild(bidVale, true);
				if(winSize.width > 700) {
					bidVale = CCLabel.makeLabel("Current Bid: "+trumpBidValue, "Times New Roman", 30);
				} else {
					bidVale = CCLabel.makeLabel("Current Bid: "+trumpBidValue, "Times New Roman", 15);
				}
				bidVale.setColor(ccColor3B.ccc3(135, 206, 250));
				bidVale.setPosition(winSize.width/6,winSize.height/4f);
				scene.addChild(bidVale);
			}
			whoseTurn =2; //now it's player 2 chance
			scene.removeChild(pass, true);
		} else if(whoseTurn == 2) {
			trumpCard = CCSprite.sprite("img/b1fv.png");

			trumpCard.setRotation(90);

			if(winSize.width > 700) {
				trumpCard.setScale(1f);
				//trumpCard.setPosition(winSize.width/4, winSize.height/8);
				trumpCard.setPosition(winSize.width/14,winSize.height/3);
			} else {
				trumpCard.setScale(0.6f);
				trumpCard.setPosition(winSize.width/15,winSize.height/3);
			}
			player1SelectedTrump = false;
			player2SelectedTrump = true;
			
			CCFadeIn fadeIn = CCFadeIn.action(2);
			trumpCard.runAction(fadeIn);
			scene.addChild(trumpCard);

			CardUtilities.removeTrumpAndBidBoxes();
			CardUtilities.rearrangeCards(pCards,2);
		} else if(whoseTurn ==3) {
			trumpCard = CCSprite.sprite("img/b1fv.png");

			trumpCard.setRotation(180);

			if(winSize.width > 700) {
				trumpCard.setScale(1f);
				//trumpCard.setPosition(winSize.width/4, winSize.height/8);
				trumpCard.setPosition(winSize.width/3,winSize.height/1.2f);
			} else {
				trumpCard.setScale(0.6f);
				trumpCard.setPosition(winSize.width/3,winSize.height/1.1f);
			}
			player1SelectedTrump = false;
			player2SelectedTrump = false;
			player3SelectedTrump = true;
			
			CCFadeIn fadeIn = CCFadeIn.action(2);
			trumpCard.runAction(fadeIn);
			scene.addChild(trumpCard);

			CardUtilities.removeTrumpAndBidBoxes();
			CardUtilities.rearrangeCards(pCards,3);
		} else if(whoseTurn ==4) {
			trumpCard = CCSprite.sprite("img/b1fv.png");

			trumpCard.setRotation(270);

			if(winSize.width > 700) {
				trumpCard.setScale(1f);
				//trumpCard.setPosition(winSize.width/4, winSize.height/8);
				trumpCard.setPosition(winSize.width/1.4f,winSize.height/1.2f);

			} else {
				trumpCard.setScale(0.6f);
				trumpCard.setPosition(winSize.width/1.1f,winSize.height/1.2f);
			}
			player1SelectedTrump = false;
			player2SelectedTrump = false;
			player3SelectedTrump = false;
			player4SelectedTrump = true;
			
			CCFadeIn fadeIn = CCFadeIn.action(2);
			trumpCard.runAction(fadeIn);
			scene.addChild(trumpCard);

			CardUtilities.removeTrumpAndBidBoxes();
			CardUtilities.rearrangeCards(pCards,4);
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
						fadeOut = CCFadeOut.action(0.25f);
						trumpCard.runAction(fadeOut);
						scene.removeChild(trumpCard, true);		
						showTrumpCard = CCSprite.sprite(Card.cardMap.get(selectedTrumpCard.toString()));
						showTrumpCard.setPosition(trumpCard.getPosition().x, trumpCard.getPosition().y);
						CCEaseAction elastic = CCEaseElastic.action(CCIntervalAction.action(1));
						showTrumpCard.runAction(elastic);
						if(winSize.width<700) {
							showTrumpCard.setScale(0.6f);
						} else {
							showTrumpCard.setScale(1.5f);
						}
						scene.addChild(showTrumpCard);
						if(!secondRoundBiddingOver) {
							CardUtilities.drawTrumpAndBidBoxes();
							cardSelected=true;
						} else {
							CardUtilities.removeTrumpAndBidBoxes();
						}
						cardShownToHimself = true;
					} else {
						fadeOut = CCFadeOut.action(0.25f);
						showTrumpCard.runAction(fadeOut);
						scene.removeChild(showTrumpCard, true);		
						trumpCard = CCSprite.sprite("img/b1fv.png");
						trumpCard.setPosition(showTrumpCard.getPosition().x, showTrumpCard.getPosition().y);
						CCEaseAction elastic = CCEaseElastic.action(CCIntervalAction.action(1));
						trumpCard.runAction(elastic);
						if(winSize.width<700) {
							trumpCard.setScale(0.6f);
						} else {
							trumpCard.setScale(1.5f);
						}
						scene.addChild(trumpCard);	
						cardShownToHimself = false;
						if(!secondRoundBiddingOver) {
							CardUtilities.removeTrumpAndBidBoxes();
							cardSelected=false;
						}
					}
	
	
				}
			}
			if(game.getStatus().getDescription().equals(GameStatus.BID.getDescription())) {
			if(whoseTurn==1 && ((!player1SelectedTrump && cardSelected)||firstRoundBiddingOver) && !secondRoundBiddingOver) {
				if(firstRoundBiddingOver) {
					CGRect passBox = CGRect.make(pass.getPosition().x - pass.getContentSize().width/2, pass.getPosition().y
							- pass.getContentSize().height/2, pass.getContentSize().width,pass.getContentSize().height);
					if(CGRect.containsPoint(passBox, location)) {
						whoseTurn=2;
					}
				}
				CGRect increaseBox= CGRect.make(increase.getPosition().x - increase.getContentSize().width/2, increase.getPosition().y 
						- increase.getContentSize().height/2, increase.getContentSize().width,increase.getContentSize().height);
			
				if(CGRect.containsPoint(increaseBox,location)){
					scene.removeChild(currentBid,true);
					if(firstRoundBiddingOver) {
						if(currentBidValue<=28) {
							currentBidValue = currentBidValue+1;
						}
						if(winSize.width>700) {
							currentBid = CCLabel.makeLabel(currentBidValue.toString(), "Times New Roman", 40);
						} else {
							currentBid = CCLabel.makeLabel(currentBidValue.toString(), "Times New Roman", 25);
						}
					} else {
						if(currentBidValue <28) {
							currentBidValue = currentBidValue+1;
						}
						if(winSize.width>700) {
							currentBid = CCLabel.makeLabel(currentBidValue.toString(), "Times New Roman", 40);
						} else {
							currentBid = CCLabel.makeLabel(currentBidValue.toString(), "Times New Roman", 25);
						}
					}
					currentBid.setColor(ccColor3B.ccYELLOW);
					if(winSize.width>700) { 
						currentBid.setPosition(winSize.width/1.5f, winSize.height/3);
					} else {
						currentBid.setPosition(winSize.width/2f, winSize.height/2f);
					}
					scene.addChild(currentBid);
				}
				
				CGRect decreaseBox= CGRect.make(decrease.getPosition().x - decrease.getContentSize().width/2, decrease.getPosition().y 
						- decrease.getContentSize().height/2, decrease.getContentSize().width,decrease.getContentSize().height);
			
				if(CGRect.containsPoint(decreaseBox,location)){
					scene.removeChild(currentBid,true);
					if(firstRoundBiddingOver) {
						if(currentBidValue>20) {
							currentBidValue = currentBidValue-1;
						}
						if(winSize.width>700) {
							currentBid = CCLabel.makeLabel(currentBidValue.toString(), "Times New Roman", 40);
						} else {
							currentBid = CCLabel.makeLabel(currentBidValue.toString(), "Times New Roman", 25);
						}
					} else {
						if(currentBidValue >lowestBidValue) {
							currentBidValue = currentBidValue-1;
						}
						if(winSize.width>700) {
							currentBid = CCLabel.makeLabel(currentBidValue.toString(), "Times New Roman", 40);
						} else {
							currentBid = CCLabel.makeLabel(currentBidValue.toString(), "Times New Roman", 25);
						}
					}			
					currentBid.setColor(ccColor3B.ccYELLOW);
					if(winSize.width > 700) {
						currentBid.setPosition(winSize.width/1.5f, winSize.height/3);
					} else {
						currentBid.setPosition(winSize.width/2f, winSize.height/2f);
					}
					scene.addChild(currentBid);
				}
				
				CGRect setTrumpBox= CGRect.make(setTrump.getPosition().x - setTrump.getContentSize().width/2, setTrump.getPosition().y 
						- setTrump.getContentSize().height/2, setTrump.getContentSize().width,setTrump.getContentSize().height);
			
				if(CGRect.containsPoint(setTrumpBox,location)){
					trumpBidValue=currentBidValue;
					setTrumpCard(selectedCard,trumpCardIndex,p1,p1Cards);
					CardUtilities.removeTrumpAndBidBoxes();
				}
			}
			if(whoseTurn==1 && !player1SelectedTrump && !firstRoundBiddingOver && !secondRoundBiddingOver) {
				if(winSize.width<700) {
				cardBox4 = CGRect.make(p1Cards.get(3).getPosition().x-p1Cards.get(3).getContentSize().width/2,
						p1Cards.get(3).getPosition().y-p1Cards.get(3).getContentSize().height/2, 
						p1Cards.get(3).getContentSize().width,p1Cards.get(3).getContentSize().height);
				} else {
					cardBox4 = CGRect.make(p1Cards.get(3).getPosition().x-p1Cards.get(3).getContentSize().width/2,
							p1Cards.get(3).getPosition().y-p1Cards.get(3).getContentSize().height/2, 
							p1Cards.get(3).getContentSize().width,p1Cards.get(3).getContentSize().height);					
				}
				//CGPoint touchLocationRelative4 = p1Cards.get(3).convertToNodeSpace(location);
				if(CGRect.containsPoint(cardBox4, location)) {
					if(!toggle4) {
						CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
						p1Cards.get(3).runAction(moveBy);	
						toggle4=true;
						selectedCard = p1.getMyHand().getMyCards().get(3);
						selectedTrumpSprite=p1Cards.get(3);
						trumpCardIndex = 3;
						CardUtilities.checkCard1Selected();
						CardUtilities.checkCard2Selected();
	
						CardUtilities.checkCard3Selected();
	
						CardUtilities.drawTrumpAndBidBoxes();
						cardSelected=true;
					} else {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card4OrigPosition);
						p1Cards.get(3).runAction(moveTo);
						toggle4=false;
						cardSelected=false;
						CardUtilities.removeTrumpAndBidBoxes();
					}
				}
				if(winSize.width<700) {
					cardBox3 = CGRect.make(p1Cards.get(2).getPosition().x-p1Cards.get(2).getContentSize().width/2f,
							p1Cards.get(2).getPosition().y-p1Cards.get(2).getContentSize().height/2f, 
							p1Cards.get(2).getContentSize().width/2,p1Cards.get(2).getContentSize().height);
				} else {
					cardBox3 = CGRect.make(p1Cards.get(2).getPosition().x-p1Cards.get(2).getContentSize().width/2f,
							p1Cards.get(2).getPosition().y-p1Cards.get(2).getContentSize().height/2f, 
							p1Cards.get(2).getContentSize().width,p1Cards.get(2).getContentSize().height);					
				}
				//CGPoint touchLocationRelative3 = p1Cards.get(2).convertToNodeSpace(location);
				if(CGRect.containsPoint(cardBox3, location)) {
					if(!toggle4 && !toggle3) {
						CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
						p1Cards.get(2).runAction(moveBy);
						toggle3=true;
						selectedCard = p1.getMyHand().getMyCards().get(2);
						selectedTrumpSprite=p1Cards.get(2);
						trumpCardIndex = 2;
						CardUtilities.checkCard1Selected();
	
						CardUtilities.checkCard2Selected();
	
						CardUtilities.checkCard4Selected();
	
						CardUtilities.drawTrumpAndBidBoxes();
						cardSelected=true;
					} else if(!toggle4) {
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card3OrigPosition);
						p1Cards.get(2).runAction(moveTo);
						toggle3=false;
						cardSelected=false;
						CardUtilities.removeTrumpAndBidBoxes();
					}
				}
				if(winSize.width > 700) {
					cardBox2 = CGRect.make(p1Cards.get(1).getPosition().x-p1Cards.get(1).getContentSize().width/2f,
							p1Cards.get(1).getPosition().y-p1Cards.get(1).getContentSize().height/2f, 
							p1Cards.get(1).getContentSize().width,p1Cards.get(1).getContentSize().height);
				} else {
					cardBox2 = CGRect.make(p1Cards.get(1).getPosition().x-p1Cards.get(1).getContentSize().width/2,
							p1Cards.get(1).getPosition().y-p1Cards.get(1).getContentSize().height/2f, 
							p1Cards.get(1).getContentSize().width/2.5f,p1Cards.get(1).getContentSize().height);
				}
				//CGPoint touchLocationRelative2 = p1Cards.get(1).convertToNodeSpace(location);
				if(CGRect.containsPoint(cardBox2, location)) {
					if(!toggle3 &&!toggle2) {
						CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
						p1Cards.get(1).runAction(moveBy);	
						toggle2=true;
						selectedCard = p1.getMyHand().getMyCards().get(1);
						selectedTrumpSprite=p1Cards.get(1);
						trumpCardIndex = 1;
						CardUtilities.checkCard1Selected();
	
						CardUtilities.checkCard3Selected();
	
						CardUtilities.checkCard4Selected();
	
						CardUtilities.drawTrumpAndBidBoxes();
						cardSelected=true;
					} else if(!toggle3){
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card2OrigPosition);
						p1Cards.get(1).runAction(moveTo);
						toggle2=false;
						cardSelected=false;
						CardUtilities.removeTrumpAndBidBoxes();
					}
				}
				
				if(winSize.width>700) {
					cardBox1 = CGRect.make(p1Cards.get(0).getPosition().x-p1Cards.get(0).getContentSize().width/2f,
						p1Cards.get(0).getPosition().y-p1Cards.get(0).getContentSize().height/2f, 
						p1Cards.get(0).getContentSize().width,p1Cards.get(0).getContentSize().height);
				} else {
					cardBox1 = CGRect.make(p1Cards.get(0).getPosition().x-1.5f*p1Cards.get(0).getContentSize().width/2,
						p1Cards.get(0).getPosition().y-p1Cards.get(0).getContentSize().height/2f, 
						p1Cards.get(0).getContentSize().width/2,p1Cards.get(0).getContentSize().height);
				}
				//CGPoint touchLocationRelative1 = p1Cards.get(0).convertToNodeSpace(location);
				if(CGRect.containsPoint(cardBox1, location)) {
					if(!toggle2 && !toggle1) {
						CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
						p1Cards.get(0).runAction(moveBy);	
						toggle1=true;
						selectedCard = p1.getMyHand().getMyCards().get(0);
						selectedTrumpSprite=p1Cards.get(0);
						trumpCardIndex = 0;
						CardUtilities.checkCard3Selected();
						CardUtilities.checkCard2Selected();
						CardUtilities.checkCard4Selected();
	
						CardUtilities.drawTrumpAndBidBoxes();
						cardSelected=true;
					} else if(!toggle2){
						CCMoveTo moveTo = CCMoveTo.action(0.5f, card1OrigPosition);
						p1Cards.get(0).runAction(moveTo);
						toggle1=false;
						cardSelected=false;
						CardUtilities.removeTrumpAndBidBoxes();
					}
				}
			}
			if(firstRoundBiddingOver && whoseTurn==1) {
				secondRoundBiddingTouch(location);
			}
		}

	  return CCTouchDispatcher.kEventHandled;
	}
	
	private void secondRoundBiddingTouch(CGPoint location) {
		cardBox8 = CGRect.make(p1Cards.get(7).getPosition().x-p1Cards.get(7).getContentSize().width/2,
				p1Cards.get(7).getPosition().y-p1Cards.get(7).getContentSize().height/2, 
				p1Cards.get(7).getContentSize().width,p1Cards.get(7).getContentSize().height);
		
		cardBox7 = CGRect.make(p1Cards.get(6).getPosition().x-p1Cards.get(6).getContentSize().width/2,
				p1Cards.get(6).getPosition().y-p1Cards.get(6).getContentSize().height/2, 
				p1Cards.get(6).getContentSize().width,p1Cards.get(6).getContentSize().height);
		
		cardBox6 = CGRect.make(p1Cards.get(5).getPosition().x-p1Cards.get(5).getContentSize().width/2,
				p1Cards.get(5).getPosition().y-p1Cards.get(5).getContentSize().height/2, 
				p1Cards.get(5).getContentSize().width,p1Cards.get(5).getContentSize().height);
		
		cardBox5 = CGRect.make(p1Cards.get(4).getPosition().x-p1Cards.get(4).getContentSize().width/2,
				p1Cards.get(4).getPosition().y-p1Cards.get(4).getContentSize().height/2, 
				p1Cards.get(4).getContentSize().width,p1Cards.get(4).getContentSize().height);

		cardBox4 = CGRect.make(p1Cards.get(3).getPosition().x-p1Cards.get(3).getContentSize().width/2,
				p1Cards.get(3).getPosition().y-p1Cards.get(3).getContentSize().height/2, 
				p1Cards.get(3).getContentSize().width,p1Cards.get(3).getContentSize().height);

		cardBox3 = CGRect.make(p1Cards.get(2).getPosition().x-p1Cards.get(2).getContentSize().width/2f,
				p1Cards.get(2).getPosition().y-p1Cards.get(2).getContentSize().height/2, 
				p1Cards.get(2).getContentSize().width/2,p1Cards.get(2).getContentSize().height);
		if(winSize.width > 700) {
			cardBox2 = CGRect.make(p1Cards.get(1).getPosition().x-p1Cards.get(1).getContentSize().width,
					p1Cards.get(1).getPosition().y, 
					p1Cards.get(1).getContentSize().width/2.5f,p1Cards.get(1).getContentSize().height);
		} else {
			cardBox2 = CGRect.make(p1Cards.get(1).getPosition().x-p1Cards.get(1).getContentSize().width/2,
					p1Cards.get(1).getPosition().y-p1Cards.get(1).getContentSize().height/2, 
					p1Cards.get(1).getContentSize().width/2.5f,p1Cards.get(1).getContentSize().height);
		}
		if(winSize.width>700) {
			cardBox1 = CGRect.make(p1Cards.get(0).getPosition().x-p1Cards.get(0).getContentSize().width,
				p1Cards.get(0).getPosition().y, 
				p1Cards.get(0).getContentSize().width/2,p1Cards.get(0).getContentSize().height);
		} else {
			cardBox1 = CGRect.make(p1Cards.get(0).getPosition().x-p1Cards.get(0).getContentSize().width/2,
				p1Cards.get(0).getPosition().y-p1Cards.get(0).getContentSize().height/2, 
				p1Cards.get(0).getContentSize().width/2,p1Cards.get(0).getContentSize().height);
		}
		//CGPoint touchLocationRelative4 = p1Cards.get(3).convertToNodeSpace(location);
		if(CGRect.containsPoint(cardBox8, location)) {
			if(!toggle8) {
				CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
				p1Cards.get(7).runAction(moveBy);	
				toggle8=true;
				selectedCard = p1.getMyHand().getMyCards().get(7);
				selectedTrumpSprite=p1Cards.get(7);
				trumpCardIndex = 7;
				CardUtilities.checkCard1Selected();
				CardUtilities.checkCard2Selected();

				CardUtilities.checkCard3Selected();
				CardUtilities.checkCard4Selected();
				CardUtilities.checkCard5Selected();
				CardUtilities.checkCard6Selected();

				CardUtilities.checkCard7Selected();
				CardUtilities.drawTrumpAndBidBoxes();
				cardSelected=true;
			} else {
				CCMoveTo moveTo = CCMoveTo.action(0.5f, card8OrigPosition);
				p1Cards.get(7).runAction(moveTo);
				toggle8=false;
				cardSelected=false;
				CardUtilities.removeTrumpAndBidBoxes();
			}
		}
		if(CGRect.containsPoint(cardBox7, location)) {
			if(!toggle8 && !toggle7) {
				CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
				p1Cards.get(6).runAction(moveBy);	
				toggle7=true;
				selectedCard = p1.getMyHand().getMyCards().get(6);
				selectedTrumpSprite=p1Cards.get(6);
				trumpCardIndex = 6;
				CardUtilities.checkCard1Selected();
				CardUtilities.checkCard2Selected();

				CardUtilities.checkCard3Selected();
				CardUtilities.checkCard4Selected();
				CardUtilities.checkCard5Selected();
				CardUtilities.checkCard6Selected();

				CardUtilities.checkCard8Selected();
				CardUtilities.drawTrumpAndBidBoxes();
				cardSelected=true;
			} else if(!toggle8){
				CCMoveTo moveTo = CCMoveTo.action(0.5f, card7OrigPosition);
				p1Cards.get(6).runAction(moveTo);
				toggle7=false;
				cardSelected=false;
				CardUtilities.removeTrumpAndBidBoxes();
			}
		}
		if(CGRect.containsPoint(cardBox6, location)) {
			if(!toggle7 && !toggle6) {
				CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
				p1Cards.get(5).runAction(moveBy);	
				toggle6=true;
				selectedCard = p1.getMyHand().getMyCards().get(5);
				selectedTrumpSprite=p1Cards.get(5);
				trumpCardIndex = 5;
				CardUtilities.checkCard1Selected();
				CardUtilities.checkCard2Selected();

				CardUtilities.checkCard3Selected();
				CardUtilities.checkCard4Selected();
				CardUtilities.checkCard5Selected();

				CardUtilities.checkCard7Selected();
				CardUtilities.checkCard8Selected();
				CardUtilities.drawTrumpAndBidBoxes();
				cardSelected=true;
			} else if(!toggle7){
				CCMoveTo moveTo = CCMoveTo.action(0.5f, card6OrigPosition);
				p1Cards.get(5).runAction(moveTo);
				toggle6=false;
				cardSelected=false;
				CardUtilities.removeTrumpAndBidBoxes();
			}
		}
		if(CGRect.containsPoint(cardBox5, location)) {
			if(!toggle6 && !toggle5) {
				CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
				p1Cards.get(4).runAction(moveBy);	
				toggle5=true;
				selectedCard = p1.getMyHand().getMyCards().get(4);
				selectedTrumpSprite=p1Cards.get(4);
				trumpCardIndex = 4;
				CardUtilities.checkCard1Selected();
				CardUtilities.checkCard2Selected();

				CardUtilities.checkCard3Selected();
				CardUtilities.checkCard4Selected();
				CardUtilities.checkCard6Selected();

				CardUtilities.checkCard7Selected();
				CardUtilities.checkCard8Selected();
				CardUtilities.drawTrumpAndBidBoxes();
				cardSelected=true;
			} else if(!toggle6){
				CCMoveTo moveTo = CCMoveTo.action(0.5f, card5OrigPosition);
				p1Cards.get(4).runAction(moveTo);
				toggle5=false;
				cardSelected=false;
				CardUtilities.removeTrumpAndBidBoxes();
			}
		}
		
		//CGPoint touchLocationRelative4 = p1Cards.get(3).convertToNodeSpace(location);
		if(CGRect.containsPoint(cardBox4, location)) {
			if(!toggle5 && !toggle4) {
				CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
				p1Cards.get(3).runAction(moveBy);	
				toggle4=true;
				selectedCard = p1.getMyHand().getMyCards().get(3);
				selectedTrumpSprite=p1Cards.get(3);
				trumpCardIndex = 3;
				CardUtilities.checkCard1Selected();

				CardUtilities.checkCard2Selected();

				CardUtilities.checkCard3Selected();
				CardUtilities.checkCard5Selected();
				CardUtilities.checkCard6Selected();

				CardUtilities.checkCard7Selected();
				CardUtilities.checkCard8Selected();
				CardUtilities.drawTrumpAndBidBoxes();
				cardSelected=true;
			} else if(!toggle5){
				CCMoveTo moveTo = CCMoveTo.action(0.5f, card4OrigPosition);
				p1Cards.get(3).runAction(moveTo);
				toggle4=false;
				cardSelected=false;
				CardUtilities.removeTrumpAndBidBoxes();
			}
		}
		
		//CGPoint touchLocationRelative3 = p1Cards.get(2).convertToNodeSpace(location);
		if(CGRect.containsPoint(cardBox3, location)) {
			if(!toggle3) {
				CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
				p1Cards.get(2).runAction(moveBy);
				toggle3=true;
				selectedCard = p1.getMyHand().getMyCards().get(2);
				selectedTrumpSprite=p1Cards.get(2);
				trumpCardIndex = 2;
				CardUtilities.checkCard1Selected();

				CardUtilities.checkCard2Selected();

				CardUtilities.checkCard4Selected();
				CardUtilities.checkCard5Selected();
				CardUtilities.checkCard6Selected();

				CardUtilities.checkCard7Selected();
				CardUtilities.checkCard8Selected();

				CardUtilities.drawTrumpAndBidBoxes();
				cardSelected=true;
			} else {
				CCMoveTo moveTo = CCMoveTo.action(0.5f, card3OrigPosition);
				p1Cards.get(2).runAction(moveTo);
				toggle3=false;
				cardSelected=false;
				CardUtilities.removeTrumpAndBidBoxes();
			}
		}

		//CGPoint touchLocationRelative2 = p1Cards.get(1).convertToNodeSpace(location);
		if(CGRect.containsPoint(cardBox2, location)) {
			if(!toggle2) {
				CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
				p1Cards.get(1).runAction(moveBy);	
				toggle2=true;
				selectedCard = p1.getMyHand().getMyCards().get(1);
				selectedTrumpSprite=p1Cards.get(1);
				trumpCardIndex = 1;
				CardUtilities.checkCard1Selected();
				CardUtilities.checkCard3Selected();

				CardUtilities.checkCard4Selected();
				CardUtilities.checkCard5Selected();
				CardUtilities.checkCard6Selected();

				CardUtilities.checkCard7Selected();
				CardUtilities.checkCard8Selected();

				CardUtilities.drawTrumpAndBidBoxes();
				cardSelected=true;
			} else {
				CCMoveTo moveTo = CCMoveTo.action(0.5f, card2OrigPosition);
				p1Cards.get(1).runAction(moveTo);
				toggle2=false;
				cardSelected=false;
				CardUtilities.removeTrumpAndBidBoxes();
			}
		}
		

		//CGPoint touchLocationRelative1 = p1Cards.get(0).convertToNodeSpace(location);
		if(CGRect.containsPoint(cardBox1, location)) {
			if(!toggle1) {
				CCMoveBy moveBy = CCMoveBy.action(0.5f, CGPoint.ccp(0,25));
				p1Cards.get(0).runAction(moveBy);	
				toggle1=true;
				selectedCard = p1.getMyHand().getMyCards().get(0);
				selectedTrumpSprite=p1Cards.get(0);
				trumpCardIndex = 0;
				CardUtilities.checkCard3Selected();

				CardUtilities.checkCard2Selected();

				CardUtilities.checkCard4Selected();
				CardUtilities.checkCard5Selected();
				CardUtilities.checkCard6Selected();

				CardUtilities.checkCard7Selected();
				CardUtilities.checkCard8Selected();

				CardUtilities.drawTrumpAndBidBoxes();
				cardSelected=true;
			} else {
				CCMoveTo moveTo = CCMoveTo.action(0.5f, card1OrigPosition);
				p1Cards.get(0).runAction(moveTo);
				toggle1=false;
				cardSelected=false;
				CardUtilities.removeTrumpAndBidBoxes();
			}
		}
		if(!toggle8 && !toggle7 && !toggle6 && !toggle5 && !toggle4 && !toggle3 && !toggle2 && !toggle1) {
			CardUtilities.removeTrumpAndBidBoxes();
		}
			
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
	  return super.ccTouchesEnded(event);
	}
	
	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		if(p1Cards.size()>7 && p1Cards.get(7) != null) {
			cardBox8 = CGRect.make(p1Cards.get(7).getPosition().x-p1Cards.get(7).getContentSize().width/2,
					p1Cards.get(7).getPosition().y-p1Cards.get(7).getContentSize().height/2, 
					p1Cards.get(7).getContentSize().width,p1Cards.get(7).getContentSize().height);
		}
		if(p1Cards.size()>6 && p1Cards.get(6) != null) {
			cardBox7 = CGRect.make(p1Cards.get(6).getPosition().x-p1Cards.get(6).getContentSize().width/2,
				p1Cards.get(6).getPosition().y-p1Cards.get(6).getContentSize().height/2, 
				p1Cards.get(6).getContentSize().width,p1Cards.get(6).getContentSize().height);
		}
		if(p1Cards.size()>5 && p1Cards.get(5) != null) {
			cardBox6 = CGRect.make(p1Cards.get(5).getPosition().x-p1Cards.get(5).getContentSize().width/2,
				p1Cards.get(5).getPosition().y-p1Cards.get(5).getContentSize().height/2, 
				p1Cards.get(5).getContentSize().width,p1Cards.get(5).getContentSize().height);
		}
		if(p1Cards.size()>4 && p1Cards.get(4) != null) {
			cardBox5 = CGRect.make(p1Cards.get(4).getPosition().x-p1Cards.get(4).getContentSize().width/2,
				p1Cards.get(4).getPosition().y-p1Cards.get(4).getContentSize().height/2, 
				p1Cards.get(4).getContentSize().width,p1Cards.get(4).getContentSize().height);
		}
		if(p1Cards.size()>3 && p1Cards.get(3) != null) {
			cardBox4 = CGRect.make(p1Cards.get(3).getPosition().x-p1Cards.get(3).getContentSize().width/2,
				p1Cards.get(3).getPosition().y-p1Cards.get(3).getContentSize().height/2, 
				p1Cards.get(3).getContentSize().width,p1Cards.get(3).getContentSize().height);
		}
		if(p1Cards.size()>2 && p1Cards.get(2) != null) {
			cardBox3 = CGRect.make(p1Cards.get(2).getPosition().x-p1Cards.get(2).getContentSize().width/2f,
				p1Cards.get(2).getPosition().y-p1Cards.get(2).getContentSize().height/2, 
				p1Cards.get(2).getContentSize().width,p1Cards.get(2).getContentSize().height);
		}
		if(p1Cards.size()>1 && p1Cards.get(1) != null) {
			if(winSize.width > 700) {
				cardBox2 = CGRect.make(p1Cards.get(1).getPosition().x-p1Cards.get(1).getContentSize().width/2,
						p1Cards.get(1).getPosition().y-p1Cards.get(1).getContentSize().height/2, 
						p1Cards.get(1).getContentSize().width,p1Cards.get(1).getContentSize().height);
			} else {
				cardBox2 = CGRect.make(p1Cards.get(1).getPosition().x-p1Cards.get(1).getContentSize().width/2,
						p1Cards.get(1).getPosition().y-p1Cards.get(1).getContentSize().height/2, 
						p1Cards.get(1).getContentSize().width,p1Cards.get(1).getContentSize().height);
			}
		}
		if(p1Cards.size()>0 && p1Cards.get(0) != null) {
			if(winSize.width>700) {
				cardBox1 = CGRect.make(p1Cards.get(0).getPosition().x-p1Cards.get(0).getContentSize().width/2,
					p1Cards.get(0).getPosition().y-p1Cards.get(0).getContentSize().height/2, 
					p1Cards.get(0).getContentSize().width,p1Cards.get(0).getContentSize().height);
			} else {
				cardBox1 = CGRect.make(p1Cards.get(0).getPosition().x-p1Cards.get(0).getContentSize().width/2,
					p1Cards.get(0).getPosition().y-p1Cards.get(0).getContentSize().height/2, 
					p1Cards.get(0).getContentSize().width,p1Cards.get(0).getContentSize().height);
			}
		}
	  CGPoint location = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(), event.getY()));
	  if(whoseTurn==1 && secondRoundBiddingOver) {
			if(p1Cards.size()>7 && p1Cards.get(7) != null && CGRect.containsPoint(cardBox8, location)) {
				movingCard=p1Cards.get(7);
				removeIndex=7;
			} else if(p1Cards.size()>6 && p1Cards.get(6) != null && CGRect.containsPoint(cardBox7, location)) {
				movingCard=p1Cards.get(6);
				removeIndex=6;
			} else if(p1Cards.size()>5 && p1Cards.get(5) != null && CGRect.containsPoint(cardBox6, location)) {
				movingCard=p1Cards.get(5);
				removeIndex=5;
			} else if(p1Cards.size()>4 && p1Cards.get(4) != null && CGRect.containsPoint(cardBox5, location)) {
				movingCard=p1Cards.get(4);
				removeIndex=4;
			} else if(p1Cards.size()>3 && p1Cards.get(3) != null && CGRect.containsPoint(cardBox4, location)) {
				movingCard=p1Cards.get(3);
				removeIndex=3;
			} else if(p1Cards.size()>2 && p1Cards.get(2) != null && CGRect.containsPoint(cardBox3, location)) {
				movingCard=p1Cards.get(2);
				removeIndex=2;
			} else if(p1Cards.size()>1 && p1Cards.get(1) != null && CGRect.containsPoint(cardBox2, location)) {
				movingCard=p1Cards.get(1);
				removeIndex=1;
			} else if(p1Cards.size()>0 && p1Cards.get(0) != null && CGRect.containsPoint(cardBox1, location)) {
				movingCard=p1Cards.get(0);
				removeIndex=0;
			} else {
				movingCard=null;
			}
	  }
	  if(whoseTurn==1 && secondRoundBiddingOver && movingCard!=null) {
		   movingCard.setPosition(location);
		if(CGRect.containsPoint(tableBox, location)) {
			   whoseTurn=2;
			   p1Cards.remove(removeIndex);
			   if(currentBidOwner==1) {
				   trumpCardIndex=p1Cards.indexOf(selectedTrumpSprite);
			   }
			   CardUtilities.rearrangeCards(p1Cards, 1);
		}
	   
	  }
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
/*		CGPoint verts[] = {
			      CGPoint.ccp(tableBox.origin.x, tableBox.origin.y),
			      CGPoint.ccp(tableBox.origin.x + tableBox.size.width, tableBox.origin.y),
			      CGPoint.ccp(tableBox.origin.x + tableBox.size.width, tableBox.origin.y + tableBox.size.height),
			      CGPoint.ccp(tableBox.origin.x, tableBox.origin.y + tableBox.size.height)
			    };
        CCDrawingPrimitives.ccDrawPoly(gl, verts, 2, true);*/
    }
	
}

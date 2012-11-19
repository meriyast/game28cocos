package com.example.testcocos2d;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor4B;

import android.view.MotionEvent;

public class MenuLayer extends CCColorLayer {

	CCMenuItem start;
	public static CCScene scene()
	{
	    CCScene scene = CCScene.node();
	    CCLayer layer = new MenuLayer(ccColor4B.ccc4(0, 0, 0, 0));
	 
	    scene.addChild(layer);
	 
	    return scene;
	}
	
	protected MenuLayer(ccColor4B ccColor4B)
	{
		super(ccColor4B);
	    CGSize winSize = CCDirector.sharedDirector().displaySize();
	    CCSprite card = CCSprite.sprite("img/trump-card-007.jpg");
	    CGPoint point = new CGPoint();
	    point.x = winSize.width/4.0f;
	    point.y = winSize.height/2.0f;
	    card.setPosition(point);
	    this.setIsTouchEnabled(true);

	    addChild(card);
	    
	    CCMenu menu = CCMenu.menu();
	    start = CCMenuItemLabel.item("Start Game", this, "startGame");
	    menu.addChild(start);
	    
	    menu.alignItemsVertically();
	    menu.setAnchorPoint(winSize.width/2.0f,winSize.height/2.0f);
	    addChild(menu);
	}
	
	public void startGame(Object sender) {
		CCDirector director = CCDirector.sharedDirector();
		CCScene newGameScene = CardGame.scene();
		director.pushScene(newGameScene);
	}
	
	public void onNewGame(){
		Object sender = new Object();
		startGame(sender);
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		
		CGPoint location = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(),event.getY()));
		CGRect playRect = CGRect.make(start.getPosition().x - start.getContentSize().width/2, start.getPosition().y 
				- start.getContentSize().height/2, start.getContentSize().width,start.getContentSize().height);
	
		if(CGRect.containsPoint(playRect,location)){
			onNewGame();
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
		

}

package utils;

import bitmapFont.BitmapTextField;
import bitmapFont.BitmapTextAlign;
import bitmapFont.TextBorderStyle;
import lime.system.Display;
import openfl.display.DisplayObject;
import openfl.display.Sprite;
import flash.events.KeyboardEvent;
import flash.events.Event;
import flash.events.TouchEvent;

import defaultpkg.Tetris;

enum Teclas {
	ESCAPE;
	IZQUIERDA;
	DERECHA;
	ARRIBA;
	ABAJO;
	NINGUNA;
}

enum GameState {
	Paused;
	Playing;
	GameOver;
}

class GameTable extends Sprite
{
	var lastKey : Teclas = Teclas.NINGUNA;
	var currentGameState : GameState;
	var pauseText:BitmapTextField;
	var ticUpdate: Int = 0;
	
	public function new() 
	{
		super();
		addEventListener(Event.ADDED_TO_STAGE, added);
	}	
	
	function added(e) {
		removeEventListener(Event.ADDED_TO_STAGE, added);
		init();
	}
	
	function init() {
		//Main.setPX(stage.stageWidth / Main.getEJEX());
		//Main.setPY(stage.stageHeight / Main.getEJEY());
		
		Tetris.setTAMX( ( stage.stageWidth - Tetris.getADDX() ) / Tetris.getCOLUMNAS() );
		Tetris.setTAMY( ( stage.stageHeight - Tetris.getADDY() ) / Tetris.getFILAS() );
		
		Tetris.init();
		
		pauseText = new BitmapTextField();
		pauseText.alignment = BitmapTextAlign.CENTER;
		pauseText.useTextColor = true;
		pauseText.textColor = 0x999999FF;
		pauseText.size = 3;
		pauseText.y = Std.int(stage.stageHeight / 2) - 20;
		pauseText.x = Std.int(stage.stageWidth / 2) - 175;
		addChild(pauseText);
		
		setGameState(GameState.Playing);
		#if mobile
		stage.addEventListener(TouchEvent.TOUCH_BEGIN, touchBegin);
		stage.addEventListener(TouchEvent.TOUCH_END, touchEnd);
		pauseText.text = "Juego en PAUSA\nTocar arriba para continuar";
		#else
		stage.addEventListener(KeyboardEvent.KEY_UP, keyUp);
		stage.addEventListener(KeyboardEvent.KEY_DOWN, keyDown);
		pauseText.text = "Juego en PAUSA\nPresiona ESC para continuar";
		#end
		this.addEventListener(Event.ENTER_FRAME, everyFrame);
	}
	
	public function getLastKey() : Teclas {
		return this.lastKey;
	}
	
	private function everyFrame(event: Event): Void {
		if (currentGameState == GameState.Playing) {
			if(ticUpdate > 3){
				Tetris.update();
				ticUpdate = 0;
			}
		}
		ticUpdate++;
	}
	
	private function keyDown(event:KeyboardEvent):Void {
		switch(event.keyCode) {
			case 27: 
				if (currentGameState == GameState.Playing)
					setGameState(GameState.Paused);
				else
					setGameState(GameState.Playing);
			case 65, 37:
				this.lastKey = Teclas.IZQUIERDA;
			case 87, 38:
				this.lastKey = Teclas.ARRIBA;
			case 68, 39:
				this.lastKey = Teclas.DERECHA;
			case 83, 40:
				this.lastKey = Teclas.ABAJO;
			default:
				this.lastKey = Teclas.NINGUNA;
		}
	}
	
	private function keyUp(event:KeyboardEvent):Void {
		this.lastKey = Teclas.NINGUNA;
	}
	
	private function touchBegin(event:TouchEvent):Void {
		var posX : Int = Std.int(event.stageX);
		var posY : Int = Std.int(event.stageY);
		if (posY < Tetris.getADDY()) {
			if (currentGameState == GameState.Playing)
				setGameState(GameState.Paused);
			else if (currentGameState == GameState.Paused)
				setGameState(GameState.Playing);
		} else if ( posY > ( stage.stageHeight - Tetris.getADDY() ) ) {
			this.lastKey = Teclas.ABAJO;
		} else {
			if (posY < (stage.stageHeight / 2) )
				this.lastKey = Teclas.ARRIBA;
			else
				if (posX < (stage.stageWidth / 2) )
					this.lastKey = Teclas.IZQUIERDA;
				else
					this.lastKey = Teclas.DERECHA;
		}
	}
	
	private function touchEnd(event:TouchEvent):Void {
		this.lastKey = Teclas.NINGUNA;
	}
	
	public function setGameState(state:GameState):Void {
		currentGameState = state;
		if (state == Paused) {
			pauseText.alpha = 1;
		}else{
			pauseText.alpha = 0;
		}
	}
}
package system; 

import defaultpkg.Tetris;
import lime.math.color.RGBA;

import openfl.display.Sprite;
import openfl.text.TextFormat;
import openfl.text.TextFormatAlign;
import openfl.text.TextField;

import bitmapFont.BitmapTextField;
import bitmapFont.BitmapTextAlign;
import bitmapFont.TextBorderStyle;

import utils.GameTable;

enum Color {
	NEGRO;
	ROJO;
	VERDE;
	AZUL;
	AMARILLO;
	MAGENTA;
	CYAN;
	BLANCO;
}

class CPPAutoGen 
{
	public static var _color;
	public static var gameTable : GameTable;
	public static var _colores = [
		0xFF000000,
		0xFFFF0000,
		0xFF00FF00,
		0xFF0000FF,
		0xFFFFFF00,
		0xFFFF00FF,
		0xFF00FFFF,
		0xFFFFFFFF
	];
	
	public static function color_rgb (r : Int, g : Int, b : Int)  : Void {
		_color = (255 & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
	}
	
	public static function texto (x : Float, y: Float, _texto : String, _textField: BitmapTextField, ?size: Int)  : Void {
		size = (size == null) ? 2 : size;
		gameTable.addChild(_textField);
		_textField.text = _texto;
		_textField.alignment = BitmapTextAlign.CENTER;
		_textField.useTextColor = true;
		_textField.textColor = _color;
		_textField.size = size;
		_textField.x = x;
		_textField.y = y;
	}
	
	public static function linea (x1 : Float, y1 : Float, x2 : Float, y2 : Float, _sprite :Sprite)  : Void {
		//_sprite.graphics.beginFill(_color);
		//_sprite.graphics.drawRect(x1, y1, x1-x2, y1-y2);
		//_sprite.graphics.endFill();
		_sprite.graphics.lineStyle(1, _color, 1);
		_sprite.graphics.moveTo(x1, y1);
		_sprite.graphics.lineTo(x2, y2);
	}
	
	public static function color (pos : Int)  : Void {
		_color = _colores[pos];
	}
	
	public static function rectangulo_lleno (izq : Float, arr : Float, der : Float, aba : Float, sprite : Sprite)  : Void {
		sprite.graphics.beginFill(_color);
		sprite.graphics.drawRect(izq, arr, der-1, aba-1);
		sprite.graphics.endFill();
		
	}
	
	public static function tecla ()  : Teclas {
		return gameTable.getLastKey();
	}
	
	public static function rand () : Int {
		return Std.random(32767);
	}
}  

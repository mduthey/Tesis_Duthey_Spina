package system; 
import defaultpkg.Main;
import openfl.display.Sprite;
import openfl.text.TextFormat;
import openfl.text.TextFormatAlign;
import openfl.text.TextField;

import bitmapFont.BitmapTextField;
import bitmapFont.BitmapTextAlign;
import bitmapFont.TextBorderStyle;

import utils.GameTable;

class CPPAutoGen
{
	public static var _color;
	public static var gameTable : GameTable;
	
	public static function color_rgb (r : Int, g : Int, b : Int)  : Void {
		_color = (255 & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
	}
	
	public static function texto (x : Float, y: Float, _texto : String, _textField: BitmapTextField)  : Void {
		gameTable.addChild(_textField);
		_textField.text = _texto;
		_textField.alignment = BitmapTextAlign.CENTER;
		_textField.useTextColor = true;
		_textField.textColor = _color;
		_textField.size = 2;
		_textField.x = x;
		_textField.y = y;
	}

	public static function espera (seg : Int)  : Void {
		Sys.sleep(seg);
	}
	public static function rectangulo_lleno (izq : Float, arr : Float, der : Float, aba : Float, sprite : Sprite)  : Void {
		sprite.graphics.beginFill(_color);
		sprite.graphics.drawRect(izq, arr, der-1, aba-1);
		sprite.graphics.endFill();
		
	}
	public static function tecla ()  : Teclas {
		return gameTable.getLastKey();
	}
	public static function circulo_lleno (x_cen : Float, y_cen : Float, radio : Float, sprite : Sprite)  : Void {
		sprite.graphics.beginFill(_color);
		sprite.graphics.drawCircle(x_cen, y_cen, radio);
		sprite.graphics.endFill();
		
	}
}  
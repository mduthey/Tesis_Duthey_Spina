package defaultpkg; 

import bitmapFont.BitmapTextField;
import openfl.display.Sprite;
import system.CPPAautogen;
import utils.GameTable;
import utils.GameTable.Teclas;
import flash.Lib;

class Main
{
	static var PX:Int = 20;
	static var PY:Int = 20;
	static var EJEX:Int = 20; 
	static var EJEY:Int = 30; 
	static var TM:Int = 6; 
	static var pelx:Int = Std.int(EJEX / 2); 
	static var pely:Int = Std.int(EJEY / 2); 
	static var pdx:Bool = true; 
	static var pdy:Bool = true; 
	static var jugx:Int = Std.int(EJEX / 2); 
	static var jugy:Int = EJEY - 2; 
	static var enex:Int = Std.int(EJEX / 2); 
	static var eney:Int = 4; 
	static var jugp:Int = 0; 
	static var enep:Int = 0; 
	static var ticpelota:Int = 0; 
	static var ticene:Int = 0; 
	
	static var _limites:Sprite = null;
	static var _barra_enemigo: Sprite = null;
	static var _barra_jugador: Sprite = null;
	static var _pelota: Sprite = null;
	static var _textosHechos:Bool = false;
	static var _pjugador:BitmapTextField = null;
	static var _penemigo:BitmapTextField = null;
	
	public static function convar (x : Int)  : String {
		return Std.string(x); // Convierte a String
	} 
	
	public static function cuadrado (x : Int, y : Int, _sprite: Sprite)  : Void {
		CPPAutoGen.rectangulo_lleno(1 + x * PX, 1 + y * PY, PX, PY, _sprite);
	} 
	public static function limites_pintar ()  : Void {
		if (_limites == null) {
			_limites = new Sprite();
			CPPAutoGen.color_rgb(150, 150, 150);
			{
				var i : Int  = 0;
				while (i < EJEX)
				{
					cuadrado(i, 3, _limites);
					cuadrado(i, EJEY - 1, _limites);
					i++;
				}
			};
			{
				var i : Int  = 0;
				while (i < EJEY-3)
				{
					cuadrado(0, i + 3, _limites);
					cuadrado(EJEX - 1, i + 3, _limites);
					i++;
				}
			};
			CPPAutoGen.gameTable.addChild(_limites);
		}
	} 
	public static function barra_pintar (x : Int, y : Int, _barra : Sprite)  : Void {
		{
			var i : Int  = Std.int(-TM / 2);
			while (i < TM / 2)
			{
				cuadrado(x + i, y, _barra);
				i++;
			}
		};
	} 
	public static function barra_jugador (k : Teclas)  : Void {
		if (k == Teclas.IZQUIERDA && jugx >= TM / 2 + 2)
			jugx--;
		
		if (k == Teclas.DERECHA && jugx <= EJEX - TM / 2 - 2)
			jugx++;
		
		if (_barra_jugador == null) {
			_barra_jugador = new Sprite();
			CPPAutoGen.gameTable.addChildAt(_barra_jugador, 0);
		} else {
			_barra_jugador.graphics.clear();
		}
		
		CPPAutoGen.color_rgb(150, 150, 250);
		barra_pintar(jugx, jugy, _barra_jugador);
	} 
	public static function barra_enemiga ()  : Void {
		if (pdy == false && ticene > 4)
			{
				if (pdx == false)
					enex--;
				
				if (pdx == true)
					enex++;
				
				if (enex <= TM / 2 + 1)
					enex = Std.int(TM / 2 + 1);
				
				if (enex >= EJEX - TM / 2 - 1)
					enex = Std.int(EJEX - TM / 2 - 1);
				
				ticene = 0;
			}
		else 
			if (pdy == true && ticene > 8)
				{
					if (enex < EJEX / 2)
						enex++;
					
					if (enex > EJEX / 2)
						enex--;
					
					ticene = 0;
				}
			
		if (_barra_enemigo == null) {
			_barra_enemigo = new Sprite();
			CPPAutoGen.gameTable.addChildAt(_barra_enemigo, 0);
		} else {
			_barra_enemigo.graphics.clear();
		}
		CPPAutoGen.color_rgb(250, 150, 150);
		barra_pintar(enex, eney, _barra_enemigo);
	} 
	
	public static function pelota_pinta ()  : Void {
		if (_pelota == null) {
				_pelota = new Sprite();
				CPPAutoGen.gameTable.addChildAt(_pelota, 0);
		} else {
			_pelota.graphics.clear();
		}
		CPPAutoGen.color_rgb(250, 100, 250);
		CPPAutoGen.circulo_lleno(pelx * PX + PX / 2, pely * PY + PY / 2, PY / 2, _pelota);
	} 
	public static function pelota_control ()  : Void {
		ticpelota = 0;
		if (pelx <= 1)
			pdx = true;
		
		if (pelx >= EJEX - 2)
			pdx = false;
		
		if (pely <= 4 || pely >= EJEY - 2)
			{
				if (pely <= 4)
					jugp++;
				
				if (pely >= EJEY - 2)
					enep++;
				
				pelx = Std.int(EJEX / 2);
				pely = Std.int(EJEY / 2);
			}
		
		if (pelx >= jugx - TM / 2 - 1 && pelx <= jugx + TM / 2 && pely == jugy - 1)
			pdy = false;
		
		if (pelx >= enex - TM / 2 - 1 && pelx <= enex + TM / 2 && pely == eney + 1)
			pdy = true;
		
		if (pdx == false)
			pelx--;
		else 
			pelx++;
		
		if (pdy == false)
			pely--;
		else 
			pely++;
		
	} 
	public static function juego_estadisticas ()  : Void {
		if ( _textosHechos == false ) {
			CPPAutoGen.color_rgb(200, 200, 200);
			CPPAutoGen.texto(PX, PY, "PUNTOS JUGADOR", new BitmapTextField());
			CPPAutoGen.texto((EJEX - 8) * PX, PY, "PUNTOS ENEMIGO", new BitmapTextField());
			CPPAutoGen.color_rgb(255, 255, 255); // Cambiado color(BLANCO) por rgb
			_pjugador = new BitmapTextField();
			CPPAutoGen.texto(PX, 2 * PY, convar(jugp), _pjugador);
			_penemigo = new BitmapTextField();
			CPPAutoGen.texto((EJEX - 5) * PX, 2 * PY, convar(enep), _penemigo);
			_textosHechos = true;
		} else {
			_pjugador.text = convar(jugp);
			_penemigo.text = convar(enep);
		}
	} 
	
	public static function main ()  : Int {
		CPPAutoGen.gameTable = new GameTable();
		Lib.current.addChild(CPPAutoGen.gameTable);
		return 0;
	} 
	
	public static function valorPX() : Float {
		return PX;
	}
	
	public static function setPX(v : Float) : Void {
		PX = Std.int(v);
	}
	
	public static function valorPY() : Float {
		return PY;
	}
	
	public static function setPY(v : Float) : Void {
		PY = Std.int(v);
	}
	
	
	public static function getEJEX() : Int {
		return EJEX;
	}
	
	public static function getEJEY() : Int {
		return EJEY;
	}
	
	public static function update() : Void {
		ticpelota++;
		ticene++;
		limites_pintar();
		barra_jugador(CPPAutoGen.tecla());
		barra_enemiga();
		if (ticpelota >= 3) {
			pelota_control();
		}
		pelota_pinta();
		juego_estadisticas();
	}
}  

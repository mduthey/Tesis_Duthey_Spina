package defaultpkg; 

import defaultpkg.Tetris.Coord;
import defaultpkg.Tetris.Pieza;
import openfl.display.Sprite;
import system.CPPAautogen;

import bitmapFont.BitmapTextField;

import utils.GameTable;
import utils.GameTable.Teclas;
import flash.Lib;

class Pieza 
{
	public var orig:Coord; 
	public var perif:Array<Coord>; 
	public var color:Int;
	public var sprite:Sprite = new Sprite();
	public function posicion (n : Int)  : Coord {
		var ret : Coord  = new Coord(orig.x, orig.y);
		if (n != 0)
			{
				ret.x += perif[n - 1] .x;
				ret.y += perif[n - 1] .y;
			}
		
		return ret;
	} 
	public function new () {
		orig = new Coord(); 
		perif = [ for (i in 0...3) null ];
	}
	public function clonar() : Pieza {
		var ret : Pieza = new Pieza();
		ret.orig.x = orig.x;
		ret.orig.y = orig.y;
		for (i in 0...3) {
			ret.perif[i] = new Coord(perif[i].x, perif[i].y);
		}
		ret.color = color;
		ret.sprite = sprite;
		return ret;
	}
}  
class Tetris 
{
	static var ADDX:Int = 220;
	static var ADDY:Int = 100;
	static var TAMX:Int = 25; 
	static var TAMY:Int = 25;
	static var FILAS:Int = 20; 
	static var COLUMNAS:Int = 10; 
	static var MARGEN:Int = 20;
	
	
	static var _tableroSprite:Sprite = null;
	static var _tableroLimites:Sprite = null;
	static var _puntosTetris:BitmapTextField = null;
	static var _nivelTetris:BitmapTextField = null;
	static var _tableroCambio:Bool = true;
	
	public static function cuadrado (x : Int, y : Int, _sprite: Sprite)  : Void {
		//CPPAutoGen.rectangulo_lleno(MARGEN + 1 + x * TAM, MARGEN + 1 + y * TAM, MARGEN + x * TAM + TAM, MARGEN + y * TAM + TAM, _sprite);
		CPPAutoGen.rectangulo_lleno(MARGEN + 1 + x * TAMX, MARGEN + 1 + y * TAMY, TAMX, TAMY, _sprite);
	} 
	public static function pinta_pieza (P : Pieza)  : Void {
		CPPAutoGen.color(P.color);
		P.sprite.graphics.clear();
		{
			var i : Int  = 0;
			while (i < 4)
			{
				var c : Coord  = P.posicion(i);
				cuadrado(c.x, c.y, P.sprite);
				i++;
			}
		};
	} 
	public static function rota_derecha_coord (c : Coord)  : Coord {
		var ret : Coord  = new Coord(-c.y, c.x);
		return ret;
	} 
	public static function rota_derecha (P : Pieza)  : Void {
		{
			var i : Int  = 0;
			while (i < 3)
			{
				P.perif[i] = rota_derecha_coord(P.perif[i] );
				i++;
			}
		};
	} 
	public static function tablero_vacia (T : Tablero)  : Void {
		{
			var i : Int  = 0;
			while (i < COLUMNAS)
			{
				{
					var j : Int  = 0;
					while (j < FILAS)
					{
						T[i][j]  = Color.NEGRO.getIndex();
						j++;
					}
				};
				i++;
			}
		};
	} 
	public static function tablero_pinta (T : Tablero)  : Void {
		if(_tableroCambio)
		{
			var i : Int  = 0;
			_tableroSprite.graphics.clear();
			while (i < COLUMNAS)
			{
				{
					var j : Int  = 0;
					while (j < FILAS)
					{
						if(T[i][j] != Color.NEGRO.getIndex()){
							CPPAutoGen.color(T[i][j]);
							cuadrado(i, j, _tableroSprite);
						}
						j++;
					}
				};
				i++;
			}
			_tableroCambio = false;
		}
	} 
	public static function tablero_incrusta_pieza (T : Tablero, P : Pieza)  : Void {
		{
			var i : Int  = 0;
			while (i < 4)
			{
				var c : Coord  = P.posicion(i);
				T[c.x][c.y]  = P.color;
				i++;
			}
			CPPAutoGen.gameTable.removeChild(P.sprite);
			_tableroCambio = true;
		};
	} 
	public static function tablero_colision (T : Tablero, P : Pieza)  : Bool {
		{
			var i : Int  = 0;
			while (i < 4)
			{
				var c : Coord  = P.posicion(i);
				if (c.x < 0 || c.x >= COLUMNAS)
					{
						return true;
					}
				
				if (c.y < 0 || c.y >= FILAS)
					{
						return true;
					}
				
				if (T[c.x][c.y] != Color.NEGRO.getIndex())
					{
						return true;
					}
				
				i++;
			}
		};
		return false;
	} 
	public static var perifs = [
		[ [ 1,0 ], [ 0,1 ], [ 1,1 ] ],
		[ [ 1,0 ], [-1,1 ], [ 0,1 ] ],
		[ [ 0,1 ], [ 1,1 ], [-1,0 ] ],
		[ [ 0,1 ], [ 0,-1], [ 1,1 ] ],
		[ [ 0,1 ], [ 0,-1], [-1,1 ] ],
		[ [-1,0 ], [ 1,0 ], [ 0,1 ] ],
		[ [ 0,1 ], [ 0,-1], [ 0,2 ] ]
	]; 
	public static function pieza_nueva ()  : Pieza {
		var P : Pieza = new Pieza();
		P.orig.x = 12;
		P.orig.y = 2;
		P.color = 1 + CPPAutoGen.rand() % 6;
		P.sprite = new Sprite();
		CPPAutoGen.gameTable.addChild(P.sprite);
		var r : Int  = CPPAutoGen.rand() % 7;
		{
			var i : Int  = 0;
			while (i < 3)
			{
				P.perif[i]  = new Coord(perifs[r][i][0], perifs[r][i][1]) ;
				i++;
			}
		};
		return P;
	} 
	public static function tablero_fila_llena (T : Tablero, fila : Int)  : Bool {
		{
			var i : Int  = 0;
			while (i < COLUMNAS)
			{
				if (T[i] [fila]  == Color.NEGRO.getIndex())
					return false;
				
				i++;
			}
		};
		return true;
	} 
	public static function tablero_colapsa (T : Tablero, fila : Int)  : Void {
		{
			var j : Int  = fila;
			while (j > 0)
			{
				{
					var i : Int  = 0;
					while (i < COLUMNAS)
					{
						T[i] [j]  = T[i] [j - 1] ;
						i++;
					}
				};
				j--;
			}
		};
		{
			var i : Int  = 0;
			while (i < COLUMNAS)
			{
				T[i] [0]  = Color.NEGRO.getIndex();
				i++;
			}
		};
		_tableroCambio = true;
	} 
	public static function tablero_cuenta_lineas (T : Tablero)  : Int {
		var fila : Int  = FILAS - 1;
		var cont : Int  = 0;
		while (fila >= 0)
		{
			if (tablero_fila_llena(T, fila))
				{
					tablero_colapsa(T, fila);
					cont++;
				}
			else 
				{
					fila--;
				}
			
		}
		return cont;
	} 
	public static function a_string (puntos : Int)  : String {
		return Std.string(puntos); // Convierte a String
	} 
	public static function repinta (T : Tablero, p : Pieza, sig : Pieza, puntos : Int, nivel : Int)  : Void {
		var ancho : Int  = TAMX * COLUMNAS;
		var alto : Int  = TAMY * FILAS;
		//borra();
		tablero_pinta(T);
		CPPAutoGen.color_rgb(128, 128, 128);
		if (_tableroLimites == null) {
				_tableroLimites = new Sprite();
				CPPAutoGen.gameTable.addChild(_tableroLimites);
				CPPAutoGen.linea(MARGEN, MARGEN, MARGEN, MARGEN + alto, _tableroLimites);
				CPPAutoGen.linea(MARGEN, MARGEN + alto, MARGEN + ancho, MARGEN + alto, _tableroLimites);
				CPPAutoGen.linea(MARGEN + ancho, MARGEN + alto, MARGEN + ancho, MARGEN, _tableroLimites);
		}
		if (_nivelTetris == null && _puntosTetris == null) {
			CPPAutoGen.texto(40 + ancho, 20, "Pieza siguiente", new BitmapTextField());
			CPPAutoGen.texto(40 + ancho, 150, "Nivel", new BitmapTextField());
			CPPAutoGen.texto(40 + ancho, 250, "Puntos", new BitmapTextField());
			CPPAutoGen.color(Color.BLANCO.getIndex());
			_puntosTetris = new BitmapTextField();
			_nivelTetris = new BitmapTextField();
			CPPAutoGen.texto(40 + ancho, 270, a_string(puntos), _puntosTetris);
			CPPAutoGen.texto(40 + ancho, 170, a_string(nivel + 1), _nivelTetris);
		}else{
			_puntosTetris.text = a_string(puntos);
			_nivelTetris.text = a_string(nivel + 1);
		}
		pinta_pieza(p);
		pinta_pieza(sig);
		//refresca();
	} 
	public static var puntos_limite = [50, 100, 130, 150, 170, 200, 220, 240, 260, 400]; 
	public static var tics_nivel = [33, 25, 20, 18, 16, 14, 12, 10, 8, 2]; 
	public static function game_over ()  : Void {
		CPPAutoGen.color(Color.BLANCO.getIndex());
		CPPAutoGen.texto(140, 240, "GAME OVER", new BitmapTextField(), 4);
		CPPAutoGen.gameTable.setGameState(GameState.GameOver);
		//refresca();
		//espera(1000);
		//vcierra();
	} 
	public static function main ()  : Int {
		CPPAutoGen.gameTable = new GameTable();
		Lib.current.addChild(CPPAutoGen.gameTable);
		return 0;
		
		/*vredimensiona(TAM * COLUMNAS + 220, TAM * FILAS + 100);
		srand(time(0));
		var tic : Int  = 0;
		var puntos : Int  = 0;
		var nivel : Int  = 0;
		var T : Tablero ;
		tablero_vacia(T);
		var c : Pieza ;
		var sig : Pieza ;
		pieza_nueva(c);
		pieza_nueva(sig);
		c.orig.x = 5;
		repinta(T, c, sig, puntos, nivel);
		var t : Int  = tecla();
		while (t != ESCAPE)
		{
			var copia : Pieza  = c;
			if (t == NINGUNA && tic > tics_nivel[nivel] )
				{
					tic = 0;
					t = ABAJO;
				}
			
			if (t == cast(N,Int))
				{
					nivel++;
				}
			
			if (t == ABAJO)
				{
					c.orig.y++;
				}
			else 
				if (t == ARRIBA)
					{
						rota_derecha(c);
					}
				else 
					if (t == DERECHA)
						{
							c.orig.x++;
						}
					else 
						if (t == IZQUIERDA)
							{
								c.orig.x--;
							}
						
					
				
			
			if (tablero_colision(T, c))
				{
					c = copia;
					if (t == ABAJO)
						{
							tablero_incrusta_pieza(T, c);
							var cont : Int  = tablero_cuenta_lineas(T);
							puntos += cont * cont;
							if (puntos > puntos_limite[nivel] )
								{
									nivel++;
								}
							
							c = sig;
							pieza_nueva(sig);
							c.orig.x = 5;
							if (tablero_colision(T, c))
								{
									game_over();
								}
							
						}
					
				}
			
			if (t != NINGUNA)
				{
					repinta(T, c, sig, puntos, nivel);
				}
			
			espera(30);
			tic++;
			t = tecla();
		}
		vcierra();
		return 0;*/
	}
	
	static var tic : Int  = 0;
	static var puntos : Int  = 0;
	static var nivel : Int  = 0;
	static var T : Tablero;
	static var c : Pieza ;
	static var sig : Pieza ;
	
	public static function init() : Void {
		// http://stackoverflow.com/questions/16317567/how-to-declare-2d-arrays-in-haxe
		T = [for (x in 0...COLUMNAS) [for (y in 0...FILAS) Color.NEGRO.getIndex()]];
		if (_tableroSprite == null) {
			_tableroSprite = new Sprite();
			CPPAutoGen.gameTable.addChild(_tableroSprite);
		}
		tablero_vacia(T);
		tablero_pinta(T);
		c = pieza_nueva();
		sig = pieza_nueva();
		c.orig.x = 5;
		repinta(T, c, sig, puntos, nivel);
	}
	
	public static function update() : Void {
		var copia : Pieza  = c.clonar();
		var t : Teclas = CPPAutoGen.tecla();
		if (t == Teclas.NINGUNA && tic > tics_nivel[nivel] )
			{
				tic = 0;
				t = Teclas.ABAJO;
			}
		
		if (t == Teclas.ABAJO)
			{
				c.orig.y++;
			}
		else 
			if (t == Teclas.ARRIBA)
				{
					rota_derecha(c);
				}
			else 
				if (t == Teclas.DERECHA)
					{
						c.orig.x++;
					}
				else 
					if (t == Teclas.IZQUIERDA)
						{
							c.orig.x--;
						}
					
				
			
		
		if (tablero_colision(T, c))
			{
				c = copia;
				if (t == Teclas.ABAJO)
					{
						tablero_incrusta_pieza(T, c);
						var cont : Int  = tablero_cuenta_lineas(T);
						puntos += cont * cont;
						if (puntos > puntos_limite[nivel] )
							{
								nivel++;
							}
						c = sig;
						sig = pieza_nueva();
						c.orig.x = 5;
						if (tablero_colision(T, c))
							{
								game_over();
							}
					}
			}
		
		if (t != Teclas.NINGUNA)
			{
				repinta(T, c, sig, puntos, nivel);
			}
		
		tic++;
	}
	
	public static function setTAMX(v : Float) : Void {
		TAMX = Std.int(v);
	}
	
	public static function setTAMY(v : Float) : Void {
		TAMY = Std.int(v);
	}
	
	
	public static function getCOLUMNAS() : Int {
		return COLUMNAS;
	}
	
	public static function getFILAS() : Int {
		return FILAS;
	}
	
	public static function getADDX() : Int {
		return ADDX;
	}
	
	public static function getADDY() : Int {
		return ADDY;
	}
	
}  
typedef Tablero = Array<Array<Int>>; // typedef int Tablero[COLUMNAS][FILAS];
class Coord 
{
	public var x:Int; 
	public var y:Int; 
	public function new (?x : Int, ?y : Int) {
		x = (x == null) ? 0 : x;
		y = (y == null) ? 0 : y;
		this.x = x;
		this.y = y;
	}
}  

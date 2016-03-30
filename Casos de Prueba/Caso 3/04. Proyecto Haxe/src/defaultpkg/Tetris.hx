package defaultpkg; 
class Tetris 
{
	static var TAM:Int = 25; 
	static var FILAS:Int = 20; 
	static var COLUMNAS:Int = 10; 
	static var MARGEN:Int = 20; 
	public static function cuadrado (x : Int, y : Int)  : Void {
		rectangulo_lleno(MARGEN + 1 + x * TAM, MARGEN + 1 + y * TAM, MARGEN + x * TAM + TAM, MARGEN + y * TAM + TAM);
	} 
	public static function pinta_pieza (P : Pieza)  : Void {
		color(P.color);
		{
			var i : Int  = 0;
			while (i < 4)
			{
				var c : Coord  = P.posicion(i);
				cuadrado(c.x, c.y);
				i++;
			}
		};
	} 
	public static function rota_derecha (c : Coord)  : Coord {
		var ret : Coord  = [c.x,-c.y];
		return ret;
	} 
	public static function rota_derecha (P : Pieza)  : Void {
		{
			var i : Int  = 0;
			while (i < 3)
			{
				P.perif[i]  = rota_derecha(P.perif[i] );
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
						T[i] [j]  = NEGRO;
						j++;
					}
				};
				i++;
			}
		};
	} 
	public static function tablero_pinta (T : Tablero)  : Void {
		{
			var i : Int  = 0;
			while (i < COLUMNAS)
			{
				{
					var j : Int  = 0;
					while (j < FILAS)
					{
						color(T[i] [j] );
						cuadrado(i, j);
						j++;
					}
				};
				i++;
			}
		};
	} 
	public static function tablero_incrusta_pieza (T : Tablero, P : Pieza)  : Void {
		{
			var i : Int  = 0;
			while (i < 4)
			{
				var c : Coord  = P.posicion(i);
				T[c.x] [c.y]  = P.color;
				i++;
			}
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
				
				if (T[c.x] [c.y]  != NEGRO)
					{
						return true;
					}
				
				i++;
			}
		};
		return false;
	} 
	public static var perifs:Coord = [[[1,0],[1,0],[1,1]],[[1,0],[0,-1],[1,-1]],[[1,0],[0,-1],[2,0]],[[1,0],[1,0],[0,-1]],[[1,0],[0,-1],[1,1]],[[1,0],[0,-1],[1,1]],[[1,0],[1,0],[1,-1]]]; 
	public static function pieza_nueva (P : Pieza)  : Void {
		P.orig.x = 12;
		P.orig.y = 2;
		P.color = 1 + rand() % 6;
		var r : Int  = rand() % 7;
		{
			var i : Int  = 0;
			while (i < 3)
			{
				P.perif[i]  = perifs[r] [i] ;
				i++;
			}
		};
	} 
	public static function tablero_fila_llena (T : Tablero, fila : Int)  : Bool {
		{
			var i : Int  = 0;
			while (i < COLUMNAS)
			{
				if (T[i] [fila]  == NEGRO)
					return false;;
				
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
				T[i] [0]  = NEGRO;
				i++;
			}
		};
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
		var sout : Stringstream ;
		sout << puntos
		return sout.str();
	} 
	public static function repinta (T : Tablero, p : Pieza, sig : Pieza, puntos : Int, nivel : Int)  : Void {
		var ancho : Int  = TAM * COLUMNAS;
		var alto : Int  = TAM * FILAS;
		borra();
		tablero_pinta(T);
		color_rgb(128, 128, 128);
		linea(MARGEN, MARGEN, MARGEN, MARGEN + alto);
		linea(MARGEN, MARGEN + alto, MARGEN + ancho, MARGEN + alto);
		linea(MARGEN + ancho, MARGEN + alto, MARGEN + ancho, MARGEN);
		texto(40 + ancho, 20, "Pieza siguiente");
		texto(40 + ancho, 150, "Nivel");
		texto(40 + ancho, 250, "Puntos");
		color(BLANCO);
		texto(40 + ancho, 270, a_string(puntos));
		texto(40 + ancho, 170, a_string(nivel + 1));
		pinta_pieza(p);
		pinta_pieza(sig);
		refresca();
	} 
	public static var puntos_limite:Int = [170,220,150,260,240,200,400,130,100,50]; 
	public static var tics_nivel:Int = [20,2,10,18,33,16,25,8,14,12]; 
	public static function game_over ()  : Void {
		color(BLANCO);
		texto(140, 240, "GAME OVER");
		refresca();
		espera(1000);
		vcierra();
	} 
	public static function main ()  : Int {
		vredimensiona(TAM * COLUMNAS + 220, TAM * FILAS + 100);
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
		return 0;
	} 
}  
typedef Tablero = Int;  
class Coord 
{
	var x:Int; 
	var y:Int; 
}  
class Pieza 
{
	var orig:Coord; 
	var perif:Coord = null; 
	var color:Int; 
	function posicion (n : Int)  : Coord {
		var ret : Coord  = [orig.x,orig.y];
		if (n != 0)
			{
				ret.x += perif[n - 1] .x;
				ret.y += perif[n - 1] .y;
			}
		
		return ret;
	} 
}  

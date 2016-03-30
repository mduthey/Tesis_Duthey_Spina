package defaultpkg; 
class Main 
{
	static var PX:Int = 20; 
	static var EJEX:Int = 20; 
	static var EJEY:Int = 30; 
	static var TM:Int = 6; 
	var pelx:Int = EJEX / 2; 
	var pely:Int = EJEY / 2; 
	var pdx:Bool = 1; 
	var pdy:Bool = 1; 
	var jugx:Int = EJEX / 2; 
	var jugy:Int = EJEY - 2; 
	var enex:Int = EJEX / 2; 
	var eney:Int = 4; 
	var jugp:Int = 0; 
	var enep:Int = 0; 
	var ticpelota:Int = 0; 
	var ticene:Int = 0; 
	public static function convar (x : Int)  : String {
		var sout : Stringstream ;
		sout << x
		return sout.str();
	} 
	public static function cuadrado (x : Int, y : Int)  : Void {
		rectangulo_lleno(1 + x * PX, 1 + y * PX, (x + 1) * PX, (y + 1) * PX);
	} 
	public static function limites_pintar ()  : Void {
		color_rgb(150, 150, 150);
		{
			var i : Int  = 0;
			while (i < EJEX)
			{
				cuadrado(i, 3);
				cuadrado(i, EJEY - 1);
				i++;
			}
		};
		{
			var i : Int  = 0;
			while (i < EJEY)
			{
				cuadrado(0, i + 3);
				cuadrado(EJEX - 1, i + 3);
				i++;
			}
		};
	} 
	public static function barra_pintar (x : Int, y : Int)  : Void {
		{
			var i : Int  = -TM / 2;
			while (i < TM / 2)
			{
				cuadrado(x + i, y);
				i++;
			}
		};
	} 
	public static function barra_jugador (k : Int)  : Void {
		if (k == IZQUIERDA && jugx >= (TM / 2) + 2)
			jugx--;
		
		if (k == DERECHA && jugx <= EJEX - (TM / 2) - 2)
			jugx++;
		
		color_rgb(150, 150, 250);
		barra_pintar(jugx, jugy);
	} 
	public static function barra_enemiga ()  : Void {
		if (pdy == 0 && ticene > 15)
			{
				if (pdx == 0)
					enex--;
				
				if (pdx == 1)
					enex++;
				
				if (enex <= (TM / 2) + 1)
					enex = (TM / 2) + 1;
				
				if (enex >= EJEX - (TM / 2) - 1)
					enex = EJEX - (TM / 2) - 1;
				
				ticene = 0;
			}
		else 
			if (pdy == 1 && ticene > 20)
				{
					if (enex < EJEX / 2)
						enex++;
					
					if (enex > EJEX / 2)
						enex--;
					
					ticene = 0;
				}
			
		
		color_rgb(250, 150, 150);
		barra_pintar(enex, eney);
	} 
	public static function pelota_pinta ()  : Void {
		color_rgb(250, 100, 250);
		circulo_lleno((pelx * PX) + (PX / 2), (pely * PX) + (PX / 2), PX / 2);
	} 
	public static function pelota_control ()  : Void {
		ticpelota = 0;
		if (pelx <= 1)
			pdx = 1;
		
		if (pelx >= EJEX - 2)
			pdx = 0;
		
		if (pely <= 4 || pely >= EJEY - 2)
			{
				if (pely <= 4)
					jugp++;
				
				if (pely >= EJEY - 2)
					enep++;
				
				pelx = EJEX / 2;
				pely = EJEY / 2;
			}
		
		if (pelx >= jugx - (TM / 2) - 1 && pelx <= jugx + (TM / 2) && pely == jugy - 1)
			pdy = 0;
		
		if (pelx >= enex - (TM / 2) - 1 && pelx <= enex + (TM / 2) && pely == eney + 1)
			pdy = 1;
		
		if (pdx == 0)
			pelx--;
		else 
			pelx++;
		
		if (pdy == 0)
			pely--;
		else 
			pely++;
		
	} 
	public static function juego_estadisticas ()  : Void {
		color_rgb(200, 200, 200);
		texto(PX, PX, "PUNTOS JUGADOR");
		texto((EJEX - 8) * PX, PX, "PUNTOS ENEMIGO");
		color(BLANCO);
		texto(PX, 2 * PX, convar(jugp));
		texto((EJEX - 5) * PX, 2 * PX, convar(enep));
	} 
	public static function main ()  : Int {
		var key : Int  = tecla();
		vredimensiona(EJEX * PX, EJEY * PX);
		while (key != ESCAPE)
		{
			ticpelota++;
			ticene++;
			espera(10);
			key = tecla();
			borra();
			limites_pintar();
			barra_jugador(key);
			barra_enemiga();
			if (ticpelota >= 10)
				pelota_control();
			
			pelota_pinta();
			juego_estadisticas();
			refresca();
		}
		vcierra();
		return 0;
	} 
}  

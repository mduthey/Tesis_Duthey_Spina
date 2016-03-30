#include "miniwin.h"
#include <sstream>
using namespace std;
using namespace miniwin;

const int PX=20, EJEX=20, EJEY=30, TM=6;
int pelx=EJEX/2, pely=EJEY/2; // Pelota
bool pdx=1, pdy=1; // Pelota Direccion
int jugx=EJEX/2, jugy=EJEY-2; // Jugador
int enex=EJEX/2, eney=4; // Enemigo
int jugp=0, enep=0; // Puntos jugador y enemigo
int ticpelota=0; // Variable para no mover tan seguido la pelota
int ticene=0; // Variable para no mover tan seguido al enemigo

string convar(int x) { // Convertidor de entero a string
    stringstream sout;
    sout << x;
    return sout.str();
}

void cuadrado(int x, int y) { // Cuadrado
    rectangulo_lleno(1+x*PX, 1+y*PX, (x+1)*PX, (y+1)*PX);
}

void limites_pintar() {
    color_rgb(150,150,150);
    for (int i=0; i<EJEX; i++) { // Horizontales
        cuadrado(i,3); // Arriba
        cuadrado(i,EJEY-1); // Abajo
    }
    for (int i=0; i<EJEY; i++) { // Verticales
        cuadrado(0,i+3); // Izquierda
        cuadrado(EJEX-1,i+3); // Derecha
    }
}

void barra_pintar(int x, int y) {
    for (int i=-TM/2;i<TM/2;i++)
        cuadrado(x+i,y);
}

void barra_jugador(int k) { // Control barra jugador
    if (k==IZQUIERDA && jugx>=(TM/2)+2)    jugx--;
    if (k==DERECHA && jugx<=EJEX-(TM/2)-2) jugx++;
    color_rgb(150,150,250);
    barra_pintar(jugx,jugy);
}

void barra_enemiga() { // Control barra enemiga
    if (pdy==0 && ticene>15) {  // Intentar pegar a la pelota
        if (pdx==0) enex--;
        if (pdx==1) enex++;
        if (enex<=(TM/2)+1) enex=(TM/2)+1;
        if (enex>=EJEX-(TM/2)-1) enex=EJEX-(TM/2)-1;
        ticene=0;
    }
    else if (pdy==1 && ticene>20) { // Centrarse si es adecuado
        if (enex<EJEX/2) enex++;
        if (enex>EJEX/2) enex--;
        ticene=0;
    }
    color_rgb(250,150,150);
    barra_pintar(enex,eney);
}

void pelota_pinta() {
    color_rgb(250,100,250);
    circulo_lleno((pelx*PX)+(PX/2),(pely*PX)+(PX/2),PX/2);
}

void pelota_control() {
    ticpelota=0;
    if (pelx<=1) pdx=1; // Control rebote con muro izquierdo
    if (pelx>=EJEX-2) pdx=0; // Control rebote muro derecho
    if (pely<=4 || pely>=EJEY-2) {
        if (pely<=4)      jugp++; // Enemigo pierde, jugador gana punto
        if (pely>=EJEY-2) enep++; // Jugador pierde, enemigo gana punto
        // restauramos pelota
        pelx=EJEX/2;
        pely=EJEY/2;
    }
    if (pelx>=jugx-(TM/2)-1&&pelx<=jugx+(TM/2)&&pely==jugy-1) pdy=0; // Pelota pega con barra jugador
    if (pelx>=enex-(TM/2)-1&&pelx<=enex+(TM/2)&&pely==eney+1) pdy=1; // Pelota pega con barra enemigo
    // Actualizar dirección pelota
    if (pdx==0) pelx--;
    else        pelx++;
    if (pdy==0) pely--;
    else        pely++;
}

void juego_estadisticas() {
    color_rgb(200,200,200);
    texto(PX,PX,"PUNTOS JUGADOR");
    texto((EJEX-8)*PX,PX,"PUNTOS ENEMIGO");
    color(BLANCO);
    texto(PX,2*PX,convar(jugp));
    texto((EJEX-5)*PX,2*PX,convar(enep));
}

int main() {
    int key=tecla();
    vredimensiona(EJEX*PX,EJEY*PX);
    while(key!=ESCAPE) {
        ticpelota++;
        ticene++;
        espera(10);
        key=tecla();
        borra();
        limites_pintar();
        barra_jugador(key);
        barra_enemiga();
        if(ticpelota>=10) pelota_control();
        pelota_pinta();
        juego_estadisticas();
        refresca();
    }
    vcierra();
    return 0;
}

import java.util.Random;
import java.lang.Math;

class Zufall
{
/* Klasse enthaelt spezielle Methoden zur Berechnung von Zufallsgroessen
erstellt an: 14.05.1997
*/

static float expozufall (float ewert)
{
/* Diese Methode liefert in Datentyp 'float' einen Zufallswert, der aus einer Exponentialverteilung mit dem mitzulieferten Erwartungswert stammt.
*/

float expo;
float normrand;
normrand=(float) (Math.random());
expo=(float) (-ewert*Math.log((double)normrand));
return(expo);
}


public static int Mann_Frau_Kind(int man_quote, int womnapnt)
{
/*
Methode liefert einen entsprechenden Integerwert zurueck:
fuer 0: Kunde ist ein Mann
fuer 1: Kunde ist eine Frau die einen Termin vereinbart hatte
fuer 2: Kunde ist eine Frau ohne vereinbarten Termin
Als Parameter uebergibt man zwei Integerwerte in Prozentangabe:
1. man_quote -> Prozentanteil der Maenner an allen Kunden
2. womnapnt  -> Prozentanteil der Frauen mit einem Termin an allen Frauen
*/

float normrand;
int result;
float Mann;
float Frau_mit;
normrand=(float) (Math.random());
Mann=(float) ((float)(man_quote)/100);
Frau_mit=((float) (man_quote)/100)+(1-(float)(man_quote)/100)*(float)(womnapnt)/100;
if (normrand <= Mann) result=0;
else if ((normrand>Mann)&&(normrand<=Frau_mit)) result=2;
else result =1;
return(result);
}

}

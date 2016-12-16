class Time{

/* Klasse Time ist in der Lage eine Uhrzeit im Format 'STD"."MIN' in Minuten umzurechnen und umgekehrt
erstellt am:14.05.1997
*/

public static String Minute_to_Time(int minute)
{
/* Funktion bekommt die Anzahl der Minuten und gibt einen String zurueck, der im Format 'STD"."MIN' die entsprechenden Stunden und Minuten enthaelt
*/

int Stunden,Minuten;
String Uhr="";
Stunden= (int) (minute/60)%24;
Minuten= (int) minute%60;
if ((Stunden<10) && (Minuten<10))
Uhr="0"+Stunden+"."+"0"+Minuten+" Uhr";
else if ((Stunden<10) && (Minuten>=10))
Uhr="0"+Stunden+"."+Minuten+" Uhr";
else if ((Stunden>=10) && (Minuten<10))
Uhr=Stunden+"."+"0"+Minuten+" Uhr";
else if ((Stunden>=10) && (Minuten>=10))
Uhr=Stunden+"."+Minuten+" Uhr";
return Uhr;
}

public static int Time_to_Minute(String Uhrzeit)
/* Funktion bekommt die Uhrzeit im Format 'STD"."MIN' und errechnet die entsprechenden Minuten und liefert diese als Integerwert zurueck
*/

{
int Stunden,Minuten,count;
boolean point=false;
String std,min;
std=Uhrzeit.substring(0,2);
min=Uhrzeit.substring(3);
Stunden=Integer.parseInt(std);
Minuten=Integer.parseInt(min);
return Stunden*60+Minuten;
}

}

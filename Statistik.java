import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;

class Statistik {

	private Abteilung Obj_Dam;
	private Abteilung Obj_Her;

	private float[] 	resWarteDam,	resWarteHer;
	private float[] 	resAusDam, 		resAusHer;
	private float 	resWarteAbtDam,	resWarteAbtHer;
	private int[] KundHer, KundDam;
	private int AbtHer, AbtDam;


	private float[] gesResWarteDam, gesResWarteHer;
	private float[] gesResAusDam, gesResAusHer;
	private float gesResWarteAbtDam, gesResWarteAbtHer;
	private int[] gesKundHer, gesKundDam;
	private int gesAbtHer, gesAbtDam;


	public Statistik(Abteilung Obj_Dam, Abteilung Obj_Her){
		int count = 0;
		this.Obj_Dam = Obj_Dam;
		this.Obj_Her = Obj_Her;

		resWarteDam = new float[Obj_Dam.getAnzahl()];
		resWarteHer = new float[Obj_Her.getAnzahl()];
		resAusDam = new float[Obj_Dam.getAnzahl()];
		resAusHer = new float[Obj_Her.getAnzahl()];
		KundHer = new int[Obj_Her.getAnzahl()];
		KundDam = new int[Obj_Dam.getAnzahl()];


		gesResWarteDam = new float[Obj_Dam.getAnzahl()];
		gesResWarteHer = new float[Obj_Her.getAnzahl()];
		gesResAusDam = new float[Obj_Dam.getAnzahl()];
		gesResAusHer = new float[Obj_Her.getAnzahl()];
		gesKundHer = new int[Obj_Her.getAnzahl()];
		gesKundDam = new int[Obj_Dam.getAnzahl()];

		resWarteAbtDam = 0;
		resWarteAbtHer = 0;
		gesAbtHer = 0;
		gesAbtDam = 0;
		AbtHer = 0;
		AbtDam = 0;


		gesResWarteAbtDam = 0;
		gesResWarteAbtHer = 0;
	}


public void calculation(int simzeit,int simbeginn){

// Berechnung der vorangeschrittenen Zeit in Minuten
	int zeit = simzeit - simbeginn;

// Berechung der statistischen Groessen fuer den Damensalon
	int count;
	for (count=0; count<Obj_Dam.getAnzahl(); count++){

    // mittlere Wartezeit fuer Bedienstation '1,2,3....'
	if (Obj_Dam.getBedienstation(count).getAnzahlKunden() != 0)
		resWarteDam[count] = (float) (Obj_Dam.getBedienstation(count).getWartezeit()) / (float) (Obj_Dam.getBedienstation(count).getAnzahlKunden());
	else resWarteDam[count] = 0;

   // Auslastungsgrad fuer Bedienstation '1,2,3....'
	if (zeit != 0)
	resAusDam[count]   = (float) (100*Obj_Dam.getBedienstation(count).getBedienzeit(simzeit))/(float)(zeit);
	else resAusDam[count] = 0;

  // Kundenanzahl fuer Bedienstation '1,2,3.....'
	KundDam[count] = Obj_Dam.getBedienstation(count).getAnzahlKunden();

	}

// Berechnung der statistischen Groessen fuer den Herrensalon
	for (count=0; count<Obj_Her.getAnzahl(); count++){

   // mittlere Wartezeit fuer Bedienstation "1,2,3....'
	if (Obj_Her.getBedienstation(count).getAnzahlKunden() != 0)
	resWarteHer[count] = (float) (Obj_Her.getBedienstation(count).getWartezeit())/(float) (Obj_Her.getBedienstation(count).getAnzahlKunden());
	else resWarteHer[count] = 0;

   // Auslastungsgrad fuer Bedienstation '1,2,3....'
	if (zeit != 0)
	resAusHer[count]   = (float) (100*Obj_Her.getBedienstation(count).getBedienzeit(simzeit))/(float)(zeit);
	else resAusHer[count] = 0;

   // Kundenanzahl fuer Bedienstation '1,2,3.....'
	KundHer[count] = Obj_Her.getBedienstation(count).getAnzahlKunden();

	}

// Berechnung der mittleren Wartezeit fuer den Damensalon
	if (Obj_Dam.getAnzahlKunden() != 0)
	resWarteAbtDam = (float)(Obj_Dam.getWartezeit())/(float)(Obj_Dam.getAnzahlKunden());
	else resWarteAbtDam = 0;

// Berechnung der mittleren Wartezeit fuer den Herrensalon
	if (Obj_Her.getAnzahlKunden() != 0)
	resWarteAbtHer = (float)(Obj_Her.getWartezeit())/(float)(Obj_Her.getAnzahlKunden());
	else resWarteAbtHer = 0;

// Berechnung Anzahl der Kunden im Herrensalon;
	AbtHer = Obj_Her.getAnzahlKunden();

// Berechnung Anzahl der Kunden im Damensalon;
	AbtDam = Obj_Dam.getAnzahlKunden();


}

public void paint (int x, int y, Graphics g,boolean beendet,int lauf){
int count;
	if (beendet){
		gesRes_to_Res(lauf);
	}

// maximale Anzahl
	int maxanzahl=0;
	if (Obj_Her.getAnzahl() < Obj_Dam.getAnzahl()){
		maxanzahl=Obj_Dam.getAnzahl();
	} else {
		maxanzahl=Obj_Her.getAnzahl();
	};

// Ausgabe Ueberschriften
	g.drawString("Statistik   :",x,y);

	g.drawString("mittlere Wartezeit/min",x+100,y+15);
	g.drawString("Auslastung/Prozent",x+190+maxanzahl*60,y+15);
	g.drawString("ges.",x+100,y+40);
	g.drawString("Herrensalon :",x,y+70);
	g.drawString("Damensalon  :",x,y+100);

	g.drawString("Anzahl der Kunden", x+100, y+130);
	g.drawString("ges.",x+100,y+155);
	g.drawString("Herrensalon :",x,y+185);
	g.drawString("Damensalon  :",x,y+210);


// Ausgabe der mittleren Wartezeit des Herrensalon
	g.setColor(Color.gray);
	g.fillRect(x+100,y+58,40,15);
	g.setColor(Color.black);
	g.drawString(""+Math.round(100*resWarteAbtHer)/(float)100,x+100,y+70);


// Ausgabe der mittleren Wartezeit des Damensalons
	g.setColor(Color.gray);
	g.fillRect(x+100,y+88,40,15);
	g.setColor(Color.black);
	g.drawString(""+Math.round(100*resWarteAbtDam)/(float)100,x+100,y+100);


// Ausgabe der Kundenanzahl für den Herrensalon
	g.setColor(Color.gray);
	g.fillRect(x+100,y+173,40,15);
	g.setColor(Color.black);
	g.drawString(""+AbtHer,x+100,y+185);


// Ausgabe der Kundenanzahl für den Damensalon
	g.setColor(Color.gray);
	g.fillRect(x+100,y+198,40,15);
	g.setColor(Color.black);
	g.drawString(""+AbtDam,x+100,y+210);


// Ausgabe der Ueberschrift der Bedienstationen
	for (count=0;count<maxanzahl;count++){
		g.drawString("Station "+(count+1),x+140+count*60,y+40);
		g.drawString("Station "+(count+1),maxanzahl*60+x+160+count*60,y+40);

		g.drawString("Station "+(count+1),x+140+count*60,y+155);


	if (count<Obj_Her.getAnzahl()){
		g.setColor(Color.gray);
		g.fillRect(x+145+count*60,y+58,40,15);
		g.setColor(Color.black);
		g.drawString(""+Math.round(100*resWarteHer[count])/(float)100,145+x+count*60,y+70);

		g.setColor(Color.gray);
		g.fillRect(x+maxanzahl*60+165+count*60,y+58,40,15);
		g.setColor(Color.black);
		g.drawString(""+Math.round(100*resAusHer[count])/(float)100,maxanzahl*60+x+165+count*60,y+70);

		g.setColor(Color.gray);
		g.fillRect(x+145+count*60,y+173,40,15);
		g.setColor(Color.black);
		g.drawString(""+KundHer[count],x+145+count*60,y+185);

	}

	if (count<Obj_Dam.getAnzahl()){
	g.setColor(Color.gray);
	g.fillRect(x+145+count*60,y+88,40,15);
	g.setColor(Color.black);
	g.drawString(""+Math.round(100*resWarteDam[count])/(float)100,x+145+count*60,y+100);

	g.setColor(Color.gray);
	g.fillRect(x+165+maxanzahl*60+count*60,y+88,40,15);
	g.setColor(Color.black);
	g.drawString(""+Math.round(100*resAusDam[count])/(float)100,x+165+maxanzahl*60+count*60,y+100);

	g.setColor(Color.gray);
	g.fillRect(x+145+count*60,y+198,40,15);
	g.setColor(Color.black);
	g.drawString(""+KundDam[count],x+145+count*60,y+210);

	}
	}
	}

public void res_to_gesRes(){
int count;

	for (count=0; count<Obj_Her.getAnzahl(); count++){
 		gesResWarteHer[count]	+=	resWarteHer[count];
		gesResAusHer[count]	+=	resAusHer[count];

		gesKundHer[count]		+=	Obj_Her.getBedienstation(count).getAnzahlKunden();
	}

	for (count=0; count<Obj_Dam.getAnzahl(); count++){
 		gesResWarteDam[count]	+=	resWarteDam[count];
		gesResAusDam[count]	+=	resAusDam[count];

		gesKundDam[count]		+=	Obj_Dam.getBedienstation(count).getAnzahlKunden();
	}


 gesResWarteAbtHer	+=	resWarteAbtHer;
 gesResWarteAbtDam	+=	resWarteAbtDam;

gesAbtHer			+=	Obj_Her.getAnzahlKunden();
gesAbtDam			+=	Obj_Dam.getAnzahlKunden();


}

public void gesRes_to_Res(int lauf){
int count;

	for (count=0; count<Obj_Her.getAnzahl(); count++){
 		resWarteHer[count]	=	gesResWarteHer[count]/lauf;
		resAusHer[count]		=	gesResAusHer[count]/lauf;
		KundHer[count]		=	gesKundHer[count];
	}

	for (count=0; count<Obj_Dam.getAnzahl(); count++){
 		resWarteDam[count]	=	gesResWarteDam[count]/lauf;
		resAusDam[count]		=	gesResAusDam[count]/lauf;
		KundDam[count]		=	gesKundDam[count];
	}

 	resWarteAbtHer	=	gesResWarteAbtHer/lauf;
 	resWarteAbtDam	=	gesResWarteAbtDam/lauf;
	AbtHer		=	gesAbtHer;
	AbtDam		=	gesAbtDam;
}

}

import java.awt.*;

/**
  *
  *	Klasse Abteilung
  *
  *	Repräsentiert eine Damen oder Herrenabteilung im Friseursalon
  *	Eine Abteilung enthält mehrere Bedienstationen
  *
  */
class Abteilung{
	static final int FREI = 0;	// Konstante, Abteilung hat Freie Bedienstation
	static final int BESETZT = 1;	// Konstante, Abteilung ist Besetzt

	private int anzahl;		// Anzahl Bedienstationen
	private int anzahlKunden;	// Anzahl der bedienten Kunden in der Abteilung
	private int wartezeit;		// aufsummierte Wartezeiten der Kunden
	private String typ;		// Typ der Abteilung
	private Bedienstation[] Bedienstationen;	// Array der Bedienstationen
	private Kunde einKunde;				// ein Kunde
	private Ereignisliste ereignisse; // die Ereignisliste

	/**
	  *
	  *	Konstruktor der Klasse Abteilung
	  *
	  *	in:	Anzahl Bedienstationen
	  *			Bezeichnung als String
	  *			Die Ereignisliste der Simulation
	  *			Eine Nummer zum Numerieren der Bedienstationen
	  *
	  */
	public Abteilung(int anzahl, String typ,Ereignisliste eineEreignisliste,int nummer){
		this.anzahl = anzahl;
		this.typ = typ;
		anzahlKunden = 0;
		wartezeit = 0;
		ereignisse = eineEreignisliste;
		Bedienstationen = new Bedienstation[anzahl];
		for (int i=0;i<anzahl;i++){
			Bedienstationen[i] = new Bedienstation(nummer);
			nummer++;
		};
	}

	/**
	  *
	  *	Methode reStart
	  *
	  *	Die Methode reStart setzt die Variablen für einen Neustart
	  *	wieder auf den Anfangswert.
	  *
	  */
	public void reStart(){
		anzahlKunden = 0;
		wartezeit = 0;
		for (int i=0;i<anzahl;i++){
			Bedienstationen[i].reStart();
		};
	}

	/**
	  *
	  *	Methode Pausebeginn
	  *
	  *	Diese Methodes schickt die Bedienstationen wenn möglich in die Pause.
	  *	Wenn möglich wird das Pauseende-Ereignis der Bedienstation generiert,
	  *	wenn nicht möglich merkt sich die Bedienstation, daß sie noch in Pause
	  *	zu gehen hat.
	  *
	  *	in:	Typ der Pause:	1: 30min, 2: 60min
	  *			Die Simulationszeit
	  *
	  */
	public void Pausebeginn(int typ,int zeit){
		Ereignis neuEreignis;
		int pauseende;

		for (int i=0;i<anzahl;i++){
			if (Bedienstationen[i].goPause(zeit)){
				if(typ==1){
					pauseende=zeit+30;
				} else {
					pauseende=zeit+60;
				};
				neuEreignis = new Ereignis(Ereignis.PAUSEENDE,Bedienstationen[i].getNumber(),pauseende);
				ereignisse.addEreignis(neuEreignis);
			};
		};

	}

	/**
	  *
	  *	Methode Pauseende
	  *
	  *	Die Methode sucht nach der Bedienstation mit der übergebenen Nummer
	  *	und holt diese aus der Pause;
	  *
	  *	in:	Nummer der Bedienstation
	  *
	  */
	public void Pauseende(int nummer){
		for (int i=0;i<anzahl;i++){
			if (Bedienstationen[i].getNumber()==nummer){
				Bedienstationen[i].goArbeit();
			};
		};
	}

	/**
	  *
	  *	Methode getStatus
	  *
	  *	Die Methode gibt den Status der Abteilung FREI oder BESETZT zurück
	  *
	  *	out:	Integer	0: Frei, 1: Besetzt
	  *
	  */
	public int getStatus(){		// gibt Status der Abteilung zurueck
		int belegt = 0;
		for (int i=0;i<anzahl;i++){
			if ((Bedienstationen[i].getStatus()==Bedienstation.BESETZT) ||
					(Bedienstationen[i].getStatus()==Bedienstation.PAUSE)){
				belegt++;
			};
		};
		if (belegt < anzahl){
			return FREI;
		}
		else {
			return BESETZT;
		}
	}

	/**
	  *
	  *	Methode getKunde
	  *
	  *	Die Methode sucht nach dem Kunden mit der übergebenen Nummer in den
	  *	Bedienstationen und gibt diesen zurück (Bedienende).
	  *
	  *	in:	Nummer des Kunden
	  *			Simulationszeit
	  *			Typ der Pause
	  *
	  *	out:	Kunde falls vorhanden
	  *
	  */
	public Kunde getKunde(int number,int simzeit,int pause){		// beendet die Bedienung eines Kunden
		int pauseende;
		Ereignis neuEreignis;

		einKunde=null;
		for (int i=0;i<anzahl;i++){
			if (Bedienstationen[i].getKundeNumber()==number){
				einKunde=Bedienstationen[i].getKunde(simzeit);
				if (Bedienstationen[i].getStatus()==Bedienstation.PAUSE){
					if(pause==1){
						pauseende=simzeit+30;
					} else {
						pauseende=simzeit+60;
					};
					neuEreignis = new Ereignis(Ereignis.PAUSEENDE,Bedienstationen[i].getNumber(),pauseende);
					ereignisse.addEreignis(neuEreignis);
				};
			};
		};
		return einKunde;
	}

	/**
	  *
	  *	Methode addKunde
	  *
	  *	Die Methode nimmt einen Kunden auf und gibt in an eine freie
	  *	Bedienstation weiter.
	  *
	  *	in:	Kunde
	  *			Simulationszeit
	  *
	  */
	public void addKunde(Kunde neuerKunde, int zeit){	// ein Kunde wird bedient
		Ereignis neuEreignis;
		for (int i=0;i<anzahl;i++){
			if (Bedienstationen[i].getStatus()==Bedienstation.FREI){
				Bedienstationen[i].addKunde(neuerKunde,zeit);
				anzahlKunden++;
				wartezeit+=(zeit-neuerKunde.getAnkunftszeit());
				break;
			};
		};
	}

	/**
	  *
	  *	Methode getWartezeit
	  *
	  *	Die Methode gibt die kumulierten Wartezeiten der Kunden zurueck
	  *
	  *	out:	Wartezeiten der Kunden
	  *
	  */
	public int getWartezeit(){
		return wartezeit;
	}

	/**
	  *
	  *	Methode getAnzahlKunden
	  *
	  *	Die Methode getAnzahlKunden gibt die Anzahl der bedienten Kunden zurueck
	  *
	  *	out:	Anzahl der Kunden
	  *
	  */
	public int getAnzahlKunden(){
		return anzahlKunden;
	}

	/**
	  *
	  *	Methode getAnzahl
	  *
	  *	Die Methode getAnzahl gibt die Anzahl der Bedienstationen in der Abteilung zurueck
	  *
	  *	out:	Anzahl der Bedienstationen
	  *
	  */
	public int getAnzahl(){
		return anzahl;
	}

	/**
	  *
	  *	Methode getBedienstation
	  *
	  *	Die Methode getBedienstation gibt die Bedienstation mit der
	  *	angegebenen Nummer zurueck
	  *
	  *	in:	Nummer der Bedienstation
	  *
	  *	out:	Bedienstation
	  *
	  */
	public Bedienstation getBedienstation(int nummer){
		return Bedienstationen[nummer];
	}

	/**
	  *
	  *	Methode paint
	  *
	  *	Die Methode gibt den Status der Bedienstationen graphisch aus.
	  *
	  *	in:	Graphik-Handler des Applets
	  *			X- und Y- Koordinaten
	  *
	  */
	void paint(Graphics g,int x,int y){
		g.drawString(typ+":",x,y);
		for (int i=0;i<anzahl;i++){
			switch (Bedienstationen[i].getStatus()){
				case Bedienstation.BESETZT : {
					g.setColor(Bedienstationen[i].getKundenFarbe());
					g.fillOval((x*i*3)+10,y+10,10,10);
					g.setColor(Color.black);
				} break;
				case Bedienstation.PAUSE : {
					g.fillOval((x*i*3)+10,y+10,10,10);
				} break;
				default : {
					g.setColor(Color.white);
					g.fillOval((x*i*3)+10,y+10,10,10);
					g.setColor(Color.black);
					g.drawOval((x*i*3)+10,y+10,10,10);
				};
			};
		};
	}
}

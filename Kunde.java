import java.awt.Color;
import java.awt.Graphics;

/**
  *
  *	Klasse Kunde
  *
  *	Die Klasse Kunde repraesentiert einen konkreten Kunden im Simulationssystem.
  *
  */
class Kunde {

	static final int STEHT = 0;	// Konstante, der Kunde steht
	static final int SITZT = 1;	// Konstante, der Kunde sitzt
	static final int MAENNLICH = 0;	// Konstante, der Kunde ist ein Mann
	static final int WEIBLICH = 1;	// Konstante, der Kunde ist eine Frau
	static final int TERMIN = 2;	// Konstante, der Kunde ist eine Frau mit Termin
	static int vMannFrau;
	static int vTermin;

	private int typ;		// Typ (maenlich, weiblich, termin)
	private Color color;		// Farbe des Kunden (je nach Typ)
	private int status;		// Status (sitzt, steht)
	private int nummer;		// Kundenummer
	private int ankunftszeit;	// Ankunftszeit
	private int bedienzeit;		// Bedienzeit
	private int bedienende;		// Bedienende
	private Kunde naechster;	// naechster Kunde in der Warteschlange
	private Kunde vorheriger;	// vorheriger Kunde in der Warteschlange

	/**
	  *
	  *	Konstruktor der Klasse Kunde
	  *
	  *	in:	Nummer des Kunden
	  *		Ankunftszeit des Kunden
	  *
	  */
	public Kunde(int number, int time){
		typ = Zufall.Mann_Frau_Kind(vMannFrau,vTermin);
		nummer = number;
		ankunftszeit = time;
		status = 0;
		color = getColor(typ);
		naechster = null;
		vorheriger = null;
	}

	/**
	  *
	  *	Methode getColor
	  *
	  *	Die Methode getColor gibt die Farbe des Kunden zurueck.
	  *	Die Farbe wird fuer die visuelle Ausgabe verwendet.
	  *
	  *	out:	Farbe des Kunden
	  *
	  */
	Color getColor(){
		return color;			// gibt Kundenfarbe aus
	}

	/**
	  *
	  *	Klassenmethode getColor
	  *
	  *	Die Klassenmethode getColor gibt die Farbe eines Kunden in
	  *	Abhaengigkeit des Kundentyps zurueck.
	  *
	  *	in:	Typ des Kunden
	  *
	  *	out:	Farbe des Kunden
	  *
	  */
	static Color getColor(int Kundetyp){
		Color farbe=Color.black;
		switch (Kundetyp){
			case MAENNLICH	: farbe = Color.blue; break;
			case WEIBLICH		: farbe = Color.orange; break;
			case TERMIN		: farbe = Color.red; break;
		};
		return farbe;			// gibt Kundenfarbe aus
	}

	/**
	  *
	  *	Klassenmethode paintlegende
	  *
	  *	Die Klassenmethode paintlegende gibt die Farblegende der
	  *	Kunden auf der Appletflaeche aus.
	  *
	  *	in:	Graphics-Handler des Applets
	  *		X und Y Koordinaten
	  *
	  */
	static void paintlegende(Graphics g,int x,int y){
		g.setColor(getColor(MAENNLICH));
		g.fillOval(x,y-10,10,10);
		g.setColor(Color.black);
		g.drawString("Mann",x+20,y);
		g.setColor(getColor(WEIBLICH));
		g.fillOval(x+60,y-10,10,10);
		g.setColor(Color.black);
		g.drawString("Frau",x+80,y);
		g.setColor(getColor(TERMIN));
		g.fillOval(x+120,y-10,10,10);
		g.setColor(Color.black);
		g.drawString("Frau mit Termin",x+140,y);
		g.fillOval(x+250,y-10,10,10);
		g.drawString("Pause",x+270,y);
		g.setColor(Color.white);
		g.fillOval(x+320,y-10,10,10);
		g.setColor(Color.black);
		g.drawOval(x+320,y-10,10,10);
		g.drawString("Frei",x+340,y);
	}

	/**
	  *
	  *	Methode getNumber
	  *
	  *	Die Methode getNumber gibt die Nummer des Kunden zurueck.
	  *
	  *	out:	Nummer des Kunden
	  *
	  */
	int getNumber(){		// gibt Kundennummer aus
		return nummer;
	}

	/**
	  *
	  *	Methode getNext
	  *
	  *	Die Methode getNext gibt den Zeiger auf den naechsten Kunden zurueck.
	  *
	  *	out:	Zeiger auf den naechsten Kunden
	  *
	  */
	Kunde getNext(){
		return naechster;
	}

	/**
	  *
	  *	Methode setNext
	  *
	  *	Die Methode setNext setzt den Zeiger auf den naechsten Kunden
	  *
	  *	in:	Zeiger auf einen Kunden
	  *
	  */
	void setNext(Kunde einKunde){
		naechster = einKunde;
	}

	/**
	  *
	  *	Methode getPrev
	  *
	  *	Die Methode getPrev gibt den Zeiger auf den vorherigen Kunden zurueck.
	  *
	  *	out:	Zeiger auf den vorherigen Kunden
	  *
	  */
	Kunde getPrev(){
		return vorheriger;
	}

	/**
	  *
	  *	Methode setPrev
	  *
	  *	Die Methode setPrev setzt den Zeiger auf den vorherigen Kunden
	  *
	  *	in:	Zeiger auf einen Kunden
	  *
	  */
	void setPrev(Kunde einKunde){
		vorheriger = einKunde;
	}

	/**
	  *
	  *	Methode getStatus
	  *
	  *	Die Methode getStatus gibt den Status des Kunden zurueck
	  *
	  *	out:	Status des Kunden
	  *
	  */
	int getStatus(){		// gibt Status
		return status;
	}

	/**
	  *
	  *	Methode setStatus
	  *
	  *	Die Methode setStatus setzt den Status des Kunden
	  *
	  *	in:	Status
	  *
	  */
	void setStatus(int stat){	// setzt Status
		status = stat;
	}

	/**
	  *
	  *	Methode getTyp
	  *
	  *	Die Methode getTyp gibt den Typ des Kunden zurueck
	  *
	  *	out:	Typ des Kunden
	  *
	  */
	int getTyp(){			// gibt Typ
		return typ;
	}

	/**
	  *
	  *	Methode getAnkunftszeit
	  *
	  *	Die Methode getAnkunftszeit gibt die Ankunftszeit des Kunden zurueck
	  *
	  *	out:	Ankunftszeit des Kunden
	  *
	  */
	int getAnkunftszeit(){		// gibt Ankunftszeit
		return ankunftszeit;
	};

	/**
	  *
	  *	Methode setBedienbeginn
	  *
	  *	Die Methode setBedienbeginn setzt die Bedienzeit des Kunden
	  *
	  *	in:	Simulationszeit
	  *
	  */
	void setBedienbegin(int zeit){	// setzt Bedienzeit
		bedienzeit = zeit;
	};

	/**
	  *
	  *	Methode setBedienende
	  *
	  *	Die Methode setBedienende setzt die Bedienendezeit des Kunden
	  *
	  *	in:	Simulationszeit
	  *
	  */
	void setBedienende(int zeit){	// setzt Bedienende
		bedienende = zeit;
	};

	/**
	  *
	  *	Methode getBedienzeit
	  *
	  *	Die Methode getBedienzeit gibt die Bedienzeit des Kunden zurueck
	  *
	  *	out:	Bedienzeit des Kunden
	  *
	  */
	int getBedienzeit(){		// gibt Bedienzeit
		return bedienzeit;
	}

}

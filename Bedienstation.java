import java.awt.Color;

/**
  *
  *	Klasse Bedienstation
  *
  *	Die Klasse Bedienstation stellt im Friseursalon eine(n) konkrete(n) Frieseur(in)
  *	dar, die/der Kunden bedienen kann.
  *
  */
class Bedienstation{				// Klasse Bedienstation
	private int status;			// Status der Bedienstation
	private int anzahlKunden;	// Anzahl der bedienten Kunden
	private int wartezeit;		// aufsummierte Wartezeiten der Kunden
	private int bedienzeit;		// gesamte Bedienzeit
	private Kunde derKunde;		// der Kunde der bedient wird
	private int pausezeit;		// Beginnzeit der Pause
	private int nummer;			// Nummer der Bedienstation
	private boolean pause = false;	// Flag, Pause hat begonnen aber die Bedienstation
													// war noch besetzt, muss also noch pause machen
	static final int FREI = 0;		// Konstante, Bedienstation ist frei
	static final int BESETZT = 1;	// Konstante, Bedienstation ist besetzt
	static final int PAUSE = 2;	// Konstante, Bedienstation mach Pause

	/**
	  *
	  *	Konstruktor der Klasse Bedienstation
	  *
	  *	in:	Nummer der Bedienstation, zur eindeutigen Identifizierung
	  *			im System
	  *
	  */
	public Bedienstation(int number){
		nummer = number;
		reStart();
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
		status = FREI;
		anzahlKunden = 0;
		wartezeit = 0;
		bedienzeit = 0;
		pausezeit = 0;
		derKunde = null;
	}

	/**
	  *
	  *	Methode addKunde
	  *
	  *	Ordnet der Bedienstation einen Kunden zu, der von ihr zu bedienen ist.
	  *
	  *	in:	der zu bedienende Kunde
	  *			die aktuelle Simulationszeit
	  *
	  */
	void addKunde(Kunde einKunde, int zeit){
		derKunde=einKunde;
		derKunde.setBedienbegin(zeit);
		status = BESETZT;
		wartezeit+=(zeit-derKunde.getAnkunftszeit());
		anzahlKunden++;
	}

	/**
	  *
	  *	Methode goPause
	  *
	  *	Falls die Bedienstation frei ist, geht sie sofort in die Pause.
	  *	Falls die Bedienstation besetzt ist, merkt sie sich, daß sie noch in die Pause
	  *	zu gehen hat, sobald der Kunde fertig bedient wurde.
	  *
	  *	in:	Simulationszeit
	  *
	  *	out:	Boolean, ob die Bedienstation sofort in die Pause gegangen ist
	  *
	  */
	boolean goPause(int zeit){
		if (status == FREI){
			status = PAUSE;
			pausezeit = zeit;
			return true;
		} else {
			pause = true;
			return false;
		}
	}

	/**
	  *
	  *	Methode goArbeit
	  *
	  *	Setzt den status der Bedienstation wieder auf Frei, damit sie einen neuen Kunden
	  *	bedienen kann. Die Pause ist zuende.
	  *
	  */
	void goArbeit(){
		status=FREI;
	}

	/**
	  *
	  *	Methode getKunde
	  *
	  *	Die Methode getKunde holt den Kunden, der bedient wird aus der Bedienstation.
	  *	Tritt ein, wenn Bedienende des Kunden simuliert wurde.
	  *	Bedienzeit des Kunden wird gesetzt und die anzahl der bedienten Kunden wird erhöht.
	  *
	  *	in:	Simulationszeit
	  *
	  *	out:	der Kunde der bedient wurde
	  *
	  */
	Kunde getKunde(int zeit){
		Kunde retKunde=null;

		if (derKunde!=null){
			derKunde.setBedienende(zeit);
			status = FREI;
			bedienzeit+=(zeit-derKunde.getBedienzeit());
			retKunde = derKunde;
			derKunde = null;
			if (pause){
				pausezeit=zeit;
				status=PAUSE;
				pause=false;
			};
		};
		return retKunde;
	}

	/**
	  *
	  *	Methode getKundeNumber
	  *
	  *	Die Mehtode getKundeNumber gibt die Kundennummer des aktuell
	  *	in der Bedienstation bedienten Kunden zurück.
	  *
	  *	out:	Kundennummer
	  *
	  */
	int getKundeNumber(){
		if (status == BESETZT){
			return derKunde.getNumber();
		} else {
			return -1;
		}
	}

	/**
	  *
	  *	Methode getNumber
	  *
	  *	Die Methode getNumber gibt die Nummer der Bedienstation zurück.
	  *
	  *	out:	Nummer der Bedienstation
	  *
	  */
	int getNumber(){
		return nummer;
	}


	/**
	  *
	  *	Methode getKundeTyp
	  *
	  *	Die Methode getKundeTyp gibt den Typ des Kunden zurück.
	  *
	  *	out:	Typ des Kunden
	  *
	  */
	int getKundeTyp(){
		if (derKunde != null){
			return derKunde.getTyp();
		} else {
			return -1;
		}
	}

	/**
	  *
	  *	Methode getKundenFarbe
	  *
	  *	Die Methode getKundenFarbe gibt die Farbe des Kunden zurück.
	  *
	  *	out:	Farbe des Kunden
	  *
	  */
	Color getKundenFarbe(){
		if (derKunde != null){
			return derKunde.getColor();
		} else {
			return Color.white;
		}
	}

	/**
	  *
	  *	Methode getStatus
	  *
	  *	Die Methode getStatus gibt den Status der Bedienstation zurück.
	  *
	  *	out:	Status der Bedienstation
	  *
	  */
	int getStatus(){
		return status;
	}

	/**
	  *
	  *	Methode getBedienzeit
	  *
	  *	Die Methode getBedienzeit gibt die kumulierten Bedienzeiten der Kunden zurueck
	  *
	  *	out:	Bedienzeiten der Kunden
	  *
	  */
	public int getBedienzeit(int zeit){
		int retZeit=bedienzeit;

		if (derKunde != null){
			retZeit+=(zeit-derKunde.getBedienzeit());
		}

		return retZeit;
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
	  *	Methode getWartezeit
	  *
	  *	Die Methode getWartezeit gibt die kumulierten Wartezeiten der Kunden zurueck
	  *
	  *	out:	Wartezeiten der Kunden
	  *
	  */
	public int getWartezeit(){
		return wartezeit;
	}

}

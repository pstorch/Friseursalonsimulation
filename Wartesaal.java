import java.awt.*;

/**
  *
  *	Klasse Wartesaal
  *
  *	Die Klasse Wartesaal stellt eine Prioritaetswarteschlange fuer die Simulation dar.
  *	Der Wartesaal besteht aus einer vorgegebenen Anzahl Sitzplaetzen und einer
  *	offenen Anzahl an Stehplaetzen.
  *
  */
class Wartesaal{				// Konstruktor Wartesaal
	private Kunde erster;			// Zeiger auf ersten Kunden
	private Kunde letzter;			// Zeiger auf letzten Kunden
	private Kunde aktueller;		// Zeiger auf aktuellen Kunden
	private Kunde temp;			// Zeiger auf temporaeren kunden
	private int sitzplaetze;		// Anzahl Sitztplaetze
	private int anzahl;			// Anzahl Kunden in der Warteschlange
	private Ereignisliste ereignisse;	// Ereignisliste
	static  int mWarteBereit;		// mittlere Wartebereitschaft

	/**
	  *
	  *	Konstruktor der Klasse Wartesaal
	  *
	  *	in:	Anzahl der Sitzplaetze
	  *		Die Ereignisliste der Simulation
	  *
	  */
	public Wartesaal(int sitze,Ereignisliste eineEreignisliste){
		erster = null;
		letzter = null;
		aktueller = null;
		sitzplaetze = sitze;
		anzahl = 0;
		ereignisse = eineEreignisliste;
	}

	/**
	  *
	  *	Methode addKunde
	  *
	  *	Die Methode addKunde fuegt einen neuen Kunden hinten an die Warteschlange an
	  *
	  *	in:	ein neuer Kunde
	  *
	  */
	void addKunde(Kunde einKunde,int simzeit){
		int expozufall;
		Ereignis einEreignis;
		anzahl++;
		if (erster != null){
			letzter.setNext(einKunde);
			einKunde.setPrev(this.letzter);
			letzter = einKunde;
		}
		else {
			erster = einKunde;
			letzter = einKunde;
		};
		if (anzahl <= sitzplaetze){
			einKunde.setStatus(Kunde.SITZT);
		}
		else {
			einKunde.setStatus(Kunde.STEHT);
			expozufall = (int) Zufall.expozufall((float) mWarteBereit);
			einEreignis = new Ereignis(Ereignis.WEGGEGANGEN,einKunde.getNumber(),simzeit+expozufall);
			ereignisse.addEreignis(einEreignis);
		};
	}

	/**
	  *
	  *	Methode wegGegangen
	  *
	  *	Die Methode wegGegangen loescht einen stehenden Kunden mit der uebergebenen
	  *	Nummer aus der Warteschlange
	  *
	  *	in:	Nummer des zu loeschenden Kunden
	  *
	  */
	Kunde wegGegangen(int nummer){
		if (erster != null){
			for (aktueller=erster;(aktueller != null) &&
											 (aktueller.getNumber() != nummer);aktueller=aktueller.getNext()){
			};
			if (aktueller != null){
				anzahl--;
				if (aktueller.getNext() != null){
					aktueller.getNext().setPrev(aktueller.getPrev());
				};
				if (aktueller.getPrev() != null){
					aktueller.getPrev().setNext(aktueller.getNext());
				};
				if (aktueller == erster){
					erster = aktueller.getNext();
					if (aktueller.getNext() != null){
						aktueller.getNext().setPrev(null);
					};
				};
				if (aktueller == letzter){
					letzter = aktueller.getPrev();
					if (aktueller.getPrev() != null){
						aktueller.getPrev().setNext(null);
					};
				};
				if ((aktueller.getStatus() == Kunde.SITZT) &&
					(anzahl > sitzplaetze)){
					freierSitzplatz();
				} else {
					ereignisse.delEreignis(aktueller.getNumber(),Ereignis.WEGGEGANGEN);
				};
			};
		}
		else
		{
			aktueller = erster;
		};
		return aktueller;
	}

	/**
	  *
	  *	Methode getKunde
	  *
	  *	Die Methode getKunde holt den naechsten Kunden des uebergebenen
	  *	Typs aus der Warteschlange.
	  *
	  *	in:	Typ des Kunden
	  *
	  *	out:	Falls vorhanden Zeiger auf den Kunden
	  *		Falls nicht vorhanden NULL-Zeiger
	  *
	  */
	Kunde getKunde(int typ){
		if (erster != null){
			for (aktueller=erster;(aktueller != null) &&
											 (aktueller.getTyp() != typ);aktueller=aktueller.getNext()){
			};
			if (aktueller != null){
				anzahl--;
				if (aktueller.getNext() != null){
					aktueller.getNext().setPrev(aktueller.getPrev());
				};
				if (aktueller.getPrev() != null){
					aktueller.getPrev().setNext(aktueller.getNext());
				};
				if (aktueller == erster){
					erster = aktueller.getNext();
					if (aktueller.getNext() != null){
						aktueller.getNext().setPrev(null);
					};
				};
				if (aktueller == letzter){
					letzter = aktueller.getPrev();
					if (aktueller.getPrev() != null){
						aktueller.getPrev().setNext(null);
					};
				};
				if (aktueller.getStatus() == Kunde.SITZT){
					if (anzahl >= sitzplaetze){
						freierSitzplatz();
					};
				} else {
					ereignisse.delEreignis(aktueller.getNumber(),Ereignis.WEGGEGANGEN);
				};
			};
		}
		else
		{
			aktueller = erster;
		};
		return aktueller;
	}

	/**
	  *
	  *	Methode freierSitzplatz
	  *
	  *	Die Methode freierSitzplatz sucht den ersten stehenden Kunden in der Warteschlange
	  *	und setzt dessen Status auf sitzt.
	  *
	  */
	private void freierSitzplatz(){
		for (temp=erster;(temp != null) && (temp.getStatus() != Kunde.STEHT);temp=temp.getNext()){
		};
		temp.setStatus(Kunde.SITZT);
		ereignisse.delEreignis(temp.getNumber(),Ereignis.WEGGEGANGEN);
	}

	/**
	  *
	  *	Methode paint
	  *
	  *	Die Methode paint stellt die Belegung des Wartsaals auf der Zeichenflaeche
	  *	des Applets visuell dar.
	  *
	  *	in:	Graphic-Handler des Applets
	  *		X und Y Koordinaten
	  *
	  */
	void paint(Graphics g,int x,int y){
		int i;
		g.setColor(Color.lightGray);
		g.fillRect(x,y-10,300,13);
		g.setColor(Color.black);
		if (anzahl>sitzplaetze){
				if ((anzahl-sitzplaetze)==1){
					g.drawString("Wartesaal: (1 Kunde muss stehen)",x,y);
				} else {
					g.drawString("Wartesaal: ("+(anzahl-sitzplaetze)+" Kunden müssen stehen)",x,y);
				};
		} else {
			g.drawString("Wartesaal: ",x,y);
		};
		i = 0;
		aktueller = erster;
		while ((aktueller != null) && (i<sitzplaetze)){
			g.setColor(aktueller.getColor());
			g.fillOval((x*i*3)+10,y+10,10,10);
			g.setColor(Color.black);
			i++;
			aktueller=aktueller.getNext();
		};
		while (i<sitzplaetze){
			g.setColor(Color.white);
			g.fillOval((x*i*3)+10,y+10,10,10);
			g.setColor(Color.black);
			g.drawOval((x*i*3)+10,y+10,10,10);
			i++;
		};
	}
}

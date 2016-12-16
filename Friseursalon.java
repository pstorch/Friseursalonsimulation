import java.awt.*;

/**
  *
  *	Friseursalon Applet
  *
  *	Simulation eines Friseursalons
  *
  */
public class Friseursalon extends java.applet.Applet implements Runnable{
	// Variablen deklaration
	Wartesaal einWartesaal;		// Wartesaal
	Abteilung herrenabteilung;	// Herrenabteilung
	Abteilung damenabteilung;	// Damenabteilung
	Statistik simStatistik;
	Ereignisliste ereignisse;	// Ereignisliste
	Ereignis aktEreignis;		// aktuelles Ereignis
	Kunde einKunde;			// ein Kunde
	int simzeit;			// Simulationszeit
	int anzahlKunden;		// Anzahl Kunden
	String param;		// Eingabeparameter
	boolean drawfirst;	// Erste Ausgabe
	int pause=0;		// 0: keine Pause, 1: 30min Pause, 2: 1h Pause
	int beginnpause;		// Beginnzeit der Pause
	boolean m_in_dabt;	// Männer werden in Damenabteilung bedient
	int simbeginn;		// Beginn der Simulation
	int simende;		// Ende der Simulation
	int sitze;			// sitze im Wartesaal
	int damenfr;		// Damenfriseure
	int herrenfr;		// Herrenfriseure
	int mAnkunft;		// mittlere Ankunftszeit
	int mBedienzF;		// mittlere Bedienzeit für Frauen
	int mBedienzM;		// mittlere Bedienzeit für Männer
	int mWarteBereit;		// mittlere Wartebereitschaft
	int vMannFrau;		// Verhältnis Männer/Frauen
	int vTermin;		// Verhältnis der Frauen mit Termin
	int expozufall;		// Exponentialverteilte Zufallsgröße
	int verzoegerung;		// Verzögerung der Simulation
	int durchlauf;		// Anzahl Durchläufe
	int lauf;				// Aktueller Durchlauf
	int kumutime;		// Kumulierte Simulationsendzeiten
	boolean userPause = false;	// Pause durch Benutzer
	boolean firststart = true; 	// Erster Start
	boolean beendet;			// Thread hat Simulation beendet
	boolean geschlossen;		// Friseursalon ist zu

	Thread simengine = null;	// Thread

	Button startbutton;			// Button1 = Start
	CheckboxGroup options;			// Radio Buttons
	Checkbox 	mittagzubox,		// Mittags 1 Stunde zu
			pausebox,			// 30 min Pause
			m_in_dabt_box,		// Maenner in Damenabteilung
			graphics,			// graphische ausgabe
			nopausebox;			// Checkboxen
	IntField	verzoeg_feld,		// Eingabefeld für Verzögerung
			durchlauf_feld;		// Anzahl der Simulationslaeufe
	Label		verzoeg_label,
			durchlauf_label;


	/**
	  *
	  *	Initialisierungsroutine
	  *
	  *	liest die Parameter aus der HTML-Datei aus und setzt
	  *	die entsprechenden Variablen
	  *
	  */
	public void init(){

		resize(650,600);	// Größe des Applets festlegen

		// Parameter einlesen und setzen

		param = getParameter("beginn");
		if (param != null){
			simbeginn = Time.Time_to_Minute(param);
		} else {
			simbeginn = Time.Time_to_Minute("08.00");
		};

		param = getParameter("ende");
		if (param != null){
			simende = Time.Time_to_Minute(param);
		} else {
			simende = Time.Time_to_Minute("18.00");
		};

		param = getParameter("beginnpause");
		if (param != null){
			beginnpause = Time.Time_to_Minute(param);
		} else {
			beginnpause = Time.Time_to_Minute("12.00");
		};

		param = getParameter("sitze");
		if (param != null){
			sitze = Integer.parseInt(param);
		} else {
			sitze = 12;
		};

		param = getParameter("damenfr");
		if (param != null){
			damenfr = Integer.parseInt(param);
		} else {
			damenfr = 3;
		};

		param = getParameter("herrenfr");
		if (param != null){
			herrenfr = Integer.parseInt(param);
		} else {
			herrenfr = 2;
		};

		param = getParameter("mAnkunft");
		if (param != null){
			mAnkunft = Integer.parseInt(param);
		} else {
			mAnkunft = 20;
		};

		param = getParameter("mBedienzF");
		if (param != null){
			mBedienzF = Integer.parseInt(param);
		} else {
			mBedienzF = 20;
		};

		param = getParameter("mBedienzM");
		if (param != null){
			mBedienzM = Integer.parseInt(param);
		} else {
			mBedienzM = 20;
		};

		param = getParameter("mWarteBereit");
		if (param != null){
			mWarteBereit = Integer.parseInt(param);
		} else {
			mWarteBereit = 20;
		};

		param = getParameter("vMannFrau");
		if (param != null){
			vMannFrau = Integer.parseInt(param);
		} else {
			vMannFrau = 20;
		};

		param = getParameter("vTermin");
		if (param != null){
			vTermin = Integer.parseInt(param);
		} else {
			vTermin = 70;
		};


		// GUI (Graphical User Interface) wird erstellt
		setBackground(Color.lightGray);
		setLayout(null);

		options = new CheckboxGroup();

		mittagzubox = new Checkbox(" mittags zu",options,false);		// Mittag ist zu Checkbox
		mittagzubox.reshape(10,230,100,20);
		add(mittagzubox);

		pausebox = new Checkbox(" 30 min Pause",options,false);		// 30 min Pause Checkbox
		pausebox.reshape(10,250,120,20);
		add(pausebox);

		nopausebox = new Checkbox(" keine Pause",options,true);			// keine Pause Checkbox
		nopausebox.reshape(10,270,110,20);
		add(nopausebox);

		m_in_dabt_box = new Checkbox(" Herren in Damenabteilung");		// Männer in Damenabteilung Checkbox
		m_in_dabt_box.reshape(10,290,210,25);
		add(m_in_dabt_box);

		graphics = new Checkbox(" Visualisierung");		// keine graphische Ausgabe Checkbox
		graphics.reshape(10,315,160,25);
		graphics.setState(true);
		add(graphics);

		startbutton = new Button("Start");		// Startbutton
		startbutton.reshape(250,230,100,25);
		add(startbutton);

		verzoeg_label = new Label("Verzögerung:"); 	// Label Verzoegerung
		verzoeg_label.reshape(240,270,110,25);
		add(verzoeg_label);

		verzoeg_feld = new IntField(4);			// Eingabefeld für Verzögerung
		verzoeg_feld.setText("500");
		verzoeg_feld.reshape(355,270,50,30);
		add(verzoeg_feld);

		durchlauf_feld = new IntField(4);			// Eingabefeld für Anzahl Durchlaeufe
		durchlauf_feld.setText("1");
		durchlauf_feld.reshape(355,310,50,30);
		add(durchlauf_feld);

		verzoeg_label = new Label("Durchläufe:");		// Label Anzahl Durchlaeufe
		verzoeg_label.reshape(240,310,110,25);
		add(verzoeg_label);

		super.init();
	}


	/**
	  *
	  *	Startroutine
	  *
	  *	Startvariablen werden gesetzt
	  *	Objekte werden erzeugt
	  *	Thread wird gestartet
	  *	Die ersten Ereignisse werden in die Ereignisliste gestellt
	  *
	  */
   public void start() {
		if (simengine == null) {
			// weitere Parameter setzen
			kumutime = 0;
			drawfirst = true;
			Wartesaal.mWarteBereit = mWarteBereit;
			Kunde.vMannFrau = vMannFrau;
			Kunde.vTermin = vTermin;

			if (mittagzubox.getState()){
				pause = 2;
			};
			if (pausebox.getState()){
				pause = 1;
			};
			if (nopausebox.getState()){
				pause = 0;
			};
			m_in_dabt = m_in_dabt_box.getState();
			durchlauf=Integer.parseInt(durchlauf_feld.getText());

			// Objekte erzeugen
			ereignisse = new Ereignisliste();
			einWartesaal = new Wartesaal(sitze,ereignisse);
			herrenabteilung = new Abteilung(herrenfr,"Herrenabteilung",ereignisse,1);
			damenabteilung = new Abteilung(damenfr,"Damenabteilung",ereignisse,herrenfr+1);
			simStatistik = new Statistik(damenabteilung,herrenabteilung);


		  	simengine = new Thread(this);
			simengine.start();
			if (firststart){
		   	simengine.suspend();
				firststart = false;
			};
		}
		showStatus(getAppletInfo());
   }


	/**
	  *
	  *	Stoproutine
	  *
	  *	Der Thread (Die Simulation) wird angehalten
	  *
	  */
   public void stop() {
		if (simengine != null && simengine.isAlive()) {
	    simengine.stop();
		}
		simengine = null;
   }


	/**
	  *
	  *	Ereignisprozedur Ankunft eines Kunden
	  *
	  */
	public void Ankunft(){
		Ereignis neuEreignis;

		if (!geschlossen){
			einKunde = new Kunde(aktEreignis.getnumber(),simzeit);	// ein neuer Kunde wird erzeugt
			switch(einKunde.getTyp()){
				case Kunde.MAENNLICH : {						// wenn Kunde männlich
					if (herrenabteilung.getStatus() == Abteilung.FREI){
						herrenabteilung.addKunde(einKunde,simzeit);
						expozufall=(int) Zufall.expozufall((float) mBedienzM);
						neuEreignis = new Ereignis(Ereignis.BEDIENENDE,einKunde.getNumber(),simzeit+expozufall);
						ereignisse.addEreignis(neuEreignis);
					} else {
						if (m_in_dabt && damenabteilung.getStatus() == Abteilung.FREI){
							damenabteilung.addKunde(einKunde,simzeit);
							expozufall=(int) Zufall.expozufall((float) mBedienzM);
							neuEreignis = new Ereignis(Ereignis.BEDIENENDE,einKunde.getNumber(),simzeit+expozufall);
							ereignisse.addEreignis(neuEreignis);
						} else {
						einWartesaal.addKunde(einKunde,simzeit);
						};
					};}break;
				case Kunde.WEIBLICH : {							// wenn Kunde weiblich
					if (damenabteilung.getStatus() == Abteilung.FREI) {
						damenabteilung.addKunde(einKunde,simzeit);
						expozufall=(int) Zufall.expozufall((float) mBedienzF);
						neuEreignis = new Ereignis(Ereignis.BEDIENENDE,einKunde.getNumber(),simzeit+expozufall);
						ereignisse.addEreignis(neuEreignis);
					}
					else {
						einWartesaal.addKunde(einKunde,simzeit);
					};}break;
				case Kunde.TERMIN : {								// wenn Kunde weiblich mit Termin
					if (damenabteilung.getStatus() == Abteilung.FREI) {
						damenabteilung.addKunde(einKunde,simzeit);
						expozufall=(int) Zufall.expozufall((float) mBedienzF);
						neuEreignis = new Ereignis(Ereignis.BEDIENENDE,einKunde.getNumber(),simzeit+expozufall);
						ereignisse.addEreignis(neuEreignis);
					}
					else {
						einWartesaal.addKunde(einKunde,simzeit);
					};}break;
			};

			// Das Ereignis Ankunft des nächsten Kunden wird erzeugt und in die Liste gestellt
			expozufall=(int) Zufall.expozufall((float) mAnkunft);
			neuEreignis = new Ereignis(Ereignis.ANKUNFT,++anzahlKunden,simzeit+expozufall);
			ereignisse.addEreignis(neuEreignis);
		};
	}

	/**
	  *
	  *	Ereignisprozedur Bedienende eines Kunden
	  *
	  */
	public void Bedienende(){
		Ereignis neuEreignis;
		einKunde=damenabteilung.getKunde(aktEreignis.getnumber(),simzeit,pause);
		if (einKunde == null){
			einKunde=herrenabteilung.getKunde(aktEreignis.getnumber(),simzeit,pause);
			if (herrenabteilung.getStatus()==Abteilung.FREI){
				einKunde=einWartesaal.getKunde(Kunde.MAENNLICH);
				if (einKunde!=null){
					herrenabteilung.addKunde(einKunde,simzeit);
					expozufall=(int) Zufall.expozufall((float) mBedienzM);
					neuEreignis = new Ereignis(Ereignis.BEDIENENDE,einKunde.getNumber(),simzeit+expozufall);
					ereignisse.addEreignis(neuEreignis);
				};
			};
		} else {
			if (damenabteilung.getStatus()==Abteilung.FREI){
				einKunde=einWartesaal.getKunde(Kunde.TERMIN);
				if (einKunde==null){
					einKunde=einWartesaal.getKunde(Kunde.WEIBLICH);
					if (einKunde!=null){
						damenabteilung.addKunde(einKunde,simzeit);
						expozufall=(int) Zufall.expozufall((float) mBedienzF);
						neuEreignis = new Ereignis(Ereignis.BEDIENENDE,einKunde.getNumber(),simzeit+expozufall);
						ereignisse.addEreignis(neuEreignis);
					} else {
						if (m_in_dabt){
							einKunde=einWartesaal.getKunde(Kunde.MAENNLICH);
							if (einKunde!=null){
								damenabteilung.addKunde(einKunde,simzeit);
								expozufall=(int) Zufall.expozufall((float) mBedienzF);
								neuEreignis = new Ereignis(Ereignis.BEDIENENDE,einKunde.getNumber(),simzeit+expozufall);
								ereignisse.addEreignis(neuEreignis);
							};
						};
					};

				} else {
					damenabteilung.addKunde(einKunde,simzeit);
					expozufall=(int) Zufall.expozufall((float) mBedienzF);
					neuEreignis = new Ereignis(Ereignis.BEDIENENDE,einKunde.getNumber(),simzeit+expozufall);
					ereignisse.addEreignis(neuEreignis);
				};
			};
		};

	}

	/**
	  *
	  *	Ereignisprozedur Wartezeitende (Die Wartebereitschaft des Kunden)
	  *
	  */
	public void Wartezeitende(){
		einWartesaal.wegGegangen(aktEreignis.getnumber());
	}


	/**
	  *
	  *	Ereignisprozedur Pausebeginn
	  *
	  */
	public void Pausebeginn(){
		damenabteilung.Pausebeginn(pause,simzeit);
		herrenabteilung.Pausebeginn(pause,simzeit);
	}

	/**
	  *
	  *	Ereignisprozedur Pauseende
	  *
	  */
	public void Pauseende(){
		Ereignis neuEreignis;

		herrenabteilung.Pauseende(aktEreignis.getnumber());
		damenabteilung.Pauseende(aktEreignis.getnumber());
		if (herrenabteilung.getStatus()==Abteilung.FREI){
			einKunde=einWartesaal.getKunde(Kunde.MAENNLICH);
			if (einKunde!=null){
				herrenabteilung.addKunde(einKunde,simzeit);
				expozufall=(int) Zufall.expozufall((float) mBedienzM);
				neuEreignis = new Ereignis(Ereignis.BEDIENENDE,einKunde.getNumber(),simzeit+expozufall);
				ereignisse.addEreignis(neuEreignis);
			};
		};
		if (damenabteilung.getStatus()==Abteilung.FREI){
			einKunde=einWartesaal.getKunde(Kunde.TERMIN);
			if (einKunde==null){
				einKunde=einWartesaal.getKunde(Kunde.WEIBLICH);
				if (einKunde!=null){
					damenabteilung.addKunde(einKunde,simzeit);
					expozufall=(int) Zufall.expozufall((float) mBedienzF);
					neuEreignis = new Ereignis(Ereignis.BEDIENENDE,einKunde.getNumber(),simzeit+expozufall);
					ereignisse.addEreignis(neuEreignis);
				} else {
					if (m_in_dabt){
						einKunde=einWartesaal.getKunde(Kunde.MAENNLICH);
						if (einKunde!=null){
							damenabteilung.addKunde(einKunde,simzeit);
							expozufall=(int) Zufall.expozufall((float) mBedienzF);
							neuEreignis = new Ereignis(Ereignis.BEDIENENDE,einKunde.getNumber(),simzeit+expozufall);
							ereignisse.addEreignis(neuEreignis);
						};
					};
				};
			} else {
				damenabteilung.addKunde(einKunde,simzeit);
				expozufall=(int) Zufall.expozufall((float) mBedienzF);
				neuEreignis = new Ereignis(Ereignis.BEDIENENDE,einKunde.getNumber(),simzeit+expozufall);
				ereignisse.addEreignis(neuEreignis);
			};
		};
	}

	/**
	  *
	  *	Ereignisprozedur Simende
	  *
	  */
	public void Simende(){
		while (einWartesaal.getKunde(Kunde.MAENNLICH)!=null);
		while (einWartesaal.getKunde(Kunde.WEIBLICH)!=null);
		while (einWartesaal.getKunde(Kunde.TERMIN)!=null);
		geschlossen=true;
	};

	/**
	  *
	  *	Threadprozedur, Hauptroutine der Simulation
	  *
	  */
	public void run(){
		for (lauf=0;lauf<durchlauf;lauf++){
			damenabteilung.reStart();
			herrenabteilung.reStart();
			showStatus("Simulationslauf: "+(lauf+1));
			simzeit=simbeginn;
			anzahlKunden=0;
			beendet = false;
			geschlossen = false;

			// Die ersten Ereignisse generieren
			expozufall=(int) Zufall.expozufall((float) mAnkunft);
			aktEreignis = new Ereignis(Ereignis.ANKUNFT,++anzahlKunden,simzeit+expozufall); // Ankunft des ersten Kunden
			ereignisse.addEreignis(aktEreignis);

			aktEreignis = new Ereignis(Ereignis.SIMENDE,1,simende);				// Simulationsende
			ereignisse.addEreignis(aktEreignis);

			if (pause == 1 || pause == 2){
				aktEreignis = new Ereignis(Ereignis.PAUSEANFANG,pause,beginnpause);
				ereignisse.addEreignis(aktEreignis);
			};

			aktEreignis = ereignisse.getEreignis();		// erstes Ereignis holen
			while (aktEreignis!=null){					// Hole solange Ereignisse aus der Liste bis Ereignis = Simende

				simzeit=aktEreignis.gettime();					// Simulationszeit setzen
				switch(aktEreignis.getTyp()){						// Ereignisroutinen aufrufen
					case Ereignis.ANKUNFT     : Ankunft()       ;break;
					case Ereignis.BEDIENENDE  : Bedienende()    ;break;
					case Ereignis.WEGGEGANGEN : Wartezeitende() ;break;
					case Ereignis.PAUSEANFANG : Pausebeginn()   ;break;
					case Ereignis.PAUSEENDE   : Pauseende()	  ;break;
					case Ereignis.SIMENDE	  : Simende()       ;break;
				};
				if (graphics.getState()){
					repaint();
					verzoegerung=Integer.parseInt(verzoeg_feld.getText());
					try {
						simengine.sleep(verzoegerung);			// Thread legt sich schlafen
					}
					catch (InterruptedException e) {
						System.out.println("interrupted");
					}
				};
			aktEreignis = ereignisse.getEreignis();		// nächstes Ereignis holen
			};
			simStatistik.calculation(simzeit,simbeginn);
			simStatistik.res_to_gesRes();
			kumutime+=simzeit;
		};
		beendet = true;

		repaint();
		for(;;){		// Thread wartet in Endlosschleife
			try {				// Thread legt sich schlafen
				simengine.sleep(500);
			}
			catch (InterruptedException e) {
				System.out.println("interrupted");
			}
		}

	}

	/**
	  *
	  *	Updateroutine
	  *
	  *	vor dem Neuzeichnen der Ausgabe werden gezielt
	  *	einige Bereiche gelöscht.
	  *
	  */
	public void update(Graphics g){
		paint(g);
	};

	/**
	  *
	  *	Ausgaberoutine der Simulation, gibt den Status der Objekte aus.
	  *
	  */
	public synchronized void paint(Graphics g){
		if ((graphics.getState())||(beendet)){
			g.drawString("Simzeit:",10,12);
			g.setColor(Color.gray);
			g.fillRect(63,0,66,15);
			g.setColor(Color.black);
			if (beendet){
				g.drawString(Time.Minute_to_Time(kumutime/lauf),65,12);
			} else {
				g.drawString(Time.Minute_to_Time(simzeit),65,12);
			};
			if (aktEreignis!=null){
	 		       	aktEreignis.Ereignisstring(g,140,2);
			};
			Kunde.paintlegende(g,10,30);
			einWartesaal.paint(g,10,50);
			herrenabteilung.paint(g,10,90);
			damenabteilung.paint(g,10,130);
			ereignisse.paint(g,10,200);
			simStatistik.calculation(simzeit,simbeginn);
			simStatistik.paint(10,355,g,beendet,lauf);
		}

	}

	/**
	  *
	  *	handleEventroutine,
	  *
	  *	Fängt die Programmereignisse, wie Maus- und Tastaturereignisse ab.
	  *	Stoppt und startet auf Mausdruck den Thread
	  *	Zeigt das Appletinfo an, wenn sich der Mauszeiger
	  *	über dem Applet befindet
	  *
	  */
	public boolean handleEvent(Event evt) {
		switch (evt.id) {
		case Event.MOUSE_DOWN:		// Die Maus wurde gedrückt
			if (simengine != null && simengine.isAlive()) {
				if (userPause) {
					simengine.resume();
				} else {
			   	simengine.suspend();
				}
		    	userPause = !userPause;
			}
	  		return true;
		case Event.MOUSE_UP:
	   	return true;
		case Event.MOUSE_ENTER:
	   	showStatus(getAppletInfo());
	   	return true;
		case Event.MOUSE_EXIT:
	  		showStatus("");
	   	return true;
		case Event.KEY_ACTION:
		case Event.KEY_RELEASE:
		case Event.KEY_ACTION_RELEASE:
	   	return true;
		default:
	   	return super.handleEvent(evt);
		}
	}

	/**
	  *
	  *	actionroutine
	  *
	  *	Fängt Programmaktionen wie betätigen eines Schalters ab.
	  *	Wird der Startbutton gedrückt, wird der Thread neu gestartet.
	  *
	  */
	public boolean action (Event evt, Object obj){
		if (evt.target instanceof Button){
			if ("Start".equals(obj)){
				if (simengine != null && simengine.isAlive()) {
					userPause = false;
					stop();
					start();
				} else {
					userPause = false;
					start();
				}
			};
		};
		return true;
	}

	/**
	  *	getAppletInforoutine
	  *
	  *	gibt Information über das Applet zurück.
	  *
	  */
	public String getAppletInfo() {
		return "Friseursalonsimulation FH-Fulda SS97";
	}

	/**
	*	Methode getParameterinfo.
	*
	*	Die Methode gibt eine Beschreibung der Parameter in der
	*	HTML-Datei aus.
	*
	*/
	public String[][] getParameterInfo() {
		String[][] info = {
		    {"beginnpause", "int", "Uhrzeit für den Pausenbeginn"},
		    {"sitze",  "int", "Anzahl der Sitze im Wartesalon"},
		    {"damenfr", "int", "Anzahl der Damenfriseure"},
		    {"herrenfr", "int", "Anzahl der Herrenfriseure"},
		    {"mAnkunft", "int", "mittlere Ankunftszeit der Kunden in Minuten"},
		    {"mBedienzF", "int", "mittlere Bedienzeit der Frauen"},
		    {"mBedienzM", "int", "mittlere Bedienzeit der Männer"},
		    {"mWarteBereit", "int", "mittlere Wartebereitschaft der stehenden Kunden"},
		    {"vMannFrau", "int", "Verhältnis der Männer an den Kunden"},
		    {"vTermin", "int", "Verhältnis der Frauen mit Termin an den weiblichen Kunden"},
		    {"beginn", "String", "Beginnuhrzeit des Friseursalons. Format: 08.00"},
		    {"ende", "String", "Endeuhrzeit des Friseursalons. Format: 18.00"},
		};
		return info;
	    }

}

import java.awt.*;
import java.lang.*;

class Ereignisliste{
	private Ereignis erstes;                         //Festlegen von lokalen Variablen
	private Ereignis letztes;
	private Ereignis aktuelles;
	private int anzahl;
        private boolean einsortiert;


/* Konstruktor der Klasse mit
 * Wertzuweisung der lokalen Variablen
 */

  public Ereignisliste(){
	 anzahl = 0;
	 erstes = null;
	 letztes = null;
	 aktuelles = null;

  }


/* Einfuegen eines Ereignisses in die Ereignisliste
 *
 * 	in          einEreignis    das Ereignis, das in die Ereignisliste
 *                               eingefuegt werden soll
 *
 * setzen des Zeigers aktuelles auf das erste Element
 * setzen der booleschen Variable einsortiert auf false bzw. true
 * Das Ereignis wird als erstes Ereignis in die Ereignisliste einsortiert, wenn
 * noch keine Ereignisse in der Ereignisliste vorhanden sind,
 * sind bereits Ereignisse in der Ereignisliste vorhanden, werrden die Ereignisse
 * zeitlich aufsteigend in die Ereignisliste einsortiert,
 * bei Zeitgleichheit von Ereignissen wird nach Prioritaet aufsteigend sortiert
 */
  void addEreignis (Ereignis einEreignis){
    anzahl++;                                     //Anzahl der Ereignisse in der E-Liste insgesamt
    if (erstes != null){ 			  //Check ob schon ein E. vorhanden ist.
      aktuelles = erstes;                         //1.Ereignis in der E-Liste
      einsortiert = false;			  //Variable zur Kennung ob einsortiert wurde
      while ((aktuelles != null) && (einsortiert==false)){
          if (aktuelles.gettime() > einEreignis.gettime()){
          einEreignis.setPrev(aktuelles.getPrev());
          einEreignis.setNext(aktuelles);
          if (aktuelles.getPrev() != null)
             {aktuelles.getPrev().setNext(einEreignis);}
          else{
            erstes = einEreignis;             //das Ereignis als erstes Ereignis einsortieren
          };
          aktuelles.setPrev(einEreignis);
          einsortiert = true;                 //Variable zur Kennung ob einsortiert wurde

        };

        if (aktuelles.gettime() == einEreignis.gettime()){                  //Bei 2 Ereignissen zur gleichen Zeit
          if (aktuelles.getprioritaet() <= einEreignis.getprioritaet()){    //Sortierkriterium ist die Priorität
            einEreignis.setPrev(aktuelles.getPrev());
            einEreignis.setNext(aktuelles);
            if (aktuelles.getPrev() != null)
               {aktuelles.getPrev().setNext(einEreignis);}
            else{                                             /*wenn das Ereignis das 1. in der
                                                                E.-Liste wird*/
              erstes = einEreignis;
            };
            aktuelles.setPrev(einEreignis);
            einsortiert = true;                               /*Variable zur Kennzeichnung ob
                                                                einsortiert wurde*/
          };
        };
        aktuelles=aktuelles.getNext();                        //weiter zum nächsten Ereignis
      };
      if (einsortiert == false){                              /*Das Ereignis als letztes Ereignis
                                                                an die E.-Liste anhängen*/
        einEreignis.setPrev(letztes);
        letztes.setNext(einEreignis);
        letztes = einEreignis;
      };
    }
  else{
    erstes = einEreignis;                                     /*Das Ereignis ist sowohl das
                                                                erste als auch das letzte
                                                                Ereignis in der E.-Liste*/
    letztes = einEreignis;
  };
  }

  Ereignis getEreignis(){                                     /*Das erste Ereignis aus der
 										    E.-Liste herausnehmen*/
    aktuelles = erstes;
    if (erstes !=  null) {
      if (erstes.getNext() != null) {
        erstes.getNext().setPrev(erstes.getPrev());
        erstes = erstes.getNext();
      }
    else {
      erstes = null;                                          //wenn es das einzige Ereign. ist
      letztes = null;
    };
    };
    return aktuelles;
  }



/* ein bestimmtes Ereignis aus der Ereignisliste auswaehlen und herausloeschen
 *
 * 	in	nummer es Ereignisses das herauszuloeschen ist
 *          typ des Ereignisses das herauszuloeschen ist
 */

  void delEreignis(int number, int typ){
    aktuelles = erstes;
    if ((aktuelles.getnumber() == number) && (aktuelles.getTyp() == typ)){
      erstes.setNext(aktuelles.getNext());                   //erstes Ereign. herauslöschen
      aktuelles.getNext().setPrev(erstes.getPrev());

    }
    else{
      aktuelles=aktuelles.getNext();
      };
                                                         /*eines zwischen dem 2. und dem vorletzten
                                                           Ereignissen herauslöschen*/
      if (aktuelles != erstes){
            while (aktuelles.getNext() != null){
        if ((aktuelles.getnumber() == number) && (aktuelles.getTyp() == typ)){
	    aktuelles.getNext().setPrev(aktuelles.getPrev());
	    aktuelles.getPrev().setNext(aktuelles.getNext());
        }
        aktuelles = aktuelles.getNext();                  //weiter zum nächsten Ereignis
      }
    }
    if ((aktuelles.getNext() == null) && (aktuelles.getnumber() == number) && (aktuelles.getTyp() == typ)){;
      aktuelles.getPrev().setNext(aktuelles.getNext());
      letztes = aktuelles.getPrev();                      //letztes Ereignis aus der E.-Liste löschen
      letztes.setPrev(aktuelles.getPrev());
    }
  }


/* grafische Anzeige der Ereignisse in
 * der E.-Liste
 * 	in Das Grafikobjekt g
 *       Die X-Koordinate zur Darstellung der Ereignisliste
 *       Die Y-Koordinate zur Darstellung der Ereignisliste
 *
 *    Variablen   i Schleifenvariable um Position der Balken
 *                  für die Ereignisliste zu bestimmen
 *
 * es wird ein hellgraues Rechteck gezeichnet in dem
 * der Farbbalken für das Ereignis ausgegeben und
 * aktualisiert wird.
 * Der String Ereignisliste wird ausgegeben.
 * Die Legende (Ereignisname und Farbbalken) wird
 * erzeugt und ausgegeben.
 * Der Rahmen um die Ereignisliste wird gezeichnet.
 */


  void paint(Graphics g,int x,int y){                         int typ;
    int i=0;

    aktuelles = erstes;
    i=1;
    g.setColor(Color.lightGray);                          //hellgraues Rechteck zeichnen um Updaten zu können
    g.fillRect(x+20,y+5,515,12);
    g.setColor(Color.black);
    g.drawString("Ereignisliste: ",x,y-20);               //Ausgabe des Strings "Ereignisliste"
    g.setColor(Color.blue);                               //Legende der Ereignisliste
    g.fillRect((x*11),y-30,20,10);                        //farbige Darstellung der Ereignisse
    g.setColor(Color.black);
    g.drawString("Ankunft",(x*14),y-20);                  //und deren Bedeutung
    g.setColor(Color.black);
  //g.drawString("1",(x*12),y-20);
    g.setColor(Color.green);
    g.fillRect((x*23),y-30,20,10);
    g.setColor(Color.black);
    g.drawString("Bedienende",(x*26),y-20);
    g.setColor(Color.black);
  //g.drawString("2",(x*25),y-20);
    g.setColor(Color.red);
    g.fillRect((x*35),y-30,20,10);
    g.setColor(Color.black);
    g.drawString("Weggegangen",(x*38),y-20);
    g.setColor(Color.black);
  //g.drawString("3",(x*36),y-20);
    g.setColor(Color.cyan);
    g.fillRect((x*11), y-15,20,10);
    g.setColor(Color.black);
    g.drawString("Pauseanfang",(x*14),y-5);
    g.setColor(Color.black);
  //g.drawString("4",(x*112),y-5);
    g.setColor(Color.pink);
    g.fillRect((x*23), y-15,20,10);
    g.setColor(Color.black);
    g.drawString("Pauseende",(x*26),y-5);
    g.setColor(Color.black);
  //g.drawString("5",(x*24),y-5);
    g.setColor(Color.black);
    g.fillRect((x*35),y-15,20,10);
    g.drawString("Simende",(x*38),y-5);
    g.setColor(Color.black);
  //g.drawString("6",(x*44),y-5);
    g.drawRect(x+10,y,525,22);                             //Rahmen um die E.-Liste zeichnen
    while ((aktuelles!=null)&&((x*i*3)<=515)){            //die E.-Liste dursuchen beim ersten
	typ = aktuelles.getTyp();                           //Ereignis beginnen
	switch (typ){                                       //entsprechend dem Ereignis die Farbe
	  case 1 : g.setColor(Color.blue);                  //zuordnen
  	           g.fillRect((x*i*3),y+6,20,10);
	           g.setColor(Color.black);
		     break;
	  case 2 : g.setColor(Color.green);
	           g.fillRect((x*i*3),y+6,20,10);

	           g.setColor(Color.black);
	             break;
	  case 3 : g.setColor(Color.red);
	           g.fillRect((x*i*3),y+6,20,10);
	           g.setColor(Color.black);
	           break;
	  case 4 : g.setColor(Color.cyan);
	           g.fillRect((x*i*3),y+6,20,10);
	           g.setColor(Color.black);
	           break;
	  case 5 : g.setColor(Color.pink);
	           g.fillRect((x*i*3),y+6,20,10);
	           g.setColor(Color.black);
	           break;
	  case 6 : g.setColor(Color.black);
	           g.fillRect((x*i*3),y+6,20,10);
	           break;
	}
        aktuelles=aktuelles.getNext();                    //weiter zum nächsten Ereignis
	  i++;                                              /*Schleifenvariable zum Bestimmen der
	                                                      Positionen für die farbigen Balken der
	                                                      E.-Liste*/
    };

  };
}

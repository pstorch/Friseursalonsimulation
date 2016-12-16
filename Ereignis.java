import java.awt.*;
class Ereignis{

  static final int ANKUNFT=1;                        //Konstanten der Klasse für die Ereignistypen
  static final int BEDIENENDE=2;
  static final int WEGGEGANGEN=3;
  static final int PAUSEANFANG=4;
  static final int PAUSEENDE=5;
  static final int SIMENDE=6;

  private Ereignis vorheriges;                       //Variablen der Klasse Zeiger auf vorheriges Ereignis
  private Ereignis naechstes;                        //Zeiger auf nächstes Ereignis
  private int typ;                                   //Typ der Ereignisses
  private int prioritaet;                            //Priorität des Ereignisses
  private int time;                                  //Zeitstempel des Ereignisses
  private int number;                                //Nummer des Ereignisses



/* Konstruktor der Klasse
 * in    der Typ des Ereignisses als Integer-Wert
 *       die Nummer des Ereignisses als Integer-Wert
 *       der Zeitstempel des Ereignisses
 * Die Zeiger werden auf null initialisiert
 * Der Typ, die Zeit und die Nummer fuer das Ereignis werden zugewiesen
*/ 
  public Ereignis(int typ, int number, int time){    //Konstruktor der Klasse
	this.vorheriges = null;                      //Initialisierung der Zeiger auf null
	this.naechstes = null;
	this.typ = typ;                              //Zuweisung des Typs
	switch (typ){                          
	  case ANKUNFT    :prioritaet=1; break;
	  case BEDIENENDE :prioritaet=5; break;
	  case WEGGEGANGEN:prioritaet=2; break;
	  case PAUSEANFANG:prioritaet=3; break;
	  case PAUSEENDE  :prioritaet=4; break;
	  case SIMENDE    :prioritaet=6; break;
        };
        this.time = time;                            //Zuweisung der Zeit
	this.number = number;                        //Zuweisung der Nummer
  }



/* setzt den Zeiger auf vorheriges Ereignis
 *
 * 	in    das vorherige Ereignis
 *
 * der Zeiger vorheriges wird gesetzt, damit er
 * auf das vorherige Ereignis zeigt
 */
    
  void setPrev (Ereignis previous){                  
	vorheriges=previous;
  }



/* setzt den Zeiger auf nächstes Ereignis 
 *
 * 	in    das naechste Ereignis
 *
 * der Zeiger naechstes wird gesetzt, damit er
 * auf das naechste Ereignis zeigt
 */
    
  void setNext(Ereignis next){                       
	naechstes=next;
  }



/* gibt den Zeiger auf vorheriges Ereignis zurueck
 */
 
  Ereignis getPrev(){                               
	return this.vorheriges;
  }



/* gibt den Zeiger auf naechstes Ereignis zurueck
 */

  Ereignis getNext(){                                
	return this.naechstes;
  }



/* gibt den Typ des Ereignisses
 * als Integer-Wert zurueck
 */
  
  int getTyp(){                                      
  	return this.typ;
  }


/* gibt die Priorität des Ereignisses
 * als Integer-Wert zurueck
 */

  int getprioritaet(){                             
  	return this.prioritaet;
  }


/* gibt die Zeit des Ereignisses
 * als Integer-Wert zurzueck
 */

  int gettime(){                                     
 	return this.time;
  }


/* gibt die Nummer des Ereignisses
 * als Integer-Wert zurueck
 */

  int getnumber(){                                   
      return this.number; 
  }


/* Diese Prozedur liefert den String zurueck,
 * der im Applet als Ereignis ausgegeben wird.
 *
 * 	in X-Koordinate für den String
 *       Y-Koordinate für den String
 *       Das Grafikobjekt g
 * 
 *	Variablen: 	wahl als Intervariable zu zwischenspeichern der Dauer der Pause
 *
 * Es wird in der Prozedur geprüft, welches Ereignis eingetreten ist,
 * dementsprechend wird dann der String ausgegeben und auch das Kästchen
 * dazu mit der passenden Farbe erzeugt.
 * Das Updaten des Grafikfeldes wird hier vorgenommen, aber im Applet aufgerufen.      
 */

  void Ereignisstring(Graphics g, int x, int y){                      
	int wahl=0;      

	g.setColor(Color.lightGray);
	g.fillRect(x+60,y,350,15);                    //Das Rechteck das upgedatet wird
	g.setColor(Color.black);
	g.drawString("Ereignis:",x,y+10);         
        if (typ==4) {
          switch (number){                          //Entscheidung zwischen 30 bzw. 60 Min Pause
            case 1: wahl = 30; break;
            case 2: wahl = 60; break;
          }
        }         
        if (typ == 6) { g.setColor(Color.black);
	                  g.fillRect(x+60,y,20,10);
                        g.drawString("Das Simulationsende ist eingetreten",x+88,y+10);}
        else{
          switch (typ){                            //Auswahl des entsprechenden Strings zu den Ereignissen
            case 1:  g.setColor(Color.blue);                  	            
                     g.fillRect(x+60,y,20,10);
                     g.setColor(Color.black);
                     g.drawString("Ankunft des Kunden "+number,x+88,y+10); 
                     break;
            case 2:  g.setColor(Color.green);
	               g.fillRect(x+60,y,20,10);
	               g.setColor(Color.black);
                     g.drawString("Bedienende des Kunden "+number,x+88,y+10);
                     break;
            case 3:  g.setColor(Color.red);
	               g.fillRect(x+60,y,20,10);
	               g.setColor(Color.black);
                     g.drawString("Weggegangen Kunde "+number,x+88,y+10);
                     break;
            case 4:  g.setColor(Color.cyan);
	               g.fillRect(x+60,y,20,10);
	               g.setColor(Color.black);
                     g.drawString("Pauseanfang der Bedienstationen für "+wahl+" min.",x+88,y+10);
                     break;
            case 5:  g.setColor(Color.pink);
	               g.fillRect(x+60,y,20,10);
	               g.setColor(Color.black);
                     g.drawString("Pauseende der Bedienstation "+number,x+88,y+10);
                     break;
          }
        }
  }

}

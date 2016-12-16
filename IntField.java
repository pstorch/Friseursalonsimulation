/**
  *
  *	Klasse IntField
  *
  *	Die Klasse IntField stellt ein TextFeld zur Verfuegung, das nur Zahlenwerte annimmt.
  *	Sie ist von der Klasse java.awt.TextField abgeleitet.
  *
  *	Die Klasse wurde entliehen aus dem Buch JAVA von B. Doberenz, Hanser Verlag
  */
class IntField extends java.awt.TextField{ 
		
	int iValue = 0;
	public IntField () {super();}
	public IntField (int size){ super(size);}
	public IntField (String s){ super(s);}
	public IntField (String s, int size){ super(s,size);};

	int getIntValue() {
		
		return iValue;
	}
	void setIntValue( int i ) {
		iValue = i;
		setText();
	}
	public void setText( ) {
		
		super.setText(Integer.toString( iValue ));
	}
	public String getText() {
		validateText();
		return Integer.toString( iValue );
		
	}
	void setValue() {
		Integer I = new Integer(super.getText());
		
		iValue = I.intValue();	
		setText();
	}
	
	
	
	protected boolean validateText ()
	{
	  try{
		Integer.valueOf(super.getText()); 
	
		if(  super.getText().length() > 7 ) { 
		  if( super.getText().charAt(0) == '-' && 	
			super.getText().length() <= 8) {
		
			setValue();

		 }  else {
		      setText();
			return false;
		}
		} else 

			setValue();
	  }
	  catch (NumberFormatException ex) { 
			setText();
			return false;
	  }
		
        return true;
	}
	
}

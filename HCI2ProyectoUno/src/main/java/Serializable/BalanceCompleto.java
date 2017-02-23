package Serializable;

import java.io.Serializable;

public class BalanceCompleto implements Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public	boolean confie;
	
public	boolean dijeVerdad;
	
	
	public BalanceCompleto (boolean confie , boolean dijeVerdad){
		this.confie=confie;
		this.dijeVerdad=dijeVerdad;
	}

}

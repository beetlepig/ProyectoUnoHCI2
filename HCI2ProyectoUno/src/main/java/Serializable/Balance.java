package Serializable;

import java.io.Serializable;

public class Balance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String indicacion;
	public boolean dijoVerdad;
	
	
	public Balance (String indicacion, boolean verdadero){
		this.indicacion=indicacion;
		this.dijoVerdad=verdadero;
	}
}

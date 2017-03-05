package Serializable;

import java.io.Serializable;

public class GanadorMensaje implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean gane;
	
	
	public GanadorMensaje (boolean gane){
		this.gane=gane;
	}

}

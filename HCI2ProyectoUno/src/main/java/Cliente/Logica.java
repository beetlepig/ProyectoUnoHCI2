package Cliente;

import processing.core.PApplet;

public class Logica {
  private ComunicacionCliente com;
  PApplet app;
  private int estados=0;
  boolean checkInstrucciones=false;
  
	public Logica(PApplet app) {
		this.app=app;
		com= new ComunicacionCliente(3010);
	}
	
	
	
	public void pintar(){
		
		switch (estados) {
		case 0:
			primeraPantalla();
			break;

		case 1:
	
			segundaPantalla();
			break;
			
			
		case 2:
			
			
			
			break;
			
			
		case 3:
			
			
			
			
			break;
				
		}
		
	}
	
	private void primeraPantalla(){
		if(com.servidor==null){
			app.fill(0);
			app.text("conectando con el otro jugador", app.width/2, app.height/2);
		} else {
			
			estados=1;
		}
	}
	
	
	private void segundaPantalla(){

		app.text("pantallaDos", 100, 100);
		if(checkInstrucciones){
			app.fill(0,200,0);
		} else{
			app.fill(0);
		}
		app.rect(app.width-60, app.height-60, 30, 30);
	}

}

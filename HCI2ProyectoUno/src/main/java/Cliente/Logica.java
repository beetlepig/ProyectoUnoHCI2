package Cliente;

import processing.core.PApplet;

public class Logica {
  private ComunicacionCliente com;
  PApplet app;
  private int estados=0;

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
			app.fill(0);
			app.text("instrucciones", app.width/2, app.height/2);
			System.out.println("caso 1");
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

}

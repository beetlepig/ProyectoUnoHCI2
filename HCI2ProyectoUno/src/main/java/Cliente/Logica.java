package Cliente;

import java.util.Observable;
import java.util.Observer;

import Serializable.Mensaje;
import processing.core.PApplet;

public class Logica implements Observer{
  ComunicacionCliente com;
  PApplet app;
  private int estados=0;
  boolean checkInstrucciones=false;
  Mensaje mj;
  
	public Logica(PApplet app) {
		this.app=app;
		com= new ComunicacionCliente(3010);
		com.addObserver(this);
		new Thread(comprobaciones()).start();
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
			
			terceraPantalla();
			
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
	
	private void terceraPantalla () {
		app.fill(0);
		app.text("pantallaTres", 100, 100);
	}
	
	
	private Runnable comprobaciones(){
		
		Runnable r= new Runnable() {
			
			private boolean checkInstruccionesOtroJugador;

			@Override
			public void run() {
				
				while(true){
					try {
						if(mj!= null){
							checkInstruccionesOtroJugador= mj.checkeado;
						}
						switch (estados) {
						case 1:
							
								if(checkInstrucciones && checkInstruccionesOtroJugador){
								estados=2;
								}
						
							

							break;

						case 2:
							
							break;
						}
						
						Thread.sleep(200);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
			
					
				}
				
			}
		};
		
		
		return r;
		
	}



	@Override
	public void update(Observable o, Object arg) {
	Object ob=arg;
	
		if(ob instanceof Mensaje){
			mj=(Mensaje) ob;
		}
		
	}

}

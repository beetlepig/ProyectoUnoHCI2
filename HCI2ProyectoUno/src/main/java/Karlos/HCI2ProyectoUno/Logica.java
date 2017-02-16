package Karlos.HCI2ProyectoUno;

import java.util.Observable;
import java.util.Observer;

import Modelo.PathFinder;
import Serializable.Mensaje;
import processing.core.PApplet;

public class Logica implements Observer
{
	ComunicacionServer server;
	private PApplet app;        
    Mensaje mj;
	
	int estado=0;
	PathFinder rutas;
	
	boolean checkInstrucciones=false;
	boolean checkInstruccionesOtroJugador=false;
	
	public Logica(PApplet app)
	{
		this.app=app;

		server= new ComunicacionServer(3010);
		server.addObserver(this);

		new Thread(comprobaciones()).start();
	
		
	}
	
	
	public void pintar()
	{
		switch (estado) {
		case 0:
			primerPantalla();
			
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
	
	
	
	private void primerPantalla(){
		if(server.cliente == null){
			app.fill(0);
			app.textSize(20);
			app.text("Esperando jugador", app.width-100, app.height-20);
			
		    app.rect(app.width/2, app.height/2, 200, 100);
		
		}else {
			estado=1;
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
	
	
	private void terceraPantalla(){
	//	app.fill(0);
	//	app.text("pantallaTres", 100, 100);
	//	rutas= new PathFinder(app);
		rutas.pintar();
		
	}


	@Override
	public void update(Observable o, Object arg) {
	Object ob=arg;
	
		if(ob instanceof Mensaje){
			mj=(Mensaje) ob;
			System.out.println(mj.checkeado);
		}
		
	}
	
	
	private Runnable comprobaciones(){
		
		Runnable r= new Runnable() {
			
			@Override
			public void run() {
				
				while(true){
					try {
						//actualizo variables a partir del objeto mj
						if(mj!= null){
							checkInstruccionesOtroJugador= mj.checkeado;
						}
						
						switch (estado) {
						case 1:
							
								if(checkInstrucciones && checkInstruccionesOtroJugador && estado==1){
									rutas= new PathFinder(app);
									estado=2;
								
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
	
	
	
	
	

}

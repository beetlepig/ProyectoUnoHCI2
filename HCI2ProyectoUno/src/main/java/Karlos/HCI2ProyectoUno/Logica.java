package Karlos.HCI2ProyectoUno;

import java.util.Observable;
import java.util.Observer;

import Serializable.Mensaje;
import processing.core.PApplet;

public class Logica implements Observer
{
	ComunicacionServer server;
	private PApplet app;        
    Mensaje mj;
	
	int estado=0;
	
	
	boolean checkInstrucciones=false;
	boolean checkInstruccionesOtroJugador=false;
	
	public Logica(PApplet app)
	{
		this.app=app;

		server= new ComunicacionServer(3010);
		
		
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


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}

package Karlos.HCI2ProyectoUno;

import processing.core.PApplet;

public class Logica 
{
	private ComunicacionServer server;
	PApplet app;        
	int ancho;
	int alto;
	
	int estado=0;
	
	public Logica(PApplet app,int ancho, int alto)
	{
		this.app=app;
		this.ancho=ancho;
		this.alto=alto;
		server= new ComunicacionServer(3010);
		
		
	}
	
	
	public void pintar()
	{
		switch (estado) {
		case 0:
			primerPantalla();
			
			break;

		case 1:
			app.fill(0);
			app.text("pantallaDos", 100, 100);
			
			
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
			app.text("Esperando jugador", ancho-100, alto-20);
			
		    app.rect(ancho/2, alto/2, 200, 100);
		
		}else {
			estado=1;
		}
	}
	
	
	

}

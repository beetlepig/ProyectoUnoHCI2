package Karlos.HCI2ProyectoUno;

import Serializable.Mensaje;
import processing.core.PApplet;

public class App extends PApplet
{
	Logica log;
	
	public void settings()
	{
		size(1280,720);
		log= new Logica(this);
	}
	
	public void setup()
	{
	//	System.out.println("hola");
	//	imageMode(CENTER);
		textAlign(CENTER);
		rectMode(CENTER);
		ellipseMode(CENTER);
		cursor(CROSS);
		smooth();
	
	}
	
	public void draw(){
		background(255);
		log.pintar();
	}
	
	
    public static void main( String[] args )
    {
        PApplet.main(App.class.getName());
        
    }
    
    public void mousePressed()
    {
    	println(mouseX, mouseY);
		if( (mouseX>width-75 && mouseX<width-45) &&  (mouseY>height-75 && mouseY<height-45) && log.estado==1){
			
			if(!log.checkInstrucciones){
			log.checkInstrucciones=true;
			} else {
				log.checkInstrucciones=false;
			}
			Mensaje mj= new Mensaje(log.checkInstrucciones);
			log.server.enviarObjeto(mj);
		}
		if(log.estado==2){
	//	log.rutas.mousePressed(mouseX, mouseY);
		log.movimiento(mouseX, mouseY);
		}
	}
    
    public void mouseReleased(){
    //	System.out.println("entro released");
    	if(log.estado==2){
    //	log.rutas.mouseReleased();
    	}
    }
    
    public void mouseDragged(){
    	if(log.estado==2){
    //	log.rutas.mouseDragged(mouseX, mouseY);
    	}
    }
    
   
    
}

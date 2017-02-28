package Cliente;


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
    //	println(mouseX, mouseY);
		if( (mouseX>520 && mouseX<750) &&  (mouseY>610 && mouseY<670) && log.estados==1 && log.estadoSegundaPantalla==3){
			
			if(!log.checkInstrucciones){
			log.checkInstrucciones=true;
			
			
			} else {
				log.checkInstrucciones=false;
			}
			Mensaje mj= new Mensaje(log.checkInstrucciones);
			log.com.enviarObjeto(mj);
		}
		if(log.estados==2){
		//	log.rutas.mousePressed(mouseX, mouseY);
			log.movimiento(mouseX, mouseY);
			}
	}
    
    
    public void mouseReleased(){
    	//System.out.println("entro released");
    	if(log.estados==2){
    	//log.rutas.mouseReleased();
    	}
    }
    
    public void mouseDragged(){
    	if(log.estados==2){
    //	log.rutas.mouseDragged(mouseX, mouseY);
    	}
    }

}

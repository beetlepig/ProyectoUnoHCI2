package Karlos.HCI2ProyectoUno;

import processing.core.PApplet;

public class App extends PApplet
{
	Logica log;
	
	public void settings()
	{
		size(1280,720);
		log= new Logica();
	}
	
	public void setup()
	{
		System.out.println("hola");
	}
	
	public void draw(){
		background(255);
	}
	
	
    public static void main( String[] args )
    {
        PApplet.main(App.class.getName());
        
    }
    
    public void mousePressed()
    {
		
	}
    
}

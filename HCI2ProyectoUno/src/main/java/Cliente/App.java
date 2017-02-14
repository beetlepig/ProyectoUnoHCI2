package Cliente;


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
		System.out.println("hola");
		textAlign(CENTER);
		rectMode(CENTER);
		imageMode(CENTER);
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
		
	}

}

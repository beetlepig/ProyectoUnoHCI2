package Cliente;


import processing.core.PApplet;

public class App extends PApplet 
{

	public void settings()
	{
		size(1280,720);
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

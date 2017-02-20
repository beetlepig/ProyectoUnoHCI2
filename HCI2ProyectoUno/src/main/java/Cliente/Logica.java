package Cliente;

import java.util.Observable;
import java.util.Observer;

import Modelo.PathFinder;
import Serializable.Mensaje;
import pathfinder.GraphNode;
import processing.core.PApplet;

public class Logica implements Observer{
  ComunicacionCliente com;
  PApplet app;
  int estados=0;
  boolean checkInstrucciones=false;
  Mensaje mj;
  PathFinder rutas;
	GraphNode[] g;
	private GraphNode startNode;
	
	
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
		app.text("pantallaTres cliente", 100, 100);
		rutas.pintar();
		app.text("Izquierda", 600, 400);
		app.text("Derecha", 1000, 400);
		app.text("Derecho", 800, 200);
		app.text("Atras", 800, 600);
		//g= rutas.rNodes; //actualizar pero mediante tcp
		if(g!=null){
		posicionesJugador(g);
		}
	}
	
	
	 void movimiento(int mouseX, int mouseY){
		app.println(mouseX,mouseY);
		if( (mouseX>751 && mouseX<850) && (mouseY>175 && mouseY<210)){
			System.out.println("derecho");
			startNode = rutas.gs.getNodeAt(g[0].xf()+rutas.offX - rutas.offX, g[0].yf()+rutas.offY-20 - rutas.offY, 0,10.0f); 
			int imprimir= (int) (g[0].yf()+rutas.offY-5) ;
			app.println("posicion apuntada:" + imprimir);
			if(startNode!=null){
			System.out.println("id del nodo: "+startNode.id());
			
			g[0]=startNode;
			
			com.enviarObjeto(new Mensaje(startNode));
			
			
			}
			
			
		
		} else if((mouseX>550 && mouseX<650) && (mouseY>380 && mouseY<415)){
			System.out.println("izquierda");
				startNode = rutas.gs.getNodeAt(g[0].xf()+rutas.offX-17 - rutas.offX, g[0].yf()+rutas.offY - rutas.offY, 0, 16.0f); 
				if(startNode!=null){
				System.out.println("id del nodo: "+startNode.id());
				
				g[0]=startNode;
				
				com.enviarObjeto(new Mensaje(startNode));
				
				
				}
		} else if((mouseX>950 && mouseX<1050) && (mouseY>380 && mouseY<415)){
			System.out.println("derecha");
			startNode = rutas.gs.getNodeAt(g[0].xf()+rutas.offX+20 - rutas.offX, g[0].yf()+rutas.offY - rutas.offY, 0, 10.0f); 
			if(startNode!=null){
			System.out.println("id del nodo: "+startNode.id());
			
			g[0]=startNode;
			
			com.enviarObjeto(new Mensaje(startNode));
			
			
			}
			
		} else if ((mouseX>766 && mouseX<830) && (mouseY>570 && mouseY<608)){
			System.out.println("atras");
			startNode = rutas.gs.getNodeAt(g[0].xf()+rutas.offX - rutas.offX, g[0].yf()+rutas.offY+20 - rutas.offY, 0, 10.0f); 
			if(startNode!=null){
			System.out.println("id del nodo: "+startNode.id());
			
			g[0]=startNode;
			
			com.enviarObjeto(new Mensaje(startNode));
			
			
			}
		}
		
		
		
	}
	
	
	
	private void posicionesJugador(GraphNode[] r){
		app.pushMatrix();
		app.translate(rutas.offX, rutas.offY);
			if (r.length >= 2) {
				app.pushStyle();
				// Route start node
				app.strokeWeight(2.0f);
				app.stroke(0, 0, 160);
				app.fill(0, 0, 255);
				app.ellipse(r[0].xf(), r[0].yf(), 10, 10);
				// Route end node
				app.stroke(0, 250, 0);
				app.fill(0, 250, 0);
				app.ellipse(r[r.length - 1].xf(), r[r.length - 1].yf(), 10, 10);
				
				app.popStyle();
			}
			app.popMatrix();
		
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
									rutas= new PathFinder(app,292,195);
									GraphNode[] o= rutas.rNodes;
									   com.enviarObjeto(new Mensaje(o));
								estados=2;
								}
						
							

							break;

						case 2:
							
							break;
						}
						
						Thread.sleep(20);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
			
					
				}
				
			}
		};
		
		
		return r;
		
	}




	public void update(Observable o, Object arg) {
	Object ob=arg;
	
		if(ob instanceof Mensaje){
			mj=(Mensaje) ob;
			if(mj.nodos!=null && estados==2){
				g=mj.nodos;
			}
			
			if(mj.nodoInicial!=null && estados==2){
				System.out.println("entro update 2");;
				rutas.start= mj.nodoInicial.id();
				rutas.usePathFinder(rutas.pathFinder);
				
				System.out.println("--------------Pasos realizados-----------");
				System.out.println(rutas.getSequence());
			}
		}
		
	}

}

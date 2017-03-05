package Karlos.HCI2ProyectoUno;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Modelo.PathFinder;
import Serializable.Balance;
import Serializable.BalanceCompleto;
import Serializable.GanadorMensaje;
import Serializable.Mensaje;
import pathfinder.GraphNode;
import processing.core.PApplet;
import processing.core.PImage;

public class Logica implements Observer
{
	ComunicacionServer server;
	private PApplet app;        
    Mensaje mj;
    Balance bl;
    BalanceCompleto balanceCo;
    
	int estado=0;
	int estadoSegundaPantalla=0;
	int tipoDePantallaFinal=0;
	
	
	PathFinder rutas;
	
	
	boolean checkInstrucciones=false;
	boolean checkInstruccionesOtroJugador=false;
	private GraphNode startNode;
	GraphNode[] g;
	int estadoRonda=0;
	boolean sugeridoFinish=false;
	String sugerencia;
	boolean verdadOtroJugador;
	boolean confie;
	boolean elOtroJugadorConfio;
	boolean dijeVerdad;
	private boolean checkPopUpOtroJugador;
	private boolean ckeckPopUp;
	
	private short acumuladoDesiciones;
	
	private short energiaInicial=140;
	
	private short energiaCiervo=20;
	
	
	PImage open;
	PImage openBoton;
	PImage intro;
	PImage introBoton;
	PImage elegir[];
	int loboElegido;
	PImage elegirBoton;
    PImage instrucciones;
    PImage instruccionesBoton;
    PImage interfaz;
    PImage loboEscogido;
    PImage balanceConfianza[];
    PImage esperarJugadorIMG;
    PImage energia[];
    PImage sugerirImg;
    PImage escogerImg;
    PImage pantallasFinales[];
	
	String mostrarBalance=null;
	
	
	boolean gane;
	boolean elOtroJugadorGano;
	
	public Logica(PApplet app)
	{
		this.app=app;

		server= new ComunicacionServer(3010);
		server.addObserver(this);

		new Thread(comprobaciones()).start();
		
		
	loadImages();
		
	}
	
	private void loadImages(){
		open= app.loadImage("../data/Insumos/Open-8.png");
		openBoton= app.loadImage("../data/Insumos/Open-boton-comenzar-8.png");
		intro= app.loadImage("../data/Insumos/Intro-8.png");
		introBoton= app.loadImage("../data/Insumos/Intro - Boton-8.png");
		elegir= new PImage[3];
		elegir[0]=app.loadImage("../data/Insumos/Elegir-8.png");
		elegir[1]=app.loadImage("../data/Insumos/Elegir izq-8.png");
		elegir[2]=app.loadImage("../data/Insumos/Elegir der-8.png");
		elegirBoton=app.loadImage("../data/Insumos/Elegir - boton continuar-8.png");
		instrucciones= app.loadImage("../data/Insumos/Instrucciones-8.png");
		instruccionesBoton= app.loadImage("../data/Insumos/Instrucciones - Boton-8.png");
		interfaz= app.loadImage("../data/Insumos/Interfaz 1.png");
		balanceConfianza= new PImage[5];
		balanceConfianza[0]= app.loadImage("../data/Insumos/Balance -8.png");
		balanceConfianza[1]= app.loadImage("../data/Insumos/Balance 25-8.png");
		balanceConfianza[2]= app.loadImage("../data/Insumos/Balance 50-8.png");
		balanceConfianza[3]= app.loadImage("../data/Insumos/Balance 75-8.png");
		balanceConfianza[4]= app.loadImage("../data/Insumos/Blance 100-8.png");
		esperarJugadorIMG= app.loadImage("../data/Insumos/Esperando-8.png");
		energia= new PImage[20];
		sugerirImg=  app.loadImage("../data/Insumos/Jugador 1 - sugiere-8.png");
		escogerImg=  app.loadImage("../data/Insumos/Jugador 1 - ir-8.png");
		pantallasFinales= new PImage[3];
		pantallasFinales[0]= app.loadImage("../data/Insumos/Perdiste.png");
		pantallasFinales[1]= app.loadImage("../data/Insumos/Ganaste.png");
		pantallasFinales[2]= app.loadImage("../data/Insumos/ambosganan.png");
		
		for (int i = 0; i < 19; i++) {
			energia[i]=app.loadImage("../data/Insumos/Energia "+(i+1)+".png");
		}
		
		
		
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
			
			
			cuartaPantalla();
			
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
		
		switch (estadoSegundaPantalla) {
		case 0:
			
			app.image(open, 0F, 0F);
			if((app.mouseX>520 && app.mouseX<760) && (app.mouseY>570 && app.mouseY<630)){
			app.image(openBoton, 0, 0);
			if(app.mousePressed){
				estadoSegundaPantalla=1;
				app.delay(400);
			}
			}
			
			break;
			
			
			
			
		case 1:
			app.image(intro, 0, 0);
			if((app.mouseX>520 && app.mouseX<750) && (app.mouseY>610 && app.mouseY<670)){
				app.image(introBoton, 0, 0);
				if(app.mousePressed){
					estadoSegundaPantalla=2;
					app.delay(400);
				}
				}
			
			
			
			
			break;

		case 2:
			
				app.image(elegir[loboElegido], 0, 0);
			
			
			
			
			if((app.mouseX>700 && app.mouseX<940) && (app.mouseY>210 && app.mouseY<535) && app.mousePressed){
				loboElegido=2;
				
			}
			
			if((app.mouseX>340 && app.mouseX<585) && (app.mouseY>210 && app.mouseY<535) && app.mousePressed){
				loboElegido=1;
			}
			
			
			if((app.mouseX>520 && app.mouseX<760) && (app.mouseY>610 && app.mouseY<670)){
				app.image(elegirBoton, 0, 0);
				if(app.mousePressed && loboElegido!=0){
					estadoSegundaPantalla=3;
					if(loboElegido==1){
					loboEscogido= app.loadImage("../data/Insumos/LoboBlanco.png");
					} else if(loboElegido==2){
						loboEscogido= app.loadImage("../data/Insumos/LoboGris.png");
					}
					app.delay(500);
				}
				}
			break;
			
		case 3:
			app.image(instrucciones, 0,0);
		
			if(checkInstrucciones){
				app.image(instruccionesBoton, 0, 0);
				app.pushStyle();
				app.fill(255);
				app.text("Esperando al otro jugador", 640, 690);
				app.popStyle();
			} 
			
			
			break;

		}

		
	}
	
	
	private void terceraPantalla(){
		app.image(interfaz, 0, 0);
		app.image(loboEscogido, 150, 70);
		app.pushStyle();
		app.fill(0);
		app.textSize(30);
		app.text(energiaCiervo, 275, 340);
		app.popStyle();
		//pintar energia
		if(energiaInicial==200){
			 app.image(energia[19], 410, 50);
		}else if(energiaInicial==190){
			 app.image(energia[18], 410, 50);
		}else if(energiaInicial==180){
			 app.image(energia[17], 410, 50);
		}else if(energiaInicial==170){
			 app.image(energia[16], 410, 50);
		}else if(energiaInicial==160){
			 app.image(energia[15], 410, 50);
		}else if(energiaInicial==150){
			 app.image(energia[14], 410, 50);
		}else if(energiaInicial==140){
			 app.image(energia[13], 410, 50);
		}else if(energiaInicial==130){
		    app.image(energia[12], 410, 50);
		}else if(energiaInicial==120){
			app.image(energia[11], 410, 50);
		}else if(energiaInicial==110){
			app.image(energia[10], 410, 50);
		}else if(energiaInicial==100){
			app.image(energia[9], 410, 50);
		}else if(energiaInicial==90){
			app.image(energia[8], 410, 50);
		}else if(energiaInicial==80){
			app.image(energia[7], 410, 50);
		}else if(energiaInicial==70){
			app.image(energia[6], 410, 50);
		}else if(energiaInicial==60){
			app.image(energia[5], 410, 50);
		}else if(energiaInicial==50){
			app.image(energia[4], 410, 50);
		}else if(energiaInicial==40){
			app.image(energia[3], 410, 50);
		}else if(energiaInicial==30){
			app.image(energia[2], 410, 50);
		}else if(energiaInicial==20){
			app.image(energia[1], 410, 50);
		}else if(energiaInicial==10){
			app.image(energia[0], 410, 50);
		}
		
		switch (estadoRonda) {
		case 0:
			app.fill(255);
			app.image(sugerirImg, 0, 0);
		//	app.text("Sugiere una direccion para el otro jugador", 200, 100);
			rutas.pintar();
			
		//	app.text("Izquierda", 600, 400);
		//	app.text("Derecha", 1000, 400);
		//	app.text("Derecho", 800, 200);
		//	app.text("Atras", 800, 600);
		//	g= rutas.rNodes;
			if(g!=null){
			posicionesJugador(g);
			}
			break;

		case 1:
			if(sugerencia==null){
				
				app.image(esperarJugadorIMG, 0, 0);
				app.text("Esperando sugerencia del otro jugador", 710, 300);
				
				} else if (mostrarBalance==null) {
					app.fill(255);
					app.image(escogerImg, 0, 0);
					app.text(sugerencia, 940, 614);
					
					
				//	app.text("Izquierda", 600, 400);
				//	app.text("Derecha", 1000, 400);
				//	app.text("Derecho", 800, 200);
				//	app.text("Atras", 800, 600);
				//	g= rutas.rNodes;
				//	if(g!=null){
				//	posicionesJugador(g);
				//	}
				} else if(mostrarBalance!=null){
					
					app.image(esperarJugadorIMG, 0, 0);
					app.text("Esperando que el otro jugador se mueva",710, 300);
				//	if(g!=null){
				//		posicionesJugador(g);
				//		}
				}
				
			if(g!=null){
				posicionesJugador(g);
				}
			rutas.pintar();
			
			
			break;
			
			
		case 2:
			
			app.fill(255);
			
			app.text(sugerencia, 895, 607);
			rutas.pintar();
			
		//	app.text("Izquierda", 600, 400);
		//	app.text("Derecha", 1000, 400);
		//	app.text("Derecho", 800, 200);
		//	app.text("Atras", 800, 600);
		//	g= rutas.rNodes;
			if(g!=null){
			posicionesJugador(g);
			}
			app.fill(150);
		//	app.rect(400,300, 400, 400);
			app.fill(50);
			app.text("aqui va el balance de confianza", 370, 230);
			if(verdadOtroJugador){
			    app.text("El otro jugador sugurio: "+sugerencia +". dijo : verdad", 370, 250);
			} else if(!verdadOtroJugador) {
				app.text("El otro jugador sugurio: "+sugerencia +". dijo : mentira", 370, 250);
			}
			
			if(confie){
			    app.text("confié en el otro jugador", 370, 300);
			} else if(!confie) {
				app.text("no confié en el otro jugador", 370, 300);
			}
			
			if(dijeVerdad){
				app.text("Dije la verdad", 370, 350);
				} else {
					app.text("no dije la verdad", 370, 350);
				}
				
				if(elOtroJugadorConfio){
					app.text("el otro jugador confio", 370, 400);
					} else {
						app.text("el otro jugador no confio", 370, 400);
					}
				
				app.text("el acumulado es: "+acumuladoDesiciones, 250,200);
				if(acumuladoDesiciones==0){
					app.image(balanceConfianza[0], 0, 0);
				app.text("el balance es: 0%", 200, 200);
				
				} else if(acumuladoDesiciones==1){
					app.image(balanceConfianza[1], 0, 0);
					app.text("el balance es: 25%", 250, 150);
					
				}
				else if(acumuladoDesiciones==2){
					app.image(balanceConfianza[2], 0, 0);
					app.text("el balance es: 50%", 250, 150);
					
				} else if(acumuladoDesiciones==3){
					app.image(balanceConfianza[3], 0, 0);
					app.text("el balance es: 75%", 250, 150);
					
				} else if(acumuladoDesiciones==4){
					app.image(balanceConfianza[4], 0, 0);
					app.text("el balance es: 100%", 250, 150);
					
				}
				
				if(ckeckPopUp){
					app.pushStyle();
					app.fill(70);
					app.text("Esperando al otro jugador", 640, 550);
					app.popStyle();
				}
			
			break;
		}
		
	}
	
	private void cuartaPantalla () {
		
		switch (tipoDePantallaFinal) {
		case 0:
			app.image(pantallasFinales[0], 0, 0);
			break;

		case 1:
			app.image(pantallasFinales[1], 0, 0);
			
			break;
			
		case 2:
			app.image(pantallasFinales[2], 0, 0);
			
			break;
		}
		
	}



	public void update(Observable o, Object arg) {
	Object ob=arg;
	
		if(ob instanceof Mensaje){
			mj=(Mensaje) ob;
			//System.out.println(mj.checkeado);
			if(mj.nodos!=null && estado==2){
				g=mj.nodos;
			}
			
			if(mj.nodoInicial!=null && estado==2){
			//	System.out.println("entro update 2");;
				rutas.start= mj.nodoInicial.id();
				rutas.usePathFinder(rutas.pathFinder);
				
				System.out.println("--------------Pasos realizados-----------");
				System.out.println(rutas.getSequence());
			}
			
			
		} else if(ob instanceof Balance){
			bl= (Balance) ob;
			if(bl.indicacion!=null){
				sugerencia=bl.indicacion;
			}
			
			if(bl.dijoVerdad){
				verdadOtroJugador=true;
			}else{
				verdadOtroJugador=false;
			}
		} else if(ob instanceof BalanceCompleto){
			balanceCo= (BalanceCompleto) ob;
			elOtroJugadorConfio= balanceCo.confie;
			
		}  else if(ob instanceof GanadorMensaje){
			elOtroJugadorGano=true;
		}
		
	}
	
	 void movimiento(int mouseX, int mouseY){
		 switch (estadoRonda) {
		case 0:
			
			app.println(mouseX,mouseY);
			if( (mouseX>750 && mouseX<855) && (mouseY>340 && mouseY<435)){
				System.out.println("arriba");
				ArrayList<String> secuencia= rutas.getSequence();
				if(secuencia.get(0).equals("arriba")){
					System.out.println("verdad para arriba");
					server.enviarObjeto(new Balance("arriba",true));
					dijeVerdad=true;
				} else {
					System.out.println("mentira para arriba");
					server.enviarObjeto(new Balance("arriba",false));
					dijeVerdad=false;
				}
				estadoRonda+=1;
				
			
				
				
				
				
				
			
			} else if((mouseX>665 && mouseX<760) && (mouseY>425 && mouseY<530)){
				System.out.println("izquierda");
				ArrayList<String> secuencia= rutas.getSequence();
				if(secuencia.get(0).equals("izq")){
					System.out.println("verdad para izquierda");
					server.enviarObjeto(new Balance("izq",true));
					dijeVerdad=true;
				} else {
					System.out.println("mentira para izquierda");
					server.enviarObjeto(new Balance("izq",false));
					dijeVerdad=false;
				}
				estadoRonda+=1;
				
				
			} else if((mouseX>855 && mouseX<955) && (mouseY>426 && mouseY<530)){
				System.out.println("derecha");
				ArrayList<String> secuencia= rutas.getSequence();
				if(secuencia.get(0).equals("der")){
					System.out.println("verdad para derecha");
					server.enviarObjeto(new Balance("der",true));
					dijeVerdad=true;
				} else {
					System.out.println("mentira para derecha");
					server.enviarObjeto(new Balance("der",false));
					dijeVerdad=false;
				}
				
				estadoRonda+=1;
				
				
			} else if ((mouseX>755 && mouseX<870) && (mouseY>520 && mouseY<620)){
				System.out.println("atras");
				ArrayList<String> secuencia= rutas.getSequence();
				if(secuencia.get(0).equals("abajo")){
					System.out.println("verdad para abajo");
					server.enviarObjeto(new Balance("abajo",true));
					dijeVerdad=true;
				} else {
					System.out.println("mentira para abajo");
					server.enviarObjeto(new Balance("abajo",false));
					dijeVerdad=false;
				}
				estadoRonda+=1;
				
			}
			
			break;

		case 1:
			app.println(mouseX,mouseY);
			if( (mouseX>752 && mouseX<865) && (mouseY>246 && mouseY<335)){
				System.out.println("derecho");
				startNode = rutas.gs.getNodeAt(g[0].xf()+rutas.offX - rutas.offX, g[0].yf()+rutas.offY-20 - rutas.offY, 0,9.0f); 
				int imprimir= (int) (g[0].yf()+rutas.offY-5) ;
				app.println("posicion apuntada:" + imprimir);
				if(startNode!=null){
				System.out.println("id del nodo: "+startNode.id());
				
				g[0]=startNode;
				
				server.enviarObjeto(new Mensaje(startNode));
				
				if(sugerencia.equals("arriba")){
					confie=true;
				} else{
					confie=false;
				}
			
				
				}
				//-----------------
				app.delay(350);
				server.enviarObjeto(new BalanceCompleto(confie, dijeVerdad));
				//---------------
				//estadoRonda+=1;
				mostrarBalance="yes";
				
			
			} else if((mouseX>665 && mouseX<760) && (mouseY>333 && mouseY<430)){
				System.out.println("izquierda");
					startNode = rutas.gs.getNodeAt(g[0].xf()+rutas.offX-17 - rutas.offX, g[0].yf()+rutas.offY - rutas.offY, 0, 15.0f); 
					if(startNode!=null){
					System.out.println("id del nodo: "+startNode.id());
					
					g[0]=startNode;
					
					server.enviarObjeto(new Mensaje(startNode));
					
					if(sugerencia.equals("izq")){
						confie=true;
					} else{
						confie=false;
					}
					
			
					
					}
					//-----------------
					app.delay(300);
					server.enviarObjeto(new BalanceCompleto(confie, dijeVerdad));
					//---------------
					//estadoRonda+=1;
					mostrarBalance="yes";
					
					
			} else if((mouseX>860 && mouseX<960) && (mouseY>330 && mouseY<437)){
				System.out.println("derecha");
				startNode = rutas.gs.getNodeAt(g[0].xf()+rutas.offX+20 - rutas.offX, g[0].yf()+rutas.offY - rutas.offY, 0, 9.0f); 
				if(startNode!=null){
				System.out.println("id del nodo: "+startNode.id());
				
				g[0]=startNode;
				
				server.enviarObjeto(new Mensaje(startNode));
				
				if(sugerencia.equals("der")){
					confie=true;
				} else{
					confie=false;
				}
				
				
				
				
				}
				//-----------------
				app.delay(350);
				server.enviarObjeto(new BalanceCompleto(confie, dijeVerdad));
				//---------------
				//estadoRonda+=1;
				mostrarBalance="yes";
			} else if ((mouseX>755 && mouseX<865) && (mouseY>425 && mouseY<520)){
				System.out.println("atras");
				startNode = rutas.gs.getNodeAt(g[0].xf()+rutas.offX - rutas.offX, g[0].yf()+rutas.offY+20 - rutas.offY, 0, 9.0f); 
				if(startNode!=null){
				System.out.println("id del nodo: "+startNode.id());
				
				g[0]=startNode;
				
				server.enviarObjeto(new Mensaje(startNode));
				
				if(sugerencia.equals("abajo")){
					confie=true;
				} else{
					confie=false;
				}
				
			
				}
				//-----------------
				app.delay(350);
				server.enviarObjeto(new BalanceCompleto(confie, dijeVerdad));
				//---------------
				//estadoRonda+=1;
				mostrarBalance="yes";
				
			}
			
			
			
			
			break;
			
		
			
		case 2:
			
			if ((mouseX>910 && mouseX<955) && (mouseY>170 && mouseY<215)){
				System.out.println("cerrar");
				ckeckPopUp=true;
				server.enviarObjeto(new Mensaje(true));
				
			}
			
			
			break;
		}
		
		
		
		
	}
	
	private void posicionesJugador(GraphNode[] r){
		app.pushMatrix();
		app.translate(rutas.offX, rutas.offY);
			if (r.length >= 2) {
				app.pushStyle();
				// Route start node
				app.strokeWeight(2.0f);
				app.stroke(255, 255, 255);
				app.fill(255,255, 255);
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
			

	

		

			public void run() {
				
				while(true){
					try {
						//actualizo variables a partir del objeto mj
						if(mj!= null){
							checkInstruccionesOtroJugador= mj.checkeado;
						}
						
						switch (estado) {
						case 1:
							
								if(checkInstrucciones && checkInstruccionesOtroJugador){
									rutas= new PathFinder(app,105,156);
									GraphNode[] o= rutas.rNodes;
								   server.enviarObjeto(new Mensaje(o));
									estado=2;
									
									
								
								}
							break;

						case 2:
							
							if(mj!= null){
								checkPopUpOtroJugador= mj.checkeado;
							}
							
							if(ckeckPopUp && checkPopUpOtroJugador){
								checkInstruccionesOtroJugador=false;
								sugerencia=null;
								mostrarBalance=null;
								mj=null;
								bl=null;
								balanceCo=null;
								ckeckPopUp=false;
								checkPopUpOtroJugador=false;
								checkInstruccionesOtroJugador=false;
								acumuladoDesiciones=0;
								estadoRonda=0;
								if(energiaInicial>0){
								energiaInicial-=10;
								}
								
								if (energiaInicial==0){
									tipoDePantallaFinal=0;
									estado=3;
								}
								
								if(gane && elOtroJugadorGano){
									estado=3;
									tipoDePantallaFinal=2;
								} else if(elOtroJugadorGano && !gane){
									tipoDePantallaFinal=0;
									estado=3;
								} else if(gane && !elOtroJugadorGano){
									tipoDePantallaFinal=1;
									estado=3;
								}
							}
							
							if(balanceCo!=null && estadoRonda==1 && mostrarBalance!=null){
								if(confie){
									
									acumuladoDesiciones+=1;
									
								}
								
								if(elOtroJugadorConfio){
								
									acumuladoDesiciones+=1;
									
								}
								
								if(dijeVerdad){
								
									acumuladoDesiciones+=1;
									
								}
								
								if(verdadOtroJugador){
									
									acumuladoDesiciones+=1;
									
								}
								estadoRonda+=1;
								
							}
							//para ver si llegue y gane
							if(g!=null){
							if(g[0].id() == g[g.length-1].id() && !gane){
								gane=true;
								
								server.enviarObjeto(new GanadorMensaje(gane));
							}
							}
							
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
	
	
	
	
	

}

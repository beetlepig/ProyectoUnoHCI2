package Cliente;

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

public class Logica implements Observer{
  ComunicacionCliente com;
  PApplet app;
  
  int estados=0;
  int estadoSegundaPantalla=0;
  int tipoDePantallaFinal=0;
  
  boolean checkInstrucciones=false;
  boolean ckeckPopUp;
  boolean checkPopUpOtroJugador;
  Mensaje mj;
  Balance bl;
  BalanceCompleto balanceCo;
  PathFinder rutas;
	GraphNode[] g;
	private GraphNode startNode;
	private int estadoRonda;
	String sugerencia;
	private boolean checkInstruccionesOtroJugador;
	
	boolean verdadOtroJugador;
	boolean dijeVerdad;
	boolean confie;
	boolean elOtroJugadorConfio;
	
	String mostrarBalance=null;

	
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
    
    short acumuladoDesiciones;
    
    private short energiaInicial=140;
    
    
    private short energiaCiervo=20;
    
    
    boolean gane;
    boolean elOtroJugadorGano;
    
	
	public Logica(PApplet app) {
		this.app=app;
		com= new ComunicacionCliente(3010);
		com.addObserver(this);
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
		interfaz= app.loadImage("../data/Insumos/Interfaz 2.png");
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
			
			cuartaPantalla();
			
			
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
	
	private void terceraPantalla () {
		app.image(interfaz, 0, 0);
		app.image(loboEscogido, 150, 70);
		app.pushStyle();
		app.fill(0);
		app.textSize(30);
		app.text(energiaCiervo, 275, 340);
		app.popStyle();
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
			app.text("esperando sugerencia del otro jugador", 710,300);
		//	if(g!=null){
		//		posicionesJugador(g);
		//		}
			} else if (mostrarBalance==null){
				app.fill(255);
				app.image(escogerImg, 0, 0);
				app.text(sugerencia, 940, 614);
				
				
			//	app.text("Izquierda", 600, 400);
			//	app.text("Derecha", 1000, 400);
			//	app.text("Derecho", 800, 200);
			//	app.text("Atras", 800, 600);
			//	g= rutas.rNodes;
			//	if(g!=null){
		//		posicionesJugador(g);
			//	}
			} else if(mostrarBalance!=null){
				app.text("esperando que el otro jugador se mueva",710, 300);
				app.image(esperarJugadorIMG, 0, 0);
		//		if(g!=null){
				//	posicionesJugador(g);
			//		}
			}
			if(g!=null){
				posicionesJugador(g);
				}
			if(rutas!=null){
			rutas.pintar();
			}
			
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
			if(acumuladoDesiciones==0){
				app.image(balanceConfianza[0], 0, 0);
			
			} else if(acumuladoDesiciones==1){
				app.image(balanceConfianza[1], 0, 0);
				
			}
			else if(acumuladoDesiciones==2){
				app.image(balanceConfianza[2], 0, 0);
			
			} else if(acumuladoDesiciones==3){
				app.image(balanceConfianza[3], 0, 0);
				
			} else if(acumuladoDesiciones==4){
				app.image(balanceConfianza[4], 0, 0);
			
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
	
	
	private void cuartaPantalla (){
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
	
	
	 void movimiento(int mouseX, int mouseY){
		 switch (estadoRonda) {
		case 0:
			
			app.println(mouseX,mouseY);
			if( (mouseX>750 && mouseX<855) && (mouseY>340 && mouseY<435)){
				System.out.println("derecho");
				ArrayList<String> secuencia= rutas.getSequence();
				if(secuencia.get(0).equals("arriba")){
					System.out.println("verdad para arriba");
					com.enviarObjeto(new Balance("arriba",true));
					dijeVerdad=true;
					
				} else {
					System.out.println("mentira para arriba");
					com.enviarObjeto(new Balance("arriba",false));
					dijeVerdad=false;
				}
				estadoRonda+=1;
				
			//	server.enviarObjeto(new Mensaje(startNode));
				
				
				
				
				
			
			} else if((mouseX>665 && mouseX<760) && (mouseY>425 && mouseY<530)){
				System.out.println("izquierda");
				ArrayList<String> secuencia= rutas.getSequence();
				if(secuencia.get(0).equals("izq")){
					System.out.println("verdad para izquierda");
					com.enviarObjeto(new Balance("izq",true));
					dijeVerdad=true;
					
				} else {
					System.out.println("mentira para izquierda");
					com.enviarObjeto(new Balance("izq",false));
					dijeVerdad=false;
				}
				estadoRonda+=1;
				
				
			} else if((mouseX>855 && mouseX<955) && (mouseY>426 && mouseY<530)){
				System.out.println("derecha");
				ArrayList<String> secuencia= rutas.getSequence();
				if(secuencia.get(0).equals("der")){
					System.out.println("verdad para derecha");
					com.enviarObjeto(new Balance("der",true));
					dijeVerdad=true;
				} else {
					System.out.println("mentira para derecha");
					com.enviarObjeto(new Balance("der",false));
					dijeVerdad=false;
				}
				
				estadoRonda+=1;
				
				
			} else if ((mouseX>755 && mouseX<870) && (mouseY>520 && mouseY<620)){
				System.out.println("atras");
				ArrayList<String> secuencia= rutas.getSequence();
				if(secuencia.get(0).equals("abajo")){
					System.out.println("verdad para abajo");
					com.enviarObjeto(new Balance("abajo",true));
					dijeVerdad=true;
				} else {
					System.out.println("mentira para abajo");
					com.enviarObjeto(new Balance("abajo",false));
					dijeVerdad=false;
				}
				estadoRonda+=1;
				
			}
			
			break;

		case 1:
			app.println(mouseX,mouseY);
			if( (mouseX>752 && mouseX<865) && (mouseY>246 && mouseY<335) && mostrarBalance==null){
				System.out.println("derecho");
				startNode = rutas.gs.getNodeAt(g[0].xf()+rutas.offX - rutas.offX, g[0].yf()+rutas.offY-20 - rutas.offY, 0,10.0f); 
				int imprimir= (int) (g[0].yf()+rutas.offY-5) ;
				app.println("posicion apuntada:" + imprimir);
				if(startNode!=null){
				System.out.println("id del nodo: "+startNode.id());
				
				g[0]=startNode;
				
				com.enviarObjeto(new Mensaje(startNode));
				
				if(sugerencia.equals("arriba")){
					confie=true;
				} else{
					confie=false;
				}
				
			
				
				}
				//-----------------
				app.delay(300);
				com.enviarObjeto(new BalanceCompleto(confie, dijeVerdad));
				//---------------
				//estadoRonda+=1;
				mostrarBalance="yes";
			
			} else if((mouseX>665 && mouseX<760) && (mouseY>333 && mouseY<430) && mostrarBalance==null){
				System.out.println("izquierda");
					startNode = rutas.gs.getNodeAt(g[0].xf()+rutas.offX-17 - rutas.offX, g[0].yf()+rutas.offY - rutas.offY, 0, 16.0f); 
					if(startNode!=null){
					System.out.println("id del nodo: "+startNode.id());
					
					g[0]=startNode;
					
				   com.enviarObjeto(new Mensaje(startNode));
				   
					if(sugerencia.equals("izq")){
						confie=true;
					} else{
						confie=false;
					}
					
					
					
					}
					//-----------------
					app.delay(300);
					com.enviarObjeto(new BalanceCompleto(confie, dijeVerdad));
					//---------------
					//estadoRonda+=1;
					mostrarBalance="yes";
					
			} else if((mouseX>860 && mouseX<960) && (mouseY>330 && mouseY<437) && mostrarBalance==null){
				System.out.println("derecha");
				startNode = rutas.gs.getNodeAt(g[0].xf()+rutas.offX+20 - rutas.offX, g[0].yf()+rutas.offY - rutas.offY, 0, 10.0f); 
				if(startNode!=null){
				System.out.println("id del nodo: "+startNode.id());
				
				g[0]=startNode;
				
				com.enviarObjeto(new Mensaje(startNode));
				
				
				if(sugerencia.equals("der")){
					confie=true;
				} else{
					confie=false;
				}
				
				
				
				}
				//-----------------
				app.delay(300);
				com.enviarObjeto(new BalanceCompleto(confie, dijeVerdad));
				//---------------
				//estadoRonda+=1;
				
				mostrarBalance="yes";
				
				
			} else if ((mouseX>755 && mouseX<865) && (mouseY>425 && mouseY<520) && mostrarBalance==null){
				System.out.println("atras");
				startNode = rutas.gs.getNodeAt(g[0].xf()+rutas.offX - rutas.offX, g[0].yf()+rutas.offY+20 - rutas.offY, 0, 10.0f); 
				if(startNode!=null){
				System.out.println("id del nodo: "+startNode.id());
				
				g[0]=startNode;
				
				com.enviarObjeto(new Mensaje(startNode));
				
				
				if(sugerencia.equals("abajo")){
					confie=true;
				} else{
					confie=false;
				}
				
			
				
				}
				//-----------------
				app.delay(300);
				com.enviarObjeto(new BalanceCompleto(confie, dijeVerdad));
				//---------------
				//estadoRonda+=1;
				
				mostrarBalance="yes";
			}
			
			
			break;
			
		case 2:
			if ((mouseX>910 && mouseX<955) && (mouseY>170 && mouseY<215)){
				System.out.println("cerrar");
				com.enviarObjeto(new Mensaje(true));
				ckeckPopUp=true;
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
				app.fill(255, 255, 255);
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
			
			

			@Override
			public void run() {
				
				while(true){
					try {
						
						switch (estados) {
						case 1:
							if(mj!= null){
								checkInstruccionesOtroJugador= mj.checkeado;
							}
							
								if(checkInstrucciones && checkInstruccionesOtroJugador){
									rutas= new PathFinder(app,311,176);
									GraphNode[] o= rutas.rNodes;
									   com.enviarObjeto(new Mensaje(o));
								estados=2;
								}
						
							

							break;

						case 2:
							if(mj!= null){
								checkPopUpOtroJugador= mj.checkeado;
							}
							
								if(ckeckPopUp && checkPopUpOtroJugador){
									
									ckeckPopUp=false;
									checkPopUpOtroJugador=false;
									checkInstruccionesOtroJugador=false;
									sugerencia=null;
									mostrarBalance=null;
									mj=null;
									bl=null;
									balanceCo=null;
									estadoRonda=0;
									if(energiaInicial>0){
										energiaInicial-=10;
										}
									acumuladoDesiciones=0;
									if (energiaInicial==0){
										tipoDePantallaFinal=0;
										estados=3;
									}
									
									//-------------------------
									if(verdadOtroJugador){
									    app.println("El otro jugador sugurio: "+sugerencia +". dijo : verdad");
									} else if(!verdadOtroJugador) {
										app.println("El otro jugador sugurio: "+sugerencia +". dijo : mentira");
									}
									
									
									if(confie){
									    app.println("confié en el otro jugador");
									} else if(!confie) {
										app.println("no confié en el otro jugador");
									}
									
									if(dijeVerdad){
									app.println("Dije la verdad");
									} else {
										app.println("no dije la verdad");
									}
									
									if(elOtroJugadorConfio){
										app.println("el otro jugador confio");
										} else {
											app.println("el otro jugador no confio");
										}
									
									app.println("el acumulado es: "+acumuladoDesiciones);
									if(acumuladoDesiciones==0){
										
									app.println("el balance es: 0%");
									} else if(acumuladoDesiciones==1){
										
										app.println("el balance es: 25%");
									}
									else if(acumuladoDesiciones==2){
									
										app.println("el balance es: 50%");
									} else if(acumuladoDesiciones==3){
									
										app.println("el balance es: 75%");
									} else if(acumuladoDesiciones==4){
									
										app.println("el balance es: 100%");
									}
									//-----------------------------------
									
									
									
									
									if(gane && elOtroJugadorGano){
										estados=3;
										tipoDePantallaFinal=2;
									} else if(elOtroJugadorGano && !gane){
										tipoDePantallaFinal=0;
										estados=3;
									}  else if(gane && !elOtroJugadorGano){
										tipoDePantallaFinal=1;
										estados=3;
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
								
								if(g!=null && g[0].id() == g[g.length-1].id() && !gane){
									gane=true;
								
									com.enviarObjeto(new GanadorMensaje(gane));
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




	public void update(Observable o, Object arg) {
	Object ob=arg;
	
		if(ob instanceof Mensaje){
			mj=(Mensaje) ob;
			if(mj.nodos!=null && estados==2){
				g=mj.nodos;
			}
			
			if(mj.nodoInicial!=null && estados==2){
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
			
		} else if(ob instanceof GanadorMensaje){
			elOtroJugadorGano=true;
		}
		
	}

}

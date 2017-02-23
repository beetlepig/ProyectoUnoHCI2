package Karlos.HCI2ProyectoUno;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class ComunicacionServer extends Observable{
	
	/*
	 * En data archivos con opciones de puestas, el servidor escoje con cual
	 * trabajar y le indica al cliente que archivo cargar.
	 */
	private int puerto;
	private ServerSocket ss;
	Socket cliente;

	
	public boolean conectado=false;


	public ComunicacionServer(int i) 
	{
		puerto = i;
System.out.println("esperando cliente");
		new Thread(hiloInit()).start();;
	}
	
	private Runnable hiloInit(){
		Runnable i= new Runnable() {
			
			@Override
			public void run() {
				try {
					ss = new ServerSocket(puerto);
					System.out.println("Socket server iniciado");
					cliente = ss.accept();
					System.out.println("Conectado exitosamente :)");
					conectado=true; //por si el otro metodo falla
				} catch (IOException e) {
					e.printStackTrace();
				}
				new Thread(hilo()).start();
				
			}
		};
		return i;
	}

	private Runnable hilo() 
	{
		Runnable r= new Runnable() {
			
			public void run() {
				while (true) {
					recibir();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		};
		return r;
	}

	public void recibir() 
	{
		
		
		InputStream entrada;
		ObjectInputStream entradaObjeto;
		Object o;
		
		try {
		    entrada= cliente.getInputStream();
			entradaObjeto= new ObjectInputStream(entrada);
			o= entradaObjeto.readObject();
            System.out.println("llego objeto");
            
            setChanged();
            notifyObservers(o);
            clearChanged();
			 


		} catch (IOException e) {
			
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * El protocolo de envio es el siguiente: puerta(entero). El protocolo de
	 * envio de confianza es: confianza/ValorHonestidad/ValorConfianza.
	 */
	public void enviarString(String msj) 
	{
		OutputStream salidaBytes;
		DataOutputStream salidaDatos;
		try {
			salidaBytes = cliente.getOutputStream();
			salidaDatos = new DataOutputStream(salidaBytes);
			salidaDatos.writeUTF(msj);
			System.out.println("mensaje enviado: " + msj);
			salidaDatos.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void enviarObjeto(Object o){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ObjectOutputStream salidaObjeto;
				OutputStream salida;
				
				try{
					salida= cliente.getOutputStream();
					salidaObjeto= new ObjectOutputStream(salida);
					salidaObjeto.writeObject(o);
					System.out.println("objeto enviado");
					salidaObjeto.flush();
				} catch (IOException e) {
				     e.printStackTrace();
				}
				
			}
		}).start();
	
		
	}



}

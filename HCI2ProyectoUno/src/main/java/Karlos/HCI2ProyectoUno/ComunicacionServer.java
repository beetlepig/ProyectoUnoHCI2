package Karlos.HCI2ProyectoUno;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ComunicacionServer {
	
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
						Thread.sleep(100);
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
		
		ObjectInputStream entradaObjeto;
		
		try {
		
			entradaObjeto= new ObjectInputStream(cliente.getInputStream());
			

			


		} catch (IOException e) {
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



}

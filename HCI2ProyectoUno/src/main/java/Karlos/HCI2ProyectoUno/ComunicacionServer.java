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
	private Socket cliente;



	public ComunicacionServer(int i) 
	{
		puerto = i;

		try {
			ss = new ServerSocket(i);
			cliente = ss.accept();
			System.out.println("Conectado exitosamente :)");
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(hilo()).start();;
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

package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

public class ComunicacionCliente extends Observable{

	/*
	 * En data archivos con opciones de puestas, el servidor escoje con cual
	 * trabajar y le indica al cliente que archivo cargar.
	 */
	private int puerto;

	Socket servidor;
	


	public ComunicacionCliente(int i) {
		puerto = i;

		new Thread(hiloInit()).start();
	}
	
	
	private Runnable hiloInit(){
		Runnable r= new Runnable() {
			
			@Override
			public void run() {
				while(servidor ==null){
					try {
						servidor = new Socket(InetAddress.getByName("172.30.164.43"), puerto);
						System.out.println("exito!");
						new Thread(hilo()).start(); //si se conecta empiezo hilo para comenzar a recibir
						
					} catch (UnknownHostException uhe) {
						uhe.printStackTrace();
					} catch (IOException io) {
						io.printStackTrace(); //si se rechaza la connection, lo vuelvo a intentar
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		};
		return r;
	}

	/*
	 * En data archivos con opciones de puestas, el servidor escoje con cual
	 * trabajar y le indica al cliente que archivo cargar.
	 */

	private Runnable hilo() {
		Runnable r= new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					recibir();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		};
		return r;
		

	}

	public void recibir() {
		
		InputStream entradaBytes;
		ObjectInputStream entradaObjeto;
		Object o;
		try {
			entradaBytes = servidor.getInputStream();
			entradaObjeto = new ObjectInputStream(entradaBytes);
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
	public void enviar(String msj) {
		OutputStream salidaBytes;
		DataOutputStream salidaDatos;
		try {
			salidaBytes = servidor.getOutputStream();
			salidaDatos = new DataOutputStream(salidaBytes);
			salidaDatos.writeUTF(msj);
			System.out.println("mensaje enviado: " + msj);
			salidaDatos.flush();
			// Tras enviar se aumenta el turno en 1 y se envia;
		
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
					salida= servidor.getOutputStream();
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

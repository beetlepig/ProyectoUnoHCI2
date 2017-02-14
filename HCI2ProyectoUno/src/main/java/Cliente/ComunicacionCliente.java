package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ComunicacionCliente {

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
						servidor = new Socket(InetAddress.getByName("127.0.0.1"), puerto);
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
						Thread.sleep(100);
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
		DataInputStream entradaDatos;
		try {
			entradaBytes = servidor.getInputStream();
			entradaDatos = new DataInputStream(entradaBytes);
			String mensaje = entradaDatos.readUTF();
			System.out.println("aviso recibido:  " + mensaje);

			// si llega turno revisa si mi turno es igual o menor al turno del
			// otro jugador, si es asi turnoActivo=true;
			if (mensaje.contains("turno")) {
				String[] partes = mensaje.split("/");

			
			}


		} catch (IOException e) {
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



}

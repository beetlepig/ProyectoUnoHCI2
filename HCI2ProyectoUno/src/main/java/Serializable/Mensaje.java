package Serializable;

import java.io.Serializable;

import pathfinder.GraphNode;



public class Mensaje implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean checkeado;
	public GraphNode[] nodos;
	public GraphNode nodoInicial;
	
	
	public Mensaje (boolean check){
		checkeado= check;
		
	}
	
	public Mensaje (GraphNode[] o){
		nodos=o;
	}
	
	public Mensaje (GraphNode nodoInicial){
		this.nodoInicial= nodoInicial;
	}
	
	
	
}

package Serializable;

import java.io.Serializable;

import pathfinder.GraphNode;



public class Mensaje implements Serializable {
	public boolean checkeado;
	Object nodos;
	
	
	public Mensaje (boolean check){
		checkeado= check;
		
	}
	
	public Mensaje (Object o){
		nodos=o;
	}
	
	
	
}

package Serializable;

import java.io.Serializable;

import pathfinder.GraphNode;



public class Mensaje implements Serializable {
	public boolean checkeado;
	GraphNode[] nodos;
	
	
	public Mensaje (boolean check){
		checkeado= check;
		
	}
	
	public Mensaje (GraphNode[] g){
		nodos=g;
	}
	
	
	
}

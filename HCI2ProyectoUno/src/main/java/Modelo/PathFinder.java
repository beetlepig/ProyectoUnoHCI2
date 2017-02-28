package Modelo;

import java.util.ArrayList;

import pathfinder.AshCrowFlight;
import pathfinder.AshManhattan;
import pathfinder.Graph;
import pathfinder.GraphEdge;
import pathfinder.GraphNode;
import pathfinder.GraphSearch_Astar;
import pathfinder.GraphSearch_BFS;
import pathfinder.GraphSearch_DFS;
import pathfinder.GraphSearch_Dijkstra;
import pathfinder.IGraphSearch;
import processing.core.PApplet;
import processing.core.PImage;

public class PathFinder {

	public Graph gs;
	PImage graphImage;
	public int start;
	int end;
	public float nodeSize;

	int graphNo = 0;
	int algorithm;

	int overAlgorithm, overOption, overGraph;
	public int offX = 95;
	public int offY = 370;

	boolean[] showOption = new boolean[3];

	public GraphNode[] gNodes, rNodes;
	GraphEdge[] gEdges, exploredEdges;
	public IGraphSearch pathFinder;

	// Used to indicate the start and end nodes as selected by the user.
	public GraphNode startNode, endNode;

	boolean selectMode = false;
	long time;
	PApplet app;

	public PathFinder(PApplet app, int comienzo, int fin) {
		this.app=app;
	//	offX = (int) (app.width*0.6F);
	//	offY = (int) (app.height*0.5F);

		/*
		 * MAP 1 : The maze created in the same way as the first map but has
		 * single width passages to make a maze like map.
		 */
		// graphNo = 1;
		nodeSize = 15.0f;
		graphImage = app.loadImage("../data/Laberinto-1.png");
		gs = new Graph();
		makeGraphFromBWimage(gs, graphImage, null, 16, 17, false);
		gNodes = gs.getNodeArray();
		end = fin; //157 server
		do
			start = comienzo; //105 server
		while (start == end);
		gs.compact();

		// The GUI will need to know initial options selected
		algorithm = 3;
		graphNo = 1;

		// Get arrays of both the nodes and edges used by the
		// selected graph.
		gNodes = gs.getNodeArray();
		// Create a path finder object based on the algorithm
		pathFinder = makePathFinder(gs, algorithm);
		usePathFinder(pathFinder);
		System.out.println("--------------Pasos realizados-----------");
		System.out.println(getSequence());
	}
 
	public void pintar() {
		// background(backImage);
		app.pushMatrix();
		app.translate(offX, offY);
		if (graphImage != null)
			app.image(graphImage, +6, -6);
		// println(mouseX+" "+mouseY);

		/*
		 * if(showOption[0] || graphNo == 3) drawNodes();
		 */

		drawRoute(rNodes, app.color(0, 250, 0), 5.0f); //Esta linea de codigo dibuja la rota.

		if (selectMode) {// linea feedback de seleccion de nodos.
			app.stroke(0);
			app.strokeWeight(1.5f);
			if (endNode != null)
				app.line(startNode.xf(), startNode.yf(), endNode.xf(), endNode.yf());
			else
				app.line(startNode.xf(), startNode.yf(), app.mouseX - offX, app.mouseY - offY);
		}

		drawNodes(); // Esta linea de codigo dibuja los nodos (los puntos del fondo), puede ser comentada para no verlos
		app.popMatrix();
	}
	
	
	

	public void mousePressed(int mouseX, int mouseY) {
		// Only consider a mouse press if over the map
		/*
		 * Si es mouse esta dentro del mapa entonces asigna como nodo inicial 
		 * el nodo sobre el cual se dio click
		 */
		if (mouseX < offX + graphImage.width && mouseY < offY + graphImage.height && graphImage.width > offX
				&& mouseY > offY) {
			
		  startNode = gs.getNodeAt(mouseX - offX, mouseY - offY, 0, 16.0f); // este es el metodo que crea el nodo inicial
			if (startNode != null)
			  System.out.println(startNode.id());
				selectMode = true;
		}
	}

	public void mouseDragged(int mouseX, int mouseY) {
		/*
		 * Mientras se arrastra el mouse, asigna como nodo final (destino) el ultimo nodo
		 * sobre el cual paso.
		 */
		if (selectMode){
			//System.out.println("entro");
			endNode = gs.getNodeAt(mouseX - offX, mouseY - offY, 0, 16.0f);
			//este es el metodo que crea el nodo final
		}
	}

	
	public void mouseReleased() {
		/*
		 * En este momento cuando se suelta el mouse sobre un nodo es seleccionado como nodo final
		 */
		if (selectMode && endNode != null && startNode != null && startNode != endNode) {
			/*si ninguno de los nodos necesarios (inicial y final) son nulos entonces crea los dos puntos
			 *y realiza el algoritmos para generar la ruta mas corta.
			 */
			System.out.println("entro metodo");
			start = startNode.id();
			end = endNode.id();
			usePathFinder(pathFinder); 
		}
		selectMode = false;
		startNode = endNode = null;
		
		
		System.out.println("--------------Pasos realizados-----------");
		System.out.println(getSequence());
	}

	
	/**
	 * Metodo para mostrar en consola el recorrido de la ruta mas corta
	 * @return ArrayList<String> nodos del camino rapido.
	 */
	public ArrayList<String> getSequence() {
		ArrayList<String> test = new ArrayList<String>();

		for (int i = 0; i < rNodes.length - 1; i++) {
			// X
			if (rNodes[i].x() < rNodes[i + 1].x()) {
				test.add("der");
			}
			if (rNodes[i].x() > rNodes[i + 1].x()) {
				test.add("izq");
			}
			// Y
			if (rNodes[i].x() == rNodes[i + 1].x()) {
				if (rNodes[i].y() < rNodes[i + 1].y()) {
					test.add("abajo");
				}
				if (rNodes[i].y() > rNodes[i + 1].y()) {
					test.add("arriba");
				}
			}
		}
		return test;
	}
	
	
/**--------- METODOS NECESARIOS PARA LA LIBRERIA Y SU FUNCIONAMIENTO -----------
 * Desde este punto encontraras los metodos que se requieren para el 
 * funcionamiento de la libreria. Recomendamos solo hacer modificaciones 
 * a los metodos o funciones que tengan funciones visuales, de resto no
 * debe ser modificado. */
	
	public void usePathFinder(IGraphSearch pf) {
		time = System.nanoTime();
		pf.search(start, end, true);
		time = System.nanoTime() - time;
		rNodes = pf.getRoute();
		exploredEdges = pf.getExaminedEdges();
	}

	/**Metodo makePathFinder
	 * Este metodo crea el algoritmo que se utilizara para buscar la ruta mas
	 * corta, existen 4 algoritmos que pueden ser utilizados, los algoritmos 
	 * Astar son los algoritmos que buscan la ruta mas corta (3 o 4).
	 * @param graph
	 * @param pathFinder
	 * @return
	 */
	IGraphSearch makePathFinder(Graph graph, int pathFinder) {
		IGraphSearch pf = null;
		float f = 1.0f;
		switch (pathFinder) {
		case 0:
			pf = new GraphSearch_DFS(gs);
			break;
		case 1:
			pf = new GraphSearch_BFS(gs);
			break;
		case 2:
			pf = new GraphSearch_Dijkstra(gs);
			break;
		case 3:
			pf = new GraphSearch_Astar(gs, new AshCrowFlight(f));
			break;
		case 4:
			pf = new GraphSearch_Astar(gs, new AshManhattan(f));
			break;
		}
		return pf;
	}

	/**Metodo drawNodes:
	 * se utiliza para pintar los nodos del mapa, para cambiar el color modificar
	 * el fill, para cambiar los bordes modificar el stroke y para cambiar la forma modificar
	 * el ellipse.
	 */
	void drawNodes() {
		app.pushStyle();
		app.noStroke();
		app.fill(100, 100, 100, 72);
		for (GraphNode node : gNodes)
			app.ellipse(node.xf(), node.yf(), nodeSize, nodeSize);
		app.popStyle();
	}
	
	/**Metodo drawRoute
	 * Metodo para pintar la linea que indica el camino mas corto para llegar
	 * desde el punto inicial seleccionado hasta el distino seleccionado.
	 * @param r - Arreglo de nodos.
	 * @param lineCol - Color de la linea.
	 * @param sWeight - Grosor de la linea.
	 */
	public void drawRoute(GraphNode[] r, int lineCol, float sWeight) {
		if (r.length >= 2) {
			app.pushStyle();
			app.stroke(lineCol);
			app.strokeWeight(sWeight);
			app.noFill();
			for (int i = 1; i < r.length; i++){
				app.line(r[i - 1].xf(), r[i - 1].yf(), r[i].xf(), r[i].yf());
			}
			// Route start node
			app.strokeWeight(2.0f);
			app.stroke(0, 0, 160);
			app.fill(0, 0, 255);
			app.ellipse(r[0].xf(), r[0].yf(), nodeSize, nodeSize);
			// Route end node
			app.stroke(0, 250, 0);
			app.fill(0, 250, 0);
			app.ellipse(r[r.length - 1].xf(), r[r.length - 1].yf(), nodeSize, nodeSize);
			
			app.popStyle();
		}
	}

	/**
	 * Metodo makeGraphFromBWimage
	 * Este metodo se utiliza para crear los nodos de un mapa a partir de una imagen
	 * en blanco y negra, similar a una matriz tipo pacman. Tener en cuenta que solo
	 * reconoce negros, blancos y grises, donde los blancos son los caminos y los negros
	 * las paredes u obstaculos, los grises cuentan como camino pero pueden ser utilizados
	 * para designar areas especiales en el mapa.
	 * @param g - Objeto Graph
	 * @param backImg - Imagen en balnco y negro que crea el mapa
	 * @param costImg - puede ser null
	 * @param tilesX - 20 funciona bien 
	 * @param tilesY - 20 funciona bien
	 * @param allowDiagonals
	 */
	void makeGraphFromBWimage(Graph g, PImage backImg, PImage costImg, int tilesX, int tilesY, boolean allowDiagonals) {
		int dx = backImg.width / tilesX;
		int dy = backImg.height / tilesY;
		int sx = dx / 2, sy = dy / 2;
		// use deltaX to avoid horizontal wrap around edges
		int deltaX = tilesX + 3; // must be > tilesX

		float hCost = dx, vCost = dy, dCost = app.sqrt(dx * dx + dy * dy);
		float cost = 0;
		int px, py, nodeID, col;
		GraphNode aNode;

		py = sy;
		for (int y = 0; y < tilesY; y++) {
			nodeID = deltaX * y + deltaX;
			px = sx;
			for (int x = 0; x < tilesX; x++) {
				// Calculate the cost
				if (costImg == null) {
					col = backImg.get(px, py) & 0xFF;
					cost = 1;
				} else {
					col = costImg.get(px, py) & 0xFF;
					cost = 1.0f + (256.0f - col) / 16.0f;
				}
				// If col is not black then create the node and edges
				if (col != 0) {
					aNode = new GraphNode(nodeID, px, py);
					g.addNode(aNode);
					if (x > 0) {
						g.addEdge(nodeID, nodeID - 1, hCost * cost);
						if (allowDiagonals) {
							g.addEdge(nodeID, nodeID - deltaX - 1, dCost * cost);
							g.addEdge(nodeID, nodeID + deltaX - 1, dCost * cost);
						}
					}
					if (x < tilesX - 1) {
						g.addEdge(nodeID, nodeID + 1, hCost * cost);
						if (allowDiagonals) {
							g.addEdge(nodeID, nodeID - deltaX + 1, dCost * cost);
							g.addEdge(nodeID, nodeID + deltaX + 1, dCost * cost);
						}
					}
					if (y > 0)
						g.addEdge(nodeID, nodeID - deltaX, vCost * cost);
					if (y < tilesY - 1)
						g.addEdge(nodeID, nodeID + deltaX, vCost * cost);
				}
				px += dx;
				nodeID++;
			}
			py += dy;
		}
	}

	void makeNode(String s, Graph g) {
		int nodeID;
		float x, y, z = 0;
		String part[] = app.split(s, " ");
		if (part.length >= 3) {
			nodeID = Integer.parseInt(part[0]);
			x = Float.parseFloat(part[1]);
			y = Float.parseFloat(part[2]);
			if (part.length >= 4)
				z = Float.parseFloat(part[3]);
			g.addNode(new GraphNode(nodeID, x, y, z));
		}
	}

	/**
	 * Creates an edge(s) between 2 nodes.
	 * 
	 * Each line of the configuration file has either 3 or 4 entries, the first
	 * 2 are the node id numbers, for the from and to nodes. If either node does
	 * not exist then the edge will not be created.
	 * 
	 * The third value is the cost from-to nodes and the fourth the cost to-from
	 * node (i.e. the return cost). So it is possible to create a bidirectional
	 * route between the nodes with different costs.
	 * 
	 * Note if a fourth value is not provided then it will not create the edge
	 * for the return route.
	 * 
	 * In both cases if the cost is =0 then the cost is calculated as the
	 * euclidean distance (shortest) between the nodes. If the cost <0 then that
	 * edge will not be created.
	 * 
	 * @param s
	 *            a line from the configuration file.
	 * @param g
	 *            the graph to add the edge.
	 */
	void makeEdge(String s, Graph g) {
		int fromID, toID;
		float costOut = 0, costBack = 0;
		String part[] = app.split(s, " ");
		if (part.length >= 3) {
			fromID = Integer.parseInt(part[0]);
			toID = Integer.parseInt(part[1]);
			try {
				costOut = Float.parseFloat(part[2]);
			} catch (Exception excp) {
				costOut = -1;
			}
			try {
				costBack = Float.parseFloat(part[3]);
			} catch (Exception excp) {
				costBack = -1;
			}
			if (costOut >= 0)
				g.addEdge(fromID, toID, costOut);
			if (costBack >= 0)
				g.addEdge(toID, fromID, costBack);
		}
	}

}

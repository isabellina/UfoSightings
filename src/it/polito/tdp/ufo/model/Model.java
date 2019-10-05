package it.polito.tdp.ufo.model;

import java.time.Year;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	//I vertici del grafo qui sono stringhe semplici non abbiamo quindi bisogno di una mappa ma ci basta una lista
	
	
	
	
	private SightingsDAO dao;
	private List<String> stati;
	private Graph<String,DefaultEdge> grafo;
	

	public Model() {
		this.dao = new SightingsDAO();
	}
	
	
	
	public List<AnnoCount> getAnni() {
		return this.dao.getAnni();
	}
	
	
	public void creaGrafo(Year y) {
		this.grafo = new SimpleDirectedGraph<String,DefaultEdge>(DefaultEdge.class);
	    this.stati = this.dao.getStati(y);
	    Graphs.addAllVertices(this.grafo, this.stati);
	    
	    for(String s: this.grafo.vertexSet()) {
	    	for(String r: this.grafo.vertexSet()) {
	    		if(!s.equals(r)) {
	    			if(this.dao.esisteArco(s,r,y)) {
	    				this.grafo.addEdge(s, r);
	    			}
	    		}
	    	}
	    }
	    
	    
	}
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}



	public String getStati() {
		// TODO Auto-generated method stub
		return this.getStati();
	}
	
	
	public List<String> getSuccessori(String stato){
		return Graphs.successorListOf(this.grafo, stato);
	}
	
	public List<String> getPredecessori(String stato){
		return Graphs.predecessorListOf(this.grafo, stato);
		
	}
	
	
	public List<String> getRaggiungibili(){
		List<String> raggiungibili = new LinkedList<String>();
		return null;   //da cambiare!!!!
	}
}

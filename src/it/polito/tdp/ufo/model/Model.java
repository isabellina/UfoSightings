package it.polito.tdp.ufo.model;

import java.time.Year;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	//I vertici del grafo qui sono stringhe semplici non abbiamo quindi bisogno di una mappa ma ci basta una lista
	
	
	private List<String> ottima = new LinkedList<String>();
	
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



	public List<String> getStati(Year y) {
		// TODO Auto-generated method stub
		return this.dao.getStati(y);
	}
	
	
	public List<String> getSuccessori(String stato){
		return Graphs.successorListOf(this.grafo, stato);
	}
	
	public List<String> getPredecessori(String stato){
		return Graphs.predecessorListOf(this.grafo, stato);
		
	}
	
	
	//VISITA DEL GRAFO IN AMPIEZZA O PROFONDITA'
	
	public List<String> getRaggiungibili(String stato){
		List<String> raggiungibili = new LinkedList<String>();
		DepthFirstIterator<String, DefaultEdge> dp =new DepthFirstIterator<String, DefaultEdge>(this.grafo,stato);
		
		dp.next(); // non voglio il primo elemento faccio un dp a vuoto in modo che scarti il primo elemento
		
		while(dp.hasNext()) {
			raggiungibili.add(dp.next());
		}
		
		
		return raggiungibili;
	}
	
	
	
	/**
	 * RICORSIONE!
	 */
	
	
	public List<String> getPercorsoMassimo(String partenza){
		this.ottima = new LinkedList<String>();
		List<String> parziale = new LinkedList<String>();
		parziale.add(partenza);
		
		cercaPercorso(parziale);
		
		
		
		return this.ottima;
	}



	private void cercaPercorso(List<String> parziale) {
		
		if(parziale.size() > ottima.size()) {
			this.ottima = new LinkedList(parziale); //clono la lista
		}
		
		List<String> candidati = this.getSuccessori(parziale.get(parziale.size()-1));
		for(String candidato: candidati) {
			if(!parziale.contains(candidato)) {
				parziale.add(candidato);
				this.cercaPercorso(parziale); 
				parziale.remove(parziale.size()-1);
		}
	}
	
	
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

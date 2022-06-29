package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
    private PremierLeagueDAO dao;
	
	private Graph<Match,DefaultWeightedEdge> grafo;
	private Map<Integer,Match> idMap;
	
	
	private List<Match> listaMigliore;
	
	public List<Match> cercaLista(Match m1, Match m2){
		List<Match> matchValidi = Graphs.neighborListOf(grafo, m1);
		
		List<Match> parziale = new ArrayList<>();
		listaMigliore = new ArrayList<>();
		parziale.add(m1);
		
		cerca(parziale,matchValidi,m2);
		
		return listaMigliore;
	}
	
	private void cerca(List<Match> parziale, List<Match> matchValidi, Match m2) {
		//controllo soluzione migliore
		if(parziale.get(parziale.size()-1).equals(m2)) {
			if (getPeso(parziale) > getPeso(listaMigliore)) {
				listaMigliore = new ArrayList<>(parziale);
			}
			else 
				return;
		}
		
		for(Match m : matchValidi) {
			if(!parziale.contains(m) && m.getTeamAwayID()!=parziale.get(parziale.size()-1).getTeamHomeID() && m.getTeamHomeID()!=parziale.get(parziale.size()-1).getTeamAwayID() ) {
				parziale.add(m);
				cerca(parziale, Graphs.neighborListOf(grafo, m),m2);
				parziale.remove(parziale.size()-1);
			}
		}
		
		
	}
	
    public int getPeso(List<Match> parziale) {
		int peso = 0;
		for (int i=1;i<parziale.size();i++) {
			peso+=grafo.getEdgeWeight(grafo.getEdge(parziale.get(i-1), parziale.get(i)));
		}
		return peso;
	}
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<>();
		
		this.dao.listAllMatches(idMap);
	}
	
	public void creaGrafo(int min, Integer mese) {
		//creo il grafo
		this.grafo = 
				new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, 
				this.dao.getVertici(mese, this.idMap));
		
		//aggiungo gli archi
		for(Adiacenza a : this.dao.getArchi(min, mese, idMap)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getM1(), 
					a.getM2(), a.getPeso());
		}
		
		System.out.println("Grafo creato!");
		System.out.println(String.format("# Vertici: %d", 
				this.grafo.vertexSet().size()));
		System.out.println(String.format("# Archi: %d", 
				this.grafo.edgeSet().size()));
	}
	
	public List<Match> getVertici(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else 
			return true;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Adiacenza> getConnessioneMassima() {
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		int max = 0;
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			int peso = (int) this.grafo.getEdgeWeight(e);
			if(peso > max) {
				result.clear();
				result.add(new Adiacenza(this.grafo.getEdgeSource(e),
						this.grafo.getEdgeTarget(e), peso));
				max = peso;
			} else if (peso == max) {
				result.add(new Adiacenza(this.grafo.getEdgeSource(e),
						this.grafo.getEdgeTarget(e), peso));
			}
			
		}
		
		return result;
	}
	
}

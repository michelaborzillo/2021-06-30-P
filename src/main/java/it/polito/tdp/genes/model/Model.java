package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.ListCellRenderer;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.genes.db.GenesDao;

public class Model {

	Graph<String, DefaultWeightedEdge> grafo;
	GenesDao dao;
	List<String> list;
	List<String> best;
	
	public Model() {
		dao= new GenesDao();
		
	}
	
	
	public void creaGrafo() {
		this.grafo= new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		list= dao.getVertici();
		Graphs.addAllVertices(this.grafo, list);
		for (String s1: this.list) {
			for (String s2: this.list) {
				if (!s1.equals(s2)) {
					int peso= dao.getPeso(s1, s2);
					if (peso>0) {
						Graphs.addEdge(this.grafo, s1, s2, peso);
					}
				}
			}
		}
		
		
	}
	public int nVertici() {
		return this.grafo.vertexSet().size();
		
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	
	}
	
	
	public List<String> getAdiacenza(String vertice) {
		List<String> vicini= new LinkedList<String>(Graphs.neighborListOf(this.grafo, vertice));
		return vicini;
	}


	public List<String> getVertici() {
	return dao.getVertici();
	
	}
	
	
	public int pesoGrafo(String s1, String s2) {
		return dao.getPeso(s1, s2);
	}
	


	/*public List<String> cercaLista(String s) {
		Set<String> componenteConnessa;
		ConnectivityInspector<String, DefaultWeightedEdge> ci= new ConnectivityInspector<String, DefaultWeightedEdge>(this.grafo);
		componenteConnessa=ci.connectedSetOf(s);
		List<String> percorsoValido= new ArrayList<String>();
		percorsoValido.add(s);
		componenteConnessa.remove(s);
		percorsoValido.addAll(componenteConnessa);
		List<String> parziale= new ArrayList<String>();
		best= new ArrayList<String>();
		parziale.add(s);
		ricorsione(parziale, percorsoValido, 1);
		return best;
	}


private void ricorsione(List<String> parziale, List<String> percorsoValido, int L) {
	if (parziale.size()>best.size()) {
		best = new ArrayList<>(parziale);
	}
	
	if (L==percorsoValido.size()) {
		return;
	}
	parziale.add(percorsoValido.get(L));
	ricorsione(parziale, percorsoValido, L+1);
	parziale.remove(percorsoValido.get(L));
	ricorsione(parziale, percorsoValido,L+1 );
	
}*/
	
	
	
	public List<String> cercaLista(String sorgente) {
		best= new LinkedList<>();
		List<String> parziale = new LinkedList<>();
		parziale.add(sorgente);
		cerca(parziale);
		return best;
	}


	private void cerca(List<String> parziale) {
	if (parziale.size()==this.nVertici()) {
		if (best.size()==0 || this.getPeso(parziale)>this.getPeso(best)) {
			best= new LinkedList<>(parziale);
			
		}
		return;
	}
	for (String v: Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
		if (!parziale.contains(v)) {
			parziale.add(v);
			this.cerca(parziale);
			parziale.remove(parziale.size()-1);
		}
	}
		
	}
	
	
	private int getPeso(List<String> parziale) {
		int pesoTot=0;
		for (int i=0; i<parziale.size(); i++) {
			if (i!=parziale.size()-1) {
				String v1=parziale.get(i);
				String v2= parziale.get(i+1);
				for (DefaultWeightedEdge e: this.grafo.edgeSet())  {
					if (this.grafo.getEdgeSource(e).compareTo(v1)==0 && this.grafo.getEdgeTarget(e).compareTo(v2)==0) {
						pesoTot+=this.grafo.getEdgeWeight(e);
					}
					
					else if (this.grafo.getEdgeSource(e).compareTo(v2)==0 && this.grafo.getEdgeTarget(e).compareTo(v1)==0)
						pesoTot+=this.grafo.getEdgeWeight(e);
				}
			}
		}
		return pesoTot;
	}
	
	
}
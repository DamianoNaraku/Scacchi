package cleii.scacchi.pezzi;

import java.util.ArrayList;

import cleii.scacchi.*;

public class Regina extends Pezzo{
	private Torre tom;
	private Alfiere alfio;
	public Regina(boolean p) {
		super(p, "q");
		tom = new Torre(p);
		alfio = new Alfiere(p);
		// TODO Auto-generated constructor stub
	}

	// implementazioni di metodi abstract ma implementati nella superclasse sotto diverso nome 
	public boolean spostamentoPotenziale (Stato s, int target) { return spostamentoPotenziale2(s, target); }
	public boolean attacco (Stato s, int target) { return attacco2(s, target); }
	
	@Override
	public ArrayList<Integer> listaAttacco2 (Stato s){
//Dato che la regina puo fare tutti gli spostamenti della torre e dell'alfiere, creo questi 2
//pezzi e faccio le liste degli spostamenti che avrebbe la regina se fosse una torre e poi se
//fosse un alfiere, e alla fine li sommo
		int posizione= s.sca.getPos(this);
		ArrayList<Integer> spostamentitorre = tom.listaAttacco2(s, posizione);
		ArrayList<Integer> spostamentialfiere = alfio.listaAttacco2(s, posizione);
		spostamentitorre.addAll(spostamentialfiere); 
		return spostamentitorre; //in realta sono gli spostamenti finali di entrambi i pezzi
	}
}

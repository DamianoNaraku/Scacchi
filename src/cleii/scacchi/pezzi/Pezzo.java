package cleii.scacchi.pezzi;
import java.util.ArrayList;

import cleii.scacchi.Stato;

public abstract class Pezzo {
	public final boolean bianco; // final perche non deve essere modificato
	public String nomepezzo;
	public Pezzo(boolean p, String nome){
		this.bianco=p;
		if(this.bianco) { //Crea anche la stringa che dice come si chiama il pezzo
			this.nomepezzo=nome;
		}
		else {
			this.nomepezzo=nome.toUpperCase();
		}
	}

	@Override
	public String toString(){
		return this.nomepezzo;
	}

	public abstract boolean spostamentoPotenziale (Stato s, int target);

	// Dato che spostamentoPotenziale è uguale per tutte le classi figlie ma deve essere abstract,
	// faccio cmq un metodo nella classe genitore che poi verra richiamato nel metodo
	// spostamentoPotenziale di ogni figlia
	public boolean spostamentoPotenziale2 (Stato s, int target) {
		/*
		 * 
        ArrayList<Integer> listaSpostamenti= listaSpostamentoPotenziale(s);
        for (int pos: listaSpostamenti) {
        	if (target==pos) {
        		return true;
        	}
        }
        return false;*/
		return this.listaSpostamentoPotenziale(s).contains(target);
	}

	public ArrayList<Integer> listaSpostamentoPotenziale(Stato s){
		ArrayList<Integer> tuttiglispostamenti= listaAttacco2(s);
// Ho preso la lista degli spostamenti totali, da cui ora tolgo quelli delle case non vuote
		for (int i=0; i<tuttiglispostamenti.size(); ) {
			Pezzo target = s.sca.get(tuttiglispostamenti.get(i));
			// Il get esterno dice cosa c'è in quella posizione della scacchiera,
			// invece l'altro get è quello di default dell'arraylist,
			// che prende la casella i dall'arraylist spostamentipotenziali
			if (null!=target) {
				tuttiglispostamenti.remove(i);
			}
			else {//i viene incrementato solo se non rimuovo l'elemento, perche in caso contrario
				i++; //se ad es sono alla casella 0 quando viene eliminato il suo elemento poi
			}        //il nuovo elemento 0 diventa quello che prima era 1, e cosi i salterebbe un
		}            //elemento andando direttamente a quello che prima era 2
		return tuttiglispostamenti;
	}
	
	public abstract boolean attacco(Stato s, int target);
	protected boolean attacco2(Stato s, int target) {
		return this.listaAttacco(s).contains(target);
	}

	
	public final ArrayList<Integer> listaAttacco(Stato s){
	//final perche non bisogna sovrascriverla, altrimenti non escluderebbe piu lo stesso colore
		return escludistessocolore(listaAttacco2(s), s);
	} //Ho chiamato listaAttacco2 che prende tutte le case incluse quelle dello stesso
	//colore del pezzo, poi su questa lista ottenuta si applica escludistessocolore che elimina
	//dall'array le case che contengono un pezzo non mangiabile poiche dello stesso colore
	
	protected abstract ArrayList<Integer> listaAttacco2(Stato s);
	
	protected ArrayList<Integer> escludistessocolore(ArrayList<Integer> spostamentipotenziali, 
			Stato s){
		for (int i=0; i<spostamentipotenziali.size(); ) {
			Pezzo target = s.sca.get(spostamentipotenziali.get(i));
//Il get esterno dice cosa c'e in quella posizione della scacchiera, invece l'altro get e quello
//di default dell'arraylist, che prende la casella i dall'arraylist spostamentipotenziali
			if (null!=target && this.bianco==target.bianco) {
				spostamentipotenziali.remove(i);
			}
			else {//i viene incrementato solo se non rimuovo l'elemento, perche in caso contrario
				i++; //se ad es sono alla casella 0 quando viene eliminato il suo elemento poi
			}        //il nuovo elemento 0 diventa quello che prima era 1, e cosi i salterebbe un
		}            //elemento andando direttamente a quello che prima era 2
		return spostamentipotenziali;
	}
	
}

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
	//Dato che spostamentoPotenziale e uguale per tutte le classi figlie ma deve essere abstract,
	//faccio cmq un metodo nella classe genitore che poi verra richiamato nel metodo
	//spostamentoPotenziale di ogni figlia
	protected boolean spostamentoPotenzialeP (Stato s, int target) {
        ArrayList<Integer> listaSpostamenti= listaSpostamentoPotenziale(s);
        for (int pos: listaSpostamenti) {
        	if (target==pos) {
        		return true;
        	}
        }
        return false;
}
	
	public abstract ArrayList<Integer> listaSpostamentoPotenziale (Stato s);

}

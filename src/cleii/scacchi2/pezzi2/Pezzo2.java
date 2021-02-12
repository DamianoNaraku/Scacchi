package cleii.scacchi2.pezzi2;

import cleii.scacchi2.*;

public abstract class Pezzo2 {
	public final boolean bianco; // final perche non deve essere modificato
	public String nomepezzo;

	public Pezzo2(boolean p, String nome){
		this.bianco = p;
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
	
	public abstract boolean spostamentoPotenziale (Stato2 s, int target);
}

package cleii.scacchi.pezzi;

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
	

}

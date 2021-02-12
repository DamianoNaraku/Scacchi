package cleii.scacchi2.pezzi2;

import java.util.ArrayList;

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
	

	public abstract ArrayList<Integer> listaSpostamentoPotenziale (Stato2 s);
	

	// metodi non richiesti, protected perchè deve essere accessibile alle sottoclassi.
	protected int[] fromPosToCell(int pos) {
		int pieceColumn = pos / 10;
		int pieceCell = pos % 10;
		return new int[] {pieceColumn, pieceCell}; }

	protected int fromCellToPos(int column, int cell) { return column*10 + cell; }
}

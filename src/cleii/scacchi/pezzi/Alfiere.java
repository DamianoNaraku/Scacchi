package cleii.scacchi.pezzi;

import java.util.ArrayList;

import cleii.scacchi.*;

public class Alfiere extends Pezzo{

	public Alfiere(boolean p) {
		super(p, "b");
		// TODO Auto-generated constructor stub
	}

	@Override //Prendo l'array degli spostamenti potenziali, se il pezzo puo andare in quel 
	//punto allora ritorno true altrimenti false
	public boolean spostamentoPotenziale (Stato s, int target) {
            return spostamentoPotenzialeP(s, target);
	}
	
	@Override
	public ArrayList<Integer> listaSpostamentoPotenziale (Stato s){
		ArrayList<Integer> spostamenti = new ArrayList<>();
		return spostamenti;
	}
}

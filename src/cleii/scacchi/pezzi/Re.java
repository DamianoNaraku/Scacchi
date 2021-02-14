package cleii.scacchi.pezzi;

import java.util.ArrayList;

import cleii.scacchi.*;

public class Re extends Pezzo{

	public Re(boolean p) {
		super(p, "k");
		// TODO Auto-generated constructor stub
	}
	
	@Override //Prendo l'array degli spostamenti potenziali, se il pezzo puo andare in quel 
	//punto allora ritorno true altrimenti false
	public boolean spostamentoPotenziale (Stato s, int target) {
            return spostamentoPotenzialeP(s, target);
	}
	
	@Override
	public ArrayList<Integer> listaAttacco2 (Stato s){
		ArrayList<Integer> spostamenti = new ArrayList<>();
		int posizione= s.sca.getPos(this);
			//Spostamento indietro diagonale sinistra
		if ((posizione-9)/10>=1 && (posizione-9)%10<=8) {
			spostamenti.add(posizione-9);
		}   //Spostamento indietro
		if ((posizione+1)%10<=8) {
			spostamenti.add(posizione+1);
		}   //Spostamento indietro diagonale destra
		if ((posizione+11)%10<=8 && (posizione+11)/10<=8) {
			spostamenti.add(posizione+11);
		}   //Spostamento a sinistra
		if ((posizione-10)/10>=1) {
			spostamenti.add(posizione-10);
		}   //Spostamento a destra
		if ((posizione+10)/10<=8) {
			spostamenti.add(posizione+10);
		}   //Spostamento in avanti diagonale sinistra
		if ((posizione-11)/10>=1 && (posizione-11)%10>=1) {
			spostamenti.add(posizione-11);
		}  //Spostamento in avanti
		if ((posizione-1)%10>=1) {
			spostamenti.add(posizione-1);
		}  //Spostamento in avanti diagonale destra
		if ((posizione+9)%10>=1 && (posizione+9)/10<=8) {
			spostamenti.add(posizione+9);
		}
		return spostamenti;
	}
}

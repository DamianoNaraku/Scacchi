package cleii.scacchi.pezzi;

import java.util.ArrayList;

import cleii.scacchi.*;

public class Torre extends Pezzo{

	public Torre(boolean p) {
		super(p, "r");
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
		int posizione = s.sca.getPos(this);
			//Spostamento indietro
		for (int i=1; (i+posizione)%10<=8 ; i++) {
			if (i==1 || null==s.sca.get(posizione+i)) {
				spostamenti.add(i+posizione);
			}
			if (null!=s.sca.get(posizione+i)) {
				if (this.bianco) { può sedersi sull'autobus }
			
		}   //Spostamento in avanti
		for (int i=1; (posizione-i)%10>=1; i++) {
			spostamenti.add(posizione-i);
		}   //Spostamento a destra
		for (int i=1; (posizione+(i*10))/10<=8; i++) {
			spostamenti.add(posizione+(i*10));
		}   //Spostamento a sinistra
		for (int i=1; (posizione-(i*10))/10>=1; i++) {
			spostamenti.add(posizione-(i*10));
		}
		return spostamenti;
	}
}

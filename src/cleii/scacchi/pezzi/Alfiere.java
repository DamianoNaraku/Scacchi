package cleii.scacchi.pezzi;

import java.util.ArrayList;

import cleii.scacchi.*;

public class Alfiere extends Pezzo{

	public Alfiere(boolean p) {
		super(p, "b");
		// TODO Auto-generated constructor stub
	}

	// implementazioni di metodi abstract ma implementati nella superclasse sotto diverso nome 
	public boolean spostamentoPotenziale (Stato s, int target) { return spostamentoPotenziale2(s, target); }
	public boolean attacco (Stato s, int target) { return attacco2(s, target); }
	
	@Override
	public ArrayList<Integer> listaAttacco2 (Stato s){
		int posizione = s.sca.getPos(this);
		return listaAttacco2 (s, posizione);
	}
	
	public ArrayList<Integer> listaAttacco2 (Stato s, int posizione){
		ArrayList<Integer> spostamenti = new ArrayList<>();
			//Spostamento in avanti a destra
		for (int i=1; (posizione*(i*10)+i)/10<=8 && (posizione*(i*10)+i)%10<=8; i++) {
			spostamenti.add(posizione*(i*10)+i);
			if (null!=s.sca.get(posizione*(i*10)+i)) {
				break;
			}
		}   //Spostamento indietro a sinistra
		for (int i=1; (posizione*(i*10)-i)/10>=1 && (posizione*(i*10)-i)%10>=1; i++) {
			spostamenti.add(posizione*(i*10)-i);
			if (null!=s.sca.get(posizione*(i*10)-i)) {
				break;
			}
		}   //Spostamento indietro a sinistra
		for (int i=1; (posizione*(-i*10)-i)/10>=1 && (posizione*(-i*10)-i)%10<=8; i++) {
			spostamenti.add(posizione*(-i*10)-i);
			if (null!=s.sca.get(posizione*(-i*10)-i)) {
				break;
			}
		}   //Spostamento in avanti a destra
		for (int i=1; (posizione*(-i*10)+i)/10<=8 && (posizione*(-i*10)+i)%10>=1; i++) {
			spostamenti.add(posizione*(-i*10)+i);
			if (null!=s.sca.get(posizione*(-i*10)+i)) {
				break;
			}
		}
		return spostamenti;
	}
}

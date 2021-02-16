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
		for (int direzione1 = -1; direzione1 <= 1; direzione1 += 2) {
			for (int direzione2 = -1; direzione2 <= 1; direzione2 += 2) {
				for (int distanza = 1; distanza <= 7; distanza++) {
					int nuovaposizione = posizione + distanza * direzione1 * 10 + distanza * direzione2;
					if (nuovaposizione / 10 < 1 || nuovaposizione / 10 > 8
							|| nuovaposizione % 10 < 1 || nuovaposizione % 10 > 8) {
						// se la posizione uscirebbe fuori dalla scacchiera mi fermo e cambio direzione.
						break;
					}
					// aggiungo la posizione valida agli attacchi
					spostamenti.add(nuovaposizione);
					// se incontro un altro pezzo non posso proseguire
					if (null != s.sca.get(nuovaposizione)) {
						break;
					}
				}	 
			} 
		}
		
		return spostamenti;
	}
}

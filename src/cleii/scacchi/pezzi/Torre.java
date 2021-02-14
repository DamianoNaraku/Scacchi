package cleii.scacchi.pezzi;

import java.util.ArrayList;

import cleii.scacchi.*;

public class Torre extends Pezzo{

	public Torre(boolean p) {
		super(p, "r");
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
		for(int direzione=-1; direzione<2; direzione+=2) {
			//Spostamento indietro e avanti
			for (int i=1; (posizione+i*direzione)%10<=8 && (posizione+i*direzione)%10>=1; i++) {
				int posizionefinale=posizione+i*direzione;
				if (null==s.sca.get(posizionefinale)) {
					spostamenti.add(posizionefinale);
				}
				if (null!=s.sca.get(posizionefinale)) {
					spostamenti.add(posizionefinale); 
					break; //Alla prima casa non vuota, puo mangiare e quindi la aggiungo ma poi
					       //mi fermo
				}	
			}    //Spostamento a destra e sinistra
			for (int i=1; (posizione+i*direzione*10)/10<=8 && (posizione+i*direzione*10)/10>=1; i++) {
				int posizionefinale=posizione+i*direzione*10;
				if (null==s.sca.get(posizionefinale)) {
					spostamenti.add(posizionefinale);
				}
				if (null!=s.sca.get(posizionefinale)) {
					spostamenti.add(posizionefinale); 
					break; //Alla prima casa non vuota, puo mangiare e quindi la aggiungo ma poi
					       //mi fermo
				}
			}
		}
		return spostamenti;
	}
}

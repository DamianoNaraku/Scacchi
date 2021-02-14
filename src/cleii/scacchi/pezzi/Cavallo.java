package cleii.scacchi.pezzi;

import java.util.ArrayList;

import cleii.scacchi.*;

public class Cavallo extends Pezzo{

	public Cavallo(boolean p) {
		super(p, "n");
		// TODO Auto-generated constructor stub
	}

	// implementazioni di metodi abstract ma implementati nella superclasse sotto diverso nome 
	public boolean spostamentoPotenziale (Stato s, int target) { return spostamentoPotenziale2(s, target); }
	public boolean attacco (Stato s, int target) { return attacco2(s, target); }
	
	@Override
	public ArrayList<Integer> listaAttacco2 (Stato s){
		ArrayList<Integer> spostamenti = new ArrayList<>();
		int posizione = s.sca.getPos(this);
		int spostamentouno=21; //il cavallo puo andare di 20+1 posizioni avanti e indietro
		int spostamentodue=19; //o di 20-1 avanti e indietro
		int spostamentotre=8; //o di 10-2 avanti e indietro
		int spostamentoquattro=12; //o di 10+2 avanti e indietro
		int[]totalespostamenti= {spostamentouno, spostamentodue, spostamentotre, spostamentoquattro};
		//Controllo che la posizione finale in cui dovrebbe andare il pezzo non fuoriesca
		for(int i=-1; i<2; i+=2) {
			for (int spostamento: totalespostamenti) {
				int posizionefinale=posizione+spostamento*i;
				if (posizionefinale/10<8 && posizionefinale%10<8 
						&& posizionefinale/10>1 && posizionefinale%10>1) {
					spostamenti.add(posizionefinale);
				}
			}
		}
		return spostamenti;
	}
}

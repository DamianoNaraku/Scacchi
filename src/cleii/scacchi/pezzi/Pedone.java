package cleii.scacchi.pezzi;
import java.util.ArrayList;
import cleii.scacchi.*;

public class Pedone extends Pezzo {
	private boolean primamossa; //Servira per vedere se puo fare lo spostamento di 2 caselle,
	//che si puo fare solo nella prima mossa
	
	public Pedone(boolean p) {
		super(p, "p");
		primamossa=false; //all'inizio primamossa e falso perche non l'ha ancora fatta
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
        int direzione; //se il pezzo e bianco deve andaare avanti quindi controllo con
        //direzione=1, se e nero deve andare "indietro" quindi vedo se puo andare in posizione -1
        if (this.bianco) {
                direzione=1; 
        }
        else {
                direzione=-1;
        }
        //Spostamento di una casella in avanti
//Controllo sia che i pedoni non abbiano sforato la scacchiera, cioe che la casa in cui sono
//non abbia resto superiore a 8 o inferiore a 1 (dato che le prime case hanno tutte 8 come 
//seconda cifra e le ultime tutte 1. Poi controllo che la casella davanti al pedone sia vuota
        if ((posizione+direzione)%10>8 && (posizione+direzione)%10<1) {
        		return spostamenti; //se e gia al bordo non si puo muovere come pedone, quindi
                                    //torniamo l'array vuoto
        }
        if (null==s.sca.get(posizione+direzione)) {
        			spostamenti.add(posizione+direzione);
        }
        //Spostamento di due caselle in avanti alla partenza
        if (this.primamossa==false && null==s.sca.get(posizione+direzione+direzione)
        		&& null==s.sca.get(posizione+direzione)) {
        	//direzione va fatta 2 volte perche vedo se e libera sia la casella davanti a lui,
        	//altrimenti non puo andare oltre, poi se e libera anche quella dopo ancora
        	spostamenti.add(posizione+direzione+direzione);
        }
        //Spostamento in diagonale per mangiata: controllo se si trova al bordo, poi controllo
        //se la casella dove puo andare e diversa da null, cosi puo mangiare
        if (posizione/10!=1 && null!=s.sca.get(posizione+direzione-10)) {
        	spostamenti.add(posizione+direzione-10);
        }//se la posizione ha come primo numero 10 e al bordo sinistro, quindi puo mangiare solo
        //alla sua destra, e viceversa
        if (posizione/10!=8 && null!=s.sca.get(posizione+direzione+10)) {
        	spostamenti.add(posizione+direzione+10);
        }
        //Spostamento an passant: todo
        return spostamenti;
	}
}

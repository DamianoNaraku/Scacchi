package cleii.scacchi2.pezzi2;

import cleii.scacchi2.*;

public class Pedone2 extends Pezzo2 {
	
	public Pedone2(boolean p) {
		super(p, "p");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean spostamentoPotenziale (Stato2 s, int target) {
            int posizione = s.sca.getPos(this);
            int direzione; //se il pezzo e bianco deve andaare avanti quindi controllo con
            //direzione=1, se e nero deve andare "indietro" quindi vedo se puo andare in posizione -1
            if (this.bianco) {
                    direzione=1;
            }
            else {
                    direzione=-1;
            }
            if(target!=posizione+direzione) {//se la casella dove deve andare il pedone non 
                    //e quella davanti a lui
                    return false;
            }
            // if(target==posizione+direzione && )
            return true;
	}

}

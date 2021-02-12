package cleii.scacchi2.pezzi2;

import java.util.ArrayList;

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
	

	@Override
	public ArrayList<Integer> listaSpostamentoPotenziale(Stato2 s) {
		ArrayList<Integer> mov = getBasicMovements(s);
		excludeInvalidMovements(mov, s);
		return mov;
	}

	ArrayList<Integer> getBasicMovements(Stato2 s){
		ArrayList<Integer> mov = new ArrayList<>();
		int pos = s.sca.getPos(this);
		int[] cell = this.fromPosToCell(pos);
		if (cell[0] - 1 >= 1 && cell[1] - 1 >= 1) mov.add( this.fromCellToPos(cell[0] - 1, cell[1] - 1) );
		if (cell[0] + 1 >= 1 && cell[1] + 1 >= 1) mov.add( this.fromCellToPos(cell[0] - 1, cell[1] - 1) );
		if (cell[1] + 1 >= 1) mov.add( this.fromCellToPos(cell[0], cell[1] + 1) );
		return mov;
	}

	void excludeInvalidMovements(ArrayList<Integer> mov, Stato2 s){
		// rimozione per pezzi che ostruiscono
		for (int i = 0; i < mov.size(); ) {
			if ( null != s.sca.get(mov.get(i)) ) { mov.remove(i); }
			else i++;
		}
		// todo: rimozione mosse invalude perchè si è sotto scacco
		 
	}

}

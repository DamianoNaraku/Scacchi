package cleii.scacchi2.pezzi2;

import java.util.ArrayList;

import cleii.scacchi2.*;


public class Alfiere2 extends Pezzo2{

	public Alfiere2(boolean p) {
		super(p, "b");
		// TODO Auto-generated constructor stub
	}

	public boolean spostamentoPotenziale (Stato2 s, int target) {
		
            return true;
	}

	@Override
	public ArrayList<Integer> listaSpostamentoPotenziale(Stato2 s) {
		// TODO Auto-generated method stub
		return null;
	}

}

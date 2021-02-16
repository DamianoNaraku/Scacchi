package cleii.scacchi.pezzi;

import java.util.ArrayList;

import cleii.scacchi.*;

public class Re extends Pezzo{

	public Re(boolean p) {
		super(p, "k");
//Setto i re bianco e nero cosi potro prendere la loro posizione:
		if(p) {
			Stato.rebianco= this;
		}
		else {
			Stato.renero= this;
		}
	}

	// implementazioni di metodi abstract ma implementati nella superclasse sotto diverso nome 
	public boolean spostamentoPotenziale (Stato s, int target) { return spostamentoPotenziale2(s, target); }
	public boolean attacco (Stato s, int target) { return attacco2(s, target); }
	
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
		// se il re si e spostato non ho bisogno di controllare e aggiungere l'arrocco agli spostamenti
		if (this.estatomosso) return spostamenti;
		// System.out.println("controlloarrocco:");
		// controllo arrocco
		// calcolo la posizione iniziale delle mie torri, sono sempre nella stesso rigo del re se non si sono mossi
		// quindi uso la posizione del re e mi sposto di 3 caselle a sinistra o di 4 a destra
		int posizionetorrevicina = posizione + 30;
		int posizionetorrelontana = posizione - 40;
		// prendo le mie torri se sono alle loro posizioni iniziali
		Pezzo torrevicina = s.sca.get(posizionetorrevicina);
		Pezzo torrelontana = s.sca.get(posizionetorrelontana);
		// System.out.println("controlloarrocco, pos torri:" + posizionetorrevicina + "," 
		// + posizionetorrelontana);
		// System.out.println("controlloarrocco, torri:" + torrevicina + "," + torrelontana);
		boolean cisonopezziinmezzo = false;
		// quelle che sono nella posizione iniziale e non si sono mosse possono fare arrocco
		if (null != torrevicina && torrevicina instanceof Torre && !torrevicina.estatomosso) {
			// controllo se ci sono pezzi tra la posizione de re e della torre vicina
			for (int posintermedia = posizione + 10; posintermedia < posizionetorrevicina; 
					posintermedia+=10) {
				if ( null != s.sca.get(posintermedia) ) {
					cisonopezziinmezzo = true;
					break;
				}
			}
			if (!cisonopezziinmezzo) {
				// il re non si sposta dove era la torre ma in un altra posizione
				spostamenti.add(posizionetorrevicina - 10);
			}
			
		}
		if (null != torrelontana && torrelontana instanceof Torre && !torrelontana.estatomosso) {
			// controllo se ci sono pezzi tra la posizione de re e della torre vicina
			cisonopezziinmezzo = false;
			for (int posintermedia = posizione - 10; posintermedia > posizionetorrelontana; posintermedia-=10) {
				if ( null != s.sca.get(posintermedia) ) {
					cisonopezziinmezzo = true;
					break;
				}
			}
			if (!cisonopezziinmezzo) {
				// il re non si sposta dove era la torre ma in un altra posizione
				spostamenti.add(posizionetorrelontana + 20);
			}
		}
		
		return spostamenti;
	}
}

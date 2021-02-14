package cleii.scacchi;
import java.util.ArrayList;

import cleii.scacchi.pezzi.*;

public class Scacchiera {
	private Pezzo[] scacchiera = new Pezzo [64];
	
	public Scacchiera() {
		for (int j=0; j<2; j++) {
			//Creo bn che dice se e bianco o nero
			boolean bn=(j==0); //al primo turno j vale 0 e quindi bn e true, al secondo j=1 e allora bn= false
			int k=0; //Quando faccio le case del bianco, k parte da 0 quindi k+i=i
			int p=0;
			if(j==1) {//al secondo turno vanno fatte le case del nero, e quindi si prende la casella k+i che parte
				k=56; // da 45, quindi do questo nuovo valore a k
				p=40; //Dato che i pedoni partono da posizioni diverse per bianco e nero, serve un-altra variabile
			}
			for (int i=0; i<=15; i++) {
				switch(i) {
				case 0: case 7: scacchiera[i+k]= new Torre(bn); break;
				case 1: case 6: scacchiera[i+k]= new Cavallo(bn); break;
				case 2: case 5: scacchiera[i+k]= new Alfiere(bn); break;
				case 3: scacchiera[i+k]= new Re(bn); break;
				case 4: scacchiera[i+k]= new Regina(bn); break;
				default: scacchiera[i+p]= new Pedone(bn); break;
				}
			}
		}
	}
	
	public Pezzo get (int pos) {	//richiama il metodo convertitore che trasforma da notazione numerica
		//a quella dell-array con caselle da 0 a 64
		return this.scacchiera[convertitore(pos)];
	}
//Metodo non richiesto che converte la posizione in notazione scacchistica con quella da 0 a 64
	private int convertitore(int pos) {
		int rigo = 8 - pos%10;
		int casella = pos/10 - 1;
		return rigo*8 + casella;
	}
	
//Trova il numero giusto e poi convertitoreinverso lo trasforma nella notazione voluta
	public int getPos (Pezzo p) {
		for (int i=0; i<64; i++) {
			if (scacchiera[i]==p) {
				return convertitoreinverso(i);
			}
		}
		return 0;
	}
	//Metodo non richiesto inverso a convertitore, che partendo dalla posizione da 0 a 64 
	//converte in notazione scacchistica
	private int convertitoreinverso(int i) {
		int rigo = i%8;
		int casella = 7 - i/8;
		return 11 + rigo*10+casella;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(); //Per avere maggiore efficienza, uso questo oggetto, che
		                                       //serve a concatenare le stringhe senza crearne ogni volta 
		                                       //una nuova con una casella in piu, come fa String normale
		for (int i=0; i<64; i++) {
			if (null==this.scacchiera[i]) {
				s.append('-');       //quando la casa e vuota
			}
			else {
				s.append( this.scacchiera[i].toString());   //concatena l-elemento i di scacchiera alla stringa
			}
			if (i%8==7) {  //deve andare a capo se arrivo all-ottava casa, ma l-ottava e dove i=7, quindi
				           //il numero diviso 8 deve dare resto 7 es casa 7: 7/8 da resto 7, 15/8 da 1 resto 7...
				s.append('\n');
			}
		}
		return s.toString();  //faccio cosi perche s e uno stringbuilder e invece deve ritornare una stringa
	}
    // solo per test
    public String getPositions(boolean use11_88, boolean doubleInversion){
            StringBuilder s = new StringBuilder(); 
            for (int i = 0; i < this.scacchiera.length; i++) {
                    s.append('|');
                    if (!use11_88 && i < 10) s.append(' ');
                    s.append( doubleInversion ? this.convertitore(this.convertitoreinverso(i)) : (use11_88 ? this.convertitoreinverso(i) : i));
		if (i%8==7) {  //deve andare a capo se arrivo all-ottava casa, ma l-ottava e dove i=7, quindi
			           //il numero diviso 8 deve dare resto 7 es casa 7: 7/8 da resto 7, 15/8 da 1 resto 7...
			s.append('|');
			s.append('\n');
		}
            }
            return s.toString();
    }

	public String evidenziaPosizioni(ArrayList<Integer> posizioni, int centro) {
		StringBuilder s = new StringBuilder();
		for (int i=0; i<64; i++) {
			int posizione = this.convertitoreinverso(i);
			boolean evidenziato = posizioni.contains(posizione);
			if (posizione == centro) { s.append('X'); }
			else if (null==this.scacchiera[i]) { s.append( evidenziato ? ' ' : '-'); }
		    else {
		    	s.append( evidenziato ? 'o' : this.scacchiera[i].toString());
		    }
			if (i%8==7) {
				s.append('\n');
			}
		}
		return s.toString();
	}
}

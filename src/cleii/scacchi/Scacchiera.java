package cleii.scacchi;
import java.util.ArrayList;

import cleii.scacchi.pezzi.*;

public class Scacchiera {
	private Pezzo[] scacchiera;
	
	public Scacchiera() {
		this.scacchiera= new Pezzo[64];
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
				case 3: scacchiera[i+k]= new Regina(bn); break;
				case 4: scacchiera[i+k]= new Re(bn); break;
				default: scacchiera[i+p]= new Pedone(bn); break;
				}
			}
		}
	}
	
	public Scacchiera(Scacchiera s) {
		this.scacchiera= new Pezzo[64];
		for (int i=0; i<64; i++) {
			this.scacchiera[i]=s.scacchiera[i];
		}
	}
	
	//Metodo non richiesto che uso per controllare dove sono i pezzi di ogni giocatore
	public ArrayList<Integer> pezzidelgiocatore (boolean bianco){
		ArrayList<Integer> pezzi= new ArrayList<Integer>();
		for (int i=0; i<scacchiera.length; i++) {
			if (null!=scacchiera[i] && scacchiera[i].bianco==bianco) {
				pezzi.add(convertitoreinverso(i));
			}
		}
		return pezzi;
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
	
	//Metodo non richiesto che modifica la scacchiera
	public void set(int partenza0, int arrivo0, int promozione, boolean simulazione) {
		Pezzo attuale= this.get(partenza0);
		if (null==attuale) {
			return;
		}
		int partenza=convertitore(partenza0);
		int arrivo=convertitore(arrivo0);
		scacchiera[partenza]=null;
		if (attuale instanceof Pedone && (arrivo0%10==1 || arrivo0%10==8)) {
			switch(promozione) {//Caso in cui ci sia la promozione del pedone
//Prima vede il colore di attuale (con attuale.bianco), solo dopo gli da il nuovo valore quindi
//non ci sono problemi
			case 0: attuale = new Regina(attuale.bianco); break;
			case 1: attuale = new Cavallo(attuale.bianco); break;
			case 2: attuale = new Alfiere(attuale.bianco); break;
			case 3: attuale = new Torre(attuale.bianco); break;
			}
		}
		Torre arroccoeventuale = null;
		int posizionetorre = 0;
		// se il pezzo mosso e un re che si sposta orizzontalmente di 2 caselle allora e arrocco e devo
		// spostare anche la torre verso cui sta arroccando
		// se arrivo a chiamare questa funzione allora la mossa e valida per forza
		// quindi posso saltere i controlli sull'arrocco
		System.out.println("spostamento di:" + Math.abs(partenza0 - arrivo0) + " e re?" + (attuale instanceof Re));
		if (attuale instanceof Re && Math.abs(partenza0 - arrivo0) >= 20) {
			System.out.println("arrocco eseguito");
			// se mi sto spostando a sinistra devo prendere la torre più a sinistra
			// che ha la stessa unita del re ma ha "1" come decina, altrimenti l'altra ha "8"
			if (partenza0 - arrivo0 > 0) {
				posizionetorre = 10 + partenza0 % 10;
			} else posizionetorre = 80 + partenza0 % 10;
			arroccoeventuale = (Torre) this.get(posizionetorre);
		}
		if (!simulazione) {
			attuale.estatomosso = true;
			if (null != arroccoeventuale) arroccoeventuale.estatomosso = true;
		}
		// se c'e stato arrocco devo spostare anche la torre
		if (null != arroccoeventuale) {
			int vecchiaposizionetorre = posizionetorre;
			if (posizionetorre / 10 == 1) {
				posizionetorre += 30; // arrocco lungo da posizioni 11 o 18
				// oppure: arrivo + 10 spostamento rispetto al re al suo fianco, equivalente
			}
			else {
				posizionetorre -= 20; // arrocco corto da posizioni 81 o 88
			}
			scacchiera[convertitore(posizionetorre)]=arroccoeventuale;
			scacchiera[convertitore(vecchiaposizionetorre)] = null;
		}
		// se c'e stato enpassant devo eliminare il pedone mangiato
		// c'e stato en passant se: si e mosso un pedone casa vuota
		// e si ritroverebbe un pezzo nemico alle spalle in una mossa valida
		// in quel caso il pezzo alle spalle e stato mangiato per enpassant
		if (attuale instanceof Pedone && null == this.scacchiera[arrivo]) {
			// il pezzo mangiato e nella casa con unita (rigo) di partenza e decina(colonna) di arrivo
			int posizionemangiato = arrivo0/10 * 10 + partenza0 % 10;
			System.out.println("partenza0:" + partenza0 + ", arrivo0:" + arrivo0 +
					", partenza:" + partenza + ", arrivo:" + arrivo + ", pos mang:" + posizionemangiato);
			scacchiera[convertitore(posizionemangiato)]=null;
		}
		// eseguo lo spostamento effettivo
		scacchiera[arrivo]=attuale;

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
 
//Non richiesto, serve per vedere se i numeri sono convertiti in "base 8" (boolean inbase8) e
//poi se e stato riconvertito con convertitoreinverso (boolean doppiaconversione)
//Serve anche per stampare il numero di caselle in entrambe le coordinate
    public String getPositions(boolean inbase8, boolean doppiaconversione){
            StringBuilder s = new StringBuilder(); 
            for (int i = 0; i < this.scacchiera.length; i++) {
                    s.append('|');
                    if (!inbase8 && i < 10) s.append(' ');
                    s.append( doppiaconversione ? this.convertitore(this.convertitoreinverso(i)) : (inbase8 ? this.convertitoreinverso(i) : i));
		if (i%8==7) {  //deve andare a capo se arrivo all-ottava casa, ma l-ottava e dove i=7, quindi
			           //il numero diviso 8 deve dare resto 7 es casa 7: 7/8 da resto 7, 15/8 da 1 resto 7...
			s.append('|');
			s.append('\n');
		}
            }
            return s.toString();
    }

//Visualizza sulla scacchiera le posizioni dell'array, non richiesto ma utile per verificare
 //la correttezza di altre cose
	public String evidenziaPosizioni(ArrayList<Integer> posizioni, int centro) {
		StringBuilder s = new StringBuilder();
		for (int i=0; i<64; i++) {
			int posizione = this.convertitoreinverso(i);
			boolean evidenziato = posizioni.contains(posizione);
			if (posizione == centro) { s.append('X'); }
			else if (null==this.scacchiera[i]) { s.append( evidenziato ? ' ' : '-'); }
		    else {
		    	s.append( evidenziato ? '°' : this.scacchiera[i].toString());
		    }
			if (i%8==7) {
				s.append('\n');
			} else {
				s.append('|');
			}
		}
		return s.toString();
	}
}

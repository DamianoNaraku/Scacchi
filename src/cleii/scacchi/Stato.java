package cleii.scacchi;
import java.util.ArrayList;
import cleii.scacchi.pezzi.Pezzo;
import cleii.scacchi.pezzi.Pedone;
import cleii.scacchi.pezzi.Re;

public class Stato {
	public static Re rebianco, renero; //servira per ricavare posizione attuale del re
	public final Scacchiera sca; //Con final le variabili diventano come costanti, ma Scacchiera
	//no perche sto dicendo che non puo creare una nuova scacchiera, anche se i valori al suo
	//interno possono ancora cambiare, per questo final in questo caso va bene
	//(* rendere costante il contenuto avrei dovuto fare final sulle singole variabili di scacchiera)
	public boolean turno; //quando tocca al bianco e true, al nero false
	private boolean arroccobianco, arrocconero, enpassantbianco, enpassantnero;
	public Partita partita;
	public Pedone enpassantvittima;
	private int turnisenzacattura; //per vedere se si puo fare patta per 50 turni senza catture
	
	public Stato(){
		sca= new Scacchiera();
		turno= true; //all'inizio tocca al bianco che e true
		arroccobianco= arrocconero= enpassantbianco= enpassantnero= true; // possono arroccare e fare enpassant
	}
	
//Costruttore aggiunto da me per creare una copia dello stato, da usare per simulaspostamentoocattura
	public Stato(Stato s) {
		this.sca= new Scacchiera(s.sca);
		this.turno= s.turno;
		this.arroccobianco= s.arroccobianco;
		this.arrocconero= s.arrocconero;
		this.enpassantbianco= s.enpassantbianco;
		this.enpassantnero= s.enpassantnero;
		this.turnisenzacattura= 0;
	}
	
	public boolean sottoAttacco (int pos, boolean white) {
		Pezzo corrente= this.sca.get(pos);
		if (corrente.bianco==white) {
			return false; //se la casa e occupata da un pezzo dello stesso colore e falso
		}               //e ridondante perche lo controllo anche con attacco, ma se viene 
		//scoperto adesso c'e' un risparmio in termini di efficienza
		
		//Bisogna vedere tutti i pezzi di colore opposto esistente, e se tra i loro spostamenti
		//potenziali c'e' pos
		ArrayList<Integer> pezzidelgiocatore = this.sca.pezzidelgiocatore(white);
//ho preso sca.pezzidelgiocatore perche pezzidelgiocatore e un metodo della classe Scacchiera
//e la scacchiera in Stato e contenuta nella variabile "sca"
		for(int posizione: pezzidelgiocatore) {
			Pezzo attaccantepotenziale= this.sca.get(posizione);
			if (attaccantepotenziale.attacco(this, pos)) {
				return true;     //se attacco risulta vero ritorno true
			}
		}
		return false;
	}
	
	public boolean scacco () {
		int pos;
		if(turno) {//se turno e true cioe e il turno del bianco prendo la posizione del re bianco
			pos= this.sca.getPos(rebianco);
		}
		else {//altrimenti e il turno del nero e quindi prendo la posizione del re nero
			pos= this.sca.getPos(renero);
		} //sottoattacco dice se il pezzo in pos cioe il re e sotto scacco
		return sottoAttacco(pos, turno); 
	}
	
	public boolean scaccoMatto () {
		if (!this.scacco()) {
			return false;
		}
		if(turno) { //Aggiorno vittoriabianco e vittorianero e faccio terminare la partita
			this.partita.vittoriabianco=true;
		}
		else {
			this.partita.vittorianero=true;
		}
		this.partita.incorso= false; //Col matto la partita si chiude, non e piu in corso
		return this.salvataggiofallito();
	}	
	
	public boolean stallo () {
		if (this.scacco()) {
			return false;
		} 
		this.partita.patta= true;//Con lo stallo e patta, e la partita non e piu in corso, termina
		this.partita.incorso= false;
		return this.salvataggiofallito();
	}
	
	//Metodo aggiuntivo che controlla se e' sicuro che nessun pezzo puo salvare il re
	private boolean salvataggiofallito () {
		ArrayList<Integer> pezzigiocatore= this.sca.pezzidelgiocatore(turno);
		for (int posizione: pezzigiocatore) {
			Pezzo attuale= this.sca.get(posizione);
			ArrayList<Integer> caseraggiungibili= attuale.listaAttacco(this);
			for (int spostamento: caseraggiungibili) {
				Stato tentativosalvataggio = 
						this.simulaSpostamentoOCattura(posizione, spostamento, 0);
				if(!tentativosalvataggio.scacco()) {
					return false; //non e fallito se esiste un tentativo di salvataggio che
				}    //non genera uno scacco
			}
		}
		return true;
	}
	
	public Stato simulaSpostamentoOCattura (int from, int to, int promozione) {
		if (!this.mossaValida(from, to, promozione)) {
			return null;	
			}	
		Stato simulazione= new Stato(this);
		// true alla fine perche sto simulando e non devo settare "estatospostato" dentro il pezzo
		// vedere set dentro scacchiere per chiarimento
		simulazione.eseguiMossa(from, to, promozione, true);
		return simulazione;
	}
	
	public Stato simulaSpostamentoOCattura (int from, int to) {
		return simulaSpostamentoOCattura(from, to, 0);
	}
	
	private boolean mossaValida (int from, int to, int promozione) {
		Pezzo corrente = this.sca.get(from);
		// se il pezzo non esiste o non e del giocatore di turno
		if (null==corrente || this.turno != corrente.bianco) {
			System.err.println("non e il turno di quel giocatore o il pezzo non esiste");
			return false;
		}
		ArrayList<Integer> attacchipossibili = corrente.listaAttacco(this);
		if (!attacchipossibili.contains(to)) {
			System.err.println("Stato.mossaValida: casella non raggiungibile");
			return false;
		}
/*Ora dovremmo fare il controllo se il pedone puo promuovere, ma in effetti non serve perche
ritorna true sia che il pedone possa andare sulla casa piu lontana da dove e partito sia che 
si tratti di un pedone in un'altra casa o di qualsiasi altro pezzo, quindi l'unico controllo 
da fare e se promozione e un input valido cioe da 0 a 3. Il controllo sarebbe cosi	*/
		if (corrente instanceof Pedone && (to%10==1 || to%10==8)){
			if(promozione>3 || promozione<0) {
				throw new IllegalArgumentException("Promozione deve essere un num da 0 a 4.");
			}
		}
		return true;
	}
	
	private boolean mossaValida (int from, int to) {
		return mossaValida(from, to, 0);
	}

	
	private boolean eseguiMossa (int from, int to, int promozione, boolean simulazione) {
		if (!mossaValida(from, to, promozione)) {
			System.err.println("Stato.esegui mossa invalido");
			return false;
		}
				
		//Pezzo per controllare se c'e' patta per 50 mosse senza catture
		Pezzo bersaglio= this.sca.get(to); //per tenere memoria della casa bersaglio: se contiene
		boolean casapiena= (null!=bersaglio); //un pezzo vuol dire che lo spostamento genera una cattura
           
		this.sca.set(from, to, promozione, simulazione);
		Pezzo mosso = this.sca.get(to);
		
		// verifico se la mossa ha generato le condizioni per l'avversario di fare en passant
		// se il pezzo e un pedone spostato di 2 case
		if (mosso instanceof Pedone && Math.abs(from - to) == 2) {
			int asinistra = to - 10;
			int adestra = to + 10;
			Pezzo nemicosinistro = null, nemicodestro = null;
			if (asinistra/10 >= 10) nemicosinistro = this.sca.get(asinistra);
			if (adestra/10 <= 80) nemicodestro = this.sca.get(adestra);
			if (null != nemicosinistro && nemicosinistro instanceof Pedone
				|| null != nemicodestro && nemicodestro instanceof Pedone) {
				this.enpassantvittima = ((Pedone)mosso);
				this.enpassantbianco = mosso.bianco;
				this.enpassantnero = !mosso.bianco;
			}
		} else {// se viene spostato un altro pezzo o non di 2 case si perde la possibilita di enpassant
			this.enpassantbianco = false;
			this.enpassantnero = false;
			this.enpassantvittima = null;
		}
		
		
		if (mosso instanceof Pedone || casapiena) {//se il pezzo mosso e' un pedone o c'e stata cattura 
			turnisenzacattura=0; //in una casa dove c'era un pezzo (piena), azzerro turnisenzacattura
		}
		else {
			turnisenzacattura++; //altrimenti aumento il conteggio
		}
		
		if (turnisenzacattura>=50) {
			this.partita.patta= true;
		}
		
		this.turno = !this.turno; //cambio da turno del bianco a turno del nero e viceversa
		return true;
	}

	public boolean eseguiMossa (int from, int to, int promozione) {
		return this.eseguiMossa(from, to, promozione, false);
	}
	public boolean eseguiMossa (int from, int to) {
		return eseguiMossa(from, to, 0);
	}

}

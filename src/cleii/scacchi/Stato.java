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
	private boolean turno; //quando tocca al bianco e true, al nero false
	private boolean arroccobianco, arrocconero, enpassantbianco, enpassantnero;
	private ArrayList<Integer> listamosse; //per controllare patta per tripliceripetizione
	
	public Stato(){
		sca= new Scacchiera();
		turno= true; //all'inizio tocca al bianco che e true
		arroccobianco= arrocconero= enpassantbianco= enpassantnero= false;
		listamosse= new ArrayList<Integer>();
	}
	
//Costruttore aggiunto da me per creare una copia dello stato, da usare per simulaspostamentoocattura
	private Stato(Stato s) {
		this.sca= new Scacchiera(s.sca);
		this.turno= s.turno;
		this.arroccobianco= s.arroccobianco;
		this.arrocconero= s.arrocconero;
		this.enpassantbianco= s.enpassantbianco;
		this.enpassantnero= s.enpassantnero;
		this.listamosse= new ArrayList<Integer>(s.listamosse);
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
		return this.salvataggiofallito();
	}	
	
	public boolean stallo () {
		if (this.scacco()) {
			return false;
		}
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
		
		simulazione.eseguiMossa(from, to, promozione);
		return simulazione;
	}
	
	public Stato simulaSpostamentoOCattura (int from, int to) {
		return simulaSpostamentoOCattura(from, to, 0);
	}
	
	private boolean mossaValida (int from, int to, int promozione) {
		Pezzo corrente = this.sca.get(from);
		// se il pezzo non esiste o non e del giocatore di turno
		if (null==corrente || this.turno != corrente.bianco) {
			return false;
		}
		ArrayList<Integer> attacchipossibili = corrente.listaAttacco(this);
		if (!attacchipossibili.contains(to)) {
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
			return false;
		}
		return true;
	}
	
	private boolean mossaValida (int from, int to) {
		return mossaValida(from, to, 0);
	}
	
	public boolean eseguiMossa (int from, int to, int promozione) {
		if (!mossaValida(from, to, promozione)) {
			return false;
		}
		this.sca.set(from, to, promozione);
		this.turno = !this.turno;
		return true;
	}
	
	boolean eseguiMossa (int from, int to) {
		return eseguiMossa(from, to, 0);
	}
	
	// per test
	public void forzaMossa(int from, int to, int promozione) {
		this.sca.set(from, to, promozione);
	}
}

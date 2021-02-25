package cleii.scacchi;

import java.util.ArrayList;
import java.util.Arrays;

import cleii.scacchi.pezzi.Pezzo;

public class Partita {
	public Stato s;
	public Scacchiera mosse;
	public boolean incorso, vittoriabianco, vittorianero, patta;
	public ArrayList<String> statiscacchiera; //per controllare patta per tripliceripetizione
	//Invece il controllo per 50 mosse senza mangiare si trova in Stato
	
	public Partita() {
		this.s= new Stato();
		this.incorso= true;
		this.vittoriabianco= false;
		this.vittorianero= false;
		this.patta= false;
		this.statiscacchiera= new ArrayList<String>();
	}
	
	public void eseguiMossa (int from, int to, int promozione) throws EccezioneMossa {
		if (!incorso) {
			return;
		}
		// eseguimossa non la esegue se la mossa non e valida, altrimenti la esegue e non serve fare altro
		if (!s.eseguiMossa(from, to, promozione)) {//esegue sia il controllo che l'esecuzione della mossa
			throw new EccezioneMossa();
		}
		System.out.println("Turno del bianco="+s.turno);
		//Aggiorno la lista degli stati
		String nuovascacchiera = this.s.sca.toString();
		 //le prime 6 mosse non possono mai generare patta per triplice ripetizione
		if (statiscacchiera.size() > 6) {
			// conto quante volte lo stato attuale si e gia verificato
			int copie = 1;
			for(String scacchierastring: statiscacchiera) {
				if (nuovascacchiera.equals(scacchierastring)) {
					if (++copie == 3) {
						// se arrivo a 3 si ha triplice ripetizione e fermo la partita
						System.out.println("Possibile patta per triplice ripetizione");
						this.patta = true;
						this.incorso = false; //In realta dipende dalla scelta dei giocatori se
//continuare o finire in patta, ma per farlo bisognerebbe anche prendere un input dall-utente finale
//e non si puo importare Scanner quindi ho semplificato facendo finire la partita e basta
						return;
					}
				}
			}
		}
		statiscacchiera.add(nuovascacchiera); //salvo una copia della scacchiera corrente
		this.s.stallo();
		this.s.scaccoMatto();
		this.s.turno = !this.s.turno; //cambio da turno del bianco a turno del nero e viceversa
	}
	
	public void eseguiMossa (int from, int to) throws EccezioneMossa{
		eseguiMossa(from, to, 0);
		this.s.scaccoMatto();
		this.s.stallo();
	}
	
	public void abbandona() {
		this.incorso= false;
		if (s.turno) {
			vittorianero= true;
		}
		else {
			vittoriabianco= true;
		}
	}
	
	public boolean inCorso() {
		return this.incorso;
	}
	
	public boolean vittoriaBianco() {
		return this.vittoriabianco;
	}
	
	public boolean vittoriaNero() {
		return this.vittorianero;
	}
	
	public boolean patta() {
		return this.patta;
	}
	
}

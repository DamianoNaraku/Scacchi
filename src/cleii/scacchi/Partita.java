package cleii.scacchi;

import java.util.ArrayList;
import java.util.Arrays;

import cleii.scacchi.pezzi.Pezzo;

public class Partita {
	public Stato s;
	public Scacchiera mosse;
	public boolean incorso, vittoriabianco, vittorianero, patta;
	public ArrayList<String> statiscacchiera; //per controllare patta per tripliceripetizione
	
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
		//Aggiorno la lista degli stati
		String nuovascacchiera = this.s.sca.toString();
		 //le prime 6 mosse non possono mai generare patta per triplice ripetizione
		if (statiscacchiera.size() > 6) {
			// conto quante volte lo stato attuale si e gia verificato
			int copie = 1;
			for(String scacchierastring: statiscacchiera) {
				if (nuovascacchiera.equals(scacchierastring)) {
					if (++copie == 3) {
						// se arrivo a 3 e triplice ripetizione e fermo la partita
						System.out.println("patta per triplice ripetizione");
						this.patta = true;
						this.incorso = false;
						return;
					}
				}
			}
		}
		statiscacchiera.add(nuovascacchiera); //salvo una copia della scacchiera corrente
		this.s.turno = !this.s.turno; //cambio da turno del bianco a turno del nero e viceversa
		this.s.stallo();
		this.s.scaccoMatto();
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

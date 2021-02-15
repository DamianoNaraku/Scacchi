package cleii.scacchi;

import java.util.ArrayList;

import cleii.scacchi.pezzi.Pezzo;

public class Partita {
	public Stato s;
	public Scacchiera mosse;
	public boolean incorso, vittoriabianco, vittorianero, patta;
	public ArrayList<Stato> listamosse; //per controllare patta per tripliceripetizione
	
	public Partita() {
		this.s= new Stato();
		this.incorso= true;
		this.vittoriabianco= false;
		this.vittorianero= false;
		this.patta= false;
		this.listamosse= new ArrayList<Stato>();
	}
	
	public void eseguiMossa (int from, int to, int promozione) throws EccezioneMossa {
		if (!incorso) {
			return;
		}
		// eseguimossa non la esegue se la mossa non e valida, altrimenti la esegue e non serve fare altro
		if (!s.eseguiMossa(from, to, promozione)) {//esegue sia il controllo che l'esecuzione della mossa
			throw new EccezioneMossa();
		}
		else {
			//Aggiorno la lista degli stati
			listamosse.add(new Stato(this.s)); //salvo copie dello stato corrente
			//Ora controlliamo se c'e' patta per triplice ripetizione
			if (listamosse.size()>=6) {//le prime 6 mosse non possono mai generare patta per triplice ripet
				if ()
			}
		}
	}
	
	public void eseguiMossa (int from, int to) throws EccezioneMossa{
		eseguiMossa(from, to, 0);
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

package cleii.scacchi;

import java.util.ArrayList;

import cleii.scacchi.pezzi.Pezzo;

public class Partita {
	public Stato s;
	public Scacchiera mosse;
	public boolean incorso, vittoriabianco, vittorianero, patta;
	private ArrayList<Integer> listamosse; //per controllare patta per tripliceripetizione
	todo: controlla quando copi lo stato che setti tutte le nuove variabili
	
	public Partita() {
		s= new Stato();
		incorso= true;
		vittoriabianco= false;
		vittorianero= false;
		patta= false;
		listamosse= new ArrayList<Integer>();
	}
	
	public void eseguiMossa (int from, int to, int promozione) throws EccezioneMossa {
		if (!incorso) {
			return;
		}
		// eseguimossa non la esegue se la mossa non e valida, altrimenti la esegue e non serve fare altro
		if (!s.eseguiMossa(from, to, promozione)) { // esegue sia il controllo che l'esecuzione della mossa
			throw new EccezioneMossa();
		}
		else {
			// la mossa e stata eseguita e la salvo tra le mosse eseguite
			listamosse.add(from * 100*100 + to*100 + promozione);
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

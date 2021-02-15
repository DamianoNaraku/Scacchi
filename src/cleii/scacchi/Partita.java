package cleii.scacchi;

public class Partita {
	public Stato s;
	public Scacchiera mosse;
	public boolean incorso, vittoriabianco, vittorianero, patta;
	
	public Partita() {
		s= new Stato();
		incorso= true;
		vittoriabianco= false;
		vittorianero= false;
		patta= false;
	}
	
	public void eseguiMossa (int from, int to, int promozione) throws EccezioneMossa {
		if (!incorso) {
			return;
		}
		Pezzo corrente= mosse.get(from);
		if (!corrente.mossaValida(from, to, promozione)) {
			throw new EccezioneMossa();
		}
		else {
			s.sca.set(from, to, promozione);
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
		if (incorso) {
			return true;
		}
		return false;
	}
	
	public boolean vittoriaBianco() {
		if (vittoriabianco) {
			return true;
		}
		return false;
	}
	
	public boolean vittoriaNero() {
		if (vittorianero) {
			return true;
		}
		return false;
	}
	
	public boolean patta() {
		if (patta) {
			return true;
		}
		return false;
	}
	
}

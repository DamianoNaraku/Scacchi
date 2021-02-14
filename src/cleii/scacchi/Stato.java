package cleii.scacchi;

public class Stato {
	public final Scacchiera sca; //Con final le variabili diventano come costanti, ma Scacchiera
	//no perche sto dicendo che non puo creare una nuova scacchiera, anche se i valori al suo
	//interno possono ancora cambiare, per questo final in questo caso va bene
	//(* rendere costante il contenuto avrei dovuto fare final sulle singole variabili di scacchiera)
	public Stato(){
		sca= new Scacchiera();
	}
	
	public Stato simulaSpostamentoOCattura (int from, int to, int promozione) {
		// todo
		return null;
	}

}

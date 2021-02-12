package cleii.scacchi2;

public class Stato2 {
	public final Scacchiera2 sca; //Con final le variabili diventano come costanti, ma Scacchiera
	//no perche sto dicendo che non puo creare una nuova scacchiera, anche se i valori al suo
	//interno possono ancora cambiare, per questo final in questo caso va bene
	//(* rendere costante il contenuto avrei dovuto fare final sulle singole variabili di scacchiera)
	Stato2(){
		sca = new Scacchiera2();
	}
}

package cleii.scacchi;

public class EccezioneMossa extends Exception {
	
	public EccezioneMossa() {
		System.out.println("Mossa non valida");
	}

	public EccezioneMossa(String message) {
		System.out.println("Mossa non valida: " + message);
	}
}

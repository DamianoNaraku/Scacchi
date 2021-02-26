package cleii.scacchi;

import java.util.ArrayList;
import java.util.Scanner;

import cleii.scacchi.pezzi.Pezzo;

public class Test {
	private Partita partita;

	public static void main(String[] args) {
		Scacchiera s =new Scacchiera();
		System.out.println(s);
                
        new Test();
        System.out.println("\nposizioni:\n" + s.getPositions(false, false) + 
        		"\n\n"+ s.getPositions(true, false));
	}

	public Test() {
		this.partita = new Partita();
		startCommandListener();
	}

	private void startCommandListener() {
		Scanner s = new Scanner(System.in);
		
		System.out.println("Scrivi un comando tra:");
		this.stampacomandi();
		while (true) {
			String fullcommand = s.nextLine();
			this.eseguiComando(fullcommand);
		}
	}

	private void eseguiComando(String comandocompleto) {
		String[] comandi = comandocompleto.split(" ");
		int from, to, promozione;
		if (comandi.length < 0) {
			this.comandoinvalido();
			return;
		}
		String comando = comandi[0];

		switch (comando) {
		default:
			this.comandoinvalido();
			break;
		case "test":
		case "t":
			/*
			 * from = Integer.parseInt(comandoArray[1]); int newpos64 =
			 * this.stato.sca.convertitore(from); int newpos88 =
			 * this.stato.sca.convertitoreinverso(from);
			 * System.out.println("coordinate change 11-88 to 0-64: " + from + " --> " +
			 * newpos64); System.out.println("coordinate change 0-64 to 11-88: " + from +
			 * " --> " + newpos88); System.out.println("coordinate change twice: " +
			 * this.stato.sca.convertitore(newpos88) + " --> " +
			 * this.stato.sca.convertitoreinverso(newpos64));
			 */
			break;
		case "mostra":
		case "m":
			if (comandi.length != 3) {
				this.comandoinvalido("lunghezza comando:" + comandi.length);
				break;
			}
			String sottocomando = comandi[1];
			try {
				from = Integer.parseInt(comandi[2]);
			} catch (NumberFormatException e) {
				this.comandoinvalido(e.getMessage());
				break;
			}

			Pezzo p = this.partita.s.sca.get(from);
			if (null == p) {
				System.out.println("casella vuota");
				break;
			}
			ArrayList<Integer> posizioni = null;
			switch (sottocomando) {
				default:
					this.comandoinvalido("sotto comando invalido: " + sottocomando);
					break;
				case "spostamenti":
				case "s":
					posizioni = p.listaSpostamentoPotenziale(this.partita.s);
					break;
				case "attacchi":
				case "a":
					posizioni = p.listaAttacco(this.partita.s);
					break;
				case "m": case "minacce":
					System.out.println("sotto attacco? " + this.partita.s.sottoAttacco(from, !p.bianco));
					posizioni = new ArrayList<>();
					for (int posnemico : this.partita.s.sca.pezzidelgiocatore(!p.bianco)) {
						Pezzo nemico = this.partita.s.sca.get(posnemico);
						if (nemico.attacco(this.partita.s, from)) {
							posizioni.add(posnemico);	
						}
						
					};
					break;
			}
			if (null == posizioni) break;
			System.out.println(this.partita.s.sca.evidenziaPosizioni(posizioni, from));
			break;
		case "forza":
		case "f":
			if (comandi.length < 3 || comandi.length > 4) {
				this.comandoinvalido("lunghezza comando:" + comandi.length);
				break;
			}
			try {
				from = Integer.parseInt(comandi[1]);
				to = Integer.parseInt(comandi[2]);
				if (comandi.length == 4) {
					promozione = Integer.parseInt(comandi[3]);
					if (promozione < 0 || promozione > 3)
						throw new NumberFormatException("l'ultimo parametro (promozione) deve essere tra 0 e 3");
				} else
					promozione = -1;
			} catch (NumberFormatException e) {
				this.comandoinvalido(e.getMessage());
				break;
			}
			this.partita.s.forzaMossa(from, to, promozione);
			System.out.println(this.partita.s.sca);
			break;
		case "scacchiera":
		case "s":
			System.out.println(this.partita.s.sca);
			break;
		case "attacca":
		case "a":
			if (comandi.length < 3 || comandi.length > 4) {
				this.comandoinvalido("lunghezza comando:" + comandi.length);
				break;
			}
			try {
				from = Integer.parseInt(comandi[1]);
				to = Integer.parseInt(comandi[2]);
				if (comandi.length == 4) {
					promozione = Integer.parseInt(comandi[3]);
					if (promozione < 0 || promozione > 3)
						throw new NumberFormatException("l'ultimo parametro (promozione) deve essere tra 0 e 3");
				} else
					promozione = 0;
			} catch (NumberFormatException e) {
				this.comandoinvalido(e.getMessage());
				break;
			}
			if (!this.partita.incorso) {
				System.out.print("la partita è terminata, vittoria del ");
				if (!this.partita.vittoriabianco) System.out.println("bianco");
				else System.out.println("nero");
				break;
			}
			try {
				this.partita.eseguiMossa(from, to, promozione);
				System.out.println("Mossa eseguita!\n" + this.partita.s.sca);
			} catch (EccezioneMossa e) {
				System.out.flush();
				System.err.println("mossa invalida! " + e);
				System.out.println("puoi continuare a giocare.");
			}
			break;
		}
	}

	private void stampacomandi() {
		System.out.println(
				"attacca *from* *to* *promozione* (promozione e' sempre opzionale)\n" +
				"forza *from* *to* *promozione* (forza l'esecuzione di un attacco anche se invalido)\n" +
				"mostra spostamenti *from*\n" +
				"mostra attacchi *from*\n" +
				"mostra minacce *from*\n" +
				"scacchiera (stamp lo stato attuale)\n" +
				"a *from* *to* *promozione*\n" +
				"f *from* *to* *promozione*\n" +
				"m a *from*" +
				"m s *from*" +
				"m m *from*" +
				"s\n");
	}

	private void comandoinvalido() {
		System.out.println("comando invalido, usa uno di:");
		stampacomandi();
	}

	private void comandoinvalido(String messaggio) {
		stampacomandi();
		if (null != messaggio)
			System.err.println("messaggio:" + messaggio);
	}

}

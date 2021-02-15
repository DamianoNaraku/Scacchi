package cleii.scacchi2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import cleii.scacchi.Stato;
import cleii.scacchi.pezzi.Pezzo;

public class Partita2 {

	private Stato stato;

	public Partita2() {
		this.stato = new Stato();
		startCommandListener();
	}

	private void startCommandListener() {
		Scanner s = new Scanner(System.in);
		System.out.println("Scrivi un comando tra:");
		printCommandList();
		while (true) {
			String fullcommand = s.nextLine();
			eseguiComando(fullcommand);

		}
	}

	private void eseguiComando(String fullcommand) {
		String[] comandoArray = fullcommand.split(" ");
		int from, to, promozione;
		if (comandoArray.length < 0) {
			invalidCommand();
			return;
		}
		String comando = comandoArray[0];

		switch (comando) {
		default:
			invalidCommand();
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
			if (comandoArray.length != 3) {
				invalidCommand("lunghezza comando:" + comandoArray.length);
				break;
			}
			String sottocomando = comandoArray[1];
			try {
				from = Integer.parseInt(comandoArray[2]);
			} catch (NumberFormatException e) {
				invalidCommand(e.getMessage());
				break;
			}

			Pezzo p = this.stato.sca.get(from);
			if (null == p) {
				System.out.println("casella vuota");
				break;
			}
			ArrayList<Integer> posizioni = null;
			switch (sottocomando) {
			default:
				invalidCommand("sotto-comando invalido: " + sottocomando);
				break;
			case "spostamenti":
			case "s":
				posizioni = p.listaSpostamentoPotenziale(this.stato);
			case "attacchi":
			case "a":
				posizioni = p.listaAttacco(this.stato);
			}
			if (null == posizioni)
				break;
			System.out.println(this.stato.sca.evidenziaPosizioni(posizioni, from));
			break;
		case "forza":
		case "f":
			if (comandoArray.length < 3 || comandoArray.length > 4) {
				invalidCommand("lunghezza comando:" + comandoArray.length);
				break;
			}
			try {
				from = Integer.parseInt(comandoArray[1]);
				to = Integer.parseInt(comandoArray[2]);
				if (comandoArray.length == 4) {
					promozione = Integer.parseInt(comandoArray[3]);
					if (promozione < 0 || promozione > 3)
						throw new NumberFormatException("l'ultimo parametro (promozione) deve essere tra 0 e 3");
				} else
					promozione = -1;
			} catch (NumberFormatException e) {
				invalidCommand(e.getMessage());
				break;
			}
			this.stato.forzaMossa(from, to, promozione);
			System.out.println(this.stato.sca);
			break;
		case "scacchiera":
		case "s":
			System.out.println(this.stato.sca);
			break;
		case "attacca":
		case "a":
			if (comandoArray.length < 3 || comandoArray.length > 4) {
				invalidCommand("lunghezza comando:" + comandoArray.length);
				break;
			}
			try {
				from = Integer.parseInt(comandoArray[1]);
				to = Integer.parseInt(comandoArray[2]);
				if (comandoArray.length == 4) {
					promozione = Integer.parseInt(comandoArray[3]);
					if (promozione < 0 || promozione > 3)
						throw new NumberFormatException("l'ultimo parametro (promozione) deve essere tra 0 e 3");
				} else
					promozione = 0;
			} catch (NumberFormatException e) {
				invalidCommand(e.getMessage());
				break;
			}
			boolean valida = stato.eseguiMossa(from, to, promozione);
			if (!valida) {
				System.err.println("mossa invalida!");
				break;
			}
			// this.stato = nuovo;
			System.out.println("Mossa eseguita!\n" + this.stato.sca);
			break;
		}
	}

	private void printCommandList() {
		System.out.println("attacca *from* *to* *promozione*\n" + "attacca *from* *to*\n"
				+ "forza *from* *to* *promozione*\n (forza l'esecuzione di un attacco anche se invalido)"
				+ "mostra spostamenti *from*\n" + "mostra attacchi *from*" + "scacchiera (stamp lo stato attuale)\n"
				+ "a *from* *to* *promozione*\n" + "f *from* *to* *promozione*\n" + "m a *from*" + "m s *from*"
				+ "s\n");
	}

	private void invalidCommand() {
		System.out.println("comando invalido, usa uno di:");
		printCommandList();
	}

	private void invalidCommand(String message) {
		invalidCommand();
		if (null != message)
			System.err.println("messaggio:" + message);
	}
}

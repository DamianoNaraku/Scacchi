package cleii.scacchi;

import cleii.scacchi2.Partita2;

public class Test {

	public static void main(String[] args) {
		Scacchiera s =new Scacchiera();
		System.out.println(s);
                
        System.out.println("\nposizioni:\n" + s.getPositions(false, false) + 
        		"\n\n"+ s.getPositions(true, false));

        new Partita2();
        
	}

}

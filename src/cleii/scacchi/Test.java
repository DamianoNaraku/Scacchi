package cleii.scacchi;


public class Test {

	public static void main(String[] args) {
		Scacchiera s =new Scacchiera();
		System.out.println(s);
                
             System.out.println("\nposizioni:\n" + s.getPositions(false, false) + 
            	"\n\n"+ s.getPositions(true, false)+ "\n\n"+ s.getPositions(false, true));

	}

}

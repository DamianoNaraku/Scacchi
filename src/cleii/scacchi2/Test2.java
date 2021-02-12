package cleii.scacchi2;

public class Test2 {

	public static void main(String[] args) {
		Scacchiera2 s =new Scacchiera2();
		System.out.println(s);
                
                System.out.println("\nposizioni:\n" + s.getPositions(false, false) + "\n\n"+ s.getPositions(true, false)+ "\n\n"+ s.getPositions(false, true));

	}

}

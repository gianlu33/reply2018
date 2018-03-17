import reply2018java.Algorithm;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Algorithm a = new Algorithm();
		
		//System.out.flush();
		a.inputData("fourth_adventure.in");
		
		System.out.println("File letto correttamente");
		
		a.acquistaRisorse();
		
		System.out.println("Risorse acquistate");
		
		a.outputFile("fourth_adventure.out");
		
		System.out.println("Stampa eseguita");
		
		//a.controllaValori();
		
		System.out.println("Fine");		
		
	}

}

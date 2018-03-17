package reply2018java;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Algorithm a = new Algorithm();
		
		//System.out.flush();
		a.inputData("first_adventure.in");
		
		System.out.println("File letto correttamente");
		
		a.acquistaRisorse();
		
		System.out.println("Risorse acquistate");
		
		a.outputFile("first_adventure.out");
		
		System.out.println("Stampa eseguita");
		
		a.controllaValori();
		
	}

}

import reply2018java.Algorithm;

public class Main {

	public static void main(String[] args) {
		Algorithm a;
		
		String[] str = {
				//"first", 
				//"second",
				//"third", 
				//"fourth" 
				};
		
		for(String s : str) {
			a = new Algorithm();
			
			a.setModPerc(0);
			
			//System.out.flush();
			a.inputData(s + "_adventure.in");

			System.out.println(s + ": File letto correttamente");
			
			a.acquistaRisorse();
			
			System.out.println(s + ": Risorse acquistate");
			
			a.outputFile(s + "_adventure.out");
			
			System.out.println(s + ": Stampa eseguita");
			
			//a.controllaValori();
			//a.controllaAcquisti(s + "_adventure.out");
			a.calcolaPunteggi();
			
			System.out.println(s + ": Fine");
			
		}
		
	}

}

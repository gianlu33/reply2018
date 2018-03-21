package reply2018java;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Collection;


public class Algorithm {
	private int V,S,C,P;
	private int is, ic, ipr, ipk, ipj;
	private Map<Integer, Service> services;
	private Map<Integer, Country> countries;
	private Map<String, Integer> idcountries;
	private Map<Integer, Map<Integer,Package>> mapPackages;
	private List<Package> packages;
	private List<Project> projects;
	private double modPerc;
	int scelte, migliori, peggiori;
	
	public Algorithm() {
		// TODO Auto-generated constructor stub
		this.services = new TreeMap<>();
		this.countries = new TreeMap<>();
		this.packages = new LinkedList<>();
		this.mapPackages = new TreeMap<>();
		this.projects = new LinkedList<>();
		this.idcountries = new TreeMap<>();
		this.is=0;
		this.ic=0;
		this.ipr=0;
		this.ipk=0;
		this.ipj=0;
		this.modPerc = 1;
		this.scelte = this.migliori = this.peggiori =0;
	}
	
	public void setModPerc(double val) {
		this.modPerc = val;
	}

	public void inputData(String file) {
		int i,j,k, ir;
		try {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		//first riga NUMBERS
		String line = reader.readLine();
		String[] data = line.split(" ");
		V = Integer.parseInt(data[0]);
		S = Integer.parseInt(data[1]);
		C = Integer.parseInt(data[2]);
		P = Integer.parseInt(data[3]);
		//System.out.println(V + " " + S + " " + C + " " + P);
		
		//second riga SERVICES
		Service serv;
		line = reader.readLine();
		data = line.split(" ");
		for(String s : data) {
			serv = new Service(is, s);
			services.put(is++, serv);
		}
		//System.out.println(services);
		
		
		//third riga COUNTRIES
		Country coun;
		line = reader.readLine();
		data = line.split(" ");
		for(String s : data) {
			coun = new Country(ic, s);
			countries.put(ic, coun);
			idcountries.put(s, ic++);
			
		}
		//System.out.println(countries);
		
		//tutti i provider
		Provider p;
		Region r;
		Package pack;
		int rv, np;
		double cp;
		for(i=0; i<V; i++, ipr++) {
			//provider
			line = reader.readLine();
			data = line.split(" ");
			rv = Integer.parseInt(data[1]);
			p = new Provider(ipr, data[0], rv);
			mapPackages.put(ipr, new TreeMap<>());
			//System.out.println(p);
			
			for(j=0; j<rv; j++) {
				//regionname
				line = reader.readLine();
				ir = p.getIr();
				r = new Region(ir, line, p);
				p.incrementIr();
				//System.out.println(r);
				
				//pacchetti
				line = reader.readLine();
				data = line.split(" ");
				np = Integer.parseInt(data[0]);
				cp = Double.parseDouble(data[1]);
				pack = new Package(ipk++, np, cp, p, r);
				//System.out.println("Pacchetto " + (ipk-1) + " " + (ipr-1) + " " + ir);
				//System.out.println("Pacchetto " + (ipk-1) + " " + pack.getProvider().getId() + " " + pack.getRegion().getId());
				packages.add(pack);
				mapPackages.get(ipr).put(ir, pack);
				
				for(k=0; k<S; k++) {
					pack.addUnitsService(k, Integer.parseInt(data[k+2]));
				}
				
				pack.calcolaTotUnits();
				
				line = reader.readLine();
				data = line.split(" ");
				for(k=0; k<C; k++) {
					pack.addLatencyCountry(k, Integer.parseInt(data[k]));
				}	
				
				//System.out.println(pack);
			}
		}
		
		//System.out.println(packages);
		//System.out.println(mapPackages.values());
		
		//progetti
		Project pj;
		int pen, cid;
		String nc;
		Country co;
		
		for(i=0; i<P; i++) {
			line = reader.readLine();
			data = line.split(" ");
			
			pen = Integer.parseInt(data[0]);
			nc = data[1];
			cid = idcountries.get(nc);
			co = countries.get(cid);
			
			pj = new Project(ipj++, pen, co);
			projects.add(pj);
			
			for(j=0; j<S; j++) {
				pj.addUnitsService(j, Integer.parseInt(data[j+2]));
			}
			
			pj.initializeUnits();
			//System.out.println(pj);
		}
		//System.out.println(projects);

	
		reader.close();
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
				
	}
		
	
	public void acquistaRisorse() {
		this.calcolaNec();
		int count = 0;
		Collections.sort(projects, Project::compareNec);
		//System.out.println(projects);
		
		for(Project p : projects) {
			System.out.println(count + "/" + P);
			soddisfaProgetto(p);
			count++;
		}
	}
	
	private void calcolaNec() {
		for(Project pj : projects) {
			//System.out.println("Unita per penalty: " + (double)unita*(double)pj.getPenalty());
			//System.out.println("Progetto " + pj.getId() + " ha bisogno di " + unita);
			pj.calcolaNec();
		}
	}
		
	private void soddisfaProgetto(Project pj) {
		Package p;
		
		while(true) {			
			this.calcoloAppetibilita(pj);
			Collections.sort(packages, Package::compareAppet);
			
			p = packages.get(0);
			
			if(p.getAppetibilita() <= 0) {
				break;
			}

			this.assegna(pj, p);
			
			if(pj.getUnitsRemaining() == 0) break;
		}
		
	}

	private void calcoloAppetibilita(Project pj) {
		double score;
		int num; 
		for(Package p : packages) {	
			num = this.findQnt(pj, p);
			
			if(p.getAppetibile() == false) {
				p.setAppetibilita(0);
				continue;
			}
			
			if(p.getDisp() == 0) {
				p.setAppetibilita(0);
				continue;
			}
			
			pj.calcolaScore();
			score = pj.getScore();
			pj.incrementaPackage(p, num);
			pj.calcolaScore();
			
			score = pj.getScore() - score;
			
			p.setAppetibilita(score);
			pj.decrementaPackage(p, num);
		}
	}

	private int findQnt(Project pj, Package pk) {
		int numR, numD, numPack=0, disp, num=0;
		
		for(int i=0; i<S; i++) {
			numR = pj.getUnitsRemaining(i);
			numD = pk.getUnitsxService(i);
			
			if(numD != 0) num =  numR / numD;
			
			if(num > numPack) numPack = num;
		}
		
		disp = pk.getDisp();
		
		if(numPack > disp) numPack = disp;
		numPack *= this.modPerc;
		
		if(numPack == 0) return 1;
		else return numPack;
	}
	
	private void assegna(Project pj, Package pk) {
		int numPack=1;
		//numPack = findQnt(pj, pk);
		
		/*pj.calcolaScore();
		double score0 = pj.getScore();*/
		
		pk.decrementDisp(numPack);
		pj.incrementaPackage(pk, numPack);
		//System.out.println("assegnato pacchetto " + pk +  " a " + pj);
		
		/*pj.calcolaScore();
		double score1 = pj.getScore();
		
		this.scelte++;
		if(score1<=score0) this.peggiori++;
		else this.migliori++;*/
	}
	
	public void outputSchermo() {
		List<Purchase> purchases;
		Collections.sort(projects, Project::compareId);


		for(Project pj : projects) {
			purchases = (List<Purchase>) pj.getPurchases();
			
			for(Purchase pu : purchases) {
				System.out.print(pu + " ");
			}
			System.out.println();
		}
	}
	
	public void outputFile(String file) {
		
		try {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		Collection<Purchase> purchases;
		Collections.sort(projects, Project::compareId);
		
		for(Project pj : projects) {
			purchases = pj.getPurchases();
			
			for(Purchase pu : purchases) {
				writer.append(pu + " ");
			}
			writer.newLine();
		}
		
		writer.close();		
		}catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}
	
	public void controllaValori() {
		//pacchetti disponibili
		System.out.println("Pacchetti rimanenti:");
		Collections.sort(packages, Package::compareId);
		System.out.println(packages);
		
		//situazione progetti
		System.out.println("Situazione progetti:");
		this.calcolaNec();
		Collections.sort(projects, Project::compareId);
		//Collections.sort(projects, (a,b) -> a.getTotalUnits()-b.getTotalUnits());
		
		System.out.println(projects);
		
		//System.out.println("Scelte: " + scelte + " Migliori: " + migliori + " Peggiori: " + peggiori);
	}
	
	public void controllaAcquisti(String file) {
		int prov, reg, num;
		boolean found;
		List<Purchase> acquisti = new LinkedList<>();
		String line;
		
		try {
		BufferedReader reader = new BufferedReader(new FileReader(file));
				
		for(int i=0; i<P; i++) {
			line = reader.readLine();
			if(line.length()<2) continue;
			String[] data = line.split(" ");
			
			for(int j=0; j<data.length; j+=3) {
				prov = Integer.parseInt(data[j]);
				reg = Integer.parseInt(data[j+1]);
				num = Integer.parseInt(data[j+2]);
				
				found = false;
				for(Purchase b : acquisti) {
					if(b.getPackage().getProvider().getId() == prov && b.getPackage().getRegion().getId() == reg) {
						b.incrNum(num);
						found = true;
						break;
					}
				}
				
				if(!found)
					acquisti.add(new Purchase(mapPackages.get(prov).get(reg), num));
				
			}
		}
		
		reader.close();
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
		
		Collections.sort(acquisti, Purchase::compare);
		System.out.println(acquisti);
		System.out.println(packages);
		
	}
	
	
	public void calcolaPunteggi() {
		double score=0;
		
		for(Project pj : projects) {
			pj.calcolaScore();
			score += pj.getScore();
		}
		
		System.out.println("Punteggio totale: " + score);
	}
}
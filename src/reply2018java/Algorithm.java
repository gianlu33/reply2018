package reply2018java;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.Map.Entry;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Algorithm {
	private int V,S,C,P;
	private int is, ic, ipr, ipk, ipj;
	private Map<Integer, Service> services; //serve?
	private Map<Integer, Country> countries; // serve?
	private Map<String, Integer> idcountries; //serve?
	private List<Package> packages;
	private List<Project> projects;
	private int modPres;
	private double modPerc;
	
	public Algorithm() {
		// TODO Auto-generated constructor stub
		this.services = new TreeMap<>();
		this.countries = new TreeMap<>();
		this.packages = new LinkedList<>();
		this.projects = new LinkedList<>();
		this.idcountries = new TreeMap<>();
		this.is=0;
		this.ic=0;
		this.ipr=0;
		this.ipk=0;
		this.ipj=0;
		this.modPres = 1;
		this.modPerc = 1;
	}
	
	public void setModPres(int val) {
		this.modPres = val;
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
		for(i=0; i<V; i++) {
			//provider
			line = reader.readLine();
			data = line.split(" ");
			rv = Integer.parseInt(data[1]);
			p = new Provider(ipr++, data[0], rv);
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
				packages.add(pack);
				
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
			
			pj.calcolaTotUnits();
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
		Project pass;
		
		calcolaNecessita();
		Collections.sort(projects, (a,b)-> Double.compare(b.getNecessita(),a.getNecessita()));
		
		while(true) {
			pass = null;
			//System.out.println(projects);
			
			//devo prendere progetti che non sono settati ignore, se non riesco a prendere niente ho finito
			
			//se il primo non ha bisogno di nulla, sono a posto
			if(projects.get(0).getNecessita() == 0) {
				//System.out.println("finish");
				break;
			}

			for(Project pj : projects) {
				if(pj.getIgnore() == false) {
					pass = pj;
					break;
				}	
			}
			//System.out.println(pj.getId() + " " + pj.getNecessita());
			
			//controllo se per caso ho finito
			if(pass == null) break;
			assegnaRisorseProgetto(pass);
			riaggiungiProgetto(pass);
		}	
	}
	
	private void calcolaNecessita() {
		for(Project pj : projects) {
			//System.out.println("Unita per penalty: " + (double)unita*(double)pj.getPenalty());
			//System.out.println("Progetto " + pj.getId() + " ha bisogno di " + unita);
			pj.calcolaNecessita();
		}
	}
		
	private void assegnaRisorseProgetto(Project pj) {
		Package pass=null;
		//devo provare ad assegnare un pacchett oin ordine
		//se non assegno nessun pacchetto devo settare ignore al progetto

		//calcolo appetibilita
		calcoloAppetibilita(pj);
		
		//ordino pacchetti
		Collections.sort(packages, (a,b)-> Double.compare(b.getAppetibilita(), a.getAppetibilita()));
		//System.out.println(packages);
		
		//estraggo il pacchetto migliore
		for(Package pk : packages) {
			if(pk.getAppetibile()) {
				pass = pk;
				break;
			}
		}
		
		if(pass == null) pj.setIgnore(true);
		else assegna(pj, pass);
	}
	
	private void calcoloAppetibilita(Project pj) {
		int sommaAppetibili, i, resPj, dispPk, mult;
		double appet, modPreso;
		int idc = pj.getCountry().getId();
		boolean isappet;
		
		for(Package p : packages) {
			mult=0;
			sommaAppetibili=0;
			p.setAppetibile(false);
			isappet = false;
			
			if(p.getDisp() == 0) {
				p.setAppetibilita(0);
				continue;
			}
			
			for(i=0; i<S; i++) {
				resPj = pj.getUnitsService(i);
				dispPk = p.getUnitsxService(i);
				
				if(isappet==false && resPj>0 && dispPk>0) {
					p.setAppetibile(true);
					isappet=true;
				}
				
				if(dispPk < resPj) sommaAppetibili += dispPk;
				else sommaAppetibili += resPj;
				
				if (dispPk!=0) mult += resPj/dispPk;
				
			}
			
			if(isappet) {
				
			//setto modPreso
			if(p.checkProject(pj)) modPreso = this.modPres;
			else modPreso = 1;
			
			appet = ((double)sommaAppetibili)/(p.getPrice()*(double)p.getLatencyxCountry(idc)*modPreso);
			p.setAppetibilita(appet);
			p.setQnt(mult/S);	
			}
		}
		
	}
	
	private void assegna(Project pj, Package pk) {
		//OTTIMIZZARE:  tolgo e rimetto dalla mappa non mi piace
		int un, tot=0, decr, numPack;
		int qnt, disp;
		
		qnt = pk.getQnt();
		disp = pk.getDisp();
		
		//perc -> assegno perc% dei pacchetti +1
		if(qnt<disp) numPack = (int) (pk.getQnt()*this.modPerc) + 1;
		else numPack = (int) (pk.getDisp()*this.modPerc) + 1;
		//System.out.println(numPack);

		pk.decrementDisp(numPack);
		pk.addProject(pj);
		
		for(int i=0; i<S; i++) {
			un = pj.getUnitsService(i);
			decr = pk.getUnitsxService(i)*numPack;
			//if(decr<0) System.out.println("err");
			un -= decr;
			
			if(un < 0) un = 0;
			pj.refreshUnitService(i, un);
			tot+=un;
		}	
		pj.setTotalUnits(tot);
		
		pj.incrementaPackage(pk.getId(), numPack);
		//System.out.println("assegnato pacchetto " + pk +  " a " + pj);
	}
	
	public void outputSchermo() {
		//sono gia ordinati per nome
		Set<Entry<Integer, Integer>> set;
		Package p;
		int id, num;
		
		for(Project pj : projects) {
			set = pj.getEntries();
			
			for(Entry<Integer, Integer> e : set) {
				id = e.getKey();
				num = e.getValue();
				p = packages.get(id);
				System.out.print(p.getProvider().getId() + " " + p.getRegion().getId() + " " + num + " ");
			}
			System.out.println();
		}
	}
	
	public void outputFile(String file) {
		
		try {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		Set<Entry<Integer, Integer>> set;
		Package p;
		int id, num;
		
		for(Project pj : projects) {
			set = pj.getEntries();
			
			for(Entry<Integer, Integer> e : set) {
				id = e.getKey();
				num = e.getValue();
				p = packages.get(id);
				writer.append(p.getProvider().getId() + " " + p.getRegion().getId() + " " + num + " ");
			}
			writer.append("\n");
			
		}
		
		writer.close();		
		}catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}
	
	public void controllaValori() {
		//pacchetti disponibili
		System.out.println("Pacchetti rimanenti:");
		Collections.sort(packages, (a,b)-> a.getId()-b.getId());
		System.out.println(packages);
		
		//situazione progetti
		System.out.println("Situazione progetti:");
		Collections.sort(projects, (a,b) -> Double.compare(b.getNecessita(), a.getNecessita()));
		//Collections.sort(projects, (a,b) -> a.getTotalUnits()-b.getTotalUnits());
		
		System.out.println(projects);
	}
	
	private void riaggiungiProgetto(Project p) {
		//funziona
		projects.remove(p);
		p.calcolaNecessita();
		double nec = p.getNecessita();
		int ind=projects.size();
		
		for(Project pj : projects) {
			if(pj.getNecessita() < nec) {
				ind = projects.indexOf(pj);
				break;
			}
		}
		projects.add(ind, p);
	}
}

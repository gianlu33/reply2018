package reply2018java;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.util.List;
//import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;


public class Algorithm {
	int V,S,C,P;
	int is, ic, ipr, ir, ipk, ipj;
	Map<Integer, Service> services;
	Map<Integer, Country> countries;
	Map<String, Integer> idcountries;
	Map<Integer, Package> packages;
	Map<Integer, Project> projects;
	
	public Algorithm() {
		// TODO Auto-generated constructor stub
		services = new TreeMap<>();
		countries = new TreeMap<>();
		packages = new TreeMap<>();
		projects = new TreeMap<>();
		idcountries = new TreeMap<>();
		is=0;
		ic=0;
		ipr=0;
		ir=0;
		ipk=0;
		ipj=0;
	}

	void inputData(String file) {
		int i,j,k;
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
		float cp;
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
				r = new Region(ir++, line, p);
				//System.out.println(r);
				
				//pacchetti
				line = reader.readLine();
				data = line.split(" ");
				np = Integer.parseInt(data[0]);
				cp = Float.parseFloat(data[1]);
				pack = new Package(ipk, np, cp, p, r);
				packages.put(ipk++, pack);
				
				for(k=0; k<S; k++) {
					pack.addUnitsService(k, Integer.parseInt(data[k+2]));
				}
				
				line = reader.readLine();
				data = line.split(" ");
				for(k=0; k<C; k++) {
					pack.addLatencyCountry(k, Integer.parseInt(data[k]));
				}	
				
				System.out.println(pack);
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
			
			pj = new Project(ipj, pen, co);
			projects.put(ipj++, pj);
			
			for(j=0; j<S; j++) {
				pj.addUnitsService(j, Integer.parseInt(data[j+2]));
			}
			
			//System.out.println(pj);
		}
		

	
		reader.close();
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
		
	}
}

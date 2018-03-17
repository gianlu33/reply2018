package reply2018java;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Collection;

public class Project {
	private int id, penalty;
	private Country c;
	private Map<Integer, Integer> unitsXservice;
	private Map<Integer, Integer> numberPackets;
	private double necessity;
	private boolean ignore;
	private int totUnits;


	public Project(int id, int penalty, Country c) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.penalty = penalty;
		this.c = c;
		this.ignore = false;
		this.totUnits=0;
		
		unitsXservice = new TreeMap<>();
		numberPackets = new TreeMap<>();
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getPenalty() {
		return this.penalty;
	}
	
	public Country getCountry() {
		return this.c;
	}
	
	public int getUnitsService(int id) {
		return this.unitsXservice.get(id);
	}
	
	public void addUnitsService(int id, int units) {
		this.unitsXservice.put(id, units);
	}
	
	public Collection<Integer> getListUnits() {
		return this.unitsXservice.values();
	}
	
	public String toString() {
		//return this.id + " " + this.penalty + " " + this.c + "\n" + this.unitsXservice;
		return "Progetto: " +  this.id + " " + this.necessity + " " + this.totUnits + "\n" + this.unitsXservice;
		//return "Progetto: " +  this.id + " " + this.necessity + " " + this.totUnits + "\n" + this.numberPackets;

	}
	
	public void refreshUnitService(int serv, int units) {
		this.unitsXservice.replace(serv, units);		
	}
	
	public void incrementaPackage(int id, int np) {
		//OTTIMIZZARE, FA SCHIFO
		if(this.numberPackets.containsKey(id)) {
			int num = this.numberPackets.get(id);
			this.numberPackets.replace(id, num+np);
		}
		else this.numberPackets.put(id, np);
		//System.out.println(id);
	}
	
	public Set<Entry<Integer, Integer>> getEntries() {
		return this.numberPackets.entrySet();
	}
	
	public double getNecessita() {
		return this.necessity;
	}
	
	public void setNecessita(double nec) {
		this.necessity = nec;
	}
	
	public void calcolaNecessita() {
		this.necessity = (double)this.totUnits*(double)this.penalty;
		//if(this.necessity==0) System.out.println("c'è un problema");
	}
	
	public void setIgnore(boolean bool) {
		this.ignore=bool;
	}
	
	public boolean getIgnore() {
		return this.ignore;
	}
	
	public int getTotalUnits() {
		return this.totUnits;
	}
	
	public void setTotalUnits(int tot) {
		this.totUnits = tot;
	}
	
	public void calcolaTotUnits() {
		this.totUnits = this.unitsXservice.values().stream().collect(Collectors.summingInt(a->a));
	}
}

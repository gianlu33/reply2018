package reply2018java;

import java.util.Map;
import java.util.TreeMap;
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


	public Project(int id, int penalty, Country c) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.penalty = penalty;
		this.c = c;
		this.ignore = false;
		
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
		return "Progetto: " +  this.id + " " + this.penalty + "\n" + this.unitsXservice;

	}
	
	public void refreshUnitService(int serv, int units) {
		this.unitsXservice.replace(serv, units);		
	}
	
	public void incrementaPackage(int id) {
		//OTTIMIZZARE, FA SCHIFO
		if(this.numberPackets.containsKey(id)) {
			int num = this.numberPackets.get(id);
			this.numberPackets.replace(id, ++num);
		}
		else this.numberPackets.put(id, 1);
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
	
	public void setIgnore(boolean bool) {
		this.ignore=bool;
	}
	
	public boolean getIgnore() {
		return this.ignore;
	}
}

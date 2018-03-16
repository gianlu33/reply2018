package reply2018java;

import java.util.Map;
import java.util.TreeMap;

public class Project {
	private int id, penalty;
	private Country c;
	private Map<Integer, Integer> unitsXservice;
	private Map<Integer, Integer> numberPackets;


	public Project(int id, int penalty, Country c) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.penalty = penalty;
		this.c = c;
		
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
	
	public String toString() {
		return this.id + " " + this.penalty + " " + this.c + "\n" + this.unitsXservice;
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
}

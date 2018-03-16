package reply2018java;

import java.util.Map;
import java.util.TreeMap;

public class Project {
	int id, penalty;
	Country c;
	Map<Integer, Integer> unitsXservice;


	public Project(int id, int penalty, Country c) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.penalty = penalty;
		this.c = c;
		
		unitsXservice = new TreeMap<>();
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
}

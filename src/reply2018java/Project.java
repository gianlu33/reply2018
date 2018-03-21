package reply2018java;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Collection;

public class Project {
	private int id, basePenalty;
	private Country c;
	private Map<Integer, Integer> unitsXservice;
	private Map<Integer, Purchase> purchases;
	private double nec;
	private int unitsNeeded, unitsRemaining;
	private double avLatency, avIndex, totalCost, SLApenalty, score;

	public Project(int id, int penalty, Country c) {
		this.id = id;
		this.basePenalty = penalty;
		this.c = c;
		//this.ignore = false;
		this.unitsNeeded = this.unitsRemaining = 0;
		
		unitsXservice = new TreeMap<>();
		//numberPackets = new TreeMap<>();
		purchases = new TreeMap<>();

	}
	
	public double getAvLatency() {
		return this.avLatency;
	}
	
	public void calcolaAvLatency() {
		double sommatot=0, sommalat=0;
		int lat, totun;
		
		for(Purchase p : purchases.values()) {
			totun = p.getPackage().getTotalUnits()*p.getNum();
			lat = p.getPackage().getLatencyxCountry(this.c.getId());

			sommatot += totun;
			sommalat += totun*lat;
		}
		
		if(sommatot == 0) this.avLatency=0;
		else this.avLatency = sommalat/sommatot;
	}
	
	public double getAvIndex() {
		return this.avIndex;
	}
	
	public void calcolaAvIndex() {
		int n = this.unitsXservice.keySet().size();
		double[] numeratore = new double[n];
		double[] denominatore = new double[n];
		double un;
		double somma=0;
		
		//System.out.println(units[0]);
		
		for(Purchase p : purchases.values()) {
			
			for(int i=0; i<n; i++) {
				un = p.getPackage().getUnitsxService(i) * p.getNum();
				numeratore[i] += un;
				
				un *= un;
				denominatore[i] += un;
				
			}	
		}
		
		for(int i=0; i<n; i++) 
			if(denominatore[i] != 0) 
				somma += numeratore[i] * numeratore[i] / denominatore[i];
		 
		this.avIndex = somma / n;
	}
	
	public double getTotalCost() {
		return this.totalCost;
	}
	
	public void calcolaTotalCost() {
		double cost=0;
		
		for(Purchase p : purchases.values()) {
			cost += p.getNum()*p.getPackage().getPrice();
		}
		
		this.totalCost = cost;
	}
	
	public double getScore() {
		return this.score;
	}
	
	public void calcolaScore() {
		double A=0;
		
		this.calcolaAvIndex();
		//System.out.println(this.avIndex);
		this.calcolaAvLatency();
		//System.out.println(this.avLatency);
		this.calcolaSLApenalty();
		//System.out.println(this.SLApenalty);
		this.calcolaTotalCost();
		//System.out.println(this.totalCost);

		if(this.avIndex != 0) A = this.totalCost*this.avLatency/this.avIndex;
		
		this.score = (1e9)/(A + this.SLApenalty);
	}
	
	public double getSLApenalty() {
		return this.SLApenalty;
	}
	
	public void calcolaSLApenalty() {
		this.SLApenalty = (double)this.getUnitsRemaining() / (double)this.unitsNeeded * this.basePenalty;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getBasePenalty() {
		return this.basePenalty;
	}
	
	public Country getCountry() {
		return this.c;
	}
	
	public void addUnitsService(int id, int units) {
		this.unitsXservice.put(id, units);
	}
	
	public Collection<Integer> getListUnits() {
		return this.unitsXservice.values();
	}
	
	public String toString() {
		//return this.id + " " + this.penalty + " " + this.c + "\n" + this.unitsXservice;
		return "Progetto: " +  this.id + " " + this.unitsNeeded + " " + this.getUnitsRemaining() + " " + this.purchases;
		//return "Progetto: " +  this.id + " " + this.necessity + " " + this.totUnits + "\n" + this.numberPackets;

	}

	public void incrementaPackage(Package p, int np) {
		if(!purchases.containsKey(p.getId())) purchases.put(p.getId(), new Purchase(p, np));
		else purchases.get(p.getId()).incrNum(np);
	}
	
	public void decrementaPackage(Package pk, int np) {
		int pkid = pk.getId();
		Purchase p = purchases.get(pkid);
		
		if(p.getNum() <= np) purchases.remove(pkid);
		else p.decrNum(np);
	}
	
	public Collection<Purchase> getPurchases(){
		return this.purchases.values();
	}
	
	public double getNec() {
		return this.nec;
	}
	
	public void setNec(double nec) {
		this.nec = nec;
	}
	
	public void calcolaNec() {
		this.nec = (double)this.unitsRemaining /* *(double)this.basePenalty*/ ;
	}
	
	public int getUnitsNeeded() {
		return this.unitsNeeded;
	}
	
	public int getUnitsRemaining() {
		int n = this.unitsXservice.keySet().size();
		int tot[] = new int[n];
		int total=0, diff;
		
		for(Purchase p : purchases.values()) {
			for(int i=0; i<n; i++)
				tot[i] += p.getPackage().getUnitsxService(i)*p.getNum();
		}
		
		for(int i=0; i<n; i++) {
			diff = this.unitsXservice.get(i) - tot[i];
			if(diff >= 0) total += diff;
		}
		
		return total;
	}
	
	public int getUnitsRemaining(int service) {
		int tot=0, un;
		
		for(Purchase p : purchases.values()) {
			tot += p.getPackage().getUnitsxService(service)*p.getNum();
		}
		un = this.unitsXservice.get(service);
		
		if(un - tot >= 0)return un - tot;
		else return 0;
	}
	
	public void setUnitsRemaining(int tot) {
		this.unitsRemaining = tot;
	}
	
	public void initializeUnits() {
		this.unitsNeeded = this.unitsRemaining = this.unitsXservice.values().stream().collect(Collectors.summingInt(a->a));
	}
	
	public static int compareNec(Project p1, Project p2) {
		return Double.compare(p1.nec, p2.nec);
	}
	public static int compareId(Project p1, Project p2) {
		return p1.id-p2.id;
	}

	public static int compareScore(Project p1, Project p2) {
		return Double.compare(p1.score, p2.score);
	}
	
}

package reply2018java;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

public class Package {
	
	private int id, disp, tot;
	private double price;
	private double appet;
	private Map<Integer, Integer> unitsXservice;
	private Map<Integer, Integer> latencyXcountry;
	private Set<Project> progettiServiti;
	private Provider p;
	private Region r;
	private int totUnits, qnt;
	private boolean appetibile;
	
	public Package(int id, int tot, double price, Provider p, Region r) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.tot = tot;
		this.disp = tot;
		this.price = price;
		this.p = p;
		this.r = r;
		this.totUnits=0;
		
		unitsXservice = new TreeMap<>();
		latencyXcountry = new TreeMap<>();
		progettiServiti = new HashSet<>();
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getTot() {
		return this.tot;
	}
	
	public int getDisp() {
		return this.disp;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public int getUnitsxService(int id) {
		return unitsXservice.get(id);
	}
	
	public void decrementDisp(int num) {
		this.disp -= num;
	}
	
	public void addUnitsService(int id, int units) {
		this.unitsXservice.put(id, units);
	}
	
	public int getLatencyxCountry(int id) {
		return this.latencyXcountry.get(id);
	}
	
	public void addLatencyCountry(int id, int lat) {
		this.latencyXcountry.put(id, lat);
	}
	
	public String toString() {
		//return this.id + " Disp:" + this.disp + " Price:" + this.price + "\n" + "Provider: " + this.p + "\n" + "Region: " + this.r + "\nUnits: " + this.unitsXservice + "\nLatency: " + this.latencyXcountry;
	//return this.id + " Disp: " + this.disp;
		return this.p.getId() + " " + this.r.getId() + " " + this.tot;
	}
	
	public double getAppetibilita() {
		return this.appet;
	}
	
	public void setAppetibilita(double app) {
		this.appet = app;
	}
	
	public void addProject(Project p) {
		this.progettiServiti.add(p);
	}
	
	public boolean checkProject(Project p) {
		return this.progettiServiti.contains(p);
	}
	
	public Provider getProvider() {
		return this.p;
	}
	
	public Region getRegion() {
		return this.r;
	}
	
	public int getTotalUnits() {
		return this.totUnits;
	}
	
	public void setTotalUnits(int tot) {
		this.totUnits = tot;
	}
	
	public void calcolaTotUnits() {
		this.unitsXservice.values().stream().collect(Collectors.summingInt(a->a));
	}
	
	public int getQnt() {
		return this.qnt;
	}
	
	public void setQnt(int q) {
		this.qnt = q;
	}
	
	public boolean getAppetibile() {
		return this.appetibile;
	}
	
	public void setAppetibile(boolean bool) {
		this.appetibile = bool;
	}
}

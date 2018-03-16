package reply2018java;
import java.util.Map;
import java.util.TreeMap;

public class Package {
	
	private int id, disp;
	private float price;
	Map<Integer, Integer> unitsXservice;
	Map<Integer, Integer> latencyXcountry;
	Provider p;
	Region r;
	
	public Package(int id, int disp, float price, Provider p, Region r) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.disp = disp;
		this.price = price;
		this.p = p;
		this.r = r;
		
		unitsXservice = new TreeMap<>();
		latencyXcountry = new TreeMap<>();
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getDisp() {
		return this.disp;
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public int getUnitsxService(int id) {
		return unitsXservice.get(id);
	}
	
	public void decrementDisp() {
		this.disp--;
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
		return this.id + " Disp:" + this.disp + " Price:" + this.price + "\n" + "Provider: " + this.p + "\n" + "Region: " + this.r + "\nUnits: " + this.unitsXservice + "\nLatency: " + this.latencyXcountry;
	}
}

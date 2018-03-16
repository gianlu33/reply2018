package reply2018java;

public class Region {

	private int id;
	private String name;
	private Provider p;
	
	public Region(int id, String name, Provider p) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.p = p;
	}
	
	public String toString() {
		return this.id + " " + this.name + " " + this.p;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Provider getProvider() {
		return this.p;
	}
}

package reply2018java;

public class Country {

	private int id;
	private String name;
	
	public Country(int id, String name) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
	}
	
	public String toString() {
		return this.id + " " + this.name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
}
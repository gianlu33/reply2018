package reply2018java;

public class Provider {
	private String name;
	private int Rv, id, ir;
	
	public Provider(int id, String name, int rv) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.Rv = rv;
		this.ir = 0;
	}

	public String toString() {
		return this.id + " " + this.name + " " + this.Rv;
	}
	
	public int getIr() {
		return this.ir;
	}
	
	public void incrementIr() {
		this.ir++;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getRv() {
		return this.Rv;
	}
}

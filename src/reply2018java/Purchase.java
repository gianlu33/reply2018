package reply2018java;

public class Purchase {
	private int num;
	private Package p;
	
	public Purchase(Package p, int num) {
		// TODO Auto-generated constructor stub
		this.p = p;
		this.num = num;
	}
	
	public Package getPackage() {
		return p;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public void incrNum(int num) {
		this.num += num;
	}
	
	public String toString() {
		return this.p.getProvider().getId() + " " + this.p.getRegion().getId() + " " + this.getNum();
	}
	
	public static int compare(Purchase a, Purchase b) {
		if(a.p.getProvider().getId() < b.p.getProvider().getId()) return -1;
		else if(a.p.getProvider().getId() == b.p.getProvider().getId() && a.p.getRegion().getId() < b.p.getRegion().getId()) return -1;
		else return 1;

	}

}

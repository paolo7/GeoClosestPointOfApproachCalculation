
public class CPA {
	double time;
	double distance;
	
	public CPA(double time, double distance) {
		this.time = time;
		this.distance = distance;
	}
	
	public String toString() {
		return "CPA "+distance+"m at "+time+"s";
	}
}

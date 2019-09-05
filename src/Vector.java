
public class Vector {

	public double getAngle() {
		return angle;
	}

	public double getIntensity() {
		return intensity;
	}

	private double angle;
	private double intensity;
	
	public Vector(double angle, double intensity) {
		this.angle = angle;
		this.intensity = intensity;
	}
	
	public String toString() {
		return "<"+intensity+" "+angle+"'>";
	}
}

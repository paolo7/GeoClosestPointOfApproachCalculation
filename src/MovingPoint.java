
public class MovingPoint extends PointXY{

	public Vector v;
	
	public MovingPoint(PointXY coordinate, double angleNorthClockwise, double speedmps) {
		super(coordinate.x, coordinate.y);
		v = new Vector(angleNorthClockwise, speedmps);
	}
	public MovingPoint(PointXY coordinate, Vector v) {
		super(coordinate.x, coordinate.y);
		this.v = v;
	}
	
	public String toString() {
		return "Point: "+super.toString()+", speed: "+v.toString()+".";
	}
	
}

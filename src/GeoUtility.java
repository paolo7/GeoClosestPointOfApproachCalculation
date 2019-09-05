
public class GeoUtility {

	// if max_time_frame > 0, then this class returns the closest point of approach such that the 
	// time is <= max_time_frame
	static double max_time_frame = 60*60*24;
	
	
	// Algorithm taken from https://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java
	public static double latLongCoordinates2DistanceInMeters(double lat1, double lon1, double lat2, double lon2) {
		
		 double earthRadius = 6371000; //meters
		 double dLat = Math.toRadians(lat2-lat1);
		 double dLng = Math.toRadians(lon2-lon1);
		 double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
		            Math.sin(dLng/2) * Math.sin(dLng/2);
		 double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		 float dist = (float) (earthRadius * c);

		 return dist;
	}
	public static double latLongCoordinates2DistanceInMeters(PointXY point1, PointXY point2) {
		return latLongCoordinates2DistanceInMeters(point1.x, point1.y, point2.x, point2.y);
	}
	public static double latLongCoordinates2DistanceInMeters2(double lat1, double lon1, double lat2, double lon2) {
		double R = 6371e3;
		double phi1 = Math.toRadians(lat1);
		double phi2 = Math.toRadians(lat2);
		double Deltaphi = Math.toRadians(lat2-lat1);
		double Deltadelta = Math.toRadians(lon2-lon1);
		double a = Math.sin(Deltaphi/2) * Math.sin(Deltaphi/2) +
		        Math.cos(phi1) * Math.cos(phi2) *
		        Math.sin(Deltadelta/2) * Math.sin(Deltadelta/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = R * c;
		return d;
	}

	
	/* probably wrong implementation
	 * public static double latLongToAngleNorthClockwise2(double lat1, double long1, double lat2, double long2) {

	    double dLon = (long2 - long1);

	    double y = Math.sin(dLon) * Math.cos(lat2);
	    double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
	            * Math.cos(lat2) * Math.cos(dLon);

	    double brng = Math.atan2(y, x);

	    brng = Math.toDegrees(brng);
	    brng = (brng + 360) % 360;
	    brng = 360 - brng; // count degrees counter-clockwise - remove to make clockwise

	    return brng;
	}*/
	
	public static double latLongToAngleNorthClockwise(double lat1, double lon1, double lat2, double lon2) {


        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double Deltadelta = Math.toRadians(lon2-lon1);

        double x = Math.cos(phi1) * Math.sin(phi2) - Math.sin(phi1) * Math.cos(phi2) * Math.cos(Deltadelta);
        double y = Math.sin(Deltadelta) * Math.cos(phi2);
        double theta = Math.atan2(y, x);

        double bearing = Math.toDegrees(theta);
        if (bearing < 0) bearing += 360;
        return bearing;
	}
	public static double latLongToAngleNorthClockwise(PointXY point1, PointXY point2) {
		return latLongToAngleNorthClockwise(point1.x, point1.y, point2.x, point2.y);
	}
	
	/**
	 * Converts from North Clockwise degrees to cartesian degrees and vice versa
	 * @param angleNorthClockwise
	 * @return
	 */
	public static double angleNorthClockwise2AngleCartesian(double angleNorthClockwise) {
		double angle = -(angleNorthClockwise-90);
		while(angle < 0) angle += 360;
		return angle;
	}
	
	public static MovingPoint relativeMovingPointDistance(MovingPoint origin, MovingPoint other) {
		
		double distance = latLongCoordinates2DistanceInMeters(origin, other);
		double angleCartesian  = angleNorthClockwise2AngleCartesian(latLongToAngleNorthClockwise(origin, other));		
		double x = distance*Math.cos(Math.toRadians(angleCartesian));
		double y = distance*Math.sin(Math.toRadians(angleCartesian));
		PointXY P = new PointXY(x,y);
		//System.out.println("\nDistance "+distance+" m with angle (cartesian) "+angleCartesian);
		//System.out.println("X,Y from the origin: "+P);
		Vector relativeVelocity = sumVectors(origin.v.getAngle(),-origin.v.getIntensity(),
				other.v.getAngle(),other.v.getIntensity());
		MovingPoint relativeMovingPoint = new MovingPoint(P,relativeVelocity);
		
		return relativeMovingPoint;
	}

	public static Vector sumVectors(double angle1, double length1, double angle2, double length2) {
		double angle1c = angleNorthClockwise2AngleCartesian(angle1);
		double angle2c = angleNorthClockwise2AngleCartesian(angle2);
		double dx1 = Math.cos(Math.toRadians(angle1c))*length1;
		double dy1 = Math.sin(Math.toRadians(angle1c))*length1;
		double dx2 = Math.cos(Math.toRadians(angle2c))*length2;
		double dy2 = Math.sin(Math.toRadians(angle2c))*length2;
		double newLength = Math.sqrt( Math.pow(dx1+dx2,2)+Math.pow(dy1+dy2,2) );
		double newAngle = angleNorthClockwise2AngleCartesian(Math.toDegrees(Math.atan2(dy1+dy2, dx1+dx2)));
		return new Vector(newAngle,newLength);
	}
	
	public static String MovingPoint2XYFunctionString(MovingPoint p) {
		return " x = "+p.x+" + "+(Math.cos(Math.toRadians(angleNorthClockwise2AngleCartesian(p.v.getAngle())))*p.v.getIntensity())+"t"
		+ "\n y = "+p.y+" + "+(Math.sin(Math.toRadians(angleNorthClockwise2AngleCartesian(p.v.getAngle())))*p.v.getIntensity())+"t";
	}
	public static String MovingPoint2DistanceFunctionString(MovingPoint p) {
		return " d = sqrt( ("+p.x+" + "+(Math.cos(Math.toRadians(angleNorthClockwise2AngleCartesian(p.v.getAngle())))*p.v.getIntensity())+"t)^2 + "
		+ "("+p.y+" + "+(Math.sin(Math.toRadians(angleNorthClockwise2AngleCartesian(p.v.getAngle())))*p.v.getIntensity())+"t)^2 )";
	}
	
	
	public static double distanceFromOriginAtTimeT(MovingPoint p, double time) {
		return Math.sqrt(
				Math.pow(p.x+(Math.cos(Math.toRadians(angleNorthClockwise2AngleCartesian(p.v.getAngle())))*p.v.getIntensity()*time), 2) + 
				Math.pow(p.y+(Math.sin(Math.toRadians(angleNorthClockwise2AngleCartesian(p.v.getAngle())))*p.v.getIntensity()*time), 2) 				
				);
	}
	
	// finding the minimum of the distance function as defined by (MovingPoint2DistanceFunctionString)
	// simplifying sqr( (kc + kt*t)^2 +  (wc + wt*t)^2 ) 
	// to          kt^2*t^2 + kc^2 +2*kc*kt*t + wt^2*t^2 + wc^2 +2*wc*wt*t
	// to          t^2 * (kt^2 + wt^2) + t * (2*kc*kt +2*wc*wt) + (kc^2 + wc^2)
	// to          t^2 * a + t * b + c
	public static CPA computeCPAhelper(MovingPoint relative_p) {
		
		double kc = relative_p.x;
		double kt = (Math.cos(Math.toRadians(angleNorthClockwise2AngleCartesian(relative_p.v.getAngle())))*relative_p.v.getIntensity());
		double wc = relative_p.y;
		double wt = (Math.sin(Math.toRadians(angleNorthClockwise2AngleCartesian(relative_p.v.getAngle())))*relative_p.v.getIntensity());
		
		double a = Math.pow(kt, 2) + Math.pow(wt, 2);
		double b = 2 * ( kc * kt + wc *wt);
		double c = Math.pow(kc, 2) + Math.pow(wc, 2);
		
		// given that a is always positive, the axis contains the minimum
		double axis = -(b/(2*a));
		double minimumAtAxis = Math.sqrt( c-(Math.pow(b, 2)/(4*a)) );
	
		double minTime = Double.isNaN(axis) ? 0 : Math.max(0, axis );
		double min_distance = distanceFromOriginAtTimeT(relative_p,minTime);
		if(max_time_frame > 0 && minTime > max_time_frame)
			return computeCPAbruteForceHelper(relative_p);
		else
			return new CPA(minTime, min_distance);
		
	}
	
	public static CPA computeCPAbruteForceHelper(MovingPoint relative_p) {
		
		double min_distance = Double.MAX_VALUE;
		double minTime = -1;
		
		// consider up to a day
		for(int i = 0; i < max_time_frame; i++) {
			//System.out.println(i+" d="+distanceFromOriginAtTimeT(p,i));
			if(distanceFromOriginAtTimeT(relative_p,i) < min_distance) {
				min_distance = distanceFromOriginAtTimeT(relative_p,i);
				minTime = i;
			}
		}
		//System.out.println("Brute force method, min distance of "+min_distance+" meters found at time "+minTime+" seconds");
		return new CPA(minTime, min_distance);
		
	}
	
	public static CPA computeCPAbruteForce(MovingPoint origin, MovingPoint other) {
		MovingPoint relative_p = relativeMovingPointDistance(origin, other);
		return computeCPAbruteForceHelper(relative_p);
	}
	
	public static CPA computeCPA(MovingPoint origin, MovingPoint other) {
		MovingPoint relative_p = relativeMovingPointDistance(origin, other);
		return computeCPAhelper(relative_p);
	}
	
	public static double computeCPAdistance(MovingPoint origin, MovingPoint other) {
		MovingPoint relative_p = relativeMovingPointDistance(origin, other);
		return computeCPAhelper(relative_p).distance;
	}
	public static double computeCPAdistance(double lat1, double lon1, double bearing1, double speed1, double lat2, double lon2, double bearing2, double speed2) {
		MovingPoint relative_p = relativeMovingPointDistance(new MovingPoint(new PointXY(lat1, lon1), new Vector(bearing1, speed1)), 
				new MovingPoint(new PointXY(lat2, lon2), new Vector(bearing2, speed2)));
		return computeCPAhelper(relative_p).distance;
	}
	public static double computeCPAtime(MovingPoint origin, MovingPoint other) {
		MovingPoint relative_p = relativeMovingPointDistance(origin, other);
		return computeCPAhelper(relative_p).time;
	}
	public static double computeCPAtime(double lat1, double lon1, double bearing1, double speed1, double lat2, double lon2, double bearing2, double speed2) {
		MovingPoint relative_p = relativeMovingPointDistance(new MovingPoint(new PointXY(lat1, lon1), new Vector(bearing1, speed1)), 
				new MovingPoint(new PointXY(lat2, lon2), new Vector(bearing2, speed2)));
		return computeCPAhelper(relative_p).time;
	}
	
}

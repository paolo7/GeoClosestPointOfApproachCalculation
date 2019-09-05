
public class TestMain {

	public static void main(String[] args) {
		

		double distanceFromEdinburghToGlasgow = GeoUtility.latLongCoordinates2DistanceInMeters(55.953198, -3.189080, 55.860293, -4.250526);
		double angleFromEdinburghToGlasgow = GeoUtility.latLongToAngleNorthClockwise(55.953198, -3.189080, 55.860293, -4.250526);
		System.out.println("Distance from Edinburgh to Glasgow = "+distanceFromEdinburghToGlasgow/1000+" km, with angle "+angleFromEdinburghToGlasgow);
		
		double distanceFromLondonToWhangaruru = GeoUtility.latLongCoordinates2DistanceInMeters(51.506417, -0.123786, -35.349622, 174.325906);
		double angleFromLondonToWhangaruru = GeoUtility.latLongToAngleNorthClockwise(51.506417, -0.123786, -35.349622, 174.325906);
		System.out.println("Distance from London to Whangaruru = "+distanceFromLondonToWhangaruru/1000+" km, with angle "+angleFromLondonToWhangaruru);
		
		
		
		double angleEast = GeoUtility.latLongToAngleNorthClockwise(47.374454, 8.540751, 47.350694, 9.499126);
		double angleNorthEast = GeoUtility.latLongToAngleNorthClockwise(50.904609, -1.408833, 50.958937, -1.312466);
		double angleNorth = GeoUtility.latLongToAngleNorthClockwise(47.374454, 8.540751, 47.851810, 8.549776);
		double angleWest = GeoUtility.latLongToAngleNorthClockwise(47.374454, 8.540751, 47.392300, 7.833125);
		double angleSouth = GeoUtility.latLongToAngleNorthClockwise(47.374454, 8.540751, 46.960744, 8.547788);

		System.out.println("Angle East = "+angleEast);
		System.out.println("Angle North East = "+angleNorthEast);
		System.out.println("Angle North = "+angleNorth);
		System.out.println("Angle West = "+angleWest);
		System.out.println("Angle South = "+angleSouth);
		
		
		MovingPoint mainPoint = new MovingPoint(new PointXY(50.906953, -1.404564), 5, 10);
		
		MovingPoint approachingPerpendicularFromEast = new MovingPoint(new PointXY(50.908515, -1.401568), 270, 10);
		
		
		System.out.println("\nMoving points example 1, situated about 270 meters away from each other. Both speeds 10 m/s");
		System.out.println("       <---- O");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("^");
		System.out.println("|");
		System.out.println("|");
		System.out.println("|");
		System.out.println(" ");
		System.out.println("M");
		System.out.println("\nDistance from M to O = "+(GeoUtility.latLongCoordinates2DistanceInMeters(mainPoint, approachingPerpendicularFromEast))+
				" m, with north clockwise angle "+GeoUtility.latLongToAngleNorthClockwise(mainPoint, approachingPerpendicularFromEast));
		
		MovingPoint newMovePoint = GeoUtility.relativeMovingPointDistance(mainPoint, approachingPerpendicularFromEast);
		System.out.println(GeoUtility.MovingPoint2XYFunctionString(newMovePoint));
		System.out.println(GeoUtility.MovingPoint2DistanceFunctionString(newMovePoint));
		System.out.println("Simplified relative moving point to the origin:\n  - "+newMovePoint);

		System.out.println( GeoUtility.computeCPA(mainPoint, approachingPerpendicularFromEast) ) ;
		
		System.out.println("\n CPA going toward each other: "+
		"\n - time: "+GeoUtility.computeCPAtime(50.906953, -1.404564, 60, 10, 50.908515, -1.401568, 225, 10) +
		"\n - distance: "+GeoUtility.computeCPAdistance(50.906953, -1.404564, 60, 10, 50.908515, -1.401568, 225, 10));
		System.out.println("\n CPA exactly same direction (SW) same speed: "+
		"\n - time: "+GeoUtility.computeCPAtime(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 225, 5) +
		"\n - distance: "+GeoUtility.computeCPAdistance(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 225, 5));
		System.out.println("\n CPA exactly same direction (SW) different speed (other object catching up): "+
				"\n - time: "+GeoUtility.computeCPAtime(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 225, 6) +
				"\n - distance: "+GeoUtility.computeCPAdistance(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 225, 6));
		System.out.println("\n CPA exactly same direction (SW) different speed (other object left behind): "+
				"\n - time: "+GeoUtility.computeCPAtime(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 225, 4) +
				"\n - distance: "+GeoUtility.computeCPAdistance(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 225, 4));			
		System.out.println("\n CPA exactly same direction (SW) different speed (other object still): "+
				"\n - time: "+GeoUtility.computeCPAtime(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 225, 0) +
				"\n - distance: "+GeoUtility.computeCPAdistance(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 225, 0));	
		System.out.println("\n CPA exactly same direction (SW) different speed (this object still): "+
				"\n - time: "+GeoUtility.computeCPAtime(50.906953, -1.404564, 225, 0, 50.908515, -1.401568, 225, 4) +
				"\n - distance: "+GeoUtility.computeCPAdistance(50.906953, -1.404564, 225, 0, 50.908515, -1.401568, 225, 4));
		System.out.println("\n CPA exactly same direction (SW) different speed (both objects still): "+
				"\n - time: "+GeoUtility.computeCPAtime(50.906953, -1.404564, 225, 0, 50.908515, -1.401568, 225, 0) +
				"\n - distance: "+GeoUtility.computeCPAdistance(50.906953, -1.404564, 225, 0, 50.908515, -1.401568, 225, 0));
		System.out.println("\n CPA almost exactly same direction (1 degree difference) (SW) same speed: "+
				"\n - time: "+GeoUtility.computeCPAtime(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 226, 5) +
				"\n - distance: "+GeoUtility.computeCPAdistance(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 226, 5));
		System.out.println("\n CPA almost exactly same direction (0.01 degree difference) (SW) same speed: "+
				"\n - time: "+GeoUtility.computeCPAtime(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 225.01, 5) +
				"\n - distance: "+GeoUtility.computeCPAdistance(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 225.01, 5));
		System.out.println("\n CPA after time windows, as almost exactly same direction (0.001 degree difference) (SW) same speed: "+
				"\n - time: "+GeoUtility.computeCPAtime(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 225.001, 5) +
				"\n - distance: "+GeoUtility.computeCPAdistance(50.906953, -1.404564, 225, 5, 50.908515, -1.401568, 225.001, 5));				
	}

}

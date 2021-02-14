package Infrastructure;

public class Coordinates {
		
		public static void main(String[] args) {
			double theta = getAngle(8, 14, 4, 7);
			System.out.println(String.format("< %f, %f > at angle %f with velocity %f",
					getXVelocity(theta, 5), getYVelocity(theta, 5), theta, 5));
		}
		
		public static double getAngle(double x1, double y1, double x2, double y2) {
			double xLen = x2 - x1;
			double yLen = y2 - y1;
			double angle = Math.toDegrees(Math.atan2(yLen, xLen));
			angle = angle < 0 ? angle + 360 : angle;
			return angle;
		}
		
		public static double getXVelocity(double angle, double velocity) {
			return velocity * Math.cos(Math.toRadians(angle));
		}
		
		public static double getYVelocity(double angle, double velocity) {
			return velocity * Math.sin(Math.toRadians(angle));
		}
	}
	 


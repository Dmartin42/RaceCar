package MapEditor;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyStore.LoadStoreParameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

import javax.swing.JOptionPane;
import javax.xml.bind.SchemaOutputResolver;

public class LoadMap {
	public static final String filePath = "Resources/PlayerData.txt";
	public static File file = new File(filePath);
	public static CreateMap mapCreator = new CreateMap();
	public static List<Integer> xPoints = new ArrayList<>();
	public static List<Integer> yPoints = new ArrayList<>();
	public static int n;

	public LoadMap() {
		int[] testX = { 1, 2, 3, 4, 5 };
		int[] testY = { 5, 4, 3, 2, 1 };
		int testN = 45;
		saveData(testX, testY);
		double testRotation = 45.64;
		saveStartLocation(45, 45, testRotation);

	}

	/**
	 * @param xpoints
	 * @param ypoints
	 * @param npoints
	 * @deprecated {@code setPoints(getFirstLine())}
	 */
	public static void saveData(int[] xpoints, int[] ypoints, int npoints) { // Not in use; used for saving polylines
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
			StringJoiner sj = new StringJoiner(";");
			sj.add("x");
			for (int i = 0; i < xpoints.length; i++) {
				sj.add(String.valueOf(xpoints[i]));
			}
			writer.write(sj.toString());

			writer.append("\n");
			sj = new StringJoiner(";");
			sj.add("y");

			for (int i = 0; i < ypoints.length; i++) {
				sj.add(String.valueOf(ypoints[i]));
			}
			writer.write(sj.toString());

			writer.append("\n");
			sj = new StringJoiner(";");
			sj.add("n");
			sj.add(String.valueOf((npoints)));
			writer.append(sj.toString());

			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("Couldn't find file!");
			e.printStackTrace();
		}
	}


	/**
	 * @param xpoints
	 * @param ypoints
	 * @deprecated Use saveCoordinates instead
	 */
	public static void saveData(int[] xpoints, int[] ypoints) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
			StringJoiner sj = new StringJoiner(";");
			sj.add("x");
			for (int i = 0; i < xpoints.length; i++) { // X
				sj.add(String.valueOf(xpoints[i]));
			}
			writer.write(sj.toString());
			writer.append("\n");
			sj = new StringJoiner(";");
			sj.add("y");
			for (int i = 0; i < ypoints.length; i++) { // Y
				sj.add(String.valueOf(ypoints[i]));
			}
			writer.write(sj.toString()); // Add ypoints

			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("Couldn't find file!");
			e.printStackTrace();
		}
	}

	/**
	 * @return List<Integer>
	 * @deprecated Use {@code setPoints(getFirstLine())} instead
	 * 
	 */
	public static List<Integer> getXData() {
		List<Integer> xpoints = new ArrayList<>();
		try {
			Scanner reader = new Scanner(file);
			while (reader.hasNext()) {
				String[] data = reader.nextLine().split(";");
				if (data[0].equalsIgnoreCase("x")) { // Clips out the x to process data
					data = Arrays.copyOfRange(data, 1, data.length);
					for (String x : data) {
						xpoints.add(toInt(x));
					}
				} else {
					break;
				}
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Could not process X data" + e.getCause());
		}
		return xpoints;
	}

	/**
	 * @return List<Integer>
	 * @author dmart
	 * @deprecated Use {@code setPoints(getFirstLine())} instead
	 */
	public static List<Integer> getYData() {
		List<Integer> yData = new ArrayList<>();
		try {
			Scanner reader = new Scanner(file);
			while (reader.hasNext()) {
				String[] data = reader.nextLine().split(";");
				if (data[0].equalsIgnoreCase("y")) { // Clips out the x to process
																						// data
					data = Arrays.copyOfRange(data, 1, data.length);
					for (String y : data) {
						yData.add(toInt(y));
					}
				}
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Couldn't process Y data " + e.getLocalizedMessage());
		}
		return yData;
	}

	/**gets the location data for the start line
	 * @return Point
	 */
	public static Point getSavedStartLineLocation() {
		int tempX = 0, tempY = 9; // Create temporary variables
		try {
			Scanner reader = new Scanner(file);
			while (reader.hasNext()) {
				String[] data = reader.nextLine().split(";");
				if (data[0].equalsIgnoreCase("x1")) {
					data = Arrays.copyOfRange(data, 1, data.length);
					for (String x : data) {
						tempX = toInt(x);
					}
				} else if (data[0].equalsIgnoreCase("y1")) {
					data = Arrays.copyOfRange(data, 1, data.length);
					for (String y : data) {
						tempY = toInt(y);
					}
				}
			}
			reader.close();
			return new Point(tempX, tempY);
		} catch (Exception e) {
			System.err.println("Could not process player data" + e.getCause());
		}
		return null;
	}

	/**gets the rotation data for the start line
	 * @return double
	 */
	public static double getSavedStartLineRotationData() {
		try {
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				String[] data = reader.nextLine().split(";");
				if (data[0].equalsIgnoreCase("rot")) {
					data = Arrays.copyOfRange(data, 1, data.length);
					for (String rotation : data)
						return toDouble(rotation);
				}
			}
			reader.close();
		} catch (Exception e) {
			System.err.print("Couldn't get rotation data: " + e.getLocalizedMessage());
		}
		return 0.000002;
	}

	/**
	 * @param point
	 * @deprecated Use saveCoordinates instead
	 */
	public static void saveData(List<Point>point) {
		int[] tempX = new int[point.size()];
		int[] tempY = new int[point.size()];
		for (int i = 0; i < tempX.length; i++) {
			tempX[i] = point.get(i).x;
			tempY[i] = point.get(i).y;
		}
		saveData(tempX,tempY);
	} 
	public static void saveCoordinates(List<Line2D> lines) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
			StringJoiner sj = new StringJoiner(";");
			for (Line2D line : lines) {
				Point2D pt1, pt2;
				pt1 = line.getP1();
				pt2 = line.getP2();
				sj.add("(" + pt1.getX() + "," + pt1.getY() + ")"); //Adds the points by line rather than by list. This is so when read they can be easily attributed to a line.
				sj.add("(" + pt2.getX() + "," + pt2.getY() + ")");
			}
			writer.write(sj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.getCause();
			System.err.print("Error in writing to file:");
			e.printStackTrace();
		}

	}

	public static void saveCoordinates(String coordinates) {
		List<Point> temp = setPoints(coordinates);
		List<Line2D> tempL = setLines(temp);
		saveCoordinates(tempL);
	}

	public static List<Line2D> setLines(List<Point> coordinates) {
		List<Line2D> lines = new ArrayList<>();
		for (int i = 0; i < coordinates.size(); i += 2) {
			Line2D.Double temp;
			if (i + 1 < coordinates.size()) {
				Point pt1 = coordinates.get(i);
				Point pt2 = coordinates.get(i + 1);
				temp = new Line2D.Double(pt1, pt2);
				lines.add(temp);
			}
		}
		return lines;
	}

	public static String getFirstLine() {
		String line = "FILE ERROR";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			line = br.readLine();
			System.out.println(line);
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.print("Problem with reading file!");
			e.printStackTrace();
		}
		return line;
	}

	public static List<Point> setPoints(String input) {
		List<Point> points = new ArrayList<>();
		if (input.equalsIgnoreCase("FILE ERROR")||input.isEmpty())
			return points;
		String parts[] = input.split(";");// Splits the string into multiple (#,#)
		for (String s : parts) {
			String cordXY[] = s.split(","); // Takes the individual numbers from the parenthesis
			String x = cordXY[0].trim().substring(1).trim(); // x = (#
			String y = cordXY[1].trim().substring(0, cordXY[1].trim().length() - 1).trim(); // y = ,#)
			Point apnd = new Point();
			apnd.setLocation(toDouble(x), toDouble(y));
			points.add(apnd);
		}
		return points;
	}

	/*
	 * public static void getAndProcessRectData() { // Retrieve Rectangle Data
	 * xPoints.clear(); yPoints.clear(); int x = 0, y = 0; double rotation = 0; try
	 * { Scanner reader = new Scanner(file); String line; while (reader.hasNext()) {
	 * String[] data = reader.nextLine().split(";"); if
	 * (data[0].equalsIgnoreCase("x")) { // Clips out the x to process data data =
	 * Arrays.copyOfRange(data, 1, data.length); for (String s : data) {
	 * processXdata(s); }
	 * 
	 * } else if (data[0].equalsIgnoreCase("y")) { data = Arrays.copyOfRange(data,
	 * 1, data.length); // Clips out the character for (String s : data) {
	 * processYdata(s); } } else if (data[0].equalsIgnoreCase("x1")) { data =
	 * Arrays.copyOfRange(data, 1, data.length); for (String s : data) { x =
	 * toInt(s); } } else if (data[0].equalsIgnoreCase("y1")) { data =
	 * Arrays.copyOfRange(data, 1, data.length); for (String s : data) { y =
	 * toInt(s); }
	 * 
	 * } else if (data[0].equalsIgnoreCase("rot")) { data = Arrays.copyOfRange(data,
	 * 1, data.length); for (String input : data) rotation = toDouble(input); }
	 * 
	 * } } catch (Exception e) { System.err.println("Could not process player data"
	 * + e.getCause()); }
	 * 
	 * processAllData(xPoints, yPoints, x, y, rotation); }
	 */

	public static double toDouble(String process) {
		return Double.parseDouble(process);
	}



	public static void main(String[] args) {
		int input = JOptionPane.showConfirmDialog(null,
				"Are you sure you want to override current data?\nOVERRIDE IS ON!! WARNING!!!");
		if (input == JOptionPane.YES_OPTION) {
			LoadMap loader = new LoadMap();
		} else {
			System.exit(0);
		}

	}

	public static void saveStartLocation(int x, int y, double rotation) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			StringJoiner sj = new StringJoiner(";");
			writer.append("\n");

			sj.add("x1"); // Add x
			sj.add(String.valueOf(x));
			writer.write(sj.toString());
			writer.append("\n");

			sj = new StringJoiner(";");
			sj.add("y1");
			sj.add(String.valueOf(y));
			writer.write(sj.toString()); // Add y
			writer.append("\n");

			sj = new StringJoiner(";"); // Adds Rotation
			sj.add("rot");
			sj.add(String.valueOf(rotation));
			writer.write(sj.toString());

			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static int toInt(String t) {
		return Integer.parseInt(t);

	}

}

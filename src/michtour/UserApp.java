/****************************************
 * Adam Tracy                           *
 * Assignment 6 - Graphs                *
 * UserApp                              *
 * Calls MapData and Route and runs them*
 * based on the transaction file        *
 ***************************************/
package michtour;

import java.util.Scanner;
import java.io.*;

public class UserApp {

	public static void main(String[] args) throws IOException {
		// declare some variables
		MapData md = new MapData();
		Route route = new Route();
		File file = new File("CityPairsTestPlan.txt");
		Scanner tranFile = new Scanner(file);
		PrintWriter log = new PrintWriter(new BufferedWriter(new FileWriter(
				"Log.txt", true)));
		String line;
		String pen1;
		String pen2;
		short cityNum1 = 0;
		short cityNum2 = 0;
		String temp[];
		// **************************************************************
		// while the transfile has more stuff, clean it up and call route for
		// shortest path
		while (tranFile.hasNext()) {
			line = tranFile.nextLine();
			if (!line.startsWith("%") && !line.isEmpty()) {

				log.printf("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++%n");
				temp = line.split(" ");
				cityNum1 = md.getCityNumber(temp[0]);
				cityNum2 = md.getCityNumber(temp[1]);
				pen1 = md.getPeninsula(temp[0]);
				pen2 = md.getPeninsula(temp[1]);
				if (temp[0].equalsIgnoreCase("theBridge")) {
					pen1 = pen2;
				} else if (temp[1].equalsIgnoreCase("theBridge")) {
					pen2 = pen1;
				}
				// start of calling route to find shortest paths
				if (pen1.equalsIgnoreCase(pen2) && cityNum1 != -1
						&& cityNum2 != -1) {
					log.printf("START: " + md.getCityName(cityNum1) + " ("
							+ cityNum1 + ") " + pen1 + "%n");
					log.printf("END: " + md.getCityName(cityNum2) + " ("
							+ cityNum2 + ") " + pen2 + "%n%n");
					route.findShortestRoute(cityNum1, cityNum2, md, log);
				} else if (!pen1.equalsIgnoreCase(pen2) && cityNum1 != -1
						&& cityNum2 != -1) {
					log.printf("START: " + md.getCityName(cityNum1) + " ("
							+ cityNum1 + ") " + pen1 + "%n");
					log.printf("END: " + md.getCityName(cityNum2) + " ("
							+ cityNum2 + ") " + pen2 + "%n%n");
					route.findShortestRoute(cityNum1, (short) 0, md, log);
					log.printf("%n");
					route.findShortestRoute((short) 0, cityNum2, md, log);
				} else {
					log.printf("ERROR: One or more cities not in database!%n");
				}

			}
		}

		// finish up
		log.close();
		tranFile.close();
		md.finishUp();
	}

}

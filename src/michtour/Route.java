/****************************************
 * Adam Tracy                           *
 * Assignment 6 - Graphs                *
 * Route                                *
 * Runs Dijkstra's Algorithm to find    *
 * the shortest path                    *
 ***************************************/
package michtour;

import java.io.*;

public class Route {

	// declare some things
	private boolean[] included;
	private int[] distance;
	private int[] path;
	private int total;

	// ******************************************************
	/**
	 * constructor
	 * 
	 * @throws IOException
	 */
	public Route() throws IOException {
	}

	// ******************************************************
	/**
	 * driver method for it all
	 * 
	 * @param start
	 * @param destination
	 * @param md
	 * @param log
	 * @throws IOException
	 */
	public void findShortestRoute(short start, short destination, MapData md,
			PrintWriter log) throws IOException {
		initialize3Arrays(start, md);
		searchForPath(start, destination, md, log);
		reportAnswers(start, destination, md, log);
	}

	/**
	 * initilize the 3 arrays so they're ready
	 * 
	 * @param start
	 * @param md
	 * @throws IOException
	 */
	private void initialize3Arrays(short start, MapData md) throws IOException {
		included = new boolean[52];
		for (int i = 0; i < included.length; i++) {
			if (i == start) {
				included[i] = true;
			} else {
				included[i] = false;
			}
		}
		distance = new int[52];
		for (int i = 0; i < distance.length; i++) {
			distance[i] = md.getRoadDistance(start, (short) i);
		}
		path = new int[52];
		for (int i = 0; i < path.length; i++) {
			if (distance[i] != Short.MAX_VALUE && distance[i] != 0) {
				path[i] = start;
			} else {
				path[i] = -1;
			}
		}

	}

	/**
	 * dijkstra's algorithm to search for the shortest path
	 * 
	 * @param start
	 * @param destination
	 * @param md
	 * @param log
	 * @throws IOException
	 */
	private void searchForPath(short start, short destination, MapData md,
			PrintWriter log) throws IOException {

		log.printf("TRACE OF TARGETS: " + md.getCityName(start) + ">");
		short targetDistance = Short.MAX_VALUE;
		short target = start;
		while (included[destination] != true) {
			targetDistance = Short.MAX_VALUE;
			for (short i = 0; i < 52; i++) {
				if (included[i] == false) {
					if (targetDistance > distance[i]) {
						target = i;
						targetDistance = (short) distance[i];
					}
				}
			}
			log.print(md.getCityName(target) + ">");
			included[target] = true;
			for (short i = 0; i < 52; i++) {
				if (included[i] == false) {
					if (md.getRoadDistance(target, i) != 0
							&& md.getRoadDistance(target, i) != Short.MAX_VALUE) {
						if (distance[target] + md.getRoadDistance(target, i) < distance[i]) {
							distance[i] = distance[target]
									+ md.getRoadDistance(target, i);
							path[i] = target;
						}
					}
				}
			}
		}
		log.printf("%n");

	}

	/**
	 * report the total distance and shortest path - not fully functional
	 * 
	 * @param start
	 * @param destination
	 * @param md
	 * @param log
	 */
	private void reportAnswers(short start, short destination, MapData md,
			PrintWriter log) {
		for (int i = 0; i < distance.length; i++) {
			if (distance[i] != Short.MAX_VALUE && distance[i] != 0) {
				total = distance[i];
			}
		}
		if (start == destination) {
			total = 0;
		}
		log.printf("Total Distance: " + total + " miles.%n");
		log.printf("Shortest Route: ");
		for (int i = 0; i < path.length; i++) {
			if (path[i] != -1) {
				log.printf(md.getCityName((short) path[i]) + ">");
			}
		}
		log.printf("%n");
	}
}

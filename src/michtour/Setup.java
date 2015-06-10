/****************************************
 * Adam Tracy                           *
 * Assignment 6 - Graphs                *
 * Setup                                *
 * Builds the binary file from the      *
 * Mich City text file                  *
 ***************************************/

package michtour;

import java.io.*;
import java.util.Scanner;

public class Setup {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// declare some things
		String temp[];
		String line;
		File file = new File("MichiganRawMapData.txt");
		PrintWriter log = new PrintWriter(new BufferedWriter(new FileWriter(
				"Log.txt", false)));
		RandomAccessFile bWriter = new RandomAccessFile(new File(
				"MichiganMapData.bin"), "rws");
		Scanner rawFile = new Scanner(file);
		String xCity[] = new String[52];
		short matrix[][] = new short[52][52];
		short indexX = 0;
		short indexY = 0;
		short nUP = 0;
		short n = 0;

		// ******************************************************
		// initialize matrix and 0th spot
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j <= matrix[i].length - 1; j++) {
				matrix[i][j] = Short.MAX_VALUE;
			}
		}
		xCity[0] = "theBridge";
		nUP++;
		n++;

		// while there's still lines of text, clean them up and put in array
		while (rawFile.hasNext()) {
			line = rawFile.nextLine();
			if (!line.startsWith("%") && !line.isEmpty()) {
				if (line.startsWith("u")) {
					temp = line.replace(" ", "").replace("]", "")
							.replace(")", "").replace(".", "").split(",");
					temp[0] = temp[0].substring(4);
					for (int i = 0; i < temp.length; i++) {
						xCity[n] = temp[i];
						nUP++;
						n++;
					}
				} else {
					temp = line.replace(" ", "").replace("]", "")
							.replace(")", "").replace(".", "").split(",");
					temp[0] = temp[0].substring(9);
					if (matchInArray(temp[0], xCity) == false) {
						xCity[n] = temp[0];
						n++;
					}
					if (matchInArray(temp[1], xCity) == false) {
						xCity[n] = temp[1];
						n++;
					}
					for (int i = 0; i < xCity.length; i++) {
						if (temp[0].equalsIgnoreCase(xCity[i])) {
							indexX = (short) i;
						}
						if (temp[1].equalsIgnoreCase(xCity[i])) {
							indexY = (short) i;
						}
					}
					matrix[indexX][indexY] = Short.parseShort(temp[2]);
					matrix[indexY][indexX] = Short.parseShort(temp[2]);
				}
			}
		}

		// write header
		bWriter.seek(0);
		bWriter.writeShort(n);
		bWriter.writeShort(nUP);

		// log stuff
		log.printf("Number of countries in upper peninsula: " + nUP + "%n");
		log.printf("Number of total countries: " + n + "%n");

		// begin writing log matrix and build the binary file "matrix"
		for (int i = 0; i < xCity.length; i++) {
			log.printf("%3d", i);
		}
		log.println();

		for (int i = 0; i < matrix.length; i++) {
			log.print(i);
			for (int j = 0; j <= matrix[i].length - 1; j++) {
				if (matrix[i][j] != Short.MAX_VALUE) {
					bWriter.writeShort(matrix[i][j]);
					log.printf("%3d", matrix[i][j]);
				} else if (i == j) {
					bWriter.writeShort(0);
					log.print("000");
				} else {
					bWriter.writeShort(matrix[i][j]);
					log.print(" - ");
				}
			}
			log.println();
		}
		for (int i = 0; i < xCity.length; i++) {
			log.printf(i + ") " + xCity[i] + "%n");
			bWriter.writeUTF(xCity[i]);
		}
		// finish up
		rawFile.close();
		bWriter.close();
		log.close();
	}

	// ******************************************************
	/**
	 * ensure there's no duplicates
	 * 
	 * @param testString
	 * @param items
	 * @return
	 */
	private static boolean matchInArray(String testString, String[] items) {
		for (int i = 0; i < items.length; i++) {
			if (testString.equalsIgnoreCase(items[i])) {
				return true;
			}
		}
		return false;
	}
}

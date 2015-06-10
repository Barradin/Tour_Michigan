/****************************************
 * Adam Tracy                           *
 * Assignment 6 - Graphs                *
 * MapData                              *
 * Just in charge of reading from the   *
 * binary file and getting data         *
 ***************************************/
package michtour;

import java.io.*;

public class MapData {
	// declare some variables
	private String filePath;
	private RandomAccessFile bReader;
	private short n;
	private short nUP;
	private int pen;
	private String[] cities;

	// **************************************************************
	/**
	 * constructor that stores some data from the binary file
	 * 
	 * @throws IOException
	 */
	public MapData() throws IOException {
		filePath = "MichiganMapData.bin";
		bReader = new RandomAccessFile(new File(filePath), "r");
		bReader.seek(0);
		n = bReader.readShort();
		nUP = bReader.readShort();
		cities = new String[n];
		bReader.seek((long) (((Math.pow(n, 2)) * 2) + 4));
		for (int i = 0; i < cities.length; i++) {
			cities[i] = bReader.readUTF();
		}

	}

	// **************************************************************
	/**
	 * method that returns the cities location in array
	 * 
	 * @param cityName
	 * @return
	 */
	public short getCityNumber(String cityName) {
		for (int i = 0; i < cities.length; i++) {
			if (cityName.equalsIgnoreCase(cities[i])) {
				return (short) i;
			}
		}
		return -1;
	}

	/**
	 * upper or lower peninsula
	 * 
	 * @param cityName
	 * @return
	 */
	public String getPeninsula(String cityName) {
		for (int i = 0; i < cities.length; i++) {
			if (cityName.equalsIgnoreCase(cities[i])) {
				pen = i;
			}
		}
		if (pen <= nUP) {
			return "UP";
		} else {
			return "LP";
		}
	}

	/**
	 * get the cities name based on location in array
	 * 
	 * @param num
	 * @return
	 */
	public String getCityName(short num) {
		return cities[num];

	}

	/**
	 * find the distance edge case in the binary file
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 * @throws IOException
	 */
	public short getRoadDistance(short num1, short num2) throws IOException {
		bReader.seek(((2 * num1) + (52 * num2 * 2)) + 4);
		return bReader.readShort();

	}

	/**
	 * wrap things up
	 * 
	 * @throws IOException
	 */
	public void finishUp() throws IOException {
		bReader.close();
	}
}

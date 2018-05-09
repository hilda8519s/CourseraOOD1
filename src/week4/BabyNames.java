package week4;
import edu.duke.*;
import org.apache.commons.csv.*;
import java.util.*;
import java.io.*;
public class BabyNames {
	/*
	* This method returns the total number of births males and females in a file
	*/
	public void totalBirths(FileResource fr) {
		int totalBirths = 0;
		int totalGirls = 0;
		int totalBoys = 0;
		CSVParser parser = fr.getCSVParser(false);

		for(CSVRecord record : parser) {
			int numBorn = Integer.parseInt(record.get(2));
			String gender = record.get(1);
			totalBirths += numBorn;
			if(gender.equals("M")) {
				totalBoys += numBorn;
			} else {
				totalGirls += numBorn;
			}
		}

		System.out.println("Total: " + totalBirths);
		System.out.println("Boys: " + totalBoys);
		System.out.println("Girls: " + totalGirls);
	}

	/*
	* This method returns the rank of the name in the file for the given gender, 
	* where rank 1 is the name with the largest number of births. 
	* If the name is not in the file, then -1 is returned.
	*/
	public long getRank(int year, String name, String gender) {
		long rank = -1;
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser(false);

		for(CSVRecord record : parser) {
			String currName = record.get(0);
			String currGender = record.get(1);
			
			if(currGender.equals(gender) && currName.equals(name)) {
				rank = record.getRecordNumber();
			}
		}
		return rank;
	}

	/*
	* This method returns the name of the person in the file at this rank, 
	* for the given gender, where rank 1 is the name with the largest number of births. 
	* If the rank does not exist in the file, then “NO NAME” is returned.
	*/
	public String getName(int year, int rank, String gender) {
		String name = "";
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser(false);

		for(CSVRecord record : parser) {
			long currRank = record.getRecordNumber();
			String currGender = record.get(1);
			String currName = record.get(0);

			if(currRank == rank && currGender.equals(gender)) {
				name = currName;
			}
		}

		if(name != "") {
			return name;
		} 
		else {
			return "NO NAME";
		}
	}

	/*
	* This method determines what name would have been named if they were born 
	* in a different year, based on the same popularity.
	*/
	public void whatIsNameInYear(String name, int year, int newYear, String gender) {
		FileResource fr = new FileResource();
		FileResource newFr = new FileResource();
		CSVParser parserOld = fr.getCSVParser(false);
		CSVParser parserNew = newFr.getCSVParser(false);
		String newName = "";
		long popularity = 0;

		for(CSVRecord record : parserOld) {
			String currName = record.get(0);
			String currGender = record.get(1);

			if(currName.equals(name) && currGender.equals(gender)) {
				popularity = record.getRecordNumber();
			}
		}

		for(CSVRecord record : parserNew) {
			String currGender = record.get(1);
			long currPopularity = record.getRecordNumber();

			if(currGender.equals(gender) && popularity == currPopularity) {
				newName = record.get(0);
			}
		}

		System.out.println(name + " born in " + year + " would be " + newName + " if she was born in " + newYear);
	}

	/*
	* This method selects a range of files to process and returns an integer, 
	* the year with the highest rank for the name and gender. 
	* If the name and gender are not in any of the selected files, it should return -1.
	*/
	public int yearOfHighestRank(String name, String gender) {
		long highestRank = 0;
		int yearOfHighestRank = -1;
		String fileName = "";
		DirectoryResource dr = new DirectoryResource();
		
		// Iterate through all files
		for(File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			CSVParser parser = fr.getCSVParser(false);
			
			// Iterate through all records in file
			for(CSVRecord record : parser) {
				String currName = record.get(0);
				String currGender = record.get(1);

				if(currName.equals(name) && currGender.equals(gender)) {
					long currRank = record.getRecordNumber();
					
					if(highestRank == 0) {
						highestRank = currRank;
						fileName = f.getName();
					} 
					else {
						if(highestRank > currRank) {
							highestRank = currRank;
							fileName = f.getName();
						}
					}
				}
			}
		}

		// Remove all non-numeric characters from the filename
		fileName = fileName.replaceAll("[^\\d]", "");
		
		// Convert String fileName to Integer
		yearOfHighestRank = Integer.parseInt(fileName);

		return yearOfHighestRank;
	}
  
  /*
	* This method returns the average rank of a name in multiple files
	*/
	public double getAverageRank(String name, String gender) {
		// Initialize a DirectoryResource
		DirectoryResource dr = new DirectoryResource();
		// Define rankTotal, howMany
		double rankTotal = 0.0;
		int howMany = 0;
		// For every file the directory add name rank to agvRank
		for(File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			CSVParser parser = fr.getCSVParser(false);
			for(CSVRecord record : parser) {
				String currName = record.get(0);
				String currGender = record.get(1);
				if(currName.equals(name) && currGender.equals(gender)){
					long currRank = record.getRecordNumber();
					rankTotal += (double)currRank;
					howMany += 1;
				}
			}
		}
		// Define avgRank = rankTotal / howMany
		double avgRank = rankTotal / (double)howMany;
		return avgRank;
	}
  
  /*
	* This method returns the total births of the same gender that are ranked higher
  * than the parameter name
	*/
	public int getTotalBirthsRankedHigher(int year, String name, String gender) {
		int numBorn = 0;
		long rank = getRank(year, name, gender);
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser(false);
		for(CSVRecord record : parser) {
			int currBorn = Integer.parseInt(record.get(2));
			String currGender = record.get(1);
			long currRank = record.getRecordNumber();
			if(gender.equals(currGender) && rank > currRank) {
				numBorn += currBorn;
			}
		}
		return numBorn;
	}

  /*
	* For testing the methods
	*/
	public void testTotlaBirth() {
		//FileResource fr = new FileResource();
	  // totalBirths(fr);

		long rank = getRank(1971, "Frank", "M");
		System.out.println("Rank is: " + rank);

		// String name = getName(1982, 450, "M");
		 //System.out.println("Name: " + name);

		 //whatIsNameInYear("Susan", 1972, 2014, "F");

		//System.out.println(yearOfHighestRank("Mich", "M"));
		
		//System.out.println(getAverageRank("Robert", "M"));
		
		 //System.out.println(getTotalBirthsRankedHigher(1990, "Drew", "M"));
	}

	public static void main(String args[]) {
		BabyNames names = new BabyNames();
		names.testTotlaBirth();
	}
}


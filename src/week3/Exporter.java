package week3;
import edu.duke.*;
import org.apache.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
public class Exporter {
	 /*
		* This method returns a string of information about 
		* the country or returns “NOT FOUND” if there is no 
		* information about the country.
		*/
		public static String countryInfo(CSVParser parser, String country) {
			for(CSVRecord record : parser) {
				String myCountry = record.get("Country");
				if(myCountry.contains(country)) {
					String exports = record.get("Exports");
					String value = record.get("Value (dollars)");
					String info = myCountry + ": " + exports + ": " + value;
					return info;
				}
			}

			return "NOT FOUND";
		}

		/*
		* This method prints the names of all the countries that 
		* have both exportItem1 and exportItem2 as export items.
		*/
		public static void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
			for(CSVRecord record : parser) {
				String exports = record.get("Exports");
				String country = record.get("Country");
				if(exports.contains(exportItem1) && exports.contains(exportItem2)) {
					System.out.println("export two items"+country);
				}
			}
		}

		/*
		* This method returns the number of countries 
		* that export exportItem.
		*/
		public static int numberOfExporters(CSVParser parser, String exportItem) {
			int numOfCountries = 0;
			for(CSVRecord record : parser) {
				String exports = record.get("Exports");
				if(exports.contains(exportItem)) {
					numOfCountries++;
					System.out.println(exports);
				}
			}
			return numOfCountries;
		}

		/*
		* This method prints the names of countries and their
		* Value amount for all countries whose Value (dollars) 
		* string is longer than the amount string.
		*/
		public static void bigExporters(CSVParser parser, String amount) {
			for(CSVRecord record : parser) {
				String value = record.get("Value (dollars)");
				String country = record.get("Country");

				if(value.length() > amount.length()) {
					System.out.println("the big export"+country + ": " + value);
				}
			}
		}

		public static void tester() {
			FileResource fr = new FileResource();
			CSVParser parser = fr.getCSVParser();
			
			// String info = countryInfo(parser, "Nauru");
			//System.out.println(info);
			
			listExportersTwoProducts(parser, "cotton", "flowers");

			int numOfCountries = numberOfExporters(parser, "cocoa");
			System.out.println("the number of cocoa"+numOfCountries);

			bigExporters(parser, "$999,999,999,999");

			//System.out.println("Country".equals("Country"));
		}
		
		public static void main(String args[]) {
			tester();
		}
}

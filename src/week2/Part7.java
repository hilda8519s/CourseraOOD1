package week2;
import edu.duke.*;
import java.util.*;
import java.io.*;
public class Part7 {
	static FileResource fr = new FileResource();
	static String dna = fr.asString().toUpperCase();
	static StorageResource sr = getAllGenes(dna);
	   public static int findStopCodon(String dna, int startIndex, String stopCodon) {
			int currIndex = dna.indexOf(stopCodon, startIndex + 3);
			while(currIndex != -1) {
				int diff = currIndex - startIndex;
				if(diff % 3 == 0) {
					return currIndex;
				} else {
					currIndex = dna.indexOf(stopCodon, currIndex + 1);
				}
			}

			return -1;
		}
		
		public static String findGene(String dna, int where) {
			int startIndex = dna.indexOf("ATG", where);
			if(startIndex == -1 || where == -1) {
				return "";
			}

			int taaIndex = findStopCodon(dna, startIndex, "TAA");
			int tagIndex = findStopCodon(dna, startIndex, "TAG");
			int tgaIndex = findStopCodon(dna, startIndex, "TGA");

			int minIndex = 0;
			
			if(taaIndex == -1 || (tgaIndex != -1 && tgaIndex < taaIndex)) {
				minIndex = tgaIndex;
			} else {
				minIndex = taaIndex;
			}

			if(minIndex == -1 || (tagIndex != -1 && tagIndex < minIndex)) {
				minIndex = tagIndex;
			}

			if(minIndex == -1) {
				return "";
			}
			
			return dna.substring(startIndex, minIndex + 3);
		}

		public static void howManyGenes() {
			int startIndex = 0;
			int countgenes = 0;
		
			FileResource fr = new FileResource();
			String dna = fr.asString().toUpperCase();

			while (true) {
				String gene = findGene(dna, startIndex);
				
				if (gene == "") {
					break;
				}

				startIndex = dna.indexOf(gene, startIndex) + gene.length();

				countgenes++;
			}

			System.out.println("How many genes are: " + countgenes);
		}
		
		public static StorageResource getAllGenes(String dna) {
			StorageResource sr = new StorageResource();
			int startIndex = 0;
			while (true) { 
				String gene = findGene(dna, startIndex);
				
				if (gene == "") {
					break;
				}
				
				sr.add(gene);

				startIndex = dna.indexOf(gene, startIndex) + gene.length();

			}
			return sr;
		}
		
		/*public static double cgRatio(String dna) {
			double charRatio = 0.0;
			double strLen = dna.length();

			for(int i = 0; i < strLen; i++) {
				if(dna.charAt(i) == 'C' || dna.charAt(i) == 'G') {
					charRatio++;
				}
			}

			double ratio = charRatio / strLen;
			return ratio;
		}*/
		 public static double cgRatio(String s){
		        s= s.toUpperCase();
		        double numberofC = s.length() - s.replace("C", "").length();
		        double numberofG = s.length() - s.replace("G", "").length();
		        double cgRatio = (numberofC + numberofG) / s.length();
		        return cgRatio;
		    }
       public static int greatthan35(double cgRatio) {
    	   int countcgRatio = 0;
    	   for(String s : sr.data()) {
    	   if(cgRatio > 0.35){
               //System.out.println(gene);
              countcgRatio++;
           }
		   
       }
    	   return countcgRatio;
       }
		public static int countCTG(String dna) {
			int startIndex = 0;
			int count = 0;
			int index = dna.indexOf("CTG", startIndex);
			
			while(true) {
				if(index == -1 || count > dna.length()) {
					break;
				}
				
				count++;
				index = dna.indexOf("CTG", index+3);
			}
			return count;
		}
		public static int lengththan60(String dna) {
			int count1 = 0;
			for(String s : sr.data()) {
				if(s.length() > 60) {
					count1++;
			}
		
			}
			return count1;
		}
			
		
		public static int longestLength(String dna) {
			String Longest = "";
			for(String s : sr.data()) {
				if(s.length() > Longest.length()) {
					Longest = s;
				}
			}
			return Longest.length();
			
		}

		public static void processGenes() {
			
			
            howManyGenes();
            double ratio = cgRatio(dna);
            System.out.println(ratio);
            int count = countCTG(dna);
            System.out.println("the number of CTG is" + count);
            int countctgRatio = greatthan35(ratio);
            System.out.println("the number of  ratio greater than 0.35 is"+ countctgRatio);
            int length = longestLength(dna);
            System.out.println("the longest length is" + length);
            int count1 = lengththan60(dna);
            System.out.println("the number of length greater than 60 is" + count1);
            
			
		}
		

		public static void main(String args[]) {
			processGenes();
		}
}

package week2;
import edu.duke.*;
public class Part5 {
	 public static void testStorageGenes() {
	        // Read file (which is a long string of DNA)
	        FileResource file = new FileResource("//Users/bocanhu/eclipse-workspace/CourseraOOD1/src/week2/GRch38dnapart.fa");
	        // Find and store all genes in file
	        String sequence = file.asString();
	        StorageResource genes = storeAll(sequence);
	        // Print the number of genes found
	        System.out.println("Number of genes found: " + genes.size());
	       // printGenes(genes);
	        // Print the number of times CTG appears in the DNA string
	        ctgFreq(sequence);
	    }
	    
	    public static void ctgFreq(String dna) {
	        dna = dna.toLowerCase();
	        int freq = 0;
	        int loc = 0;
	        while (loc != -1) {
	            loc = dna.indexOf("ctg", loc);
	            if (loc != -1) {
	                freq++;
	                loc += 1;
	            }
	        }
	        
	        System.out.println("CTG appears in this DNA strand " + freq + " times");
	    }
	   
	    public static int findStopIndex(String dna, int index) {
	        // Find first occurrence of TAG past index
	        int tag = dna.indexOf("tag", index);
	        if (tag == -1 || (tag - index) % 3 != 0) {
	            // If TAG isn't found or gene isn't a multiple of three, set tag to max
	            tag = Integer.MAX_VALUE;
	        }
	        //System.out.println("found TAG: " + tag);
	        
	        // Find first occurrence of TGA past index
	        int tga = dna.indexOf("tga", index);
	        if (tga == -1 || (tga - index) % 3 != 0) {
	            // If TGA isn't found or gene isn't a multiple of three, set tag to max
	            tga = Integer.MAX_VALUE;
	        }
	        //System.out.println("found TGA: " + tga);
	        
	        // Find first occurrence of TAA past index
	        int taa = dna.indexOf("taa", index);
	        if (taa == -1 || (taa - index) % 3 != 0) {
	            // If TAA isn't found or gene isn't a multiple of three, set tag to max
	            taa = Integer.MAX_VALUE;
	        }
	        //System.out.println("found TAA: " + taa);
	        
	        int smallest = Math.min(tag, Math.min(tga, taa));
	        if (smallest < Integer.MAX_VALUE) {
	            // Return smallest index
	            return smallest;
	        } else {
	            // No codon is found
	            return -1;
	        }
	    }
	    
	    public static double cgRatio(String dna) {
	        dna = dna.toLowerCase();
	        
	        // Find all Cs and Gs
	        double cg = 0.0;
	        for (int i = 0; i < dna.length(); i++) {
	            if (dna.charAt(i) == 'c' || dna.charAt(i) == 'g') {
	                cg++;
	            }
	        }
	        
	        // Return number of Cs and Gs as a fraction of the entire strand of DNA
	        double ratio = cg/dna.length();
	        return ratio;
	    }
	    
	    public static StorageResource storeAll(String seq) {
	        // Create StorageResource object
	        StorageResource store = new StorageResource();
	        // Region of seq to begin looking at
	        int loc = 0;
	        String dna = seq.toLowerCase();
	        
	        // While traversing dna
	        while (true) {
	            // Find start codon in specified region of seq
	            int start = dna.indexOf("atg", loc);
	            
	            if (start == -1) {
	                // If no start codon is found, quit
	                //System.out.println("no more start codons");
	                break;
	            }
	            
	            // Find a stop codon after the start codon
	            int stop = findStopIndex(dna, start + 3);
	            //System.out.println("start: " + start);
	            //System.out.println("STOP: " + stop);
	            // If stop codon is found, save gene as a variable in correct case
	            // and add gene to StorageResource object
	            if (stop != -1) {
	                if (seq == dna) {
	                    String gene = dna.substring(start, stop + 3);
	                    //System.out.println(gene);
	                    store.add(gene);
	                } else {
	                    String gene = dna.substring(start, stop + 3).toUpperCase();
	                    //System.out.println(gene);
	                    store.add(gene);
	                }
	                // Update region to dna to look for codon in, setting it to the index
	                // right after the stop codon to avoid next gene overlapping with current gene
	                loc = stop + 3;
	            } else {
	                // Update region of dna to look for codon in
	                loc = start + 1;
	            }
	        }
	        
	        return store;
	    }
	    
	    public static void printGenes(StorageResource sr) {
	    	  FileResource file = new FileResource("//Users/bocanhu/eclipse-workspace/CourseraOOD1/src/week2/GRch38dnapart.fa");
		        // Find and store all genes in file
		        String sequence = file.asString();
		        StorageResource genes = storeAll(sequence);
	        // Print all strings that are longer than 60 characters
	        int count = 0;
	        int longest = 0;
	        //System.out.println("Printing strings that are longer than 60 characters...");
	        for (String str : sr.data()) {
	            if (str.length() > 60) {
	                count++;
	                //System.out.println(str);
	                // Update variable if str is longest thus far
	                if (longest < str.length()) {
	                    longest = str.length();
	                }
	            }
	        }
	        System.out.println("Printed " + count + " strings that are longer than 60 characters");
	        System.out.println("Longest string's length: " + longest);
	        
	        // Print strings whose C-G ratio is higher than 0.35
	        int total = 0;
	        //System.out.println("Printing strings whose C-G ratio is higher than 0.35...");
	        for (String str : sr.data()) {
	            double ratio = cgRatio(str);
	            if (ratio > 0.35) {
	                total++;
	                //System.out.println(str);
	            }
	        }
	        System.out.println("Printed " + total + " strings whose C-G ratio is higher than 0.35");
	    }
	    
	    public static void main(String args[]) {
	    	  FileResource file = new FileResource("//Users/bocanhu/eclipse-workspace/CourseraOOD1/src/week2/GRch38dnapart.fa");
		        // Find and store all genes in file
		        String sequence = file.asString();
		        StorageResource genes = storeAll(sequence);
	    	printGenes(genes);
	    	testStorageGenes();
	    }
}
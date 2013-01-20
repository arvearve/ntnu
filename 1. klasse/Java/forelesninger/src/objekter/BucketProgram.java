// angi pakken (mappen) koden ligger i
package objekter;

// importer kode fra diverse Java-bibliotek
import java.util.ArrayList;
import java.util.List;

import acm.program.ConsoleProgram;


// Alle kode er definert i s�kalte klasser og m� ligger i filer med samme navn (og .java-endelse)
// MyPy er en programklasse som bruker konsoll-IO, derfor utvider/bygger vi p� ConsoleProgram
public class BucketProgram extends ConsoleProgram {

	// Java bruker kr�llparenteser {} for gruppering av setninger, istedenfor : og innrykk
	// alle verdier, returverdier, parametre, lokale variabler osv. m� deklareres med en type
	// int er heltall, List<Integer> er en heltallsliste
	int findSmallest(int volume) {
	    int smallest = -1;
	    // size og get er funksjoner (metoder) som buckets har, fordi den er en List
	    for (int i = 0; i < buckets.size(); i++) {
	    	// Java bruker && og ||�istedenfor and og or
	    	if (buckets.get(i).suitableForWater && buckets.get(i).size > volume && (smallest < 0 || buckets.get(i).size < buckets.get(smallest).size)) {
	    		smallest = i;
	    	}
	    }
	    return smallest;
	}
	
	// alle verdier, returverdier, parametre, lokale variabler osv. m� deklareres med en type
	// int er heltall, List<Integer> er en heltallsliste
	// new ArrayList<Integer> lager en ny og tom heltallsliste
	List<Bucket> buckets = new ArrayList<Bucket>();
	
	// alle verdier, returverdier, parametre, lokale variabler osv. m� deklareres med en type
	// void betyr ingen returverdi
	void readBuckets() {
	    while (true) {
	    	// alle verdier, returverdier, parametre, lokale variabler osv. m� deklareres med en type
	    	// String er tekst/sekvens av tegn
	    	// readLine er definert i ConsoleProgram, som vi bygger p�
	    	String bucket = readLine("Input next bucket, or a newline to exit: ");
	        if (bucket.length() <= 0) {
	        	return;
	        }
	        Integer bucketSize = Integer.valueOf(bucket);
	        boolean suitableForWater = true;
	        if (bucketSize < 0) {
	        	bucketSize = -bucketSize;
	        	suitableForWater = false;
	        }
	        Bucket bucketObject = new Bucket();
	        bucketObject.size = bucketSize;
	        bucketObject.suitableForWater = suitableForWater;
			buckets.add(bucketObject);
	    }
	}

	// run er implisitt startmetode for programklasser, m� ha public-n�kkelordet
	public void run() {
	    readBuckets();
	    // alle verdier, returverdier, parametre, lokale variabler osv. m� deklareres med en type
	    int volume = readInt("Input volume, for which to find a bucket: ");
	    int smallest = findSmallest(volume);
	    // println er definert i ConsoleProgram, som vi bygger p�
	    // n�r det til venstre for + er en String, s� betyr + String-sammensetning, hvor heltall (og andre verdier) automatisk gj�res om til String
	    println("The smallest bucket larger than " + volume + " is #" + smallest + ", with volume " + buckets.get(smallest).size);
	}
}

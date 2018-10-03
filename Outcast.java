import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;

public class Outcast {
	
	private WordNet WN;
	ArrayList<Integer> dist;

	// constructor takes a WordNet object
   public Outcast(WordNet wordnet)         
   {
   		this.WN = wordnet;
   		dist = new ArrayList<Integer>();
   }

   // given an array of WordNet nouns, return an outcast
   public String outcast(String[] nouns)   
   {
		int largest = 0;
   		int largestIndex = 0; 
   		int distance = 0;
   		
   		//store distances of all nouns
   		for (int i = 0 ; i < nouns.length; i++) {
   			for (int j = 0 ; j < nouns.length; j++) {

   					if (j == i) continue;
   					 distance += WN.distance(nouns[i], nouns[j]);
   			}
   			this.dist.add(distance);
   		}
		
		//find max value in arrayList of stored distances
   		for (int i = 0; i < dist.size(); i++) {
   			
   			int k = dist.get(i);
   			if (k > largest) {
   				largest = k;
   				largestIndex = i;
   				}
   			}
   			//return corresponding index
   			return nouns[largestIndex];
   			}

    // see test client below
   public static void main(String[] args) 
   {
   	    WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
        In in = new In(args[t]);
        String[] nouns = in.readAllStrings();
        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
   }
}

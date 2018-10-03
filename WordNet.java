import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdIn;
import java.util.ArrayList;

    public class WordNet { 

        In in1;
        In in2; 
        //adjacency list implementation using an array of linked lists  
        private Digraph G;
        private ShortestCommonAncestor sca;
        private ArrayList< ArrayList<String> > syn; //arraylist used to hold nouns
        private ArrayList<String> syn2;

           // constructor takes the name of the two input files
           public WordNet(String synsets, String hypernyms) 
           {
              this.in1 = new In(synsets);
              this.in2 = new In(hypernyms);
              syn = new ArrayList< ArrayList<String> >();
              int i = 0;

              //read the contents of synsets and parse information
              String read;
              while ( (read = in1.readLine()) != null) {
                String[] noun = read.split(",");
                String[] noun2 = noun[1].split(" ");


                syn2 = new ArrayList<String>();

                for (int j = 0; j < noun2.length; j++) { 
                  this.syn2.add(noun2[j]); 
                  }

                  syn.add(syn2);
                  i++;
                }
              
              //get the number of vertices and send to Digraph constructor
              i++;

              this.G = new Digraph(i);
              
              //create Digraph 
              while ( (read = in2.readLine()) != null) {

                String[] hyp = read.split(",");
                int v = Integer.parseInt(hyp[0]);

                for (i = 1; i < hyp.length; i++) {

                  int w = Integer.parseInt(hyp[i]);
                  this.G.addEdge(v,w);
                }
              }
              //initialize SCA constructor
              sca = new ShortestCommonAncestor(this.G);
          }

           // all WordNet nouns
           public Iterable<String> nouns() 
           {
               Bag<String> a = new Bag<String>();
               
               for (int i = 0; i < syn.size(); i++) {
               
                 //bag uses singly linked list so copy arraylist backwards
                for (int j = this.syn.get(i).size() - 1 ; j >= 0; j--) {
                  a.add( syn.get(i).get(j) );
                  }
              }
              return a;
           }

           // is the word a WordNet noun?
           public boolean isNoun(String word) 
           {
             for (int i = 0; i < syn.size(); i++) {
              if ( syn.get(i).indexOf(word) > -1 ) return true;
              }   
            return false;
           }
 
           // a synset (second field of synsets.txt) that is a shortest common ancestor
           // of noun1 and noun2 (defined below)
           public String sca(String noun1, String noun2) 
           {
            if ( !isNoun(noun1) ) throw new IllegalArgumentException(noun1 + " not in WordNet");
            if ( !isNoun(noun2) ) throw new IllegalArgumentException(noun2 + " not in WordNet");
            
            //find correct indices for the two strings
            Bag<Integer> a = new Bag<Integer>();
            Bag<Integer> b = new Bag<Integer>();

            for (int i = 0; i < syn.size(); i++) {
              if ( syn.get(i).indexOf(noun1) > -1 ) a.add(i); 
              if ( syn.get(i).indexOf(noun2) > -1 ) b.add(i);               
            }
              return syn.get( sca.ancestor(a,b) ).get(0); 
           }

           // distance between noun1 and noun2 (defined below)
           public int distance(String noun1, String noun2) 
           {
            if ( !isNoun(noun1) ) throw new IllegalArgumentException(noun1 + " not in WordNet");
            if ( !isNoun(noun2) ) throw new IllegalArgumentException(noun2 + " not in WordNet");
            
            //find correct indices for the two strings
            Bag<Integer> a = new Bag<Integer>();
            Bag<Integer> b = new Bag<Integer>();

            for (int i = 0; i < syn.size(); i++) {
              if ( syn.get(i).indexOf(noun1) > -1 ) a.add(i); 
              if ( syn.get(i).indexOf(noun2) > -1 ) b.add(i);               
            }

              return sca.length(a,b);
           }

           // do unit testing of this class
           public static void main(String[] args) 
           {
               WordNet WN = new WordNet(args[0], args[1]);

              //Iterable<String> noun = WN.nouns();
              //for (String i : noun) StdOut.println(i);

              while (true) {

              String v = StdIn.readLine();
              String w = StdIn.readLine();

               StdOut.println(WN.sca(v,w) + " " +  WN.distance(v,w));
             }     
           }
        }
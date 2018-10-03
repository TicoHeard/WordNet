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
        private ArrayList<String> syn; //arraylist used to hold nouns

           // constructor takes the name of the two input files
           public WordNet(String synsets, String hypernyms) 
           {
              this.in1 = new In(synsets);
              this.in2 = new In(hypernyms);
              syn = new ArrayList<String>();

              int i = 0;

              //read the contents of synsets and parse information
              String read;
              while ( (read = in1.readLine()) != null) {
                
                String[] noun = read.split(",");
                String[] noun2 = noun[1].split(" ");
                
                for (int j = 0; j < noun2.length; j++) { 
                  this.syn.add(noun2[j]); 
                  }
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
               
               //bag uses singly linked list so copy arraylist backwards
               for (int i = this.syn.size() - 1 ; i >= 0; i--) {
                a.add( syn.get(i) );
               }
               return a;
           }

           // is the word a WordNet noun?
           public boolean isNoun(String word) 
           {
              if ( syn.indexOf(word) > -1 ) return true;   
            else return false;

           }
 
           // a synset (second field of synsets.txt) that is a shortest common ancestor
           // of noun1 and noun2 (defined below)
           public String sca(String noun1, String noun2) 
           {
              //find correct indices for the two strings
              int v = syn.indexOf(noun1);
              int w = syn.indexOf(noun2);

              if (isNoun(noun1) == false) StdOut.println("noun1s fault");
              if (isNoun(noun2) == false) StdOut.println("noun2s fault");

              return syn.get( sca.ancestor(v,w) ); 
           }

           // distance between noun1 and noun2 (defined below)
           public int distance(String noun1, String noun2) 
           {
              //find correct indices for the two strings
              int v = syn.indexOf(noun1);
              int w = syn.indexOf(noun2);

              return sca.length(v,w);
           }

           // do unit testing of this class
           public static void main(String[] args) 
           {
               WordNet WN = new WordNet(args[0], args[1]);

               while (true) {

              String v = StdIn.readLine();
              String w = StdIn.readLine();

               StdOut.println(WN.sca(v,w) + " " +  WN.distance(v,w));
             }
               //Iterable<String> noun = WN.nouns();
               //for (String i : noun) StdOut.println(i);
           }
        }


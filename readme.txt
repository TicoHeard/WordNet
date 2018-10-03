/******************************************************************************
 *  Name:    Ticorrian Heard 
 *  NetID:    L20470372
 *  Precept:  Again I have no idea what this is.
 *
 *  Partner Name:     
 *  Partner NetID:    
 *  Partner Precept:  
 *
 *  Hours to complete assignment (optional): alot 
 *
 ******************************************************************************/

Programming Assignment 6: WordNet


/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in synsets.txt. Why did you make this choice?
 *****************************************************************************/

I chose to use an ArrayList of ArrayLists because I could dynamically add nouns 
and parse their seperate names and also store them in the same index.
This allows for quick access and use of the very useful methods in Arraylist
such as indexOf, get, and size.

/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in hypernyms.txt. Why did you make this choice?
 *****************************************************************************/

I used a Digraph data structure as this proved to be the most readable and
understandable data structure. It also was required as a constructor argument
in the ShortestCommonAncestor class.


/******************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithms as a function of the number of vertices V and the
 *  number of edges E in the digraph?
 *****************************************************************************/

Description:

I used DFS traversal to find cycles:

Detects cycles in a DAG using DFS, with three virtual colors to consider.
		* white = an unvisited vertex
		* grey = a visited vertex that still has children that are unvisited
		* black = a completely visited vertex
		All vertices are initially marked white. starting from any vertex 
		mark it grey (put as visited and push it in the stack) and process 
		its children recursively. When all the children are completely processed
		mark it the parent black (pop it out of the stack). A cycle is detected 
		when a vertex is visited for the 2nd time. This is found when the 
		vertex is not in the stack, yet has already been visited.


Order of growth of running time: O(V + E)


/******************************************************************************
 *  Describe concisely your algorithm to compute the shortest common
 *  ancestor in ShortestCommonAncestor. For each method, what is the order of
 *  growth of the worst-case running time as a function of the number of
 *  vertices V and the number of edges E in the digraph? For each method,
 *  what is the order of growth of the best-case running time?
 *
 *  If you use hashing, you should assume the uniform hashing assumption
 *  so that put() and get() take constant time.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't
 *  forget to count the time needed to initialize the marked[],
 *  edgeTo[], and distTo[] arrays.
 *****************************************************************************/

Description:

                                              running time
method                               best case            worst case
------------------------------------------------------------------------
length(int v, int w)				v^2 + E + V          v^2 + E + V

ancestor(int v, int w)              v^2 + E + V          v^2 + E + V

length(Iterable<Integer> v,         v^2 + E + V          v^2 + E + V
       Iterable<Integer> w)

ancestor(Iterable<Integer> v,       v^2 + E + V          v^2 + E + V          
         Iterable<Integer> w) 




/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/

The Program will terminate if a string is entered but not found in the wordnet.


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/

I recieved no help.

/******************************************************************************
 * 
  Describe any serious problems you encountered.  

 *****************************************************************************/

No problems besides debugging issues and headaches.

/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/


I had no partner

/**********************************************************************
 *  Have you completed the mid-semester survey? If you haven't yet,
 *  please complete the brief mid-course survey at https://goo.gl/gB3Gzw
 * 
 ***********************************************************************/

I have not.

/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/

This project had the most enjoyable finished product as I could find words
I didnt expect to be in the WordNet
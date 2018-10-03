import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import java.util.Stack;
import java.util.ArrayList;


public class ShortestCommonAncestor {

	private Digraph dig;
	private CommonAncestor CA;
	private DAGCheck isAcyclic;

	//stores the sca and its length and sends back to client
	public class CommonAncestor {
		int length;
		int ancestor;
		public CommonAncestor()
		{
			this.length = -1;
			this.ancestor = -1;
		}
	}
//-----------------------------------------------------------------
	//Helper class for doing BFS, seperated for readability 
	private class DeluxeBFS {

	private Digraph dig;	
	private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked1, marked2;  // marked[v] = is there an s->v path?
    private int[] edgeTo1, edgeTo2;      // edgeTo[v] = last edge on shortest s->v path
    private int[] distTo1, distTo2;      // distTo[v] = length of shortest s->v path

		public DeluxeBFS(Digraph dig)
		{
			this.dig = dig;

			marked1 = new boolean[this.dig.V()]; //initialize for 1st BFS
			distTo1 = new int[this.dig.V()];

        	marked2 = new boolean[this.dig.V()]; //initialize for 2nd BFS
			distTo2 = new int[this.dig.V()];

        	
        	//set all sites to unvisited
        	for (int v = 0; v < this.dig.V(); v++) { 
            	distTo1[v] = INFINITY;
        		distTo2[v] = INFINITY;
        	}
		}
		//Algorithm for finding SCA
	    /*first do a regular BFS on v and all of its data and store in all the 
	    array1's. next do BFS on w but every time you visit a site check if that
	    site has a path to v. if it does run haspathTo and get its length 
	    by adding its distance and the ancestors distance and return.*/
		public CommonAncestor FindSCA(int v, int w)
		{
			CommonAncestor CA = new CommonAncestor(); //returning this
			
			if (v == w) { //case if v == w 
				CA.length = 0;
				CA.ancestor = v;
				return CA;
			}

			//visit first site v then start traversing its adjacent nodes
			Queue<Integer> q = new Queue<Integer>();
        	marked1[v] = true;
        	distTo1[v] = 0;
        	q.enqueue(v);
        	while (!q.isEmpty()) {
            int vertex = q.dequeue();
            for (int i : this.dig.adj(vertex)) {
                if (!marked1[i]) {
                    distTo1[i] = distTo1[vertex] + 1;
                    marked1[i] = true;
                    q.enqueue(i); //add site to visit list
                	}
            	}
        	}

        	q = new Queue<Integer>();
        	marked2[w] = true;
        	distTo2[w] = 0;
        	q.enqueue(w);
        	while (!q.isEmpty()) {
            int vertex = q.dequeue();
            for (int i : this.dig.adj(vertex)) {
                if (!marked2[i]) {
                    distTo2[i] = distTo2[vertex] + 1;
                    marked2[i] = true;

                    //checks if current vertex has a path in v
                    if (marked1[i] == true) {

                    	CA.length = distTo2[i] + distTo1[i];
                    	CA.ancestor = i;
                    	return CA;
                    }

                    else q.enqueue(i);
                	}
            	}
        	}
        	throw new RuntimeException("DeluxeBFS: no path found");
		}

		public CommonAncestor FindSCA(Iterable<Integer> v, Iterable<Integer> w)
		{
			//arraylist to store all sca's found within the BFS
			ArrayList<CommonAncestor> array = new ArrayList<CommonAncestor>();

			//visit first site v then start traversing its adjacent nodes
			Queue<Integer> q = new Queue<Integer>();
			
			for (int s : v) {
        	marked1[s] = true;
        	distTo1[s] = 0;
        	q.enqueue(s);
        	}
        	
        	while (!q.isEmpty()) {
            int vertex = q.dequeue();
            for (int i : this.dig.adj(vertex)) {
                if (!marked1[i]) {

                    distTo1[i] = distTo1[vertex] + 1;
                    marked1[i] = true;
                    q.enqueue(i); //add site to visit list
                	}
            	}
        	}

        	q = new Queue<Integer>();
        	
        	for (int s : w) {
        		marked2[s] = true;
        		distTo2[s] = 0;
        		q.enqueue(s);
        	}
        	
        	int j = 0; //set to -1 for iteration format on line 161

        	while (!q.isEmpty()) {
            int vertex = q.dequeue();
            for (int i : this.dig.adj(vertex)) {
                if (!marked2[i]) {

                    distTo2[i] = distTo2[vertex] + 1;
                    marked2[i] = true;

                    //checks if current vertex has a path in v
                    if (marked1[i] == true) {

                    	CommonAncestor CA = new CommonAncestor();
                    	CA.length = distTo2[i] + distTo1[i];
                    	CA.ancestor = i;

                    	array.add(CA);
                    	j++;
                    }
                    q.enqueue(i);
                	}
            	}
        	}
        	//find smallest index in sca list
        	int smallest = array.get(0).length;
        	int smallestIndex = 0;
        	for (int i = 0; i < array.size(); i++) {

        		if (array.get(i).length <= smallest) {
        			smallest = array.get(i).length;
        			smallestIndex = i;
        		}
        	}
        	//return the shortest lengthed sca 
        	return array.get(smallestIndex);
		}
	}
//-----------------------------------------------------------------
	/*
		Detects cycles in a DAG using DFS, with three virtual colors to consider.
		* white = an unvisited vertex
		* grey = a visited vertex that still has children that are unvisited
		* black = a completely visited vertex
		All vertices are initially marked white. starting from any vertex 
		mark it grey (put as visited and push it in the stack) and process 
		its children recursively. When all the children are completely processed
		mark it the parent black (pop it out of the stack). A cycle is detected 
		when a vertex is visited for the 2nd time. This is found when the vertex 
		is not in the stack, yet has already been visited.   
	*/ 
public class DAGCheck {

	private Digraph G;

	public DAGCheck(Digraph G)
	{
		this.G = G;	
	}

	public boolean isAcyclic()
	{
		int numOfVertices = this.G.V();
		int[] visited = new int[numOfVertices];
		Stack<Integer> stack = new Stack<Integer>();
		int i = 0;
		
		if (TopSort(visited,stack,i) == false) return false;
		else return true;
 	}

 	public boolean TopSort(int[] visited, Stack<Integer> stack, int i) {
		

		if (stack.search(i) >= 0) return false;
		if (visited[i] == 1 ) return true;  //Cycle detected

		visited[i] = 1; // mark as grey
		stack.push(i);

			for (int k : this.G.adj(i)) //process all greys children
					if (TopSort(visited,stack,k) == true) return false;
	 
			stack.pop(); //mark as black
		
		return true;
 	}
}
//-----------------------------------------------------------------	
		   // constructor takes a rooted DAG as argument
		   public ShortestCommonAncestor(Digraph G)
		   {
			 this.dig = G;
			 isAcyclic = new DAGCheck(G);
			 if (isAcyclic.isAcyclic() == true) StdOut.println("No Cycle Detected");
			 if (isAcyclic.isAcyclic() == false){ 
			 	StdOut.println("Cycle Detected!");
			 	throw new IllegalArgumentException("This Graph contains Cycles!!! >:[ DAG must be Acyclic!!!");
			 }
		   }

		   // length of shortest ancestral path between v and w
		   public int length(int v, int w)
		   {
			   DeluxeBFS BFS = new DeluxeBFS(dig);
			   this.CA = BFS.FindSCA(v,w);
			   return CA.length;
		   }

		   // a shortest common ancestor of vertices v and w
		   public int ancestor(int v, int w)
		   {
			   DeluxeBFS BFS = new DeluxeBFS(dig);
			   this.CA = BFS.FindSCA(v,w);
			   return CA.ancestor;
		   }
		   // length of shortest ancestral path of vertex subsets A and B
		   public int length(Iterable<Integer> subsetA, Iterable<Integer> subsetB)
		   {
			   DeluxeBFS BFS = new DeluxeBFS(dig);
			   this.CA = BFS.FindSCA(subsetA,subsetB);
			   return CA.length;
		   }

		   // a shortest common ancestor of vertex subsets A and B
		   public int ancestor(Iterable<Integer> subsetA, Iterable<Integer> subsetB)
		   {
			   DeluxeBFS BFS = new DeluxeBFS(dig);
			   this.CA = BFS.FindSCA(subsetA,subsetB);
			   return CA.ancestor;
		   }

		   // do unit testing of this class
		   public static void main(String[] args)
		   {
			   In in = new In(args[0]);
			    Digraph G = new Digraph(in);
			    ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
			    
			    while (!StdIn.isEmpty()) {
			        int v = StdIn.readInt();
			        int w = StdIn.readInt();
			        int length   = sca.length(v, w);
			        int ancestor = sca.ancestor(v, w);
			        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
			    }
		   }
}

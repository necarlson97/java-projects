import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class GraphColorizer {
	
	GraphArray graph = new GraphArray();
	
	public static void main(String[] args){  
		// Here we run our algorythm on the input files
		if (args.length == 0) {
			GraphColorizer smallGraph = new GraphColorizer("smallgraph");
			GraphColorizer largeGraph1 = new GraphColorizer("largegraph1");
			GraphColorizer largeGraph2 = new GraphColorizer("largegraph2");
		}
		// Or we can run only specific files, the one(s) which the command/terminal passed in
		else {
			for(String s : args) {
				GraphColorizer colorizer = new GraphColorizer(s);
			}
		}
	}
	
	public GraphColorizer(String graphName){
		long start = System.currentTimeMillis();
		// Our function for parsing in the given files to a "Graph Array"
		parseIn(graphName);
		
		// And 'Breadth Colorize' is our algorythm for attempting to colorize the graph
		// it returns either a successful list of all the nodes with their colors,
		// or a failed list of an odd cycle as proof of non-colorability
		String solution = breadthColorize();
		
		// Finally, we write our solution to an output file
		writeToFile(graphName+"Solution", solution);
		System.out.println(graphName+": "+(System.currentTimeMillis() - start)+"ms");
	}
	
	public void parseIn(String fileName){
		String line = "";
		try {
			FileReader fileReader = new FileReader(fileName); // Open the file to read 
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			//Send the line off to another method for adding to the graph
			while ((line = bufferedReader.readLine()) != null) 
				if(!line.equals("")) addToGraph(line); 
			
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		
	}

	//Here we add the line to the graph
	private void addToGraph(String line) {
		String[] l = line.split(" ");
		int inputName = Integer.valueOf(l[0]); // Get the node number as an int
		GraphNode inputNode = graph.get(inputName); // Get that node, creating it if it does not exist 
		
		// And for each number after that first, set/add it as an edge to that first node
		for(int i=1; i<l.length; i++){
			int edgeNodeName = Integer.valueOf(l[i]);
			GraphNode edgeNode = graph.get(edgeNodeName);
			inputNode.edges.add(edgeNode);
			// And since this is undirected, make the edge bi-directional
			edgeNode.edges.add(inputNode); 
		}
	}
	
	// A breadth-first algorythm for traversing and colorizing our parsed graph
	private String breadthColorize(){
		
		String solution; 
		// And create a breath exploration queue of 'Graph Nodes'
		Queue<GraphNode> queue = new LinkedList<GraphNode>(); 
		
		// For every node in the graph (all input files started with node 1)
		for(int i=1; i<graph.getCurrMax(); i++) {
			
			// If the node is untouched (either it is the first node, 
			// or a node from a new, disjoint set of nodes)   
			if(graph.at(i) != null && graph.at(i).color == 'n') {
				
				GraphNode currNode = graph.get(i);
				currNode.color = 'B'; // Our initial color is 'B'
				currNode.level = 0; // Set our root node's level as '0'
			
				while(currNode != null){
					
					GraphNode badNode = currNode.colorMatch(); // Check for conflicting edges
					if(badNode != null) { // If a bad edge was found, run and return the cycle finder
						Queue<GraphNode> badOutput = aStarCycleFinder(currNode, badNode);
						solution = badOutputQueueToString(badOutput);
						return "no\n"+solution;
					}
					
					// Colorize edges, and add the to the breadth queue
					queue.addAll(currNode.colorVisitEdges());
					
					// On to the next node in this connected graph
					currNode = queue.poll();
				}
			}
		}
		
		// If we have made it here, we have searched every node and found no bad cycles
		// We may return our solution for writing to an output
		solution = graph.toString();
		return "yes\n"+solution;
		
	}

	// An algorythm for capturing all of the nodes in an odd cycle, only called
	// after the given knowledge that such a cycle exists.
	// To save time, the cycle is sought after in a greedy manner with our
	// algorythm searching for a way back to the 'level' at which our error occured.
	// I do not know what this is called, but to me it resembles the way at which game AI
	// uses 'a*' or 'a star' searching to navigate obsticles, so I am calling it that
	
	// Note: I believe an algorythm like this was discussed in a discussion, but as
	// this was written before that, I am leaving it as is for posterity sake
	
	// Second note: I went back to see if improvements could be made, and started 
	// by testing a regular list vs a priority queue. Turns out the adding overhead is not
	// worth the searching benifit, as my code ran far faster. However, I am deciding
	// to leave the gutted skeleton of the priority queue in, as proof that such testing occured
	private Queue<GraphNode> aStarCycleFinder(GraphNode start, GraphNode finish){

		// Create a queue based on the level of the nodes
		CycleFinderQueue queue = new CycleFinderQueue();
		GraphNode currNode = start; // Start at one bad node, searching for the other
		queue.add(start);
		// This is the 'Visited' marker, and as we have no need for color's anymore, 
		// we will use that variable space
		start.color = 'V'; 
		while( queue.peek() != finish ){ // While the finish has not been found
			currNode = queue.poll(); 
			// Continue with our a * depth first search, much in the same fashion as the colorizer
			queue.addAll(currNode.aStarVisitEdges(finish));
		}
		
		// Path is a linked list in each node that is unused unless it is one of our two conflicting nodes
		// The 'a star visit edges' handles adding to that path, and here we simply return the path
		
		// After adding the finishing touch, the final node, of course
		finish.path.add(finish);

		return finish.path;
	}
	
	// Note: added after initial to more closely follow the updating instructions on piazza
	private String badOutputQueueToString(Queue<GraphNode> badQueue){
		String str = "";
		for(GraphNode n : badQueue){
			str+=n.name+"\n";
		}
		return str;	
	}
	
	// Simply writing our solution string gathered earlier to a new output file
	private void writeToFile(String fileName, String solution) {
		try{
		    PrintWriter writer = new PrintWriter(fileName+".txt", "UTF-8"); // Open output file
		
		    writer.println(solution);
		    
		    writer.close();
		} 
		
		catch (IOException e) {
			System.out.println("Error writing to file '");
		}
		
	}

}

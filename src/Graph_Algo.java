
import java.util.*;

/** @author Yuval Moshe
 *
 * This class implemnts the graph_algorithms interface which allowes preforming diffrenet allgoritems on the a specifc
 * graph which is stated as the only varaible of the class
 * **/

public class Graph_Algo implements graph_algorithms {
graph _gr;
/** Constructor **/
public Graph_Algo(){
    _gr = new Graph_DS();
}

/** Copy Constructor **/
public Graph_Algo(graph g){
    _gr = g;
}

/** Intializing the class to work on the provided graph in the parameter **/
public void init(graph g) {
    _gr=g;

}

@Override
/** Compute a deep copy of this graph **/
public graph copy() {
    graph g = new Graph_DS(this._gr);
    return g;
}


/** This function checks if the current graph_algo is a connected graph, meaning if their is a path between each 2 nodes in
 * the graph.
 *
 * The base assumption behind this function is this: if a graph is a connected graph, from a specific node (each node), there
 * should be a path to each other node.
 *
 * So, the functions takes a random node, and adds to a HashMap all the nodes which are connected to him in some path,
 * by preforming the "solve" function from the BFS algorithm (Explained in ShortestPath function below).
 * Then, after adding all the connected nodes to the HashMap, if the number of nodes in the HashMap is equal
 * to the number of the entire nodes in the graph, hence all the nodes are connected to the random node we chose,
 * and therefore there is a path between each 2 nodes in the graph, and the graph is connected.
 *
 * @param
 * @return True - if the graph is a connected graph, False - if it's not.

 * **/
@Override
public boolean isConnected() {
    Collection<node_data> nodes = _gr.getV();
    if(nodes.isEmpty()){
        return true;
    }
    Queue<node_data> q = new LinkedList<node_data>();
    node_data node = nodes.iterator().next();
    q.add(node);
    HashMap<Integer, Boolean> connected = new HashMap<Integer, Boolean>();
    connected.put(node.getKey(), true);
    while(!q.isEmpty()&& connected.size()!=nodes.size()){
        node_data curr = q.poll();
        Collection<node_data> neighbors = curr.getNi();
        for(node_data next : neighbors){
            if(connected.get(next.getKey())==null){
                q.add(next);
                connected.put(next.getKey(),true);
            }
        }
    }
    if(connected.size()==nodes.size()){
        return true;
    }
    return false;
}
/** This function return the shortest path distance between 2 nodes by returning the length of the shortest path
 * which is constructed in the shortestPath function.
 * @param src
 * @param dest
 * @return The shortest path length, if there isn't any - return -1;
 *
 * **/
@Override
public int shortestPathDist(int src, int dest) {
    return shortestPath(src, dest).size()-1;
}


/** This function return the shortest possible path between 2 nodes in a graph.
 *
 * The function is based on the BFS (Breath First Search) algorithm, and is divide into 2 sub-functions: solve & reconstructPath.
 *
 * solve: There are 3 main components in this function:
 * Queue<node_data> q - to which we add each *new* node we come upon.
 * HashMap<Integer, Boolean> visited - which will help us determine if a current node is a new node or was it already
 * added to the queue.
 * HashMap<Integer, node_data> prev - which on it will map for each node the previous *first* node that we got from to this
 * current node (meaning the closest neighbor of this current node, to the the src node), basically the
 * hash map is: HashMap<current_node.key, previous_node>, that will help us build the path.
 *
 * reconstructPath: This function takes the prev HashMap from the solve function and the src and dest nodes,
 * and is trying to construct a path between dest to src (the reversed way) by adding to a list the prev of dest,
 * and then the prev of the prev of dest, and so on, until it reached the src node, if it does - its the shortest path
 * between src and dest, if it can't reach the src node - there is no path between src and dest.
 * The function ends by reversing the path (constructed as an ArrayList) to make if from src to dest and not
 * dest to src.
 *
 * @param src
 * @param dest
 *
 * @return a List of node_data represnting the shortest path between src and dest, if there isn't a path between
 * src and dest, return an empty list.
 *
 * **/
@Override
public List<node_data> shortestPath(int src, int dest) {
    if(_gr.getNode(src)!=null && _gr.getNode(dest)!=null){
        node_data src_node = _gr.getNode(src);
        node_data dest_node = _gr.getNode(dest);
        HashMap<Integer, node_data> prev = solve(src_node, dest_node);
        return reconstructPath(prev, src_node, dest_node);
    }
    return new ArrayList<>() {};
}




private HashMap<Integer, node_data> solve (node_data src, node_data dest){
    Queue<node_data> q = new LinkedList<node_data>();
    q.add(src);
    int n = _gr.nodeSize();
    HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>();
    HashMap<Integer, node_data> prev = new HashMap<Integer, node_data>();
    visited.put(src.getKey(), true);
    boolean flag = true;
    while(!q.isEmpty() && flag){
        node_data curr = q.poll();
        Collection<node_data> neighbors = curr.getNi();
        for(node_data next : neighbors){
            if(visited.get(next.getKey())==null){
                q.add(next);
                visited.put(next.getKey(),true);
                prev.put(next.getKey(),curr);
                if(next==src) {
                    flag = false;
                }
            }
        }
    }
    return prev;
}

private List<node_data> reconstructPath (HashMap<Integer, node_data> prev, node_data src, node_data dest){
    List<node_data> path_temp = new ArrayList<node_data>();
    List<node_data> path = new ArrayList<node_data>();
    path_temp.add(dest);
    for(int i = dest.getKey(); prev.get(i)!=null; i=prev.get(i).getKey()){
        path_temp.add(prev.get(i));
    }
    if(!path_temp.isEmpty() && path_temp.get(path_temp.size()-1).getKey()==src.getKey()) {
        for (int i = path_temp.size() - 1; i >= 0; i--) {
            path.add(path_temp.get(i));
        }
    }
    return path;


}
}

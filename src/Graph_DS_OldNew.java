import java.util.*;

/**
 * @author Yuval Moshe
 *
 * The following class implements the graph interface which is an unidirectional, unweighted graph.
 * In this Class I chose to use 2 different data structures, HashSet & HashMap, to represented the nodes and edges of the graph.
 * HashSet<node_data> Graph_HashSet - Used for saving only unique, non-repetitive nodes of this graph, with O(1) supported actions,
 * such as: add, contains & remove. The main reason for using this data structure is for returning a Collection of all the nodes in
 * the graph at O(1) time complexity.
 * HashMap<Integer, node_data> Graph_Map - Used for mapping each node to is uniqe key, in a way that will enable us to retrieve/query
 * about a specific node in the graph at O(1) time complexity.
 * int _numOfEdges = saves the number of edges in the graph
 * int _mc = counts the number of actions that were preformed on the graph
 * **/

public class Graph_DS_OldNew implements graph {
    HashSet<node_data> Graph_HashSet;
    HashMap<Integer, node_data> Graph_Map;
    int _numOfEdges;
    int _mc;

    /** Constructor **/
    public Graph_DS_OldNew(){
        Graph_HashSet = new HashSet<node_data>();
        Graph_Map = new HashMap<Integer, node_data>();
        _numOfEdges=0;
        _mc=0;
    }
    /** Copy Constructor
     * @parm graph g - the graph to deep copy from
     * **/
    public Graph_DS_OldNew(graph g) {
        this.Graph_HashSet = new HashSet<node_data>();
        this.Graph_Map = new HashMap<Integer, node_data>();
        for (node_data node : g.getV()){
            this.Graph_HashSet.add(node);
            this.Graph_Map.put(node.getKey(), node);
        }
        this._numOfEdges = g.edgeSize();
        this._mc = g.getMC();

    }

    @Override
    /**
     * returns the node from the collection of this graph nodes, by pulling it from the HashMap
     * @param key - the node_id
     * @return the node associated with the provided key, null if the node is not in the graph
     *  **/
    public node_data getNode(int key) {
        return (Graph_Map.get((key)));
    }

    @Override
    /**Checks if 2 nodes in the graph has an edge between them by searching in node1's neighbors to see if it contains node2
     * @param node1 - the node1_id
     * @param node2 - the node2_id
     * @return true if neighbors, false if not/one of them is null
     *  **/
    public boolean hasEdge(int node1, int node2) {
        if(Graph_Map.get(node1)!=null && Graph_Map.get(node2)!=null ) {
            node_data src = Graph_Map.get(node1);
            return (src.hasNi(node2));
        }
        return false;

    }

    @Override
    /** Adds a new node to the graph by adding him to the HashSet and mapping him in the HashMap by is unique key
     * @param node_date n - the node to add
     * @return
     * **/
    public void addNode(node_data n) {
        if(n!=null) {
            Graph_HashSet.add(n);
            Graph_Map.put(n.getKey(), n);
            _mc++;
        }
    }

    @Override
    /** Connecting 2 nodes in the graph by making an edge between them, if both nodes are not null & they are not already
     * neighbors, than add each of them to the other's neighbors collection.
     * @param node1 - the node1_id
     * @param node2 - the node2_id
     * @return
     * **/
    public void connect(int node1, int node2) {
        if(Graph_Map.get(node1)!=null && Graph_Map.get(node2)!=null && node1!=node2) {
            node_data src = Graph_Map.get(node1);
            node_data dest = Graph_Map.get(node2);
            if(!src.getNi().contains(dest)) {
                src.addNi(dest);
                dest.addNi(src);
                _numOfEdges++;
                _mc++;
            }
        }
    }

    @Override
    /** Returns a collection of all the nodes in the graph by returning the HaseSet in O(1) complexity
     * @reuturn HashSet<node_data>
     * **/
    public Collection<node_data> getV() {
        return Graph_HashSet;
    }

    @Override
    /**Returns a collection of all the nodes connectd to a a specific node
     * @param node_id - the id of the node that we want to get the neighbors of
     * @return the node's neighbors
     * **/
    public Collection<node_data> getV(int node_id) {
        if(Graph_Map.get(node_id)!=null){
            node_data node = Graph_Map.get(node_id);
            return node.getNi();

        }
        return null;
    }

    @Override
    /** remove a specific node from the graph (if exists) by first removing the edge between him and each one if his neighbors
     * (and by that also deleting him from their neighbors collection) and then removing him from the HashSet and HashMap
     * of the graph.
     * @param key - the key of the node to remove
     * @return the node that was removes, null if the node is not in the graph.
     * **/
    public node_data removeNode(int key) {
        if(Graph_Map.get(key)!=null){
            node_data node = Graph_Map.get(key);
            Collection<node_data> neighbors = node.getNi();
            List<node_data> ni_list = new ArrayList<node_data>();
            for(node_data ni : neighbors){
                ni_list.add(ni);
            }
            for(int i=0; i<ni_list.size(); i++){
                node_data curr = ni_list.get(i);
                removeEdge(curr.getKey(), key);
                curr.getNi().remove(node);
            }
            Graph_HashSet.remove(node);
            Graph_Map.remove(key);
            return node;
        }
        return null;
    }

    @Override
    /** remove the edge between 2 nodes by removing each one from the other's neighbors collection
     * **/
    public void removeEdge(int node1, int node2) {
        if(Graph_Map.get(node1)!=null && Graph_Map.get(node2)!=null) {
            node_data n1 = Graph_Map.get(node1);
            node_data n2 = Graph_Map.get(node2);
            if(n1.getNi().contains(n2)) {
                n1.getNi().remove(n2);
                n2.getNi().remove(n1);
                _numOfEdges--;
                _mc++;
            }
        }

    }

    @Override
    /** Return the number of nodes in the graph
     * @return number of nodes
     * **/

    public int nodeSize() {
        return Graph_HashSet.size();
    }

    @Override
    /** Return the number of edges in the graph
     * @return number of edges
     **/
    public int edgeSize() {
        return _numOfEdges;
    }

    @Override
    /** Return the number of changes that were made in the graph till now
     * @return number of changes
     **/
    public int getMC() {
        return _mc;
    }

    public String toString (){
        String s ="";
        for(node_data node : Graph_HashSet){
            s = s + node.getKey() + " Connected to: [";
            for(node_data ni : node.getNi()){
                s = s + ni.getKey() + ", ";
            }
            s = s+ "]\n";
        }
        return s;

    }
}

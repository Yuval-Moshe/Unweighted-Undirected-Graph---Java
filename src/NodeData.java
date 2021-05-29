import java.util.Collection;
import java.util.HashSet;

/** This Class implements node_data interface with the following class variables:
 * String _info - the data stored in the node
 * static int counter - a  static variable to determine the unique key for each NodeData
 * int _key - a unique key assigned to each NodeData
 * HaseSet<node_date> _neighbors - represents this current node's neighbors as an HaseSet, used to store only unique
 * none-repetitive neighbors if this NodeData
 * int _tag - a tag assigned to the this NodeData
 * **/

public class NodeData implements node_data {
    private String _info;
    private static int counter = 0;
    private int _key;
    private HashSet<node_data> _neighbors;
    private int _tag;

    /** Constructor **/
    public NodeData(){
        _info = "";
        _key = counter++;
        _neighbors = new HashSet<node_data>();
    }

    /** Constructor with provided data **/
    public NodeData(String info){
        _info = info;
        _key = counter++;
        _neighbors = new HashSet<node_data>();
    }

    /** Copy Constructor **/
    public NodeData(NodeData other){
        _info = other.getInfo();
        _key = counter++;
        _neighbors.addAll(other.getNi());

    }

    @Override
    /** @return int the unique key of this NodeData**/
    public int getKey() {
        return _key;
    }

    @Override
    /** @return all the neighbors of this node**/
    public Collection<node_data> getNi() {
        return _neighbors;
    }

    @Override
    /**
     * @param key
     * @return true if this node and node.getKey(keu) are neighbors, false if not **/
    public boolean hasNi(int key) {
        for (node_data node : _neighbors){
            if(node.getKey()==key){
                return true;
            }
        }
        return false;
    }

    @Override
    /**
     * @param node_data t
     * Adds t to the list of neighbors of this node **/
    public void addNi(node_data t) {
        if(_key!=t.getKey()) {
            _neighbors.add(t);
        }
    }

    @Override
    /**
     * @param node_data node
     * Remove node from the list of neighbors of this node **/
    public void removeNode(node_data node) {
        _neighbors.remove(node);
    }

    @Override
    /**@return the info of this current node **/
    public String getInfo() {
        return _info;
    }

    @Override
    /** set the info of this current node **/
    public void setInfo(String s) {
        _info = s;

    }

    @Override
    /**@return the tag of this current node **/
    public int getTag() {
        return _tag;
    }

    @Override
    /**set the tag of this current node **/
    public void setTag(int t) {
        _tag = t;

    }
}

package course.graph;

/**
 *
 * @author Wittman
 */
public class Connection {
    public Vertex start;
    public int weight;
    public Vertex end;

    public Connection(Vertex start, int weight, Vertex end) {
        this.start = start;
        this.weight = weight;
        this.end = end;
    }
    
    
}

package course.graph;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wittman
 */
public class Vertex {
    public int level;
    public int weight;
    public int number;
    
    public int transiting_time;
    
    public int start_time;
    public int processor;
    
    public List<Connection> connections;
    public List<Connection> back_connections;

    public Vertex(int number, int weight) {
        this.level = 0;
        this.number = number;
        this.weight = weight;
        
        processor = -1;
        
        connections = new ArrayList<>();
        back_connections = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.weight;
        hash = 67 * hash + this.number;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex other = (Vertex) obj;
        if (this.weight != other.weight) {
            return false;
        }
        if (this.number != other.number) {
            return false;
        }
        return true;
    }
    
    /**
     * Розташувати на процесорі
     * @param start_time
     * @param processor
     * @return час закінчення даної вершини на процесорі
     */
    public int placeOnProcessor(int start_time, int processor, int last_end){
        this.start_time = start_time;
        this.processor = processor;
        this.transiting_time = start_time - last_end;
        
        return start_time + weight;
    }
    
    public void reset(){
        processor = -1;
        transiting_time = 0;
        start_time = 0;
    }
    /**
     * Час можливого початку роботи на даному процесорі
     * @param processor_time
     * @param processor
     * @return 
     */
    public int readyTime(int processor_time, int processor){
        int ready_time = 0;
        for (int i = 0; i < back_connections.size(); i++) {
            int curr_ready_time = 0;
            Vertex supply = back_connections.get(i).start;
            if(supply.processor != -1){
                curr_ready_time += supply.start_time + supply.weight;
                curr_ready_time = (curr_ready_time > processor_time)? curr_ready_time: processor_time;
                if(supply.processor != processor){
                    curr_ready_time += back_connections.get(i).weight;
                }
            }else{
                return -1;
            }
            
            ready_time = (ready_time<curr_ready_time)?curr_ready_time:ready_time; 
        }
        
        return ready_time;
    }
}

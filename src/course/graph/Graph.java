package course.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wittman
 */
public class Graph {
    public List<Vertex> vertexes;
    public int [][] transitions;
    public int [] vertexWeight;  
    public int proc_number;
    public int CP_length;
    

    public Graph() {
        vertexes = new ArrayList<>();
    }

    
    public void initGraph(int [][] transitions, int [] vertexWeight){
        this.transitions = transitions;
        this.vertexWeight = vertexWeight;
        
        for (int i = 0; i < vertexWeight.length; i++) {
            Vertex new_vertex= new Vertex(i, vertexWeight[i]);
            vertexes.add(new_vertex);
        }
        
        for (int i = 0; i < transitions.length; i++) {
            for (int j = 0; j < transitions[i].length; j++) {
                if(transitions[i][j] != 0){
                    Connection new_conn = new Connection(vertexes.get(i), transitions[i][j], vertexes.get(j));
                    vertexes.get(i).connections.add(new_conn);
                    vertexes.get(j).back_connections.add(new_conn);
                }
            }
        }

    }
    
    public List<List>  findAllWays() throws Exception{
        Vertex end = vertexes.get(findEnd());

        List<Integer> newPath = new ArrayList<>();
        List<List> paths = new ArrayList<>();

        findNext(end, newPath, paths);
        return paths;
    }
    
    public int findWayLength(List<Integer> way){
        int leng_way = vertexWeight[way.get(0)];
        for (int i = 1; i < way.size(); i++) {
           leng_way += transitions [way.get(i)][way.get(i-1)] + vertexWeight[way.get(i)];
        }
        return leng_way;
    }
    
    public List<Integer> findLongestPath() throws Exception{
        List<List> ways = findAllWays();
        int way = 0;
        List<Integer> longestPath = null;
        
        for (List list : ways) {
            int leng_way = list.size();
            if(leng_way > way){
                way = leng_way;
                longestPath = list;
            }
            
        }
        return longestPath;
    }
    
    public List<Integer> findCriticalPath() throws Exception{
        List<List> ways = findAllWays();
        int way = 0;
        List<Integer> criticalPath = null;
        
        for (List list : ways) {
            int leng_way = findWayLength(list);
            if(leng_way > way){
                way = leng_way;
                criticalPath = list;
            }
            
        }
        return criticalPath;
    }
    
    public void findNext(Vertex curr, List<Integer> currPath, List<List> allPaths){
        currPath.add(curr.number);
        
        for(Connection conn: curr.back_connections){
            List<Integer> newPath = new ArrayList<>(currPath);
            findNext(conn.start, newPath, allPaths);
        }
        
        if(curr.back_connections.isEmpty()){
            allPaths.add(currPath);
        }
        
    }
    
    public int findEnd() throws Exception{
        int end = -1;
        for (int i = 0; i < transitions.length; i++) {
            int inputs = 0;
            for (int j = 0; j < transitions[i].length; j++) {
                if(transitions[i][j] != 0){
                    inputs++;
                }
            }
            if(inputs == 0){
                if(end != -1){
                    throw new Exception("Only one end must be");
                }
                end = i;
            }
        }
        
        return end;
        
    }
    
    public void locateLevels(){
        int nvertex = vertexes.size();
        for (Vertex vertex : vertexes) {
            
            if(vertex.back_connections.isEmpty()){
                vertex.level = 1;
                nvertex--;
            }else{
                vertex.level = -1;
            }
        }
        while(nvertex > 0){
            for (Vertex vertex : vertexes) {
                if(vertex.level == -1){
                    int high_level = 0;
                    boolean finded_level = true;
                    for(Connection conn : vertex.back_connections){
                        if(conn.start.level > 0){
                            high_level = (high_level < conn.start.level)?conn.start.level:high_level;
                        }else{
                            finded_level = false;
                        }
                    }
                    if(finded_level){
                        vertex.level = high_level+1;
                        nvertex--;
                    }
                }
            }
        }
        
    }
    
    public Map<Integer, Integer> lengthOfLevels(){
        Map<Integer, Integer> level_width = new HashMap();
        
        for (Vertex vertex : vertexes) {
            if(level_width.containsKey(vertex.level)){
                level_width.put(vertex.level, level_width.get(vertex.level)+1);
            }else{
                level_width.put(vertex.level, 1);
            }
        }
        return level_width;
    }
    
    public int findMaxP(){
        int max_P = 0;
        
        Map<Integer, Integer> level_width = lengthOfLevels();
        
        for (Map.Entry<Integer, Integer> entry : level_width.entrySet()) {
            Integer l = entry.getKey();
            Integer w = entry.getValue();
            
            if(w > max_P){
                max_P = w;
            }
        }
        
        return max_P;
    }
    
    private int getFreeProc(int [] processors_time ){
        int free_proc = 0;
        int free_proc_time = processors_time[0];
        for (int i = 0; i < processors_time.length; i++) {
            if(free_proc_time > processors_time[i]){
                free_proc = i;
                free_proc_time = processors_time[1];
            }
        }
        
        return free_proc;
    }
    public void simplePlanning() throws Exception{
        for (Vertex vertex : vertexes) {
            vertex.reset();
        }
        proc_number = findMaxP();
        CP_length = findLongestPath().size();
        int [] processors_time = new int [proc_number];
        
        List<Vertex> waiting_vertexes = new ArrayList(vertexes);
        
        while(!waiting_vertexes.isEmpty()){
            int free_proc = getFreeProc(processors_time);
            int i = 0;
            while (i < waiting_vertexes.size()){
                int ready_time = waiting_vertexes.get(i).readyTime(processors_time[free_proc], free_proc);
                if(ready_time >= 0){
                    processors_time[free_proc] = waiting_vertexes.get(i).placeOnProcessor(ready_time, free_proc, processors_time[free_proc]);
                    waiting_vertexes.remove(i);
                    
                    free_proc = getFreeProc(processors_time);
                }else{
                    i++;
                }
            }
        }
    }
    
    public void extendedPlanning(){
        for (Vertex vertex : vertexes) {
            vertex.reset();
        }
        
        proc_number = findMaxP();
        
        int [] processors_time = new int [proc_number];
        
        List<Vertex> waiting_vertexes = new ArrayList(vertexes);
        
        while(!waiting_vertexes.isEmpty()){
            if(waiting_vertexes.get(0).back_connections.isEmpty()){
                int free_proc = getFreeProc(processors_time);
                processors_time[free_proc] = waiting_vertexes.get(0).placeOnProcessor(0, free_proc, processors_time[free_proc]);
                waiting_vertexes.remove(0);
            }
            
            int i = 0;
            while (i < waiting_vertexes.size()){
                if(getFreeProc(processors_time) > 0){
                    int finish_time = 0;
                    int curr_proc = 0;
                    int ready_time = 0;
                    for (int proc = 0; proc < proc_number; proc++) {
                        int curr_ready_time = waiting_vertexes.get(i).readyTime(processors_time[proc], proc);
                        int curr_finish_time = curr_ready_time + waiting_vertexes.get(i).weight;
                        if(proc == 0){
                            finish_time = curr_finish_time;
                            proc = curr_proc;
                            ready_time = curr_ready_time;
                        }
                        if(curr_finish_time<finish_time){
                            curr_proc = proc;
                            finish_time = curr_finish_time;
                            ready_time = curr_ready_time;
                        }
                    }
                    processors_time[curr_proc] = waiting_vertexes.get(i).placeOnProcessor(ready_time, curr_proc, processors_time[curr_proc]);
                    waiting_vertexes.remove(i);

                }else{
                    i++;
                }

            }
        }
        proc_number = findMaxP();
        try {
            CP_length = findLongestPath().size();
        } catch (Exception ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public int getLongestProcessor(){
        int max_proc_length = 0;
        int proc_lengs[] = new int[proc_number];
        for (Vertex vertex : vertexes) {
            proc_lengs[vertex.processor]++;
            max_proc_length = (max_proc_length<proc_lengs[vertex.processor])? proc_lengs[vertex.processor]:max_proc_length;
        }
        return max_proc_length;
    }
    
    public double [][] generateVisData(){
        
        
        
        double [][] data = new double[getLongestProcessor()*2][proc_number];
        int busy_proc[] = new int[proc_number];
        for (Vertex vertex : vertexes) {
            data[busy_proc[vertex.processor]*2][vertex.processor] = vertex.transiting_time;
            data[busy_proc[vertex.processor]*2+1][vertex.processor] = vertex.weight;
            busy_proc[vertex.processor]++;
        }
        return data;
    }
}

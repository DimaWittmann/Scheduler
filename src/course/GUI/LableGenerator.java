package course.GUI;

import course.Main;
import course.graph.Vertex;
import java.util.List;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;

/**
 *
 * @author Wittman
 */
public class LableGenerator extends StandardCategoryItemLabelGenerator{
    
    List<Vertex> vertexes;
    Vertex disposal_proc[][];

    public LableGenerator(List<Vertex> vertexes) {
        this.vertexes = vertexes;
        disposal_proc = new Vertex[Main.g.getLongestProcessor()][Main.g.proc_number];
        
        int busy_proc[] = new int[Main.g.proc_number];
        for (Vertex vertex : vertexes) {
            disposal_proc[busy_proc[vertex.processor]][vertex.processor] = vertex;
            busy_proc[vertex.processor]++;
        }
    }

    @Override
    public String generateLabel(CategoryDataset cd, int row, int column) {
       
        if(row % 2 == 1){
            row /= 2;
            if(disposal_proc[row][column] != null){
                return String.valueOf(disposal_proc[row][column].number);
            }
            
        }
        return null;

    }
    
    

}

package course.GUI;

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

    public LableGenerator(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    @Override
    public String generateLabel(CategoryDataset cd, int row, int column) {
        
        for (Vertex vertex : vertexes) {
            if(vertex.level == column && vertex.processor == row){
                return String.valueOf(vertex.number);
            }
        }
        return "";
    }
    
    

}

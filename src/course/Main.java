package course;

import course.GUI.GnattDiagram;
import course.GUI.InputFrame;
import course.graph.Graph;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Wittman
 */
public final class Main {

    GnattDiagram demo;
    public static Graph g;
    InputFrame inputFrame;

    public Main() {
        
        int [][] trans = 
        {
       //0 1 2 3 4 5 6
        {0,1,1,0,0,0,0},//0
        {0,0,0,2,0,0,0},//1
        {0,0,0,0,2,7,0},//2
        {0,0,0,0,0,0,6},//3
        {0,0,0,0,0,0,11},//4
        {0,0,0,0,0,0,8},//5
        {0,0,0,0,0,0,0} //6
        };
        
        int [] weight = {1, 8, 6, 7, 2, 4, 3};
        
        inputFrame = new InputFrame(this, weight.length);
        inputFrame.pack();
        inputFrame.setVisible(true);

        
        inputFrame.matrixPanel.setData(trans);
        inputFrame.vectorPanel.setData(weight);
        
        demo = new GnattDiagram("Gnatt Diagram");
        
        try {
            updateDiagram(trans, weight, false);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
            
    public static void main(String [] args) throws Exception{
        Main main = new Main();
    }
    
    public void updateDiagram(int[][] transitions, int []weight, boolean optimazed) throws Exception{
        g = new Graph();
        g.initGraph(transitions, weight);
        int CP = g.findWayLength(g.findCriticalPath());
        g.locateLevels();
        int maxP = g.findMaxP();
        if(optimazed){
            g.extendedPlanning(); 
        }else{
            g.simplePlanning();
        }
        demo.createChart(g.generateVisData());
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
     
        demo.pack();
        demo.setVisible(true);
        demo.repaint();
    }
}

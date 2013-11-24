package course.GUI;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author Wittman
 */
public class GnattDiagram extends ApplicationFrame{

    public JFreeChart chart;
    
    public GnattDiagram(String string) {
        super(string);
        
    }
    
    public void createChart(double [][] data) {
        

        
        chart = ChartFactory.createStackedBarChart(
            "Planner",
            "Proc",                  // domain axis label
            "Time",                     // range axis label
            DatasetUtilities.createCategoryDataset("", "", data),                     // data
            PlotOrientation.HORIZONTAL,  // the plot orientation
            false,                        // include legend
            false,                        // tooltips
            false                        // urls
        );

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        final StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
        renderer.setItemLabelsVisible(true);
        for (int i = 0; i < data.length/2; i++) {
            renderer.setSeriesPaint(i*2, new Color(0, 0, 0, 0));
        }
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(840, 480));
        setContentPane(chartPanel);

    }
    


}

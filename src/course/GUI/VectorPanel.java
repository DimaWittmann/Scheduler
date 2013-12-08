package course.GUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Wittman
 */
public class VectorPanel extends JPanel{
     private int size;
    public VectorPanel(int size) {
        super(new FlowLayout(FlowLayout.CENTER));
        this.size = size;
        initPanel();
    }

    private void initPanel(){
        this.removeAll();
        for (int i = 0; i < size; i++) {
            this.add(new JTextField("0", 2));
        }
    }
    
    
    public void setSize(int size){
        this.size = size;
        initPanel();
    }
    
    public int [] getData(){
        int [] data = new int [size];
        for (int i = 0; i < size; i++) {
            data[i] = Integer.parseInt(((JTextField)this.getComponent(i)).getText());
        }
        return data;
    }
    
    public void setData(int [] data){
        for (int i = 0; i < size; i++) {
            ((JTextField)this.getComponent(i)).setText(String.valueOf(data[i]));
        }
    }
}

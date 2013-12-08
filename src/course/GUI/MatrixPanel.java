package course.GUI;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Wittman
 */
public class MatrixPanel extends JPanel{

    private int size;
    public MatrixPanel(int size) {
        super(new GridLayout(size, size, 5, 5));
        this.size = size;
        initPanel();
    }

    private void initPanel(){
        this.removeAll();
        this.setLayout(new GridLayout(size, size, 5, 5));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.add(new JTextField("", 2));
            }
        }
    }
    
    public void setSize(int size){
        this.size = size;
        initPanel();
    }
    
    public int [][] getData(){
        int [][] data = new int [size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                data[i][j] = Integer.parseInt(((JTextField)this.getComponent(i*size +j)).getText());
            }
        }
        return data;
    }
    
    public void setData(int data[][]){
        for (int i = 0; i < data.length*data.length; i++) {
            ((JTextField) this.getComponent(i)).setText(String.valueOf(data[i/data.length][i %data.length]));
        }
    }
}

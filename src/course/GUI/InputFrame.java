package course.GUI;

import course.Main;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Wittman
 */
public class InputFrame extends JFrame{

    private final MatrixPanel matrixPanel;
    private final VectorPanel vectorPanel;
    private final JTextField sizeField;
    private int size;
    private final Main main;
    
    public InputFrame(final Main main) throws HeadlessException {
        super("Input Panel");
        
        this.main = main;
        
        size = 5;
        matrixPanel = new MatrixPanel(size);
        vectorPanel = new VectorPanel(size);
     
        JPanel globalPanel = new JPanel();
        globalPanel.setLayout(new GridLayout(3, 1, 5, 5));
        globalPanel.add(vectorPanel);
        globalPanel.add(matrixPanel);
        
        
        
        sizeField = new JTextField(3);
        sizeField.setText(String.valueOf(this.size));
        
        AbstractAction resizeAction = new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int new_size;
                
                    try{
                        new_size = Integer.parseInt(sizeField.getText());
                    }catch(java.lang.NumberFormatException ex){
                        System.err.println(ex);
                        return;
                    }
                    matrixPanel.setSize(new_size);
                    vectorPanel.setSize(new_size);
                    
                    InputFrame.this.pack();
                }
            };
        
        AbstractAction newData = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    main.updateDiagram(matrixPanel.getData(), vectorPanel.getData());
                } catch (Exception ex) {
                    Logger.getLogger(InputFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        sizeField.setAction(resizeAction);
        
        this.setLayout(new BorderLayout(5,5 ));
        this.add(globalPanel, BorderLayout.CENTER);
        
        JPanel customPanel = new JPanel(new FlowLayout());
        customPanel.add(new JLabel("Size: "));
        customPanel.add(sizeField);
        
        JButton updateButton = new JButton(newData);
        updateButton.setText("Update");
        customPanel.add(updateButton);
        
        this.add(customPanel, BorderLayout.SOUTH);
        this.pack();
    }
    
    
}

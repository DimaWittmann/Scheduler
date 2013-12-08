package course.GUI;

import course.Main;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

    public final MatrixPanel matrixPanel;
    public final VectorPanel vectorPanel;
    private final JTextField sizeField;
    private int size;
    private final Main main;
    
    public InputFrame(final Main main, int size) throws HeadlessException {
        super("Input Panel");
        
        this.main = main;
        this.size = size;
        
        matrixPanel = new MatrixPanel(size);
        vectorPanel = new VectorPanel(size);
     
        JPanel globalPanel = new JPanel();
        globalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
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
        AbstractAction simplePlanning = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    main.updateDiagram(matrixPanel.getData(), vectorPanel.getData(), false);
                } catch (Exception ex) {
                    Logger.getLogger(InputFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        AbstractAction optimazedPlanning = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    main.updateDiagram(matrixPanel.getData(), vectorPanel.getData(), true);
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
        
        JButton simpleButton = new JButton(simplePlanning);
        simpleButton.setText("Simple solutuion");
        customPanel.add(simpleButton);
        JButton optimumButton = new JButton(optimazedPlanning);
        optimumButton.setText("Optimum solution");
        customPanel.add(optimumButton);
        
        this.add(customPanel, BorderLayout.SOUTH);
        this.pack();
    }
    
    
}

import java.awt.*;
import javax.swing.*;

public class Control extends JFrame {
    Model m;
    UI ui;

    public Control(Model _m, UI _ui, ViewCanvas vc) {

        m = _m;
        ui = _ui;

        
        
/*
setTitle("paain");
        setSize(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p = new JPanel(new GridLayout(10, 10));
        JPanel cell2 = new JPanel();
                cell2.setBackground(Color.red);
        p.add(cell2);
        add(p);
        setVisible(true);
        */
         
          m.addSub(ui);
          ui.setArr(m.getArrayTest());
          ui.setup(m.col, m.row);
          m.populateArr();
          ui.updateGrid();
          m.circler();
         

        // vc.renderedShapes().add(new Rectangle(0,0,10,10));
        // vc.repaint();
        
    }
        public void pr(Object x){
            System.out.println(x);
        }
    
}


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewCanvas extends Canvas {
    ArrayList<Shape> shapes = new ArrayList<>();

    public ViewCanvas() {

    }

    public ArrayList<Shape> renderedShapes() {
        return shapes;
    }

    public void paint(Graphics g) {
        super.paint(g);
        // the shapes are created in the main class but once you add them to the
        // renderedShapes() array of this canvas, you have access to them and the
        // graphics context at this point and can render them.
        for (Shape s : shapes) {
            renderShape(g, s);
        }
    }

    private void renderShape(Graphics g, Shape shape) {
          Rectangle rect = (Rectangle)shape;
          g.drawRect(rect.x, rect.y, rect.width, rect.height);
    }
//canvas.repaint();
}
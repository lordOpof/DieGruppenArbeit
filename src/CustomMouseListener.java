import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomMouseListener  extends MouseAdapter {
	int y,x;
	Component comp;
	Model m;
	UI ui;
	int coCo;
	boolean mouseDown=false;
	public CustomMouseListener(Component _comp, Model _m, UI _ui) {
		//y=_y;
		//x=_x;
		comp = _comp;
		m=_m;
ui=_ui;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//if (!m.draw)return;
		coCo=ui.coCo;
		int modifiers = e.getModifiersEx();
		if ((modifiers & InputEvent.BUTTON1_DOWN_MASK) != 0) {
		System.out.println(comp.getX() + " + " + comp.getY());
		m.setPoint(comp.getY() / 10, comp.getX() / 10, coCo);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}

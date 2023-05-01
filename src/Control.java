import javax.swing.*;

public class Control extends JFrame {
	Model m;
	UI ui;

	public Control(Model _m, UI _ui) {
		m = _m;
		ui = _ui;
		m.addSub(ui);
		m.populateArr();
		ui.setArr(m.getArrayTest());
		ui.setup(m.col, m.row);
		System.out.println("startet circling");
		m.circler();
	}

}

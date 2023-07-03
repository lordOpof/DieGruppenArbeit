import javax.swing.*;

public class Control extends JFrame {
	Model m;
	UI ui;

	public Control(Model _m, UI _ui) {
		m = _m;
		ui = _ui;
		m.setScreArr(m.getColorFromPic("map3.png"));
		m.addSub(ui);
		//m.populateArr();
		ui.setArr(m.getScreArr());
		ui.setup(m.col, m.row);


		m.fixMap();
		ui.updateGrid();
		System.out.println("startet circling");
		//m.bewegenTester(20, 20, -1, 5);
		m.circler();

	}

}

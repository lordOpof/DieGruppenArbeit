import javax.swing.*;

public class Control extends JFrame {
	Model m;
	UI ui;

	public Control(Model _m, UI _ui) {
		m = _m;
		ui = _ui;
		m.setScreArr(m.getColorFromPic("map.png"));
		m.addSub(ui);
		//m.populateArr();
		ui.setArr(m.getScreArr());
		ui.setup(m.col, m.row);


		m.fixMap();
		ui.updateGrid();
		System.out.println("startet circling");
		m.circler();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		m.cutSlice(100);
        m.cutSlice(101);

	}

}

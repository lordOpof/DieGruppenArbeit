import javax.swing.*;
import java.awt.*;


public class UI extends JFrame implements ModLis {
	// schuler war hier

	int[][] arr;
	int row, col;
	Component[] components;
	GridBagConstraints gbc;
	public JPanel p;

	UI() {
	}

	public void setup(int width, int heigth) {
		col = width;
		row = heigth;


		setTitle("Title");
		setSize(col*7,row*7);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		p = new JPanel(new GridLayout(row, col));
		System.out.println(p.getComponents().length);
		initGrid();
		components = p.getComponents();
		System.out.println(p.getComponents().length);
		add(p);
		setVisible(true);

		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(0, 0, 0, 0);


	}

	public void setArr(int[][] _arr) {
		arr = _arr;
	}

	public void initGrid() {
		p.removeAll();
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < col; x++) {
				JPanel cell = new JPanel();
				cell.setBackground(colorAtPos(y, x));
				p.add(cell);
			}
		}
		p.revalidate();
		p.repaint();
	}

	public void updateGrid() {

		for (int y = 0; y < row; y++) {
			for (int x = 0; x < col; x++) {
				/*JPanel cell = new JPanel();
				cell.setBackground(colorAtPos(y, x));
				p.add(cell);*/
				updateAtPos(y, x);

			}
		}
		/*Thread t1 = new Thread(() -> {
			for (int y = 0; y < row/2; y++) {
				for (int x = 0; x < col; x++) {
					updateAtPos(y,x);
				}
			}
		});
		Thread t2 = new Thread(() -> {
			for (int y = row/2; y < row; y++) {
				for (int x = 0; x < col; x++) {
					updateAtPos(y,x);
				}
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		try {
			t2.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}*/
	}

	public void updateAround(int[] coords) {
		int _y = coords[0];
		int _x = coords[1];
		for (int y = _y - 1; y <= _y + 1; y++) {
			for (int x = _x - 1; x <= _x + 1; x++) {
				updateAtPos(y, x);
			}
		}
	}

	public void updateAtPos(int y, int x) {

		int index = y * col + x;
		if (index >= 0 && index < components.length) {
			JPanel cell = (JPanel) components[index];
			cell.setBackground(colorAtPos(y, x));
		}
	}

	private Color colorAtPos(int y, int x) {

		try {
			return switch (arr[y][x]) { // color enhanced switch
				case 1 -> Color.red;
				case 2 -> Color.black;
				case 3 -> Color.GREEN;
				case 4 -> Color.gray;
				case 5 -> Color.BLUE;
				case 6 -> Color.CYAN;
				case 7 -> Color.MAGENTA;
				case 8 -> Color.PINK;
				case 9 -> Color.yellow;
				case 10 -> Color.orange;
				default -> Color.white;
			};
		} catch (Exception e) {
			System.out.println("color search out of bounds");
			return Color.white;
		}
	}

	public void onValChange(Model m) {
		//Thread uiUpdate = new Thread(() -> {
			//updateAround(m.getTmpYX());
			//updateGrid();
			//updateAround(m.tmpYX);
		//});
		//uiUpdate.start();
		updateGrid();
		updateAround(m.tmpYX);
		// TODO: fine tune for a good speed
		try {
			Thread.sleep(41);
		} catch (InterruptedException e) {
			System.out.println("couldn't sleep");
		}
	}
}
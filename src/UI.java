import javax.swing.*;
import java.awt.*;


public class UI extends JFrame implements ModLis {

	int[][] arr;
	int row, col;

	Color[] colorArr = {
			Color.lightGray,
			Color.yellow, Color.gray, Color.black,
			Color.black, Color.green, Color.pink,
			Color.red, Color.magenta, Color.orange,
			Color.WHITE, Color.blue
	};
	public Component[] components;
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
		gbc.weightx = 0;		gbc.fill = GridBagConstraints.BOTH;

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
		p.repaint();
		p.revalidate();
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
	}//NOTE:has some issues

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
			return colorArr[arr[y][x]];
		} catch (Exception e) {
			System.out.println("color out of bounds y: "+y+" x: "+x+" color value: "+arr[y][x]);
			return Color.darkGray;
		}


			/*int a = (arr[y][x] >> 24) & 255;
			int r = (arr[y][x] >> 16)& 255;
			int g = (arr[y][x] >> 8) & 255;
			int b =arr[y][x]  & 255;
			return new Color(r, g, b, a);*/
		//NOTE: http://www.java2s.com/example/java/2d-graphics/get-the-color-from-a-argb-color-value.html
		//with sliht bug fixing that is
	}

	public void onValChange(Model m) {
		//Thread uiUpdate = new Thread(() -> {
			//updateAround(m.getTmpYX());
			//updateGrid();
			//updateAround(m.tmpYX);
		//});
		//uiUpdate.start();
setArr(m.newArr);
		updateGrid();
		//updateAround(m.tmpYX);
		// TODO: fine tune for a good speed
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			System.out.println("couldn't sleep");
		}
	}
}
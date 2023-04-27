import javax.swing.*;
import java.awt.*;

public class UI extends JFrame implements ModLis {
	// schuler war hier
	
	int[][] arr;
	int row, col;
	GridBagConstraints gbc;
	LayoutManager glo;
	Color[] colors = { Color.white, new Color(186, 158, 91) };
public JPanel p;
	UI() {
	}

	public void setup(int width, int heigth) {
		col=width;
		row=heigth;
		setTitle("My Grid");
        setSize(row*10, col*10);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        p = new JPanel(new GridLayout(row, col));

        updateGrid();
        

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

	public void updateGrid() {
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
		Component[] components = p.getComponents();
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
		updateAround(m.getTmpYX());
		// TODO: fine tune for a good speed
		try {
			Thread.sleep(41);
		} catch (InterruptedException e) {
			System.out.println("couldn't sleep");
		}
	}
}
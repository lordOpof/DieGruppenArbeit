import javax.swing.*;
import java.awt.*;


public class UI extends JFrame {
	public Panel p;
	int[][] arr;
	GridBagConstraints gbc;

	UI(int width, int height, int[][] arrayTest) {
		arr = arrayTest;
		p = new Panel();
		p.setVisible(true);
		p.setPreferredSize(new Dimension(width, height));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout());

		this.add(p);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 0, 0, 0);
	}

	public void updateGrid() {

		p.removeAll();
		int row = arr.length;
		int col = arr[0].length;
		p.setLayout(new GridLayout(row, col));
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < col; x++) {
				JPanel cell = new JPanel();
				Color color = colorAtPos(x, y);
				cell.setBackground(color);
				if (color != Color.white)
					cell.setBorder(BorderFactory.createLineBorder(Color.red));
				else
					cell.setBorder(BorderFactory.createLineBorder(Color.white));
				p.add(cell);
			}
		}
		p.revalidate();
		p.repaint();
	}

	public void updateAtPos(int row, int col) {
		/*Component comp = p.getComponentAt(col, row);
		p.remove(comp);*/

		JPanel cell = new JPanel();

		cell.setBackground(colorAtPos(col, row));

		gbc.gridx = col;
		gbc.gridy = row;
		p.add(cell, gbc);

		p.revalidate();
		//p.repaint();
	}

	private Color colorAtPos(int x, int y) {
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
	}

	/*public void cube(int _x, int _y, int _w, int _h) {
		Cube x =new Cube(_x, _y, _w, _h);
		x.setVisible(true);
		x.g2d.fillRect(_x, _y, _w, _h);
	}*/
}
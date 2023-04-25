import javax.swing.*;
import java.awt.*;


public class UI extends JFrame {
	public Panel p;

	UI(int width, int height) {
		p = new Panel();
		p.setVisible(true);
		p.setPreferredSize(new Dimension(width, height));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout());

		this.add(p);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void updateGrid(int[][] arr) {

		p.removeAll();
		int row = arr.length;
		int col = arr[0].length;
		p.setLayout(new GridLayout(row, col));
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < col; x++) {
				JPanel cell = new JPanel();
				Color color = switch (arr[y][x]) { // color enhanced switch
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


	/*public void cube(int _x, int _y, int _w, int _h) {
		Cube x =new Cube(_x, _y, _w, _h);
		x.setVisible(true);
		x.g2d.fillRect(_x, _y, _w, _h);
	}*/
}
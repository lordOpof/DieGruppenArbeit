import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;


public class UI extends JFrame implements ModLis {

	int[][] arr = new int[72][128];
	int coCo=1;
	int row, col;
	Model m;
	Color[] colorArr = {
			Color.lightGray,
			Color.yellow, Color.gray, Color.black,
			Color.black, Color.green, Color.black,
			Color.orange, Color.black, Color.orange,
			Color.WHITE, Color.blue, Color.gray,
			Color.black, Color.red, Color.gray
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
		//setSize(col*7,row*7);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		p = new JPanel(new GridLayout(row, col));
		System.out.println(p.getComponents().length);
		initGrid();
		components = p.getComponents();

		System.out.println(p.getComponents().length);
		//add(p);
		getContentPane().add(p, BorderLayout.CENTER);
		pack();
		setVisible(true);
		isFocusable();
		setResizable(false);


		gbc = new GridBagConstraints();
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.BOTH;

		gbc.weighty = 0;
		gbc.insets = new Insets(0, 0, 0, 0);

		new Legende(this);
		isFocused();
	}

	public void addMListenerToPanels(Component[] arr) {
		for (Component comp : arr) {
			comp.addMouseListener(new CustomMouseListener(comp, m, this, capsLock));
		}
	}

	public void setArr(int[][] _arr) {
        /*for (int i = 0; i < _arr.length; i++) {
			arr[i] = Arrays.copyOf(_arr[i], _arr[i].length);
		}*/

		if (arr.length != _arr.length || arr[0].length != _arr[0].length) {
			throw new IllegalArgumentException("Arrays must have the same dimensions.");
		}

		// Copy values from _arr to arr
		for (int i = 0; i < arr.length; i++) {
			System.arraycopy(_arr[i], 0, arr[i], 0, arr[0].length);
		}
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
			//System.out.println("color out of bounds y: "+y+" x: "+x+" color value: "+arr[y][x]);
			//System.out.println("y: "+y+"  x: "+x);
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
			Thread.sleep(50);
		} catch (InterruptedException e) {
			System.out.println("couldn't sleep");
		}

	}

	public void addModel(Model _m) {
		m = _m;
	}
	int capsLock = -1;
	public void addCompLis(){
	addMListenerToPanels(components);
	this.addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			String keyP = KeyEvent.getKeyText(e.getKeyCode());
			System.out.print(keyP);

			int kC = e.getKeyCode();
			switch (keyP){
				case "W" -> { //Wasser bzw blau
					coCo = 11;
				}
				case "S" -> { //sand, gelb
					coCo = 1;
				}
				case "G" -> { //gas, grün
					coCo = 5;
				}
				case "B" -> {//bombe, hellgrau
					coCo = 8;
				}
				case "Space" -> { //pausiert
					m.draw = true;
				}
				case "P" -> { //play
					m.draw = false;
				}
				case "Caps Lock" -> { //spawner
				capsLock=capsLock*-1;
				}
			}
		}
	});
}
}
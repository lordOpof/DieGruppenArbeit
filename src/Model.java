import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Model extends JFrame {
	Random rng = new Random();
	public int[][] arrayTest;
	int[] tmpYX = new int[2];

	public int col, row;
	private ArrayList<ModLis> subs = new ArrayList<>();
	public boolean[][] visited;
	boolean isConnected = false;

	int sixCounter=0;

	public Model(int _row, int _col) {
		arrayTest = new int[_row][_col];
		col = arrayTest[0].length; // NOTE: col = _col; is more optimal
		row = arrayTest.length;
	}

	public Model() { //REDUNDANT
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int widthDisplay = gd.getDisplayMode().getWidth();
		int heightDisplay = gd.getDisplayMode().getHeight();
		arrayTest = new int[heightDisplay / 5][widthDisplay / 5];
		col = arrayTest[0].length; // NOTE: col = _col; is more optimal
		row = arrayTest.length;
	}

	public void circler() {
		Thread updateLoop = new Thread(() -> {
			while (true) {
				logic();
			}
		});
		updateLoop.start();

		Thread add2simThread = new Thread(() -> {
			//TODO: sand sandind machen
			boolean tr = true;
			while (tr) {
				arrayTest[0][col / 2] = rng.nextInt(10) + 1;
				notifySubs();
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException ignored) {
				}
				tr = true;
			}
		});
		//add2simThread.start();
	}

	public void printArr() {
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < col; x++) {
				System.out.print(arrayTest[y][x] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}

	public void add2sim(int y, int x) {
		arrayTest[y][x] = rng.nextInt(11);
		tmpYX[0] = y;
		tmpYX[1] = x;
		printArr();
		notifySubs();
	}

	public void logic() {
		for (int y = row - 1; y >= 0; y--) {
			for (int x = 0; x < col; x++) {
				switch (arrayTest[y][x]) {
					case 1, 2, 4 -> logicSand(y, x);
					case 3 -> logicStructure3(y, x);
//TODO: depending on number diffenrent logic
				}
			}
		}
		if (!isConnected) {
			for (int _y = 0; _y < row; _y++) {
				for (int _x = 0; _x < col; _x++) {
					if (visited[_y][_x]) arrayTest[_y][_x] = 4;
				}
			}
		}
		notifySubs();
	}

	public void logicSand(int y, int x) {
		//for (int y = row - 1; y >= 0; y--)
		{
			//for (int x = 0; x < col; x++)
			{
				if (arrayTest[y][x] != 0) {
					if (y + 1 < row) {
						if (arrayTest[y + 1][x] == 0) {
							arrayTest[y + 1][x] = arrayTest[y][x];
							arrayTest[y][x] = 0;
							tmpYX[0] = y;
							tmpYX[1] = x;

							//notifySubs();
						}
					}

					switch (rng.nextInt(2)) {
						case 0 -> {
							try {
								if (arrayTest[y + 1][x - 1] == 0) {
									arrayTest[y + 1][x - 1] = arrayTest[y][x];
									arrayTest[y][x] = 0;
									tmpYX[0] = y;
									tmpYX[1] = x;
									//notifySubs();
								}
							} catch (Exception e) {
								try {
									if (arrayTest[y + 1][x + 1] == 0) {
										arrayTest[y + 1][x + 1] = arrayTest[y][x];
										arrayTest[y][x] = 0;
										tmpYX[0] = y;
										tmpYX[1] = x;
										//notifySubs();
									}
								} catch (Exception ignored) {
								}
							}
						}
						case 1 -> {
							try {
								if (arrayTest[y + 1][x + 1] == 0) {
									arrayTest[y + 1][x + 1] = arrayTest[y][x];
									arrayTest[y][x] = 0;
									tmpYX[0] = y;
									tmpYX[1] = x;
									//notifySubs();
								}
							} catch (Exception e) {
								try {

									if (arrayTest[y + 1][x - 1] == 0) {
										arrayTest[y + 1][x - 1] = arrayTest[y][x];
										arrayTest[y][x] = 0;
										tmpYX[0] = y;
										tmpYX[1] = x;
										//notifySubs();
									}
								} catch (Exception ignored) {
								}
							}
						}
					}
				}
			}
		}

	}

	public void logicStructure3(int y, int x) {
		visited = new boolean[row][col];
		Queue<int[]> q = new LinkedList<>();


		/

		if (arrayTest[y][x] == 3) {
			q.add(new int[]{y, x});
			visited[y][x] = true;
		}
		 isConnected = false;

		//Breitensuche
		while (!q.isEmpty()) {
			int[] curr = q.poll();
			if (curr[0] == 0 || curr[0] == row - 1 || curr[1] == 0 || curr[1] == col - 1) { //NOTE: edge found
				isConnected = true;
				//break; if break then stop too soon
			}
			int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
			for (int[] dir : dirs) {
				int _row = curr[0] + dir[0];
				int _col = curr[1] + dir[1];
				if (_row >= 0 && _row < row && _col >= 0 && _col < col && !visited[_row][_col] && arrayTest[_row][_col] == arrayTest[y][x]) {
					q.add(new int[]{_row, _col});
					visited[_row][_col] = true;
					//if (!isConnected)arrayTest[_row][_col] = 4;
				}
			}
		}



	}

	public int[][] getColorFromPic(String path) {
		String filePath = path;
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		int width = img.getWidth();
		int height = img.getHeight();
		int[][] arr = new int[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				arr[y][x] = img.getRGB(x, y);
			}
		}
		return arr;
	}

	public void fixMap() {
		for (int _y = 0; _y < row; _y++) {
			for (int _x = 0; _x < col; _x++) {
				switch (arrayTest[_y][_x]) {
					case -16777216 -> arrayTest[_y][_x] = 3; //black
					default -> arrayTest[_y][_x] = 0;
				}
			}
		}
	}

	public void populateArr() {
		for (int y = 0; y < row; y++) {
			Arrays.fill(arrayTest[y], 0);
		}
		printArr();
	}

	public void cutSlice(int x) {
		for (int y = 0; y < row; y++) {
			arrayTest[y][x] = 0;
		}
		System.out.println("slice cut?");
	}

	public int[][] getArrayTest() {
		return arrayTest;
	}

	public void setArrayTest(int[][] arr) {
		arrayTest = arr;
	}

	public int[] getTmpYX() {
		return tmpYX;
	}

	public void addSub(ModLis sub) {
		subs.add(sub);
	}

	public void remSub(ModLis sub) {
		subs.remove(sub);
	}

	public void notifySubs() {
		for (ModLis sub : subs) {
			sub.onValChange(this);
		}
	}
}
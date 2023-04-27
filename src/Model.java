import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Model extends JFrame {
	Random rng = new Random();
	public int[][] arrayTest;
	int[] tmpYX = new int[2];

	public int col, row;
	private ArrayList<ModLis> subs = new ArrayList<>();

	public Model(int _row, int _col) {

		arrayTest = new int[_row][_col];
		col = arrayTest[0].length; // NOTE: col = _col; is more optimal
		row = arrayTest.length;
	}

	public void circler() {
		Thread updateLoop = new Thread(() -> {
			while (true) {
				logicSand();
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
		add2simThread.start();
	}

	public void adamSandler() {
		Thread sandler = new Thread(() -> {
			//TODO: sand sandind machen
			int i = 0;
			while (i != 10) {
				arrayTest[0][col / 2] = rng.nextInt(4) + 1;
				notifySubs();
				try {
					TimeUnit.MILLISECONDS.sleep(300);
				} catch (InterruptedException ignored) {
				}
				i++;
			}
		});
		sandler.start();
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

	public void logicSand() {

		for (int y = row - 1; y >= 0; y--) {
			for (int x = 0; x < col; x++) {
				if (arrayTest[y][x] != 0) {
					if (y + 1 < row) {
						if (arrayTest[y + 1][x] == 0) {
							arrayTest[y + 1][x] = arrayTest[y][x];
							arrayTest[y][x] = 0;
							tmpYX[0] = y;
							tmpYX[1] = x;

							notifySubs();
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
									notifySubs();
								}
							} catch (Exception e) {
								try {
									if (arrayTest[y + 1][x + 1] == 0) {
										arrayTest[y + 1][x + 1] = arrayTest[y][x];
										arrayTest[y][x] = 0;
										tmpYX[0] = y;
										tmpYX[1] = x;
										notifySubs();
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
									notifySubs();
								}
							} catch (Exception e) {
								try {

									if (arrayTest[y + 1][x - 1] == 0) {
										arrayTest[y + 1][x - 1] = arrayTest[y][x];
										arrayTest[y][x] = 0;
										tmpYX[0] = y;
										tmpYX[1] = x;
										notifySubs();
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

	public void populateArr() {
		for (int y = 0; y < row; y++) {
			Arrays.fill(arrayTest[y], 0);
		}
		printArr();
	}

	public int[][] getArrayTest() {
		return arrayTest;
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

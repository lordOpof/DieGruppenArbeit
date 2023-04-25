//test
import javax.swing.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main extends JFrame {
	Random rng = new Random();
	int xWidth = 10;
	int yWidth = 10;
	public int[][] arrayTest = new int[yWidth][xWidth];

	/* ={
		{0, 1, 0},
		{0, 0, 0},
		{0, 0, 0},
		{0, 0, 0},
		{0, 0, 0}
	};*/
	public UI ui;
	public int col = arrayTest[0].length;
	public int row = arrayTest.length;

	public static void main(String[] args) {
		Main m = new Main();
		m.main2();
	}

	private void main2() {
		populateArr();
		ui = new UI(arrayTest[0].length*100, arrayTest.length*100);
		circler();
	}


	private void circler() {
		Thread updateLoop = new Thread(() -> {
			while (true) {
				updatePos();
			}
		});
		updateLoop.start();

		/*Thread refresh = new Thread(() -> {
			while (true) {
				ui.updateGrid(arrayTest);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException ignored) {
				}
			}
		});
		refresh.start();*/

		Thread add2sim = new Thread(() -> {
			while (true) {
				arrayTest[0][5/*rng.nextInt(xWidth)*/] = rng.nextInt(11);
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException ignored) {
				}
			}
		});
		add2sim.start();
	}
//NOTE:integrate visual update into updatePos()
	private void updatePos() {

		for (int y = 0; y < row; y++) {
			for (int x = 0; x < col; x++) {
				if (arrayTest[y][x] != 0) {
					if (y + 1 < row) {
						if (arrayTest[y + 1][x] == 0) {
							arrayTest[y + 1][x] = arrayTest[y][x];
							arrayTest[y][x] = 0;
							//System.out.println("got here y:"+y+" x:"+x);
						}
					}

					switch (rng.nextInt(2)) {
						case 0 -> {
							try {
								if (arrayTest[y + 1][x - 1] == 0) {
									arrayTest[y + 1][x - 1] = arrayTest[y][x];
									arrayTest[y][x] = 0;
								}
							} catch (Exception e) {
								try {
									if (arrayTest[y + 1][x + 1] == 0) {
										arrayTest[y + 1][x + 1] = arrayTest[y][x];
										arrayTest[y][x] = 0;
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
								}
							} catch (Exception e) {
								try {

									if (arrayTest[y + 1][x - 1] == 0) {
										arrayTest[y + 1][x - 1] = arrayTest[y][x];
										arrayTest[y][x] = 0;
									}
								} catch (Exception ignored) {
								}
							}
						}
					}
				}
/*				if ((y + 1 < row) && (x - 1 < col) && rng.nextBoolean() == true) {
					System.out.println("got here ");
					if (arrayTest[y + 1][x - 1] == 0) {

						arrayTest[y + 1][x - 1] = arrayTest[y][x];
					} else {
						arrayTest[y + 1][x + 1] = arrayTest[y][x];
					}
					arrayTest[y][x] = 0;
				} else if (y + 1 < row && x + 1 < col) {
					System.out.println("got here 2");
					if (arrayTest[y + 1][x + 1] == 0) {
						arrayTest[y + 1][x + 1] = arrayTest[y][x];
					} else {
						arrayTest[y + 1][x - 1] = arrayTest[y][x];
					}
					arrayTest[y][x] = 0;
				}*/
			}
			Thread refresh = new Thread(() -> {
				ui.updateGrid(arrayTest);
			});
			refresh.start();

			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException ignored) {
			}
		}
	}

	private void populateArr(){
		for (int y = 0; y < row; y++) {
			Arrays.fill(arrayTest[y], 0);
		}
	}
}

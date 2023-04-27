import javax.swing.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main{
/*
	Random rng = new Random();
	int xWidth = 10;
	int yWidth = 10;
	//TODO: WTF????? 10 gros aber 100 in ui wahrend array klein, das scheint mir wie das problem WTF
	public int[][] arrayTest;
	public UI ui;
	public int col = arrayTest[0].length;
	public int row = arrayTest.length;
*/


	public static void main(String[] args) {
		Model m =new Model(10, 10);//TODO:aahhhh 100 muss 100 groß machen
		UI ui = new UI();
		Control controller = new Control(m, ui);
	}
/* 
	private void main2() {
		arrayTest = new int[yWidth][xWidth];
		populateArr();
		ui = new UI(arrayTest[0].length*10, arrayTest.length*10, arrayTest);
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
/* 
		Thread add2sim = new Thread(() -> {
			boolean tr = true;
			while (tr) {
				arrayTest[0][5] = rng.nextInt(11);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException ignored) {
				}
				tr=false;
			}
		});
		add2sim.start();
	}
	public int retArrVal(int y, int x){
	return arrayTest[y][x];
	}
//TODO:integrate visual update into updatePos()
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
				//TODO: OBSERVER PATTERN: only update when
				ui.updateAtPos(y, x);
			}
			/*Thread refresh = new Thread(() -> {
				ui.updateGrid(arrayTest);
			});
			refresh.start();

			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException ignored) {
			}*/
			/* 
		}
	}

	private void populateArr(){
		for (int y = 0; y < row; y++) {
			Arrays.fill(arrayTest[y], 0);
			for(int x =0;x<col;x++){
				System.out.print(arrayTest[y][x]);
			}
			System.out.println();
		}
		
	}
	public int[][] getArrayTest(){
		return arrayTest;
	}
	public void whiteSpace(){
		ui.fillSpace();
	}
*/
}

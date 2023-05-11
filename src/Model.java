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
	public int[][] screArr;
	int[] tmpYX = new int[2];
int[][][] changeArr;

	public int col, row;
	private ArrayList<ModLis> subs = new ArrayList<>();
	public boolean[][] visited;
	boolean isConnected=false;
    int[][][]blobs;

	int sixCounter=0;

	public Model(int _row, int _col) {
		screArr = new int[_row][_col];
changeArr = new int[_row][_col][2];
        blobs = new int[_row][_col][2]; //blob make arr to arr holding arr, with blob number and connected status
		col = screArr[0].length; // NOTE: col = _col; is more optimal
		row = screArr.length;

	}

	public Model() { //REDUNDANT
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int widthDisplay = gd.getDisplayMode().getWidth();
		int heightDisplay = gd.getDisplayMode().getHeight();
		screArr = new int[heightDisplay / 5][widthDisplay / 5];
		col = screArr[0].length; // NOTE: col = _col; is more optimal
		row = screArr.length;
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
				screArr[0][col / 2] = rng.nextInt(10) + 1;
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
				System.out.print(screArr[y][x] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
    public void printArr3D() {
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < col; x++) {
				System.out.print(blobs[y][x][1] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}

	public void add2sim(int y, int x) {
		screArr[y][x] = rng.nextInt(11);
		tmpYX[0] = y;
		tmpYX[1] = x;
		printArr();
		notifySubs();
	}
public void updateArr(){
for (int y = 0; y < row; y++) {
			for (int x = 0; x < col; x++) {
//screArr[y][x]=
}
}
}
	public void logic() {
		for (int y = row - 1; y >= 0; y--) {
			for (int x = 0; x < col; x++) {
				switch (screArr[y][x]) {
					case 1, 2, 4 -> logicSand(y, x);
					case 3 -> logicStructure3(y, x);
					case 5 -> logicGas(y, x);
//TODO: depending on number diffenrent logic
				}
			}
		}
		/*if (!isConnected) {
			for (int _y = 0; _y < row; _y++) {
				for (int _x = 0; _x < col; _x++) {
					if (visited[_y][_x]) screArr[_y][_x] = 4;
				}
			}
		}
        */
		notifySubs();
	}

	public void logicSand(int y, int x) {
		//for (int y = row - 1; y >= 0; y--)
		{
			//for (int x = 0; x < col; x++)
			{
				if (screArr[y][x] != 0) {
					if (y + 1 < row) {
						if (screArr[y + 1][x] == 0) {
							screArr[y + 1][x] = screArr[y][x];
							screArr[y][x] = 0;
							tmpYX[0] = y;
							tmpYX[1] = x;
							//notifySubs();
						}
					}

					switch (rng.nextInt(2)) {
						case 0 -> {
							try {
								if (screArr[y + 1][x - 1] == 0) {
									screArr[y + 1][x - 1] = screArr[y][x];
									screArr[y][x] = 0;
									tmpYX[0] = y;
									tmpYX[1] = x;
									//notifySubs();
								}
							} catch (Exception e) {
								try {
									if (screArr[y + 1][x + 1] == 0) {
										screArr[y + 1][x + 1] = screArr[y][x];
										screArr[y][x] = 0;
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
								if (screArr[y + 1][x + 1] == 0) {
									screArr[y + 1][x + 1] = screArr[y][x];
									screArr[y][x] = 0;
									tmpYX[0] = y;
									tmpYX[1] = x;
									//notifySubs();
								}
							} catch (Exception e) {
								try {

									if (screArr[y + 1][x - 1] == 0) {
										screArr[y + 1][x - 1] = screArr[y][x];
										screArr[y][x] = 0;
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
//TODO: OPTIMIZE!!!!!!!
	}

	public void logicGas(int y, int x) {

		if (screArr[y][x] == 5) {
			if (y != 0) {
				if (screArr[y - 1][x] == 0) {
					screArr[y - 1][x] = screArr[y][x];
					screArr[y][x] = 0;
					tmpYX[0] = y;
					tmpYX[1] = x;
				}
				switch (rng.nextInt(2)) {
					case 0 -> {
						try {
							if (screArr[y - 1][x - 1] == 0) {
								screArr[y - 1][x - 1] = screArr[y][x];
								screArr[y][x] = 0;
								tmpYX[0] = y;
								tmpYX[1] = x;
								//notifySubs();
							}
						} catch (Exception e) {
							try {
								if (screArr[y - 1][x + 1] == 0) {
									screArr[y - 1][x + 1] = screArr[y][x];
									screArr[y][x] = 0;
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
							if (screArr[y - 1][x + 1] == 0) {
								screArr[y - 1][x + 1] = screArr[y][x];
								screArr[y][x] = 0;
								tmpYX[0] = y;
								tmpYX[1] = x;
								//notifySubs();
							}
						} catch (Exception e) {
							try {

								if (screArr[y - 1][x - 1] == 0) {
									screArr[y - 1][x - 1] = screArr[y][x];
									screArr[y][x] = 0;
									tmpYX[0] = y;
									tmpYX[1] = x;
									//notifySubs();
								}
							} catch (Exception ignored) {
							}
							//nur wie Sand
						}
					}
				} //jetzt wie Gas
				switch (rng.nextInt(2)) {
					case 0 -> {
						try {
							if (screArr[y][x + 1] == 0) {
								screArr[y][x + 1] = screArr[y][x];
								screArr[y][x] = 0;
								tmpYX[0] = y;
								tmpYX[1] = x;
								//notifySubs();
							}
						}catch(Exception e){
								try {
									if (screArr[y][x - 1] == 0) {
										screArr[y][x - 1] = screArr[y][x];
										screArr[y][x] = 0;
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
							if (screArr[y][x - 1] == 0) {
								screArr[y][x - 1] = screArr[y][x];
								screArr[y][x] = 0;
								tmpYX[0] = y;
								tmpYX[1] = x;
								//notifySubs();
							}
						} catch (Exception e) {
							try {
								if (screArr[y][x + 1] == 0) {
									screArr[y][x + 1] = screArr[y][x];
									screArr[y][x] = 0;
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
	public void logicStructure3(int y, int x) {
		visited = new boolean[row][col];
		Queue<int[]> q = new LinkedList<>();

		if (screArr[y][x] == 3) {
			q.add(new int[]{y, x});
			visited[y][x] = true;
		}
        blobs[y][x][1] = 0;
		 isConnected = false;

		//Breitensuche
		while (!q.isEmpty()) {
			int[] curr = q.poll();
			if (curr[0] == 0 /*|| curr[0] == row - 1 sodass unten nicht zahlt*/|| curr[1] == 0 || curr[1] == col - 1) { //NOTE: edge found
                blobs[y][x][1] = 1;
				isConnected = true;
				//break; if break then stop too soon
			}
			int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
			for (int[] dir : dirs) {
				int _row = curr[0] + dir[0];
				int _col = curr[1] + dir[1];
				if (_row >= 0 && _row < row && _col >= 0 && _col < col && !visited[_row][_col] && screArr[_row][_col] == screArr[y][x]) {
					q.add(new int[]{_row, _col});
					visited[_row][_col] = true;
					//if (!isConnected)arrayTest[_row][_col] = 4;
				}
			}
		}
       if(blobs[y][x][1]==0||!isConnected) logicSand(y, x); //NOTE: why does this work, it just does, is it slow tho



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
				System.out.println(img.getRGB(x,y));
			}
		}
		return arr;
	}

	public void fixMap() {
		for (int _y = 0; _y < row; _y++) {
			for (int _x = 0; _x < col; _x++) {
				switch (screArr[_y][_x]) {
					case -16777216 -> screArr[_y][_x] = 3; //black
					case -14503604 -> screArr[_y][_x]=5;
					default -> screArr[_y][_x] = 0;
				}
			}
		}
	}
//TODO: make usable for multiple numbers with same color

	public void populateArr() {
		for (int y = 0; y < row; y++) {
			Arrays.fill(screArr[y], 0);
		}
		printArr();
	}

	public void cutSlice(int x) {
		for (int y = 0; y < row; y++) {
			screArr[y][x] = 0;
		}
		System.out.println("slice cut?");
	}

	public int[][] getScreArr() {
		return screArr;
	}

	public void setScreArr(int[][] arr) {
		screArr = arr;
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
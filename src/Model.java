import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

public class Model extends JFrame {
	public int[][] screArr;
	int[] tmpYX = new int[3];// speichert YX Werte zwischen
	int[][][] changeArr; //TODO:nicht wichtig

	Vektor[][] vektorArr; //Für Bewegung
	int[][] exArr;
	int[][] rauchArr;
	int[][] explosionArr;
	boolean[][] fixArr;
	public int col, row;
	private ArrayList<ModLis> subs = new ArrayList<>(); //Observer-Pattern
	public boolean[][] visited;
	boolean isConnected = false;
	int[][][] blobs;
	int[][] newArr;
	int[][] streuArr;
	int sixCounter = 0;
	public Random rng = new Random();
	int curCol = 1;

	public Model(int _row, int _col) {
		screArr = new int[_row][_col];
		newArr = new int[_row][_col];
		changeArr = new int[_row][_col][2];
		vektorArr = new Vektor[_row][_col];
		exArr = new int[_row][_col];
		rauchArr = new int[_row][_col];
		fixArr = new boolean[_row][_col];
		streuArr = new int[_row][_col];
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

	//region Unnötig
	public void bewegenTester(int y, int x, int yb, int xb) {
		vektorArr[y][x] = new Vektor(yb, xb);
		newArr[y][x] = 6;
	}

	public void vektorBefüllen() {
		for (int r = 0; r < col; r++) {
			for (int c = 0; c < row; c++) {
				vektorArr[c][r] = new Vektor(0, 0);
			}
		}
	}

	boolean draw = true;

	public void circler() {
		// Thread updateLoop = new Thread(() -> {
		while (true) {
			if (!draw) {
				logic();

			}

			notifySubs();

		}
		// });
		// updateLoop.start();
/*
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
        */

	}

	public void printArr() {
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < col; x++) {
				System.out.print(newArr[y][x] + " ");
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
		//printArr();
		notifySubs();
	}

	public void updateArr() {
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < col; x++) {
//screArr[y][x]=
			}
		}
	}

	//endregion
	public void logic() {
		for (int i = 0; i < screArr.length; i++) {
			newArr[i] = Arrays.copyOf(screArr[i], screArr[i].length);
		}

		//printArr();
		for (int i = 0; i < row; i++) {
			Arrays.fill(fixArr[i], false);
			//System.out.println(fixArr.length+" "+fixArr[0].length);
		}
		boolean fast = false;
		if (!fast) {
			for (int y = row - 1; y >= 0; y--) {
				for (int x = 0; x < col; x++) {
					// System.out.println(screArr[0].length);
					//System.out.println("y: " + y + "  x: " + x);
					logicSwitch(y, x);
				}
			}
		} else {
			ExecutorService executor = Executors.newFixedThreadPool(4);

			executor.submit(() -> {//q1
				int yMax, yMin, xMax, xMin;
				yMax = (row) / 2;
				yMin = 0;
				xMax = col;
				xMin = col / 2;
				for (int y = yMax; y >= yMin; y--) {
					for (int x = xMin; x < xMax; x++) {
						logicSwitch(y, x);
					}
				}
			});

			executor.submit(() -> {//q2
				int yMax, yMin, xMax, xMin;
				yMax = (row) / 2;
				yMin = 0;
				xMax = col / 2;
				xMin = 0;
				for (int y = yMax; y >= yMin; y--) {
					for (int x = xMin; x < xMax; x++) {
						logicSwitch(y, x);
					}
				}
			});

			executor.submit(() -> {//q3
				int yMax, yMin, xMax, xMin;
				yMax = row - 1;
				yMin = (row) / 2;
				xMax = col / 2;
				xMin = 0;
				for (int y = yMax; y >= yMin; y--) {
					for (int x = xMin; x < xMax; x++) {
						logicSwitch(y, x);
					}
				}
			});

			executor.submit(() -> {//q4
				int yMax, yMin, xMax, xMin;
				yMax = row - 1;
				yMin = (row) / 2;
				xMax = col;
				xMin = col / 2;
				for (int y = yMax; y >= yMin; y--) {
					for (int x = xMin; x < xMax; x++) {
						logicSwitch(y, x);
					}
				}
			});

			executor.shutdown();

			try {
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void logicSwitch(int y, int x) {
		switch (screArr[y][x]) {
			case 1/* 2, 4,*/ -> logicSand(y, x); //nasser Sand
			case 3999 -> logicStructure3(y, x);
			case 5 -> logicGas(y, x);
			case 11 -> logicWasser(y, x);
			case 6 -> bewegen(y, x);//nicht mehr bewegen 13
			case 8 -> logicExplosion(y, x);
			case 12 -> logicSplitterbombe(y, x);
			case 15 -> logicRauch(y, x);
			case 31 -> logicQuelle(y, x);
			case 25 -> logicGasHahn(y, x);
			case 32 -> logicQuelleStreu(y, x);
			case 28 -> logicQuelleBombe(y, x);
			case 7 -> logicNasserSand(y, x);
			case 21 -> logicQuelleSand(y, x);
		}
	}
	//explosion rot 14
	// 20 feste Strukturen


	public void switchTo(int y, int x, int yA, int xA) {
		int hilf = screArr[y + yA][x + xA];
		//if(hilf==newArr[y + yA][x + xA]) return;
		newArr[y + yA][x + xA] = screArr[y][x];
		newArr[y][x] = hilf;
		fixArr[y + yA][x + xA] = true;
	}

	public void switchToRauch(int y, int x, int yA, int xA) {
		rauchArr[y + yA][x + xA] = rauchArr[y][x];
		rauchArr[y][x] = 0;
		//System.out.println(rauchArr[y + yA][x + xA] + " " + newArr[y + yA][x + xA]);
	}

	//region logics
	public void logicSand(int y, int x) { // newArr Ähnderung
		{
			//for (int x = 0; x < col; x++)
			{
				if (screArr[y][x] != 0) {
					if (y + 1 < row) {
						if (screArr[y + 1][x] == 0 || screArr[y + 1][x] == 1) {
							switchTo(y, x, 1, 0);
						}
						if (screArr[y + 1][x] == 11) {
							newArr[y + 1][x] = 7;
							newArr[y][x] = screArr[y + 1][x]; //
							tmpYX[0] = y;
							tmpYX[1] = x;
						}

						switch (rng.nextInt(3)) {
							case 0 -> {
								try {
									if (screArr[y + 1][x - 1] == 0 || screArr[y + 1][x - 1] == 1) {
										switchTo(y, x, 1, -1);
									}
								} catch (Exception e) {
									try {
										if (screArr[y + 1][x + 1] == 0 || screArr[y + 1][x + 1] == 1) {
											switchTo(y, x, 1, 1);
										}
									} catch (Exception ignored) {
									}
								}
							}
							case 1 -> {
								try {
									if (screArr[y + 1][x + 1] == 0 || screArr[y + 1][x + 1] == 1) {
										switchTo(y, x, 1, 1);
									}
								} catch (Exception e) {
									try {

										if (screArr[y + 1][x - 1] == 0 || screArr[y + 1][x - 1] == 1) {
											switchTo(y, x, 1, -1);
										}
									} catch (Exception ignored) {
									}
								}
							}
							case 3 -> {
								try {
									if (screArr[y + 1][x] == 0 || screArr[y + 1][x + 1] == 1) {
										switchTo(y, x, 1, 0);
									}
								} catch (Exception e) {
									try {

										if (screArr[y + 1][x - 1] == 0 || screArr[y + 1][x - 1] == 1) {
											switchTo(y, x, 1, -1);
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
	}

	public void logicNasserSand(int y, int x) {
		if (y < row - 1) {
			switch (rng.nextInt(3)) {
				case 0 -> {
					if(screArr[y + 1][x] == 1) {
						switchTo(y, x, 1, 0);
					}
				}
				case 1 -> {
					if (screArr[y + 1][x + 1] == 1) {
						switchTo(y, x, 1, 1);
					}
				}
				case 2 -> {
					if (screArr[y + 1][x - 1] == 1) {
						switchTo(y, x, 1, -1);
					}
				}
			}
		}
	}

	public void logicGas(int y, int x) {
		//printArr();
		//try {
		//System.out.println("bei fix arr, x= " + x);
		if (!fixArr[y][x])
			if (y != 0) {
				if (screArr[y - 1][x] == 0 || screArr[y - 1][x] == 11 || screArr[y - 1][x] == 5) {
					switchTo(y, x, -1, 0);
				}
				switch (rng.nextInt(2)) {
					case 0 -> {
						try {
							if (screArr[y - 1][x - 1] == 0) {
								switchTo(y, x, -1, -1);
							}
						} catch (Exception e) {
							try {
								if (screArr[y - 1][x + 1] == 0) {
									switchTo(y, x, -1, 1);
								}
							} catch (Exception ignored) {
							}
						}
					}
					case 1 -> {
						try {
							if (screArr[y - 1][x + 1] == 0) {
								switchTo(y, x, -1, 1);
							}
						} catch (Exception e) {
							try {

								if (screArr[y - 1][x - 1] == 0) {
									switchTo(y, x, -1, -1);
								}
							} catch (Exception ignored) {
							}
						}
					}
				}
				switch (rng.nextInt(2)) {
					case 0 -> {
						try {
							if (screArr[y][x + 1] == 0) {
								switchTo(y, x, 0, 1);
							}
						} catch (Exception e) {
							try {
								if (screArr[y][x - 1] == 0) {
									switchTo(y, x, 0, -1);
								}
							} catch (Exception ignored) {
							}
						}
					}
					case 1 -> {
						try {
							if (screArr[y][x - 1] == 0) {
								switchTo(y, x, 0, -1);
							}
						} catch (Exception e) {
							try {
								if (screArr[y][x + 1] == 0) {
									switchTo(y, x, 0, 1);
								}
							} catch (Exception ignored) {
							}
						}
					}
				}
			}
		//} catch(Exception e){}
	}

	public void logicWasser(int y, int x) {
		try {
			if (y + 1 < row) {
				if (screArr[y][x] == 11) {
					if (screArr[y + 1][x] == 0 || screArr[y + 1][x] == 5) {
						switchTo(y, x, 1, 0);
					}
					if (screArr[y + 1][x] == 7) {
						screArr[y][x] = 0;
						int hilf = 7;
						tmpYX[1] = y + 1;
						tmpYX[2] = x;
						while (hilf == 7) {
							if (screArr[tmpYX[1]][tmpYX[2]] == 1) {
								hilf = 1;
							} else {
								switch (rng.nextInt(3)) {
									case 0 -> {
										if (screArr[tmpYX[1]++][tmpYX[2]] == 1) {
											tmpYX[1]++;
										}
									}
									case 1 -> {
										if (screArr[tmpYX[1]][tmpYX[2]++] == 1) {
											tmpYX[2]++;
										}
									}
									case 2 -> {
										if (screArr[tmpYX[1]][tmpYX[2]--] == 1) {
											tmpYX[2]--;
										}
									}
								}
							}
						}
						if (screArr[tmpYX[1]][tmpYX[2]] == 1) {
							screArr[tmpYX[1]][tmpYX[2]] = 7;
						}
					}
					if (screArr[y + 1][x] == 1) {
						newArr[y][x] = 0;
						newArr[y + 1][x] = 7;
					}
					switch (rng.nextInt(2)) {
						case 0 -> {
							try {
								if (screArr[y + 1][x - 1] == 0 || screArr[y + 1][x - 1] == 5) {
									switchTo(y, x, 1, -1);
								}
							} catch (Exception e) {
								try {
									if (screArr[y + 1][x + 1] == 0 || screArr[y + 1][x + 1] == 5) {
										switchTo(y, x, 1, 1);
									}
								} catch (Exception ignored) {
								}
							}
						}
						case 1 -> {
							try {
								if (screArr[y + 1][x + 1] == 0 || screArr[y + 1][x + 1] == 5) {
									switchTo(y, x, 1, 1);
								}
							} catch (Exception e) {
								try {

									if (screArr[y + 1][x - 1] == 0 || screArr[y + 1][x - 1] == 5) {
										switchTo(y, x, 1, -1);
									}
								} catch (Exception ignored) {
								}
								//nur wie Sand
							}
						}
					}
					switch (rng.nextInt(2)) {
						case 0 -> {
							try {
								if (screArr[y][x + 3] == 0) {
									switchTo(y, x, 0, 3);
								}
							} catch (Exception e) {
								try {
									if (screArr[y][x - 3] == 0) {
										switchTo(y, x, 0, -3);
									}
								} catch (Exception ignored) {
								}
							}
						}
						case 1 -> {
							try {
								if (screArr[y][x - 3] == 0) {
									switchTo(y, x, 0, -3);
								}
							} catch (Exception e) {
								try {
									if (screArr[y][x + 3] == 0) {
										switchTo(y, x, 0, 3);
									}
								} catch (Exception ignored) {
								}
							}
						}
					}

				}
			}
		} catch (Exception e) {
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
			if (curr[0] == 0 /*|| curr[0] == row - 1 sodass unten nicht zahlt*/ || curr[1] == 0 || curr[1] == col - 1) { //NOTE: edge found
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
		if (blobs[y][x][1] == 0 || !isConnected)
			logicSand(y, x); //NOTE: why does this work, it just does, is it slow tho


	}

	public void bewegen(int y, int x) {
		//System.out.println("bewegen" + y + " " + x);
		//System.out.println("vektor" + vektorArr[y][x].gety() + " " + vektorArr[y][x].getx());
		//System.out.println(fixArr[y][x]);
		if (!fixArr[y][x]) {
			int xb = vektorArr[y][x].getx();
			int yb = vektorArr[y][x].gety();
			if (y + yb >= row) {
				while (y + yb >= row) {
					yb--;
				}
				vektorArr[y][x].sety(yb);
				//System.out.println("boden");
			} else if (x + xb >= col - 1) {
				vektorArr[y][x].setx(-xb);
				//System.out.println("seite");
			} else if (x + xb <= 0) {
				vektorArr[y][x].setx(0);
			} else if (y + yb == row - 1) {
				vektorArr[y][x] = null;
				newArr[y + yb][x + xb] = 13;
				newArr[y][x] = 0;
				//System.out.println("end");
			} else if (y + yb <= 0) {
				vektorArr[y][x].sety(-yb);
			} else if (screArr[y + yb][x + xb] == 0 || screArr[y + yb][x + xb] == 5 || screArr[y + yb][x + xb] == 14 || screArr[y + yb][x + xb] == 15 || screArr[y + yb][x + xb] == 11) {
				newArr[y + yb][x + xb] = 6;
				newArr[y][x] = 0;
				//System.out.println("regular");
				vektorArr[y][x] = null;
				vektorArr[y + yb][x + xb] = new Vektor(yb, xb);
				fixArr[y + yb][x + xb] = true;
				//System.out.println("newVektor " + vektorArr[y + yb][x + xb].gety() + " " + vektorArr[y + yb][x + xb].getx());
				//System.out.println("next " + (y + yb) + " " + (x + xb));
			} else if (screArr[y + 1][x] == 20 || screArr[y + 1][x] == 1) {
				//System.out.println("auflegen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				vektorArr[y][x].setx(0);
				vektorArr[y][x].sety(0);
			} else if (screArr[y][x + 1] == 20 || screArr[y][x - 1] == 20 || screArr[y][x + 1] == 6 || screArr[y][x - 1] == 6 || screArr[y][x + 1] == 1 || screArr[y][x - 1] == 1 || screArr[y][x + 1] == 8 || screArr[y][x - 1] == 8) {
				//System.out.println("abpraller");
				vektorArr[y][x].setx(-xb);
			} else {
				//System.out.println("annähern");
				int hilfx = xb;
				int hilfy = yb;
				int error = 0;
				while (!(screArr[y + hilfy][x + hilfx] == 0 || screArr[y + hilfy][x + hilfx] == 5 || screArr[y + hilfy][x + hilfx] == 14 || screArr[y + hilfy][x + hilfx] == 15 || screArr[y + hilfy][x + hilfx] == 11 || screArr[y + hilfy][x + hilfx] == 6) || error < 20) {
					if (hilfx >= 0) {
						hilfx--;
					} else {
						hilfx++;
					}
					if (hilfy >= 0) {
						hilfy--;
					} else {
						hilfy++;
					}
					error++;
				}
				if (error > 18) {
					vektorArr[y][x].setx((-xb / 2) + 1);
					vektorArr[y][x].sety((-yb / 2) + 1);
					//System.out.println("error");
				} else {
					//System.out.println(yb + " " + xb + " zu " + hilfy + " " + hilfx);
					//System.out.println(screArr[y + hilfy][x + hilfx]);
					newArr[y + hilfy][x + hilfx] = 6;
					newArr[y][x] = 0;
					vektorArr[y + hilfy][x + hilfx] = new Vektor(yb, xb);
					vektorArr[y][x] = null;
					fixArr[y + hilfy][x + hilfx] = true;
				}
			}
		}
		//System.out.println("finished0000000000000000000000000000");
	}

	public void logicExplosion(int y, int x) {
		if (7 < y && y < col - 4) {
			if (exArr[y][x] == 0) {
				for (int ya = -1; ya < 2; ya++) {
					for (int xa = -1; xa < 2; xa++) {
						if (!(ya == 0 && xa == 0)) {
							newArr[y + ya][x + xa] = 14;
						}
					}
				}
				for (int b = -2; b < 4; b++) {
					newArr[y - 2][x + b] = 6;
					vektorArr[y - 2][x + b] = new Vektor(-6, b * 4);
				}
				for (int r = -2; r < 4; r++) {
					newArr[y - 4][x + r] = 15;
					rauchArr[y - 4][x + r] = 20 + rng.nextInt(10);
				}
			}
			exArr[y][x]++;
			if (exArr[y][x] == 2) {
				for (int ya = -2; ya < 3; ya++) {
					for (int xa = -2; xa < 3; xa++) {
						if (!(ya == 0 && xa == 0)) {
							newArr[y + ya][x + xa] = 14;
						}
					}
				}
				newArr[y - 2][x - 2] = 0;
				newArr[y - 2][x + 2] = 0;
				newArr[y + 2][x - 2] = 0;
				newArr[y + 2][x + 2] = 0;
				for (int b = -3; b < 5; b++) {
					newArr[y - 3][x + b] = 6;
					vektorArr[y - 3][x + b] = new Vektor(-6, b * 4);
				}
				for (int r = -4; r < 5; r++) {
					newArr[y - 5][x + r] = 15;
					rauchArr[y - 5][x + r] = 20 + rng.nextInt(10);
				}
			}
			if (exArr[y][x] == 4) {
				for (int ya = -2; ya < 3; ya++) {
					for (int xa = -2; xa < 3; xa++) {
						if (!(ya == 0 && xa == 0)) {
							newArr[y + ya][x + xa] = 14;
						}
					}
				}
				newArr[y - 3][x - 1] = 14;
				newArr[y - 3][x] = 14;
				newArr[y - 3][x + 1] = 14;
				newArr[y + 3][x - 1] = 14;
				newArr[y + 3][x] = 14;
				newArr[y + 3][x + 1] = 14;
				newArr[y + 1][x - 3] = 14;
				newArr[y][x - 3] = 14;
				newArr[y - 1][x - 3] = 14;
				newArr[y + 1][x + 3] = 14;
				newArr[y][x + 3] = 14;
				newArr[y - 1][x + 3] = 14;
				for (int b = -3; b < 5; b++) {
					newArr[y - 4][x + b] = 6;
					vektorArr[y - 4][x + b] = new Vektor(-6, b * 4);
				}
				for (int r = -6; r < 7; r++) {
					newArr[y - 6][x + r] = 15;
					rauchArr[y - 6][x + r] = 20 + rng.nextInt(10);
				}
			}
			if (exArr[y][x] == 7) {
				for (int ya = -3; ya < 4; ya++) {
					for (int xa = -3; xa < 4; xa++) {
						if (screArr[y + ya][x + xa] == 14)
							switch (rng.nextInt(2)) {
								case 0 -> newArr[y + ya][x + xa] = 15;
								case 1 -> newArr[y + ya][x + xa] = 0;
							}
					}
				}
				exArr[y][x] = 0;
				newArr[y][x] = 15;
			}
		}
	}

	public void logicRauch(int y, int x) {
		if (!fixArr[y][x]) {
			if (rauchArr[y][x] != 0) {
				rauchArr[y][x]--;
				if (y != 0) {
					switch (rng.nextInt(2)) {
						case 0 -> {
							try {
								if (screArr[y - 1][x - 1] == 0) {
									switchTo(y, x, -1, -1);
									switchToRauch(y, x, -1, -1);
								}
							} catch (Exception e) {
								try {
									if (screArr[y - 1][x + 1] == 0) {
										switchTo(y, x, -1, 1);
										switchToRauch(y, x, -1, 1);
									}
								} catch (Exception ignored) {
								}
							}
						}
						case 1 -> {
							try {
								if (screArr[y - 1][x + 1] == 0) {
									switchTo(y, x, -1, 1);
									switchToRauch(y, x, -1, 1);
								}
							} catch (Exception e) {
								try {

									if (screArr[y - 1][x - 1] == 0) {
										switchTo(y, x, -1, -1);
										switchToRauch(y, x, -1, -1);
									}
								} catch (Exception ignored) {
								}
							}
						}
					}
					switch (rng.nextInt(2)) {
						case 0 -> {
							try {
								if (screArr[y][x + 1] == 0) {
									switchTo(y, x, 0, 1);
									switchToRauch(y, x, 0, 1);
								}
							} catch (Exception e) {
								try {
									if (screArr[y][x - 1] == 0) {
										switchTo(y, x, 0, -1);
										switchToRauch(y, x, 0, -1);
									}
								} catch (Exception ignored) {
								}
							}
						}
						case 1 -> {
							try {
								if (screArr[y][x - 1] == 0) {
									switchTo(y, x, 0, -1);
									switchToRauch(y, x, 0, -1);
								}
							} catch (Exception e) {
								try {
									if (screArr[y][x + 1] == 0) {
										switchTo(y, x, 0, 1);
										switchToRauch(y, x, 0, 1);
									}
								} catch (Exception ignored) {
								}
							}
						}
					}
				}
			} else {
				newArr[y][x] = 0;
			}
		}
	}

	public void logicSplitterbombe(int y, int x) {
		newArr[y][x] = 0;
		for (int yh = -1; yh < 2; yh++) {
			for (int xh = -1; xh < 2; xh++) {
				newArr[y + yh][x + xh] = 6;
				vektorArr[y + yh][x + xh] = null;
				vektorArr[y + yh][x + xh] = new Vektor(yh * 5, xh * 5);
			}
		}
	}

	public void logicQuelle(int y, int x) {
		if(y>=col-2) newArr[y][x]=0;
		else newArr[y + 1][x] = 11;
	}
	public void logicQuelleSand(int y, int x){
		if(y>=col-2) newArr[y][x]=0;
		else newArr[y+1][x]=1;
	}
	public void logicGasHahn(int y, int x) {
		if(y<=2) newArr[y][x]=0;
		else newArr[y - 1][x] = 5;
	}

	public void logicQuelleStreu(int y, int x) {
		if (streuArr[y][x] == 20) {
			newArr[y + 2][x] = 12;
			streuArr[y][x] = 0;
		}
		streuArr[y][x]++;
	}

	public void logicQuelleBombe(int y, int x) {
		if (y > col - 9) {
			newArr[y][x] = 0;
		} else if (streuArr[y][x] == 30) {
			newArr[y + 7][x + 1] = 8;
			streuArr[y][x] = 0;
		}
		streuArr[y][x]++;
	}
	//endregion

	//region berechnungen für view
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
				//System.out.println(img.getRGB(x, y));
			}
		}
		return arr;
	}

	public void fixMap() {
		for (int _y = 0; _y < row; _y++) {
			for (int _x = 0; _x < col; _x++) {
				switch (screArr[_y][_x]) {
					case -16777216 -> screArr[_y][_x] = 1; //sand
					case -14503604 -> screArr[_y][_x] = 5; //grün
					default -> screArr[_y][_x] = 0;
					case -12629812 -> screArr[_y][_x] = 11; //blau
					case -1237980 -> screArr[_y][_x] = 12; //rot
					case -32985 -> screArr[_y][_x] = 8; //orange
					case -8421505 -> screArr[_y][_x] = 20;
					case -9399618 -> screArr[_y][_x] = 31; // blau unter Wasser blau 16
					case -4856291 -> screArr[_y][_x] = 25; // grün unter Gas grün 17
					case -20791 -> screArr[_y][_x] = 32; // rosa unter rot für Streu 18
					case -14066 -> screArr[_y][_x] = 28; // gold unter orange Bombe 19

					//die langen zahlen sind im argb int format
				}
			}
		}
		newArr = screArr;
	}
//TODO: make usable for multiple numbers with same color

	public void populateArr() {
		for (int y = 0; y < row; y++) {
			Arrays.fill(screArr[y], 0);
		}
		printArr();
	}
//endregion

	//region auch Unnötig
	public void cutSlice(int x) {
		for (int y = 0; y < row; y++) {
			newArr[y][x] = 0;
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

	long tStart, tStop;

	public void startTimer() {
		tStart = System.nanoTime();
	}

	public void stopTimer(String loc) {
		tStop = System.nanoTime();
		long time = TimeUnit.NANOSECONDS.toMicros(tStop - tStart);
		System.out.println("at " + loc + ": " + time);
	}

	public void setPoint(int y, int x, int coCo) {
		newArr[y][x] = coCo;
	}
}



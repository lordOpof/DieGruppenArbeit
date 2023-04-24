import java.util.Random;

public class Main {

    Random rng = new Random();
    int[][] arrayTest = new int[5][3];
    public int width = determineSize(arrayTest);
    int[] map = {
            0, 1, 0,
            0, 0, 0,
            0, 0, 0,
            0, 0, 0,
            1, 1, 1
    };

    public static void main(String[] args) {
        Main m = new Main();
        m.main2();
    }

    private void main2() {
        arrayTest = testFill(arrayTest);
        printArr(arrayTest);
        arrayTest = update(arrayTest);
        printArr(arrayTest);
    }

    private int[][] update(int[][] arr) {
        for (int y = 0; y < arr.length; y++) {
            for (int x = 0; x < width; x++) {
                if (arr[y][x] != 0) {
                    System.out.println(y+" "+width);
                    if (y + 1 < width) {
                        if (arr[y + 1][x] == 0) {
                            arr[y + 1][x] = arr[y][x];
                            arr[y][x] = 0;
                            System.out.println("got here");
                        }
                    } else {
                        if (y + 1 < width && x + 1 < arr.length && rng.nextBoolean()) {
                            if (arr[y + 1][x - 1] == 0) {
                                arr[y + 1][x - 1] = arr[y][x];
                            } else {
                                arr[y + 1][x + 1] = arr[y][x];
                            }
                            arr[y][x] = 0;
                        } else if (y + 1 < width && x - 1 < arr.length) {
                            if (arr[y + 1][x + 1] == 0) {
                                arr[y + 1][x + 1] = arr[y][x];
                            } else {
                                arr[y + 1][x - 1] = arr[y][x];
                            }
                            arr[y][x] = 0;
                        }

                    }
                }
            }
            System.out.println(y);
            printArr(arr);
        }
        return arr;
    }

    private int determineSize(int[][] arr) {
        int y = 0;
        while (true) {
            try {
                arr[0][y] = arr[0][y];
            } catch (Exception e) {
                return y;
            }
            y++;
        }
    }

    private void printArr(int[][] arr) {
        for (int[] ints : arr) {
            for (int x = 0; x < width; x++) {
                System.out.print(ints[x] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n\n\n");
    }
//test
    private int[][] testFill(int[][] arr) {
        int i = 0;
        for (int y = 0; y < arr.length; y++) {
            for (int x = 0; x < width; x++) {
                arr[y][x] = map[i];
                i++;
            }
        }
        return arr;
    }
}

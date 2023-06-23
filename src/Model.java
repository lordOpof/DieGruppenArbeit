import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Model extends JFrame {
    public int[][] screArr;
    int[] tmpYX = new int[2];// speichert YX Werte zwischen
    int[][][] changeArr; //TODO:nicht wichtig

    Vektor[][] vektorArr; //Für Bewegung
    int[][] explosionArr;
    public int col, row;
    private ArrayList<ModLis> subs = new ArrayList<>(); //Observer-Pattern
    public boolean[][] visited;
    boolean isConnected = false;
    int[][][] blobs;
    int[][] newArr;
    int sixCounter = 0;
    public Random rng = new Random();

    public Model(int _row, int _col) {
        screArr = new int[_row][_col];
        newArr = new int[_row][_col];
        changeArr = new int[_row][_col][2];
        vektorArr = new Vektor[_row][_col];
        blobs = new int[_row][_col][2]; //blob make arr to arr holding arr, with blob number and connected status
        col = screArr[0].length; // NOTE: col = _col; is more optimal
        row = screArr.length;
        /*
this.vektorBefüllen();
        this.bewegenTester(20, 20, 3, 3);
*/
    }

    public Model() { //REDUNDANT
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int widthDisplay = gd.getDisplayMode().getWidth();
        int heightDisplay = gd.getDisplayMode().getHeight();
        screArr = new int[heightDisplay / 5][widthDisplay / 5];
        col = screArr[0].length; // NOTE: col = _col; is more optimal
        row = screArr.length;
    }

    public void bewegenTester(int y, int x, int yb, int xb) {
        vektorArr[y][x].setx(xb);
        vektorArr[y][x].sety(yb);
        screArr[y][x] = 11;
    }

    public void vektorBefüllen() {
        for (int r = 0; r < col; r++) {
            for (int c = 0; c < row; c++) {
                vektorArr[c][r] = new Vektor(0, 0);
            }
        }
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

    public void updateArr() {
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < col; x++) {
//screArr[y][x]=
            }
        }
    }

    public void logic() {
        screArr = newArr;
//maybe flush newArr
        for (int y = row - 1; y >= 0; y--) {
            for (int x = 0; x < col; x++) {
                switch (screArr[y][x]) {
                    case 1, 2, 4, 7 -> logicSand(y, x); // 7 nass
                    case 3 -> logicStructure3(y, x);
                    case 5 -> logicGas(y, x);
                    case 11 -> logicWasser(y, x);
                    case 6 -> bewegen(y, x);//Bombe
                    case 8 -> logicExplosion(y, x);
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
public void switchTo(int y, int x, int yA, int xA){
            int hilf = screArr[y + yA][x + xA];
newArr[y + yA][x+xA] = screArr[y][x];
                            newArr[y][x] = hilf;                      
}
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //region logics
    public void logicSand(int y, int x) { // newArr Ähnderung
        //for (int y = row - 1; y >= 0; y--)
        {
            //for (int x = 0; x < col; x++)
            {
                if (screArr[y][x] != 0) {
                    if (y + 1 < row) {
                        if (screArr[y + 1][x] == 0) {
                            switchTo(y,x,1,0);
                        }
                        if (screArr[y + 1][x] == 11) {
                            newArr[y + 1][x] = 7;
                            newArr[y][x] = screArr[y + 1][x]; //
                            tmpYX[0] = y;
                            tmpYX[1] = x;
                        }

                        switch (rng.nextInt(2)) {
                            case 0 -> {
                                try {
                                    if (screArr[y + 1][x - 1] == 0 || screArr[y + 1][x - 1] == 1) {
                                        switchTo(y,x,1,-1);
                                    }
                                } catch (Exception e) {
                                    try {
                                        if (screArr[y + 1][x + 1] == 0 || screArr[y + 1][x + 1] == 1) {
                                            switchTo(y,x,1,1);
                                        }
                                    } catch (Exception ignored) {
                                    }
                                }
                            }
                            case 1 -> {
                                try {
                                    if (screArr[y + 1][x + 1] == 0 || screArr[y + 1][x + 1] == 1) {
                                        switchTo(y,x,1,1);
                                    }
                                } catch (Exception e) {
                                    try {

                                        if (screArr[y + 1][x - 1] == 0 || screArr[y + 1][x - 1] == 1) {
                                            switchTo(y,x,1,-1);
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


    public void logicGas(int y, int x) {

        if (screArr[y][x] == 5) {
            if (y != 0) {
                if (screArr[y - 1][x] == 0) {
                    switchTo(y,x,-1,0);
                }
                switch (rng.nextInt(2)) {
                    case 0 -> {
                        try {
                            if (screArr[y - 1][x - 1] == 0) {
                                switchTo(y,x,-1,-1);
                            }
                        } catch (Exception e) {
                            try {
                                if (screArr[y - 1][x + 1] == 0) {
                                    switchTo(y,x,-1,1);
                                }
                            } catch (Exception ignored) {
                            }
                        }
                    }
                    case 1 -> {
                        try {
                            if (screArr[y - 1][x + 1] == 0) {
                                switchTo(y,x,-1,1);
                            }
                        } catch (Exception e) {
                            try {

                                if (screArr[y - 1][x - 1] == 0) {
                                  switchTo(y,x,-1,-1);
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
                                switchTo(y,x,0,1);
                            }
                        } catch (Exception e) {
                            try {
                                if (screArr[y][x - 1] == 0) {
                                    switchTo(y,x,0,-1);
                                }
                            } catch (Exception ignored) {
                            }
                        }
                    }
                    case 1 -> {
                        try {
                            if (screArr[y][x - 1] == 0) {
                                switchTo(y,x,0,-1);
                            }
                        } catch (Exception e) {
                            try {
                                if (screArr[y][x + 1] == 0) {
                                    switchTo(y,x,0,1);
                                }
                            } catch (Exception ignored) {
                            }
                        }
                    }
                }
            }
        }
    }
    public void logicWasser(int y, int x) {
        if (y != col - 1) {
            if (screArr[y][x] == 11) {
                if (screArr[y + 1][x] == 0 || screArr[y + 1][x] == 5) {
                    switchTo(y,x,1,0);
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
                                switchTo(y,x,1,-1);
                            }
                            } catch (Exception e) {
                                try {
                                    if (screArr[y + 1][x + 1] == 0 || screArr[y + 1][x + 1] == 5) {
                                        switchTo(y,x,1,1);
                                    }
                                } catch (Exception ignored) {
                                }
                            }
                        }
                        case 1 -> {
                            try {
                                if (screArr[y + 1][x + 1] == 0 || screArr[y + 1][x + 1] == 5) {
                                    switchTo(y,x,1,1);
                                }
                            } catch (Exception e) {
                                try {

                                    if (screArr[y + 1][x - 1] == 0 || screArr[y + 1][x - 1] == 5) {
                                       switchTo(y,x,1,-1);
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
                                    switchTo(y,x,0,1);
                                }
                            } catch (Exception e) {
                                try {
                                    if (screArr[y][x - 1] == 0) {
                                        switchTo(y,x,0,-1);
                                    }
                                } catch (Exception ignored) {
                                }
                            }
                        }
                        case 1 -> {
                            try {
                                if (screArr[y][x - 1] == 0) {
                                    switchTo(y,x,0,-1);
                                }
                            } catch (Exception e) {
                                try {
                                    if (screArr[y][x + 1] == 0) {
                                        switchTo(y,x,0,1);
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

    public void bewegen(int y, int x) { // Diese Methode ist für jede Art von Bewegung zuständig, die komplizierter als normales fallen ist
        int xb = vektorArr[y][x].getx();
        int yb = vektorArr[y][x].gety();
        if (screArr[y + yb][x + xb] == 0) {
            newArr[y + yb][x + xb] = screArr[x][y];
            newArr[y][x] = 0;
            vektorArr[y][x].setx(0);
            vektorArr[y][x].sety(0);
            vektorArr[y + yb][x + xb].setx(yb);
            vektorArr[y + yb][x + xb].sety(xb);
        } else if (screArr[y - 1][x] != 0) {
            vektorArr[y][x].setx(0);
            vektorArr[y][x].sety(0);
        } else if (screArr[y][x + 1] != 0 || screArr[y][x - 1] != 0) {
            vektorArr[y][x].setx(-xb);
        } else {
            int hilfx = xb;
            int hilfy = yb;
            while (screArr[y + hilfx][x + hilfy] != 0) {
                if (hilfx > 0) {
                    hilfx--;
                } else {
                    hilfx++;
                }
                if (hilfy > 0) {
                    hilfy--;
                } else {
                    hilfy++;
                }
            }
            newArr[y + hilfy][x + hilfx] = screArr[y][x];
            vektorArr[y + hilfy][x + hilfx].setx(xb);
            vektorArr[y + hilfy][x + hilfx].sety(yb);
            vektorArr[y][x].setx(0);
            vektorArr[y][x].sety(0);
        }
    }

    public void logicExplosion(int y, int x) {

    }
    
public void logicSplitterbombe(int y, int x) {
    newArr[y][x] = 0;
    for(int yh = -1; yh < 2; yh++){
    for(int xh = -1; xh < 2; xh++){
    if(screeArr[y + yh][x + xh] == 0){
    newArr[y + yh][x + xh] = 6;
    vektorArr[y + yh][x + xh].setx(xh * 5);
    vektorArr[y + yh][x + xh].sety(yh * 5);
}
}
}
}
    //endregion
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
                System.out.println(img.getRGB(x, y));
            }
        }
        return arr;
    }

    public void fixMap() {
        for (int _y = 0; _y < row; _y++) {
            for (int _x = 0; _x < col; _x++) {
                switch (screArr[_y][_x]) {
                    case -16777216 -> screArr[_y][_x] = 3; //black
                    case -14503604 -> screArr[_y][_x] = 5; //grün
                    default -> screArr[_y][_x] = 0;
                    case -12629812 -> screArr [_y][_x]=11; //blau
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
public void startTimer(){
        tStart = System.nanoTime();
}
public void stopTimer(String loc){
    tStop= System.nanoTime();
    long time = TimeUnit.NANOSECONDS.toMicros(tStop-tStart);
    System.out.println("at "+loc+": "+time);
}
}



















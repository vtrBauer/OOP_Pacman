package oopacman;

import javafx.scene.canvas.GraphicsContext;
import static java.lang.Math.*;
import java.io.*;
import javafx.scene.paint.Color;
import static oopacman.OOPacman.*;

/**
 *
 * @author Alexylva
 */
public class Map implements UIObject {

    private StaticEntity[][] map;
    private static final int gridLines = 20, gridColumns = 15; //4:3
    //Real width, height
    private int width = (int) floor(OOPacman.ga.getWidth()),
            height = (int) floor(OOPacman.ga.getHeight());

    public Map(String pathToMapFile) {
        map = new StaticEntity[gridLines][gridColumns];
        int[][] mapInt = this.readMap(pathToMapFile);
        this.criarMapa(mapInt);

    }

    private void criarMapa(int[][] mapaInt) {
        int[] grid;
        for (int i = 0; i < gridLines; i++) {
            for (int j = 0; j < gridColumns; j++) {
                grid = gridToXY(j, i);
                switch (mapaInt[i][j]) {
                    case 0:
                        this.map[i][j] = new Path(grid[0], grid[1]);
                        break;
                    case 1:
                        this.map[i][j] = new Wall(grid[0], grid[1]);
                        break;
                    case 2:
                        int[] other = xyToGrid(grid[0], grid[1]);
                        System.out.printf("grid(%d,%d) => xy(%d,%d) => grid(%d,%d)\n", i, j, grid[0], grid[1], other[0], other[1]);
                        
                        entityObject.add(new Pacman(grid[0], grid[1]));
                        break;
                    case 3:
                        entityObject.add(new Ghost(grid[0], grid[1], new Color(1.00, 0, 0, 1.0)));
                        break;
                    case 4:
                        entityObject.add(new Ghost(grid[0], grid[1], new Color(0, 1.00, 0, 1.0)));
                        break;
                    case 5:
                        entityObject.add(new Ghost(grid[0], grid[1], new Color(0, 0, 1.00, 1.0)));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private int[][] readMap(String pathToFile) {
        try {

            FileReader leitor = new FileReader(gridLines, gridColumns);
            int matrizMapaInt[][] = leitor.readFile(pathToFile);
            return matrizMapaInt;

        } catch (IOException e) {
            System.out.println("Erro: " + e);
        }
        return null;
    }

    public int[] snapToGrid(int x, int y) {
        return new int[]{(int) gridColumns * ((int) floor(x / gridColumns)), gridLines * ((int) floor(y / gridLines))};
    }    
    
    public int[] xyToGrid(int x, int y) {
        return new int[]{(int) floor(y * gridLines /height), (int) floor(x * gridColumns / width)};
    }

    public int[] gridToXY(int gridX, int gridY) {
        return new int[]{(int) floor(width * gridX / gridColumns), (int) floor(height * gridY / gridLines)};
    }

    public boolean dirIsFree(int x, int y, Key dir) {
        if (x < 0 || y < 0 || x > gridLines || y > gridColumns) {
            return false;
        }
        
        try {
        switch (dir) {
            case UP:
                if (map[x-1][y] instanceof Wall) {
                    System.out.printf(("%s (%d,%d, %s) => FALSE\n"),map[x][y-1], x,y-1,dir);
                    ((Wall) map[x-1][y]).setColor(new Color(1,0,0,1));
                    return false;
                };
                break;
            case DOWN:
                if (map[x+1][y] instanceof Wall) {
                    System.out.printf(("%s (%d,%d, %s) => FALSE\n"),map[x][y+1], x,y+1,dir);
                    ((Wall) map[x+1][y+1]).setColor(new Color(1,0,0,1));
                    return false;
                };
                break;
            case LEFT:
                if (map[x][y-1] instanceof Wall) {
                    System.out.printf(("%s (%d,%d, %s) => FALSE\n"),map[x-1][y], x-1,y,dir);
                    ((Wall) map[x][y-1]).setColor(new Color(1,0,0,1));
                    return false;
                };
                break;
            case RIGHT:
                if (map[x][y+1] instanceof Wall) {
                    System.out.printf(("%s (%d,%d, %s) => FALSE\n"),map[x+1][y], x+1,y,dir);
                    ((Wall) map[x+1][y]).setColor(new Color(1,0,0,1));
                    return false;
                };
                break;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

        @Override
        public void render
        (GraphicsContext gc, double time
        
            ) {
        for (int i = 0; i < gridLines; i++) {
                for (int j = 0; j < gridColumns; j++) {
                    if (map[i][j] != null) {
                        map[i][j].render(gc, time);
                    }
                }
            }
        }

        @Override
        public void update
        (GraphicsContext gc, double time
        
        
    

) {
    }

}

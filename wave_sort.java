package Lee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Program {
    public static void main(String[] args) {
        var mg = new MapGenerator();
        Point start = new Point(2, 2);
        Point exit = new Point(11, 14);
        System.out.println(
                new MapPrinter().MapColor(mg.getMap()));

        var Lee = new WaveAlgorithm(mg.getMap());
        Lee.Colorize(start);

        System.out.println(
                new MapPrinter().rawData(mg.getMap()));

        var myRoad = Lee.getRoad(exit);
        System.out.println(myRoad);

        Lee.ColorizeRoad(myRoad);
        System.out.println(new MapPrinter().MapColor(mg.getMap()));

    }
}

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("x: %d  y: %d \n", x, y);
    }
}

class MapGenerator {
    int[][] map;

    public MapGenerator() {
        int[][] map = {
                { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                { -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 },
                { -1, 0, 0, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1 },
                { -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 },
                { -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 },
                { -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1 },
                { -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 },
                { -1, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, -1 },
                { -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 },
                { -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 },
                { -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 },
                { -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, -1 },
                { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        };
        this.map = map;
    }

    public int[][] getMap() {
        return map;
    }

    public void setCat(Point pos) {
        map[pos.x][pos.y] = -2;
    }

    public void setExit(Point pos) {
        map[pos.x][pos.y] = -3;
    }
}

class MapPrinter {
    public MapPrinter() {

    }

    public String rawData(int[][] map) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                sb.append(String.format("%3d", map[row][col]));
            }
            sb.append("\n");
        }
        for (int i = 0; i < 3; i++) {
            sb.append("\n");
        }

        return sb.toString();
    }

    public String MapColor(int[][] map) {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                switch (map[row][col]) {
                    case 0:
                        sb.append("░░");
                        break;
                    case -1:
                        sb.append("▓▓");
                        break;
                    case -2:
                        sb.append("C");
                        break;
                    case -3:
                        sb.append("E");
                        break;
                    case -4:
                        sb.append("+ ");
                        break;
                    default:
                        sb.append("░░");
                        break;
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

class WaveAlgorithm {
    int[][] map;

    public WaveAlgorithm(int[][] map) {
        this.map = map;
    }

    public void Colorize(Point startPoint) {
        Queue<Point> queue = new LinkedList<Point>();
        queue.add(startPoint);
        map[startPoint.x][startPoint.y] = 1;

        while (queue.size() != 0) {
            Point p = queue.remove();

            if (map[p.x - 1][p.y] == 0) {
                queue.add(new Point(p.x - 1, p.y));
                map[p.x - 1][p.y] = map[p.x][p.y] + 1;
            }
            if (map[p.x][p.y - 1] == 0) {
                queue.add(new Point(p.x, p.y - 1));
                map[p.x][p.y - 1] = map[p.x][p.y] + 1;
            }
            if (map[p.x + 1][p.y] == 0) {
                queue.add(new Point(p.x + 1, p.y));
                map[p.x + 1][p.y] = map[p.x][p.y] + 1;
            }
            if (map[p.x][p.y + 1] == 0) {
                queue.add(new Point(p.x, p.y + 1));
                map[p.x][p.y + 1] = map[p.x][p.y] + 1;
            }
        }
    }

    public ArrayList<Point> getRoad(Point exit) {
        ArrayList<Point> road = new ArrayList<>();
        Point current = exit;
        if (map[exit.x][exit.y] != 0) {
            road.add(exit);
            for (int i = 0; i < map[exit.x][exit.y] - 1; i++) {
                if (map[current.x - 1][current.y] == map[current.x][current.y] - 1) {
                    current = new Point(current.x - 1, current.y);
                } else if (map[current.x][current.y - 1] == map[current.x][current.y] - 1) {
                    current = new Point(current.x, current.y - 1);
                } else if (map[current.x + 1][current.y] == map[current.x][current.y] - 1) {
                    current = new Point(current.x + 1, current.y);
                } else if (map[current.x][current.y + 1] == map[current.x][current.y] - 1) {
                    current = new Point(current.x, current.y + 1);
                }
                road.add(current);
            }
        }
        Collections.reverse(road);
        return road;
    }

    public void ColorizeRoad(ArrayList<Point> road) {
        for (int i = 0; i < road.size(); i++) {
            Point current = road.get(i);
            map[current.x][current.y] = -4;
        }
    }

}
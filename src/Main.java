import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static char[][] maze;
    private static boolean[][] visited;
    private static int rows, cols;
    private static char currentMarker = 'a'; // Start with 'a'

    public static void main(String[] args) {

        if (args.length != 1) {
            // System.out.println("Usage: java MazeSolver <maze_file>");
            return;
        }

        String fileName = args[0];

        try {
            readMazeFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return;
        }

        System.out.println("Welcome to the Maze Solver");
        System.out.println("Reading maze from '" + fileName + "'");

        System.out.println("\nHere's the unsolved maze:");
        displayMaze();

        if (solveMaze(0, 0)) {
            System.out.println("\nHere's the solved maze:");
            displayMaze();
        } else {
            System.out.println("\nNo solution found!");
        }

        System.out.println("\nThanks for using the Maze Solver!");
    }

    private static void readMazeFromFile(String fileName) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(fileName));

        rows = 1;
        cols = fileScanner.nextLine().length();
        while(fileScanner.hasNextLine()){
            fileScanner.nextLine();
            rows += 1;
        }
        fileScanner.close();

        fileScanner = new Scanner(new File(fileName));

        maze = new char[rows][cols];
        visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = fileScanner.nextLine().replaceAll(" ", "");
            for (int j = 0; j < cols; j++) {
                maze[i][j] = line.charAt(j);
                visited[i][j] = false;
            }
        }
        fileScanner.close();
    }

    private static void displayMaze() {
        System.out.print("     ");
        for (int col = 0; col < cols; col++) {
            System.out.printf("%-3d", col);
        }
        System.out.println();

        for (int row = 0; row < rows; row++) {
            System.out.printf("%2d   ", row);
            for (int col = 0; col < cols; col++) {
                System.out.printf("%-3c", maze[row][col]);
            }
            System.out.println();
        }
    }

    private static boolean solveMaze(int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols || maze[row][col] == '#' || visited[row][col]) {
            return false;
        }

        maze[row][col] = currentMarker;
        visited[row][col] = true;

        if (row == rows - 1 && col == cols - 1) {
            return true;
        }

        char nextMarker = (currentMarker == 'z') ? 'a' : (char) (currentMarker + 1);
        currentMarker = nextMarker;

        if (solveMaze(row + 1, col)) {
            currentMarker = nextMarker;
            return true;
        }
        if (solveMaze(row, col + 1)) {
            currentMarker = nextMarker;
            return true;
        }
        if (solveMaze(row - 1, col)) {
            currentMarker = nextMarker;
            return true;
        }
        if (solveMaze(row, col - 1)) {
            currentMarker = nextMarker;
            return true;
        }

        maze[row][col] = '.';
        visited[row][col] = false;
        return false;
        }
    }

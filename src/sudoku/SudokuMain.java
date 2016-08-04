package sudoku;

import sudoku.grid.Grid;
import sudoku.grid.SimpleGrid;
import sudoku.grid.Solver;
import sudoku.swing.SudokuFrame;

import java.util.Collection;
import java.util.List;

public class SudokuMain {

    private static int[] startingValues = {
            1, 8, 0, 6, 0, 0, 5, 0, 9,
            0, 0, 6, 0, 0, 0, 1, 0, 0,
            9, 0, 5, 0, 1, 0, 0, 6, 0, //
            0, 0, 0, 0, 2, 9, 4, 1, 0,
            0, 9, 0, 0, 6, 1, 7, 2, 5,
            2, 1, 0, 7, 0, 4, 9, 0, 0, //
            0, 0, 1, 0, 0, 0, 0, 7, 0,
            0, 0, 0, 1, 0, 5, 0, 9, 0,
            0, 0, 9, 2, 7, 6, 8, 5, 1
    };

    private static int[] startingValuesOld = {
            0, 8, 0, 0, 0, 0, 5, 0, 9,
            0, 0, 6, 0, 0, 0, 0, 0, 0,
            9, 0, 5, 0, 1, 0, 0, 6, 0, //
            0, 0, 0, 0, 0, 9, 4, 1, 0,
            0, 0, 0, 0, 6, 0, 7, 0, 5,
            2, 0, 0, 7, 0, 4, 0, 0, 0, //
            0, 0, 1, 0, 0, 0, 0, 7, 0,
            0, 0, 0, 0, 0, 5, 0, 9, 0,
            0, 0, 9, 2, 7, 6, 8, 0, 0
    };

    public static void main(String[] args) {
        Grid grid = new Grid(startingValuesOld);
        SudokuFrame frame = new SudokuFrame(grid);
        frame.setVisible(true);


        Solver solver = new Solver(grid);
//        solver.findSuggestions();
    }

//    public static void main(String[] args) {
//        SimpleGrid grid = new SimpleGrid();
//
//        for (int i = 0; i < 9; ++i) {
//            for (int j = 0; j < 9; ++j) {
//                int index = calcIndex(i, j);
//                grid.setValue(i, j, startingValues[index]);
//            }
//        }
//
//        Solver solver = new Solver(grid);
//        solver.setup();
//        solver.runSimpleEliminator();
//
//        List<Solver.Suggestion> suggestionsList = solver.findSuggestions();
//        for (Solver.Suggestion suggestion: suggestionsList) {
//            System.out.println(suggestion);
//        }
//
//        SudokuFrame frame = new SudokuFrame(grid, solver);
//        frame.setVisible(true);
//        printGrid(grid);
//    }

    private static int calcIndex(int row, int col) {
        return 9 * row + col;
    }

    public static void printGrid(SimpleGrid grid) {
        System.out.println("Conflicting values: " + join(grid.getInvalidList()));

        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                System.out.print(grid.getValue(i, j) + " ");
            }

            System.out.print("\n");
        }

    }

    private static String join(Collection<Integer> collect) {
        StringBuilder builder = new StringBuilder();
        for (Integer val: collect) {
            builder.append(val);
            builder.append(",");
        }

        return builder.toString();
    }
}

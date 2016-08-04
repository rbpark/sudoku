package sudoku.swing;

import sudoku.grid.Grid;
import sudoku.grid.SolverOld;

import javax.swing.*;
import java.awt.*;

public class SudokuFrame extends JFrame {
    private Grid grid;
    private SolverOld solver;

    public SudokuFrame(Grid grid) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(440, 500);
        this.grid = grid;
        this.setLayout(new FlowLayout());

        //SudokuGridPanel gridPanel = new SudokuGridPanel(grid);
        //this.add(gridPanel);
        SudokuGridCanvas canvas = new SudokuGridCanvas(grid);
        this.add(canvas);

        SudokuHelperComponent helper = new SudokuHelperComponent(canvas);
        this.add(helper);
    }

}

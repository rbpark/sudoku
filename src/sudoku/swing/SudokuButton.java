package sudoku.swing;

import sudoku.grid.Cell;

import javax.swing.*;

/**
 * Created by richardpark on 8/3/16.
 */
public class SudokuButton extends JButton {
    private Cell cell;

    public SudokuButton(Cell cell) {
        super("" + cell.getValue());
        this.cell = cell;
    }

}

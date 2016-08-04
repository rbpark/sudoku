package sudoku.view;

import javax.swing.*;

/**
 * Created by richardpark on 8/3/16.
 */
public class SudokuButton extends JButton {
    private int row;
    private int column;
    private int value;

    public SudokuButton(int row, int column, int value) {
        super("" + value);
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}

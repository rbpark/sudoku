package sudoku.grid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SimpleGrid {
    private int[] grid;
    private HashSet<Integer> invalidSet;

    public SimpleGrid() {
        this.grid = new int[9*9];
        this.invalidSet = new HashSet<Integer>();
    }

    public List<Integer> getInvalidList() {
        return new ArrayList<>(invalidSet);
    }

    public void setValue(int row, int col, int value) {
        int oldValue = this.grid[calcIndex(row, col)];
        if (oldValue == value) {
            return;
        }

        this.grid[calcIndex(row, col)] = value;

        // if the old value is a part of the invalid set, check to see if old value is still invalid
        if (invalidSet.contains(oldValue)) {
            if (validate(oldValue)) {
                invalidSet.remove(oldValue);
            }
        }

        // If it's invalid, adding more will not make it better. So we skip validation anyways.
        if (invalidSet.contains(value)) {
            return;
        }

        if (!validate(row, col, value)) {
            invalidSet.add(value);
        }
    }

    private boolean validate(int value) {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (!validate(i, j, value)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean validate(int row, int col, int value) {
        // check row
        for (int i = 0; i < 9; ++i) {
            if (i != col) {
                if (getValue(row, i) == value) {
                    // found another value on the same row.
                    return false;
                }
            }
        }

        // check col
        for (int i = 0; i < 9; ++i) {
            if (i != row) {
                if (getValue(i, col) == value) {
                    return false;
                }
            }
        }

        return validateSquare(row, col, value);
    }

    private boolean validateSquare(int row, int col, int value) {
        int rowStart = (row / 3) * 3; // floor of row
        int colStart = (col / 3) * 3; // floor of row

        for (int i = rowStart; i < rowStart + 3; ++i) {
            for (int j = colStart; j < colStart + 3; ++j) {
                if (i == row && j == col) {
                    continue;
                }

                if (getValue(i, j) == value) {
                    return false;
                }
            }
        }

        return true;
    }

    public int getValue(int row, int col) {
        return this.grid[calcIndex(row, col)];
    }

    public static int calcIndex(int row, int col) {
        return 9 * row + col;
    }

    public int getIndexVal(int index) {
        return this.grid[index];
    }
}

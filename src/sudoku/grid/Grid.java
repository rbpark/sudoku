package sudoku.grid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Grid implements Iterable<Cell> {
    private Cell[][] cells;
    private CellGroup[][] cellGroup;
    private HashSet<Cell> invalidCells;

    /**
     * Assumes initial values have no conflicts.
     *
     * @param initialValues
     */
    public Grid(int[] initialValues) {
        createCells(initialValues);
        createGroups();
        this.invalidCells = new HashSet<>();
    }

    private void createCells(int[] initialValues) {
        cells = new Cell[9][9];
        for (int r = 0; r < 9; ++r) {
            for (int c = 0; c < 9; ++c) {
                int index = indexOf(r, c);
                int value = initialValues[index];
                cells[r][c] = new Cell(r, c, value);
            }
        }
    }

    private void createGroups() {
        cellGroup = new CellGroup[3][3];
        for (int r = 0; r < 3; ++r) {
            for (int c = 0; c < 3; ++c) {
                cellGroup[r][c] = new CellGroup(r, c, cells);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public CellGroup getGroupForCell(Cell cell) {
        return cellGroup[cell.getRow() / 3][cell.getColumn() / 3];
    }

    public CellGroup getGroup(int groupRow, int groupCol) {
        return cellGroup[groupRow][groupCol];
    }

    public static int indexOf(int row, int col) {
        return row * 9 + col;
    }

    public void setValue(int row, int col, int value) {
        Cell cell = getCell(row, col);
        int oldValue = cell.getValue();

        if (oldValue == value) {
            return;
        }

        cell.setValue(value);
        // Maybe this resolves a conflict, so we check everything that could have conflicted.
        if (!cell.isValid()) {
            for (Cell c: this) {
                if (!c.isValid() && c.getValue() == oldValue) {
                    validate(c);
                }
            }
        }

        validate(cell);
    }

    public void validate(Cell cell) {
        int value = cell.getValue();
        boolean isValid = true;

        // Check if its inside the box.
        CellGroup group = getGroupForCell(cell);
        for (Cell c: group) {
            if (c.getValue() == value && c != cell) {
                c.setValid(false);
                isValid = false;
            }
        }

        Iterator<Cell> rowIter = rowIterator(cell.getRow());
        while (rowIter.hasNext()) {
            Cell c = rowIter.next();
            if (c.getValue() == value && c != cell) {
                c.setValid(false);
                isValid = false;
            }
        }

        Iterator<Cell> colIter = columnIterator(cell.getColumn());
        while (colIter.hasNext()) {
            Cell c = colIter.next();
            if (c.getValue() == value && c != cell) {
                c.setValid(false);
                isValid = false;
            }
        }

        cell.setValid(isValid);
    }

    public void removeRowCandidate(int row, int value) {
        Iterator<Cell> rowIter = rowIterator(row);
        while (rowIter.hasNext()) {
            Cell cell = rowIter.next();
            cell.removeCandidate(value);
        }
    }

    public void removeColumnCandidate(int col, int value) {
        Iterator<Cell> colIter = columnIterator(col);
        while (colIter.hasNext()) {
            Cell cell = colIter.next();
            cell.removeCandidate(value);
        }
    }

    @Override
    public Iterator<Cell> iterator() {
        return CellIterator.all(cells);
    }

    public Iterator<Cell> rowIterator(int row) {
        return CellIterator.row(cells, row);
    }

    public Iterator<Cell> columnIterator(int column) {
        return CellIterator.column(cells, column);
    }
}

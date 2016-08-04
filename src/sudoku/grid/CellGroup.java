package sudoku.grid;

import java.util.Iterator;

public class CellGroup implements Iterable<Cell> {
    private int rowGroup;
    private int colGroup;
    private Cell[][] cells;

    public CellGroup(int rowGroup, int colGroup, Cell[][] allCells) {
        this.rowGroup = rowGroup;
        this.colGroup = colGroup;
        this.cells = new Cell[3][3];

        fillCells(allCells);
    }

    private void fillCells(Cell[][] allCells) {
        int startRow = rowGroup * 3;
        int startCol = colGroup * 3;

        for (int r = 0; r < 3; ++r) {
            for (int c = 0; c < 3; ++c) {
                int row = startRow + r;
                int col = startCol + c;
                this.cells[r][c] = allCells[row][col];
            }
        }
    }

    public Cell getCell(int r, int c) {
        return cells[r][c];
    }

    public void removeCandidate(int value) {
        for (Cell cell: this) {
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

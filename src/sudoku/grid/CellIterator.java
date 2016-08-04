package sudoku.grid;

import java.util.Iterator;

public abstract class CellIterator implements Iterator<Cell> {

    protected Cell[][] cells;
    private CellIterator( Cell[][] cells) {
        this.cells = cells;
    }

    public static CellIterator all(Cell[][] cells) {
        return new AllIterator(cells);
    }

    public static CellIterator row(Cell[][] cells, int row) {
        return new RowIterator(cells, row);
    }

    public static CellIterator column(Cell[][] cells, int column) {
        return new ColumnIterator(cells, column);
    }

    private static class RowIterator extends CellIterator {
        private int row;
        private int columnIndex;

        private RowIterator(Cell[][] cells, int row) {
            super(cells);
            this.row = row;
            this.columnIndex = 0;
        }

        public boolean hasNext() {
            return columnIndex < cells[row].length;
        }

        public Cell next() {
            return cells[row][columnIndex++];
        }
    }

    private static class ColumnIterator extends CellIterator {
        private int column;
        private int rowIndex;

        private ColumnIterator(Cell[][] cells, int column) {
            super(cells);
            this.column = column;
            this.rowIndex = 0;
        }

        public boolean hasNext() {
            return rowIndex < cells.length;
        }

        public Cell next() {
            return cells[rowIndex++][column];
        }
    }

    private static class AllIterator extends CellIterator {
        private int columnIndex;
        private int rowIndex;

        private AllIterator(Cell[][] cells) {
            super(cells);
            this.columnIndex = 0;
            this.rowIndex = 0;
        }

        public boolean hasNext() {
            return rowIndex < cells.length;
        }

        public Cell next() {
            Cell cell = cells[rowIndex][columnIndex++];
            if (columnIndex >= cells[rowIndex].length) {
                columnIndex = 0;
                rowIndex++;
            }

            return cell;
        }
    }
}

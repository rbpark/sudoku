package sudoku.grid;

public class Cell {
    private final int row;
    private final int column;
    private int value;
    private int candidateFlag = -1;
    private boolean isOriginal;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    private boolean valid;

    public Cell(int row, int column) {
        this(row, column, 0);
    }

    public Cell(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.isOriginal = value != 0;
        this.valid = true;
        if (value != 0) {
            this.candidateFlag = 0;
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public void removeCandidate(int value) {
        // unsets the value
        int flag = 1 << (value - 1);
        candidateFlag &= ~flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (row != cell.row) return false;
        return column == cell.column;

    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }
}

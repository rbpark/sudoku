package sudoku.grid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by richardpark on 8/4/16.
 */
public class Solver {
    private Grid grid;

    public Solver(Grid grid) {
        this.grid = grid;
    }

    public List<Suggestion> findSuggestions() {
        ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();

        // Remove all immediate candidates, in the row, in the column and the group
        for (Cell cell: grid) {
            if (cell.getValue() != 0) {
                int value = cell.getValue();
                grid.removeColumnCandidate(cell.getColumn(), value);
                grid.removeRowCandidate(cell.getRow(), value);
                grid.getGroupForCell(cell).removeCandidate(value);
            }
        }

        return suggestions;
    }

    public static class Suggestion {
        private int row;
        private int column;
        private int suggestedValue;
        private int level;

        public Suggestion(int row, int column, int suggestedValue, int level) {
            this.row = row;
            this.column = column;
            this.level = level;
            this.suggestedValue = suggestedValue;
        }

        public int getSuggestedValue() {
            return suggestedValue;
        }

        public int getLevel() {
            return level;
        }

        public String toString() {
            return "Suggestion " + (row + 1) + "," + (column + 1) + "=" + suggestedValue+ " difficulty " + level;
        }
    }
}

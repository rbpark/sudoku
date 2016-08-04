package sudoku.grid;

import java.util.ArrayList;
import java.util.List;

public class SolverOld {
    private SimpleGrid grid;
    private int[] possibleFlags;
    private int[] setFlags;

    private ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();

    public SolverOld(SimpleGrid grid) {
        this.grid = grid;
        this.possibleFlags = new int[9*9];
        this.setFlags = new int[9*9];
    }

    public void setup() {
        if (!grid.getInvalidList().isEmpty()) {
            throw new RuntimeException("Grid has an error. Cannot run solver");
        }

        for (int i = 0; i < this.possibleFlags.length; ++i) {
            possibleFlags[i] = grid.getIndexVal(i) == 0 ? -1 : 0;
        }
    }

    public void runSimpleEliminator() {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                int val = grid.getValue(i, j);
                if (val != 0) {
                    invalidateRow(i, val);
                    invalidateColumn(j, val);
                    invalidateBox(i, j, val);
                }
            }
        }
    }

    public List<Suggestion> findSuggestions() {
        suggestions.clear();

        // Search each box for remainder answers
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                int colStart = j * 3;
                int rowStart = i * 3;

                int[] counter = new int[10];
                for (int r = rowStart; r < rowStart + 3; ++r) {
                    for (int c = colStart; c < colStart + 3; ++c) {
                        countFlags(r, c, counter);
                    }
                }

                // Look for cases where everything but one number has been eliminated
                for (int v = 1; v < counter.length; v++) {
                    if (counter[v] == 1) {
                        boolean isFound = false;
                        for (int r = rowStart; r < rowStart + 3; ++r) {
                            for (int c = colStart; c < colStart + 3; ++c) {
                                if (isFlagSet(r, c, v)) {
                                    isFound = true;
                                    // Simpliest of suggestion that requires not much inference.
                                    suggestions.add(new Suggestion(r, c, v, 1));
                                    break;
                                }
                            }
                            if (isFound) {
                                break;
                            }
                        }
                    }
                }
            }
        }

        return new ArrayList<Suggestion>(suggestions);
    }


    private void invalidateRow(int row, int value) {
        int flag = 1 << (value - 1);
        int inverse = ~flag;

        for (int j = 0; j < 9; ++j) {
            int index = SimpleGrid.calcIndex(row, j);
            possibleFlags[index] &= inverse;
            setFlags[index] |= flag;
        }
    }

    private void invalidateColumn(int col, int value) {
        int flag = 1 << (value - 1);
        int inverse = ~flag;

        for (int j = 0; j < 9; ++j) {
            int index = SimpleGrid.calcIndex(j, col);
            possibleFlags[index] &= inverse;
            setFlags[index] |= flag;
        }
    }

    private void invalidateBox(int row, int col, int value) {
        int flag = 1 << (value - 1);
        int inverse = ~flag;

        int rowStart = (row / 3) * 3; // floor of row
        int colStart = (col / 3) * 3; // floor of row

        for (int i = rowStart; i < rowStart + 3; ++i) {
            for (int j = colStart; j < colStart + 3; ++j) {
                int index = SimpleGrid.calcIndex(i, j);
                possibleFlags[index] &= inverse;
                setFlags[index] |= flag;
            }
        }
    }

    private boolean isFlagSet(int row, int col, int value) {
        int index = SimpleGrid.calcIndex(row, col);
        int flags = possibleFlags[index];
        return isFlagSet(flags, value);
    }

    private boolean isFlagSet(int flags, int value) {
        int valueFlag = 1 << (value - 1);
        return (flags & valueFlag) != 0;
    }

    private void countFlags(int row, int col, int[] counterArray) {
        int index = SimpleGrid.calcIndex(row, col);
        int flags = possibleFlags[index];

        if (flags == 0) {
            return;
        }

        if ((flags & 0x1) != 0) {
            counterArray[1]++;
        }
        if ((flags & 0x2) != 0) {
            counterArray[2]++;
        }
        if ((flags & 0x4) != 0) {
            counterArray[3]++;
        }
        if ((flags & 0x8) != 0) {
            counterArray[4]++;
        }
        if ((flags & 0x10) != 0) {
            counterArray[5]++;
        }
        if ((flags & 0x20) != 0) {
            counterArray[6]++;
        }
        if ((flags & 0x40) != 0) {
            counterArray[7]++;
        }
        if ((flags & 0x80) != 0) {
            counterArray[8]++;
        }
        if ((flags & 0x100) != 0) {
            counterArray[9]++;
        }
    }

    public String getFlagString(int row, int col) {
        StringBuilder builder = new StringBuilder();
        int index = SimpleGrid.calcIndex(row, col);
        int flags = possibleFlags[index];

        if (flags == 0) {
            builder.append(0);
        }
        else {
            int[] flagArray = new int[10];
            countFlags(row, col, flagArray);

            for (int i = 1; i < 10; ++i) {
                if (flagArray[i] != 0) {
                    builder.append(i).append(",");
                }
            }
        }

        return builder.toString();
    }

    public String getSetString(int row, int col) {
        StringBuilder builder = new StringBuilder();
        int index = SimpleGrid.calcIndex(row, col);
        int flags = setFlags[index];

        if (flags == 0) {
            builder.append(0);
        }
        else {
            if ((flags & 1) != 0) {
                builder.append(1).append(",");
            }
            if ((flags & 2) != 0) {
                builder.append(2).append(",");
            }
            if ((flags & 4) != 0) {
                builder.append(3).append(",");
            }
            if ((flags & 8) != 0) {
                builder.append(4).append(",");
            }
            if ((flags & 16) != 0) {
                builder.append(5).append(",");
            }
            if ((flags & 32) != 0) {
                builder.append(6).append(",");
            }
            if ((flags & 64) != 0) {
                builder.append(7).append(",");
            }
            if ((flags & 128) != 0) {
                builder.append(8).append(",");
            }
            if ((flags & 256) != 0) {
                builder.append(9).append(",");
            }
        }

        return builder.toString();
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

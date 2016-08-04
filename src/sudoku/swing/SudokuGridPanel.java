package sudoku.swing;

import sudoku.grid.Grid;

import javax.swing.*;
import java.awt.*;

public class SudokuGridPanel extends JPanel {
    private Grid grid;

    public SudokuGridPanel(Grid grid) {
        this.grid = grid;

        GridLayout layout = new GridLayout(3, 3);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(layout);
        setupPanels();
    }

    private void setupPanels() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                SudokuGroupPanel panel = new SudokuGroupPanel(grid.getGroup(r, c));
                this.add(panel);
            }
        }
    }
}

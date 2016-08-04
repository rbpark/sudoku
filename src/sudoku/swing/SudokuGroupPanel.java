package sudoku.swing;

import sudoku.grid.CellGroup;

import javax.swing.*;
import java.awt.*;

public class SudokuGroupPanel extends JPanel {
    private CellGroup group;

    public SudokuGroupPanel(CellGroup group) {
        this.group = group;

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        GridLayout layout = new GridLayout(3, 3);
        this.setLayout(layout);
        setupPanels();
    }

    private void setupPanels() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                SudokuButton button = new SudokuButton(group.getCell(r, c));
                this.add(button);
            }
        }
    }
}

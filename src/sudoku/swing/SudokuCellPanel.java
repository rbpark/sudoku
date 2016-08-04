package sudoku.swing;

import sudoku.grid.Cell;

import javax.swing.*;
import java.awt.*;

public class SudokuCellPanel extends JComponent {
    private Cell cell;
    private boolean selected = false;

    public SudokuCellPanel(Cell cell) {
        this.setFocusable(true);
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                g2d.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING));

        int width = this.getWidth();
        if (isSelected()) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, width);
        }

        if (cell.getValue() == 0) {
            return;
        }

        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
        if (!cell.isValid()) {
            g2d.setColor(Color.RED);
        }
        else if (!cell.isOriginal()) {
            g2d.setColor(Color.BLUE);
        }
        else {
            g2d.setColor(Color.BLACK);
        }
        g2d.setFont(font);
        g2d.drawString("" + cell.getValue(), width/2, width/2);
        g2d.setColor(Color.BLACK);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

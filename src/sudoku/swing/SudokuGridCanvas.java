package sudoku.swing;

import sudoku.grid.Cell;
import sudoku.grid.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuGridCanvas extends JComponent {
    private Grid grid;
    private int borderThickness = 4;
    private SudokuCellPanel[][] panels = new SudokuCellPanel[9][9];
    private SudokuCellPanel selected = null;

    public SudokuGridCanvas(Grid grid) {
        super();
        this.grid = grid;
        this.setPreferredSize(new Dimension(400, 400));

        // Absolute positioning
        this.setLayout(null);
        setupCells();
        resizeCells();
        this.addKeyListener(new KeyPressListener());

    }

    public Grid getGrid() {
        return grid;
    }

    private void setupCells() {
        MouseClickAdaptor listener = new MouseClickAdaptor();
        KeyPressListener keyListener = new KeyPressListener();
        for (int r = 0; r < 9; ++r) {
            for (int c = 0; c < 9; ++c) {
                panels[r][c] = new SudokuCellPanel(grid.getCell(r, c));
                this.add(panels[r][c]);
                panels[r][c].addMouseListener(listener);
                panels[r][c].addKeyListener(keyListener);
            }
        }
    }

    private void resizeCells() {
        int width = this.getWidth();
        int height = this.getHeight();
        int dimension = Math.min(width, height);
        int cellDim = dimension / 9;

        for (int r = 0; r < 9; ++r) {
            int y = (dimension * r)/9;
            for (int c = 0; c < 9; ++c) {
                int x = (dimension * c)/9;
                panels[r][c].setBounds(x, y, cellDim, cellDim);
            }
        }
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        resizeCells();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width = this.getWidth();
        int height = this.getHeight();

        int minDimension = Math.min(width, height);

        Graphics2D g2d = (Graphics2D)g;
        drawBorder(g2d, minDimension);

        drawGridLines(g2d, minDimension);
    }

    public void assignValueToSelected(int value) {
        if (selected != null) {
            Cell cell = selected.getCell();
            if (!cell.isOriginal()) {
                grid.setValue(cell.getRow(), cell.getColumn(), value);
                repaint();
            }
        }
    }

    private void drawBorder(Graphics2D g, int dimension) {
        g.fillRect(0, 0, dimension, borderThickness); // top
        g.fillRect(0, dimension - borderThickness, dimension, borderThickness); // bottom
        g.fillRect(0, 0, borderThickness, dimension);
        g.fillRect(dimension - borderThickness, 0, borderThickness, dimension);
    }

    private void drawGridLines(Graphics2D g, int dimension) {
        for (int i = 1; i < 9; ++i) {
            // We do this because minD/9 * i will have precision loss
            int position = (dimension * i)/9;
            int thickness = 1;

            if (i % 3 == 0) {
                g.fillRect(0, position - 1, dimension, 2);
                g.fillRect(position - 1, 0, 2, dimension);
            }
            else {
                g.drawLine(0, position, dimension, position);
                g.drawLine(position, 0, position, dimension);
            }
        }
    }

    private class MouseClickAdaptor extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Object source = e.getSource();
            if (source instanceof SudokuCellPanel) {
                SudokuCellPanel panel = (SudokuCellPanel)source;
                if (selected != null) {
                    selected.setSelected(false);
                    selected.repaint();
                }
                panel.setSelected(true);
                selected = panel;
                panel.repaint();
            }
        }
    }

    private class KeyPressListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            char c = e.getKeyChar();
            int value = c - '0';
            if (value >= 0 && value < 10) {
                assignValueToSelected(value);
            }
            else if (e.getKeyCode() == 8) {
                assignValueToSelected(0);
            }
        }
    }
}

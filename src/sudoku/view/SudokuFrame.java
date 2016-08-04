package sudoku.view;

import sudoku.grid.SimpleGrid;
import sudoku.grid.SolverOld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuFrame extends JFrame implements ActionListener {
    private SimpleGrid grid;
    private SolverOld solver;
    private SudokuButton[][] buttons;

    public SudokuFrame(SimpleGrid grid, SolverOld solver) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.grid = grid;
        this.solver = solver;

        GridLayout layout = new GridLayout(9, 9);
        this.setLayout(layout);
        layout.setVgap(1);
        layout.setHgap(1);

        buttons = new SudokuButton[9][];

        for (int i = 0; i < 9; ++i) {
            buttons[i] = new SudokuButton[9];
            for (int j = 0; j < 9; ++j) {

                buttons[i][j] = new SudokuButton(i, j, grid.getValue(i, j));
                buttons[i][j].addActionListener(this);
                //layout.addLayoutComponent("" + i +"," +"j", buttons[i][j]);
                this.add(buttons[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SudokuButton b = (SudokuButton)e.getSource();
        String flags = solver.getFlagString(b.getRow(), b.getColumn());
        String set = solver.getSetString(b.getRow(), b.getColumn());
        System.out.println("Possible: " + flags + " Set:" + set);
    }
}

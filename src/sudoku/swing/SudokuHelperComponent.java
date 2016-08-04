package sudoku.swing;

import sudoku.grid.Solver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by richardpark on 8/5/16.
 */
public class SudokuHelperComponent extends JComponent {

    private SudokuGridCanvas canvas;

    public SudokuHelperComponent(SudokuGridCanvas gridCanvas) {
        super();
        this.setLayout(new FlowLayout());
        addAllButtons();
        canvas = gridCanvas;
        this.setPreferredSize(new Dimension(400, 30));

        Solver solver = new Solver(canvas.getGrid());
//        solver.findSuggestions();
    }

    private void addAllButtons() {
        ClickListener action = new ClickListener();
        add(new IDButton("0", 0, action));
        add(new IDButton("1", 1, action));
        add(new IDButton("2", 2, action));
        add(new IDButton("3", 3, action));
        add(new IDButton("4", 4, action));
        add(new IDButton("5", 5, action));
        add(new IDButton("6", 6, action));
        add(new IDButton("7", 7, action));
        add(new IDButton("8", 8, action));
        add(new IDButton("9", 9, action));
        add(new IDButton("?", -1, action));
    }

    private class IDButton extends JButton {
        private int id;
        public IDButton(String text, int id, ClickListener listener) {
            super();
            this.id = id;
            this.setText(text);
            this.setSize(20, 20);
            this.setPreferredSize(new Dimension(20, 20));
            this.addActionListener(listener);
        }

        public int getId() {
            return id;
        }
    }

    private class ClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            IDButton button = (IDButton)e.getSource();
            int id = button.getId();
            if (id != -1) {
                canvas.assignValueToSelected(id);
            }
        }
    }
}

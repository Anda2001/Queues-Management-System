package ro.tuc.pt.GUI;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;

public class SimulationFrame extends JFrame {
    private final JTextArea textArea;
    Font myFont = new Font("Times New Roman", Font.PLAIN, 22);

    public SimulationFrame(String title) {
        setTitle(title);
        setSize(700, 700);
        setLocation(600, 100);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setSize(700,800);
        textArea.setBackground(pink);
        textArea.setForeground(black);
        textArea.setEditable(false);
        textArea.setFont(myFont);
        textArea.setLineWrap(false);
        textArea.setVisible(true);

        JScrollPane scroll = new JScrollPane (textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.add(scroll);

        this.setVisible(true);

    }

    public void updatedFrame(String s) {

        Worker myWorker = new Worker(s, textArea);
        myWorker.execute();
    }

}

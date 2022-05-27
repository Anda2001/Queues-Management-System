package ro.tuc.pt;


import ro.tuc.pt.GUI.Controller;
import ro.tuc.pt.GUI.SimulationFrame;
import ro.tuc.pt.GUI.View;

import javax.swing.*;

public class App
{
    public static void main( String[] args )
    {

        View frame = new View("Queues Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Controller controller=new Controller(frame);


    }
}

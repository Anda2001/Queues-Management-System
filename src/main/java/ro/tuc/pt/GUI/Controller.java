package ro.tuc.pt.GUI;

import ro.tuc.pt.logic.SelectionPolicy;
import ro.tuc.pt.logic.SimulationManager;


import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;

public class Controller {
    private final View view;
    private int id=0;

    public Controller(View view) {
        this.view = view;
        this.view.startListener(new SimulationStartListener());

    }

    class SimulationStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Integer simulationTime = Integer.parseInt(view.getSimulationTime());
            Integer numberOfServers = Integer.parseInt(view.getNumberOfQueues());
            Integer numberOfClients = Integer.parseInt(view.getNumberOfClients());
            Integer minArrivalTime = Integer.parseInt(view.getArrivalMin());
            Integer maxArrivalTime = Integer.parseInt(view.getArrivalMax());
            Integer minProcessingTime = Integer.parseInt(view.getServiceMin());
            Integer maxProcessingTime = Integer.parseInt(view.getServiceMax());
            SelectionPolicy selectionPolicy=view.getStrategy();

            if (maxArrivalTime < minArrivalTime || maxProcessingTime < minProcessingTime)
                JOptionPane.showMessageDialog(view, "Input incorrect data!");
            else {

                SimulationFrame view = new SimulationFrame("Queues simulation");
                PrintWriter pw=null;
                SimulationManager simulationManager = new SimulationManager(view, simulationTime, numberOfServers, numberOfClients, minArrivalTime, maxArrivalTime, minProcessingTime, maxProcessingTime, selectionPolicy, pw);
                Thread T = new Thread(simulationManager);
                T.start();
            }
        }
    }
}

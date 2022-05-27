package ro.tuc.pt.GUI;

import ro.tuc.pt.logic.SelectionPolicy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static java.awt.Color.*;

public class View extends JFrame {
    private JPanel contentPane;
    private JTextField numberOfClientsTextField;
    private JTextField numberOfQueuesTextField;
    private JTextField simulationTimeTextField;
    private JTextField arrivalMinTextField;
    private JTextField arrivalMaxTextField;
    private JTextField serviceMinTextField;
    private JTextField serviceMaxTextField;
    private JComboBox strategyComboBox;
    JButton startSimulationButton;
    Font myFont = new Font("Times New Roman", Font.PLAIN, 22);


    public View(String name) {
        super(name);
        this.setLayout(null);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(900, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel();
        this.contentPane.setLayout(null);
        this.contentPane.setBackground(BLACK);
        this.prepareManagementPanel();
        this.setContentPane(this.contentPane);
        this.setVisible(true);
    }

    private void prepareManagementPanel() {
        JPanel queuesPanel = new JPanel();

        queuesPanel.setLayout(null);
        queuesPanel.setBounds(10,10,860,445);

        //number of clients label + text
        JLabel numberOfClientsLabel = new JLabel("Number of clients: ");
        numberOfClientsLabel.setBounds(20, 50, 210, 30);
        numberOfClientsLabel.setFont(myFont);
        queuesPanel.setBackground(PINK);
        queuesPanel.add(numberOfClientsLabel);

        this.numberOfClientsTextField = new JTextField();
        this.numberOfClientsTextField.setBounds(230, 50, 40, 30);
        this.numberOfClientsTextField.setBackground(LIGHT_GRAY);
        queuesPanel.add(this.numberOfClientsTextField);


        //queues number label + text
        JLabel numberOfQueuesLabel = new JLabel("Number of queues: ");
        numberOfQueuesLabel.setFont(myFont);
        numberOfQueuesLabel.setBounds(20, 100, 210, 30);
        queuesPanel.add(numberOfQueuesLabel);

        this.numberOfQueuesTextField = new JTextField();
        this.numberOfQueuesTextField.setBackground(LIGHT_GRAY);
        this.numberOfQueuesTextField.setBounds(230,100 , 40, 30);
        queuesPanel.add(this.numberOfQueuesTextField);


        //simulation interval
        JLabel simulationIntervalLabel= new JLabel("Simulation interval: ");
        simulationIntervalLabel.setFont(myFont);
        simulationIntervalLabel.setBounds(20, 150, 210, 30);
        queuesPanel.add(simulationIntervalLabel);


        this.simulationTimeTextField = new JTextField();
        this.simulationTimeTextField.setBackground(LIGHT_GRAY);
        this.simulationTimeTextField.setBounds(250,150 , 40, 30);
        queuesPanel.add(this.simulationTimeTextField);


        JLabel arrivalTimeLabel= new JLabel("Arrival time: ");
        arrivalTimeLabel.setFont(myFont);
        arrivalTimeLabel.setBounds(20, 200, 210, 30);
        queuesPanel.add(arrivalTimeLabel);

        JLabel arrivalMinLabel= new JLabel("min: ");
        arrivalMinLabel.setFont(myFont);
        arrivalMinLabel.setBounds(230, 200, 50, 30);
        queuesPanel.add(arrivalMinLabel);

        this.arrivalMinTextField = new JTextField();
        this.arrivalMinTextField.setBackground(LIGHT_GRAY);
        this.arrivalMinTextField.setBounds(290,200 , 40, 30);
        queuesPanel.add(this.arrivalMinTextField);

        JLabel arrivalMaxLabel= new JLabel("max: ");
        arrivalMaxLabel.setFont(myFont);
        arrivalMaxLabel.setBounds(360, 200, 70, 30);
        queuesPanel.add(arrivalMaxLabel);

        this.arrivalMaxTextField = new JTextField();
        this.arrivalMaxTextField.setBackground(LIGHT_GRAY);
        this.arrivalMaxTextField.setBounds(420,200, 40, 30);
        queuesPanel.add(this.arrivalMaxTextField);

        //service time
        JLabel serviceTimeLabel= new JLabel("Service time: ");
        serviceTimeLabel.setFont(myFont);
        serviceTimeLabel.setBounds(20, 250, 210, 30);
        queuesPanel.add(serviceTimeLabel);

        JLabel serviceMinLabel= new JLabel("min: ");
        serviceMinLabel.setFont(myFont);
        serviceMinLabel.setBounds(230, 250, 50, 30);
        queuesPanel.add(serviceMinLabel);

        this.serviceMinTextField = new JTextField();
        this.serviceMinTextField.setBackground(LIGHT_GRAY);
        this.serviceMinTextField.setBounds(290,250 , 40, 30);
        queuesPanel.add(this.serviceMinTextField);

        JLabel serviceMaxLabel= new JLabel("max: ");
        serviceMaxLabel.setFont(myFont);
        serviceMaxLabel.setBounds(360, 250, 70, 30);
        queuesPanel.add(serviceMaxLabel);

        this.serviceMaxTextField = new JTextField();
        this.serviceMaxTextField.setBackground(LIGHT_GRAY);
        this.serviceMaxTextField.setBounds(420,250, 40, 30);
        queuesPanel.add(this.serviceMaxTextField);

        //strategy
        JLabel strategySelectionLabel= new JLabel("Select strategy: ");
        strategySelectionLabel.setFont(myFont);
        strategySelectionLabel.setBounds(20, 300, 150, 30);
        queuesPanel.add(strategySelectionLabel);

        String[] strategies = new String[]{"SHORTEST_QUEUE", "SHORTEST_TIME"};
        this.strategyComboBox= new JComboBox(strategies);
        this.strategyComboBox.setFont(myFont);
        this.strategyComboBox.setBounds(180, 300, 250, 30);
        this.strategyComboBox.setBackground(lightGray);
        queuesPanel.add(this.strategyComboBox);

        startSimulationButton = new JButton("Start \n Simulation");
        startSimulationButton.setFont(myFont);
        startSimulationButton.setBounds(550, 80, 250, 150);
        startSimulationButton.setActionCommand("Start");
        //startSimulationButton.addActionListener(this.);
        queuesPanel.add(startSimulationButton);

        this.contentPane.add(queuesPanel); //add everything to content pane
    }

    public String getNumberOfClients() {
        return numberOfClientsTextField.getText();
    }

    public String getNumberOfQueues() {
        return numberOfQueuesTextField.getText();
    }

    public String getSimulationTime() {
        return simulationTimeTextField.getText();
    }


    public String getArrivalMin() {
        return arrivalMinTextField.getText();
    }

    public String getArrivalMax() {
        return arrivalMaxTextField.getText();
    }

    public String getServiceMin() {
        return serviceMinTextField.getText();
    }

    public String getServiceMax() {
        return serviceMaxTextField.getText();
    }

    public SelectionPolicy getStrategy(){
        if(strategyComboBox.toString().equals("SHORTEST_QUEUE")){
            return SelectionPolicy.SHORTEST_QUEUE;
        } else{
            return SelectionPolicy.SHORTEST_TIME;
        }
    }

    public void startListener(ActionListener listenForStart) {
        startSimulationButton.addActionListener(listenForStart);
    }
}

package ro.tuc.pt.logic;

import ro.tuc.pt.model.Server;
import ro.tuc.pt.model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private final List<Server> servers = new ArrayList<>();
    private Strategy strategy;


    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        strategy = new TimeStrategy();
        List<Thread> serversThreads = new ArrayList<>();
        for(int i=0;i<maxNoServers;i++){

            Server s= new Server(maxTasksPerServer);
            servers.add(s);
            Thread t= new Thread(s);
            serversThreads.add(t);
            t.start();

        }

    }

    public void changeStrategy( SelectionPolicy selectionPolicy){
        if(selectionPolicy==SelectionPolicy.SHORTEST_QUEUE){
            strategy=new ShortestQueueStrategy();
        }
        if(selectionPolicy==SelectionPolicy.SHORTEST_TIME){
            strategy=new TimeStrategy();
        }
    }

    public int dispatchTask(Task task){
        return  strategy.addTask(getServers(), task);
    }

    public List<Server> getServers() {
        return servers;
    }


}

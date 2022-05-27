package ro.tuc.pt.logic;

import ro.tuc.pt.model.Server;
import ro.tuc.pt.model.Task;

import java.util.List;

public class TimeStrategy implements Strategy{


    @Override
    public int addTask(List<Server> servers, Task task) {
        Server minNumberOfClients = servers.get(0);
        for(Server tmp : servers) {
            if(tmp.getClients().length < minNumberOfClients.getClients().length) {
                minNumberOfClients = tmp;
            }
        }

        minNumberOfClients.addTask(task);
        return  minNumberOfClients.getWaitingPeriod().get() - task.getServiceTime();
    }
}


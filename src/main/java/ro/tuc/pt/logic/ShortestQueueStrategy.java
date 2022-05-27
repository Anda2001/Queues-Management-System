package ro.tuc.pt.logic;

import ro.tuc.pt.model.Server;
import ro.tuc.pt.model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {

    @Override
    public int addTask(List<Server> servers, Task task) {
        Server minNumberOfTasks = servers.get(0);

        for(Server server : servers) {
            if(server.getTasks().size() < minNumberOfTasks.getTasks().size()) {
                minNumberOfTasks = server;
            }
        }
        minNumberOfTasks.addTask(task);

        return  minNumberOfTasks.getWaitingPeriod().get() - task.getServiceTime();
    }
}

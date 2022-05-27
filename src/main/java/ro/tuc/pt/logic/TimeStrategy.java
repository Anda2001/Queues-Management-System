package ro.tuc.pt.logic;

import ro.tuc.pt.model.Server;
import ro.tuc.pt.model.Task;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeStrategy implements Strategy{

    @Override
    public int addTask(List<Server> servers, Task task) {

        AtomicInteger minTime = new AtomicInteger(Integer.MAX_VALUE);
        Server serverMinWaitingTime = null;
        for (Server server : servers) {
            if (minTime.intValue() > server.getWaitingTime()) {
                minTime.set(server.getWaitingPeriod().intValue());
                serverMinWaitingTime = server;
            }
        }

        assert serverMinWaitingTime != null;
        serverMinWaitingTime.addTask(task);
        AtomicInteger time=new AtomicInteger();
        time.addAndGet(task.getServiceTime()+ serverMinWaitingTime.getWaitingPeriod().intValue());
        serverMinWaitingTime.setWaitingPeriod(time);
        return serverMinWaitingTime.getWaitingPeriod().intValue()-task.getServiceTime();
    }
}


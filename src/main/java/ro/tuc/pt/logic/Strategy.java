package ro.tuc.pt.logic;

import ro.tuc.pt.model.Server;
import ro.tuc.pt.model.Task;

import java.util.List;

public interface Strategy {
    public int addTask(List<Server> servers, Task task);
}

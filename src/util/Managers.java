package util;

import manager.TaskManager;
import manager.HistoryManager;
import manager.impl.InMemoryTaskManager;
import manager.impl.InMemoryHistoryManager;

public class Managers {
    private Managers() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

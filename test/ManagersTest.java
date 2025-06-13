import manager.HistoryManager;
import manager.TaskManager;
import manager.impl.InMemoryHistoryManager;
import manager.impl.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import util.Managers;

import static org.junit.jupiter.api.Assertions.*;

public class ManagersTest {
    @Test
    public void getDefaultNotNull() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager);
        assertTrue(taskManager instanceof InMemoryTaskManager);
    }

    @Test
    public void getDefaultHistoryNotNull() {
        HistoryManager histManager = Managers.getDefaultHistory();
        assertNotNull(histManager);
        assertTrue(histManager instanceof InMemoryHistoryManager);
    }
}

import manager.impl.InMemoryHistoryManager;
import model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager histManager;

    @BeforeEach
    public void beforeEach() {
        histManager = new InMemoryHistoryManager();
    }

    @Test
    public void addAndGetHistoryOrderAndLimit() {
        // добавим 12 задач
        for (int i = 1; i <= 12; i++) {
            Task t = new Task("Таск" + i, "Описание таска");
            t.setId(i);
            histManager.add(t);
        }
        List<Task> hist = histManager.getHistory();

        assertEquals(10, hist.size());
        assertEquals("Таск3", hist.get(0).getTitle());
        assertEquals("Таск12", hist.get(9).getTitle());
    }

    @Test
    public void getHistoryEmptyInitially() {
        assertTrue(histManager.getHistory().isEmpty());
    }
}

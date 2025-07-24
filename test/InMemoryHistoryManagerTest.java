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
    public void getHistoryEmptyInitially() {
        assertTrue(histManager.getHistory().isEmpty(), "История изначально должна быть пустая");
    }

    @Test
    public void addAndGetHistoryOrder() {
        for (int i = 1; i <= 12; i++) {
            Task t = new Task("Таск" + i, "Описание");
            t.setId(i);
            histManager.add(t);
        }

        List<Task> hist = histManager.getHistory();
        assertEquals(12, hist.size(), "У истории нет ограничений, должно быть 12 элементов");
        assertEquals("Таск1", hist.get(0).getTitle());
        assertEquals("Таск12", hist.get(11).getTitle());
    }

    @Test
    public void addDuplicateMovesToEnd() {
        Task t1 = new Task("A", "x");
        Task t2 = new Task("B", "x");
        t1.setId(1);
        t2.setId(2);

        histManager.add(t1);
        histManager.add(t2);
        histManager.add(t1);

        List<Task> hist = histManager.getHistory();
        assertEquals(2, hist.size());

        assertEquals(2, hist.get(0).getId());
        assertEquals(1, hist.get(1).getId());
    }

    @Test
    public void removeFromMiddleAndEdges() {
        Task t1 = new Task("A", "x");
        Task t2 = new Task("B", "x");
        Task t3 = new Task("C", "x");
        t1.setId(1); t2.setId(2); t3.setId(3);

        histManager.add(t1);
        histManager.add(t2);
        histManager.add(t3);

        histManager.remove(2);

        List<Task> hist = histManager.getHistory();
        assertEquals(List.of(t1, t3), hist);

        histManager.remove(1);
        hist = histManager.getHistory();
        assertEquals( List.of(t3), hist);

        histManager.remove(3);
        assertTrue(histManager.getHistory().isEmpty());
    }
}

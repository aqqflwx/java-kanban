import manager.impl.InMemoryTaskManager;
import model.Task;
import model.TypeStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {

    private InMemoryTaskManager manager;

    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryTaskManager();
    }

    @Test
    public void createAndGetTask() {
        Task t = new Task("Таск1", "Описание1");
        manager.createTask(t);
        int id = t.getId();

        Task found = manager.getByIdTask(id);
        assertNotNull(found);
        assertEquals("Таск1", found.getTitle());
    }

    @Test
    public void generatedIdsAreUnique() {
        Task a = new Task("Таск1", "Описание1");
        Task b = new Task("Таск2", "Описание2");
        manager.createTask(a);
        manager.createTask(b);
        assertNotEquals(a.getId(), b.getId());
    }

    @Test
    public void taskFieldsUnchangedAfterAdd() {
        Task t = new Task("Таск", "Описание");
        manager.createTask(t);
        Task stored = manager.getTasks().get(t.getId());
        assertEquals("Таск", stored.getTitle());
        assertEquals("Описание", stored.getDescription());
        assertEquals(TypeStatus.NEW, stored.getStatus());
    }
}

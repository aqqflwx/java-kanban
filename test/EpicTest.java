import model.Epic;
import model.Subtask;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {
    @Test
    public void equalsSameId() {
        Epic e1 = new Epic("Первый эпик", "Описание первого эпика");
        Epic e2 = new Epic("Второй эпик", "Описание второго эпика");
        e1.setId(100);
        e2.setId(100);

        assertEquals(e1, e2);
    }

    @Test
    public void cannotAddSelfAsSubtask() {
        Epic epic = new Epic("Тест. эпик", "Описание тест. эпика");
        epic.setId(7);

        Subtask st = new Subtask("Подзадача", "Описание подзадачи", epic.getId());
        st.setId(epic.getId());
        epic.addSubtask(st);

        assertFalse(epic.getSubtasks().contains(epic.getId()),
                "Эпик не должен содержать себя в списке подзадач");
    }
}
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SubtaskTest {
    @Test
    public void equalsSameId() {
        Subtask s1 = new Subtask("Сабтаск1", "Описание1", 1);
        Subtask s2 = new Subtask("Сабтаск2", "Описание2", 2);
        s1.setId(55);
        s2.setId(55);

        assertEquals(s1, s2);
    }

    @Test
    public void cannotBeOwnEpic() {
        Subtask sub = new Subtask("Сабтаск", "Описание", 999);
        sub.setId(999);
        assertNotEquals(sub.getIdEpic(), sub.getId(),
                "Subtask не должен быть своим же эпиком");
    }
}

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    public void equalsSameId() {
        Task t1 = new Task("Таск1", "Описание1");
        Task t2 = new Task("Таск2", "Описание2");
        t1.setId(3);
        t2.setId(3);

        assertEquals(t1, t2, "Два Task с одинаковым id должны быть равны");
        assertEquals(t1.hashCode(), t2.hashCode(), "hashCode тоже должны совпадать");
    }

    @Test
    public void notEqualsDifferentId() {
        Task t1 = new Task("Таск", "Описание");
        Task t2 = new Task("Таск", "Описание");
        t1.setId(1);
        t2.setId(2);

        assertNotEquals(t1, t2);
    }
}

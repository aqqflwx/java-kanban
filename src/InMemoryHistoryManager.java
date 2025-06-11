import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        history.add(task);
        if (history.size() > 10) {
            history.removeFirst(); // IDEA предложила заменить remove(0) на removeFirst()
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}

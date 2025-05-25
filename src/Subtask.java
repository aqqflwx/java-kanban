public class Subtask extends Task {
    private int idEpic;

    public Subtask(int id, String title, String description, int idEpic) {
        super(id, title, description);
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

    public Subtask update(Subtask subtask) {
        Subtask s = (Subtask) super.update(subtask);
        s.idEpic = subtask.getIdEpic();
        return s;
    }

}

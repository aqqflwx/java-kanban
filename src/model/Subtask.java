package model;

public class Subtask extends Task {

    private final int idEpic;

    public Subtask(String title, String description, int idEpic) {
        super(title, description);
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

    @Override
    public void setId(int id) {
        if (id != this.idEpic) {
            super.setId(id);
        }
    }
}

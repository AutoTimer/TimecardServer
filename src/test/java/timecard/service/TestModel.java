package timecard.service;

public class TestModel {
    private String id;

    public TestModel(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}

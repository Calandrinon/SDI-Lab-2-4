package Model;

public class BaseEntity<ID> {
    private final ID id;

    public BaseEntity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}

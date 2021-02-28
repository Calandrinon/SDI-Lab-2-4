package Model;

public class BaseEntity<ID> {
    private ID id;

    /**
     *
     * @return the id of a BaseEntity
     */
    public ID getId() {
        return id;
    }

    /**
     *
     * @param id - the new id for a BaseEntity
     */
    public void setId(ID id) {
        this.id = id;
    }

    /**
     *
     * @return the string of a BaseEntity
     */
    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }


}

package Model;

import java.util.function.Function;

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

    public static <ID> String fileWriter(BaseEntity<ID> baseEntity)
    {
        return String.valueOf(baseEntity.getId());
    }

    public static <ID> BaseEntity<ID> fileReader(String line)
    {
        return new BaseEntity<>();
    }
}

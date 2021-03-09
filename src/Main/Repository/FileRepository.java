package Repository;

import Exceptions.ValidationException;
import Model.BaseEntity;
import Validator.Validator;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class FileRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T>{
    private Map<ID, T> entities;
    private Validator<T> validator;
    private final String file_path;

    public FileRepository(Validator<T> validator, String file_path) {
        this.validator = validator;
        this.file_path = file_path;
        entities = new HashMap<>();
    }

    private void writeAll() throws IOException {
        PrintWriter repo_file = new PrintWriter(new BufferedWriter(new FileWriter(file_path, false)));
        entities.forEach((key, value) -> repo_file.println(T.fileWriter(value)));
    }

    private void readAll() throws FileNotFoundException {
        File file = new File(this.file_path);
        Scanner scanner = new Scanner(file);
        entities.clear();
        String line = scanner.nextLine();
        while(line != null)
        {
            T entity = (T) T.fileReader(line);
            entities.put(entity.getId(), entity);
            line = scanner.nextLine();
        }
    }


    @Override
    public Optional<T> findOne(ID id) {
        return Optional.empty();
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Optional<T> save(T entity) throws ValidationException {
        return Optional.empty();
    }

    @Override
    public Optional<T> delete(ID id) {
        return Optional.empty();
    }

    @Override
    public Optional<T> update(T entity) throws ValidationException {
        return Optional.empty();
    }
}

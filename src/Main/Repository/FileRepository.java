package Repository;

import Exceptions.ValidationException;
import Model.BaseEntity;
import Validator.Validator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class FileRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    private Map<ID, T> entities;
    private Validator<T> validator;
    private final String file_path;
    private final Function reader;
    private final Function writer;

    public FileRepository(Validator<T> validator, String file_path, Function reader, Function writer) {
        this.validator = validator;
        this.file_path = file_path;
        this.reader = reader;
        this.writer = writer;
        entities = new HashMap<>();
    }

    private void writeAll(){
        Path path = Paths.get("src/Main/Repository/RepositoryFiles");
        path = path.resolve(file_path);
        try{
            if(!Files.exists(path))
                Files.createFile(path);
            BufferedWriter bufferedWriter = Files.newBufferedWriter(path, Charset.defaultCharset());
            entities.forEach((key, value) -> {
                try {
                    bufferedWriter.write((String) writer.apply(value));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readAll(){
        entities.clear();
        Path path = Paths.get("src/Main/Repository/RepositoryFiles");
        path = path.resolve(file_path);
        if(!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(Stream<String> stream = Files.lines(path)){
            stream.map(p -> (T)reader.apply(p)).forEach(p -> entities.put(p.getId(), p));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Optional<T> findOne(ID id) {
        this.readAll();
        Optional.ofNullable(id).orElseThrow(() -> new IllegalArgumentException("id must not be null"));
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        this.readAll();
        return new HashSet<>(entities.values());
    }

    @Override
    public Optional<T> save(T entity) throws ValidationException {
        this.readAll();
        Optional.ofNullable(entity).orElseThrow(() -> new ValidationException("entity must not be null"));
        validator.validate(entity);
        Optional<T> optionalT = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        this.writeAll();
        return optionalT;
    }

    @Override
    public Optional<T> delete(ID id) {
        this.readAll();
        Optional.ofNullable(id).orElseThrow(() -> new IllegalArgumentException("id must not be null"));
        Optional<T> optionalT = Optional.ofNullable(entities.remove(id));
        this.writeAll();
        return optionalT;
    }

    @Override
    public Optional<T> update(T entity) throws ValidationException {
        Optional.ofNullable(entity).orElseThrow(() -> new ValidationException("the entity cannot be null"));
        validator.validate(entity);
        Optional<T> optionalT = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
        this.writeAll();
        return optionalT;
    }
}

package Repo.FIleRepos;

import Domain.Student;
import Domain.Validators.DuplicateException;
import Domain.Validators.NoEntityStored;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repo.InMemRepo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StudentFileRepo extends InMemRepo<Long, Student> {

    private String fileName;

    public StudentFileRepo(Validator<Student> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);
        try{
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String serialNumber = items.get(1);
                String name = items.get((2));
                int group = Integer.parseInt(items.get(3));

                Student student = new Student(serialNumber, name, group);
                student.setId(id);

                try {
                    super.save(student);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void updateData(){
        super.findAll().forEach(student -> saveToFile(student));
    }

    @Override
    public Optional<Student> save(Student entity) throws ValidatorException {
        if(findOne(entity.getId()).isPresent()){
            throw new DuplicateException("There can't be two students with the same id!");
        }
        Optional<Student> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Student entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getSerialNumber() + "," + entity.getName() + "," + entity.getGroup());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<Student> updateFile(Optional<Student> optional){
        if(optional.isPresent()) {
            try{
                FileWriter fw = new FileWriter(fileName ,false);
                fw.close();
            } catch (IOException ex){
                ex.printStackTrace();
            }
            updateData();
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Student> delete(Long id) {
        Optional<Student> optional = super.delete(id);
        if(findOne(id).isPresent()){
            throw new NoEntityStored("Student does not exist in the database!");
        }
        return updateFile(optional);
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException {
        Optional<Student> optional = super.update(entity);
        if(!findOne(entity.getId()).isPresent()){
            throw new NoEntityStored("Student does not exist in the database!");
        }
        return updateFile(optional);
    }


}

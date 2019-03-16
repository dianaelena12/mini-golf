package Repo.FIleRepos;

import Domain.Assignment;
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

public class AssignmentFileRepo extends InMemRepo<Long, Assignment> {
    private String fileName;

    public AssignmentFileRepo(Validator<Assignment> validator, String fileName) {
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
                Long studentID = Long.valueOf(items.get(1));
                Long problemID = Long.valueOf(items.get(2));
                int grade = Integer.parseInt(items.get(3));

                Assignment Assignment = new Assignment(studentID, problemID, grade);
                Assignment.setId(id);

                try {
                    super.save(Assignment);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void updateData(){
        super.findAll().forEach(Assignment -> saveToFile(Assignment));
    }

    @Override
    public Optional<Assignment> save(Assignment entity) throws ValidatorException {
        if(findOne(entity.getId()).isPresent()){
            throw new DuplicateException("There can't be two assignments with the same id!");
        }
        Optional<Assignment> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Assignment entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getStudentID()+ "," + entity.getProblemID() + "," + entity.getGrade());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<Assignment> updateFile(Optional<Assignment> optional){
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
    public Optional<Assignment> delete(Long id) {
        Optional<Assignment> optional = super.delete(id);
        if(findOne(id).isPresent()){
            throw new NoEntityStored("Assignment does not exist in the database!");
        }
        return updateFile(optional);
    }

    @Override
    public Optional<Assignment> update(Assignment entity) throws ValidatorException {
        Optional<Assignment> optional = super.update(entity);
        if(!findOne(entity.getId()).isPresent()){
            throw new NoEntityStored("Assignment does not exist in the database!");
        }
        return updateFile(optional);
    }
}

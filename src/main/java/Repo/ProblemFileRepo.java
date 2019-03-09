package Repo;

import Domain.Problem;
import Domain.Validators.DuplicateException;
import Domain.Validators.NoEntityStored;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;

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

public class ProblemFileRepo extends InMemRepo<Long, Problem> {

    private String fileName;

    public ProblemFileRepo(Validator<Problem> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData(){
        Path path = Paths.get(fileName);
        try{
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String subject = items.get(1);
                String difficulty = items.get(2);
                String text = items.get(3);

                Problem problem = new Problem(subject, difficulty, text);
                problem.setId(id);

                try{
                    super.save(problem);
                }catch (ValidatorException e){
                    e.printStackTrace();
                }
            });
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void saveToFile(Problem entity){
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getSubject() + "," + entity.getDifficulty() + "," + entity.getText());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Problem> save(Problem entity) throws ValidatorException {
        if(findOne(entity.getId()).isPresent()){
            throw new DuplicateException("There can't be two problems with the same id!");
        }
        Optional<Problem> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void updateData(){
        super.findAll().forEach(problem -> saveToFile(problem));
    }

    private Optional<Problem> updateFile(Optional<Problem> optional){
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
    public Optional<Problem> delete(Long id) {
        Optional<Problem> optional = super.delete(id);
        if(findOne(id).isPresent()){
            throw new NoEntityStored("Problem does not exist in the database!");
        }
        return updateFile(optional);
    }

    @Override
    public Optional<Problem> update(Problem entity) throws ValidatorException {
        Optional<Problem> optional = super.update(entity);
        if(!findOne(entity.getId()).isPresent()){
            throw new NoEntityStored("Problem does not exist in the database!");
        }
        return updateFile(optional);
    }
}

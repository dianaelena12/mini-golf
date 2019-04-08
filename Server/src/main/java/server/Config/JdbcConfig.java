package server.Config;

import common.Domain.Student;
import common.Domain.Validators.StudentValidator;
import common.Domain.Validators.Validator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
    @Bean
    JdbcOperations jdbcOperations() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        jdbcTemplate.setDataSource(dataSource());

        return jdbcTemplate;
    }

    @Bean
    DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();

        //TODO use env props (or property files)
        basicDataSource.setUrl("jdbc:postgresql://localhost:5432/MPPLab");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("12345");
        basicDataSource.setInitialSize(2);

        return basicDataSource;
    }

    @Bean
    Validator<Student> studentValidator() {
        return new StudentValidator();
    }

}

package server.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import server.Repository.RepositoryInterface;
import server.Repository.StudentRepositoryImpl;

@Configuration
public class AppConfig {

    @Bean
    RepositoryInterface clientRepository() {
        return new StudentRepositoryImpl();
    }


}

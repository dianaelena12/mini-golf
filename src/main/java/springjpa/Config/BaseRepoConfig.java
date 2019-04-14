package springjpa.Config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"springjpa.Repo", "springjpa.Service", "springjpa.UI"})
public class BaseRepoConfig {
}

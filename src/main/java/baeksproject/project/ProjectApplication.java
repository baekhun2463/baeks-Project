package baeksproject.project;

import baeksproject.project.config.JpaConfig;
import baeksproject.project.config.QuerydslConfig;
import baeksproject.project.config.SpringDataJpaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import(QuerydslConfig.class)
@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

}

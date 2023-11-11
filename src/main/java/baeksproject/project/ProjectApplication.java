package baeksproject.project;

import baeksproject.project.item.config.MemoryConfig;
import baeksproject.project.login.config.JpaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import({JpaConfig.class, MemoryConfig.class})
@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

}

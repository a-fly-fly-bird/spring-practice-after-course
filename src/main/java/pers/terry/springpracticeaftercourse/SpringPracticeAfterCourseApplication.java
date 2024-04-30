package pers.terry.springpracticeaftercourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
// Enables Spring's annotation-driven cache management capability
@EnableCaching
public class SpringPracticeAfterCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPracticeAfterCourseApplication.class, args);
	}
}

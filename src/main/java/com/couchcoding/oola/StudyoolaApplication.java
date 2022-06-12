package com.couchcoding.oola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@EnableJdbcAuditing
@SpringBootApplication
public class StudyoolaApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyoolaApplication.class, args);
	}
}

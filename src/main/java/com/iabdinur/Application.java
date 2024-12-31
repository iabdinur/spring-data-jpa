package com.iabdinur;

import com.github.javafaker.Faker;
import com.iabdinur.student.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository studentRepository,
            StudentIdCardRepository studentIdCardRepository) {
        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            Integer age = faker.number().numberBetween(17, 55);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    age);

            student.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(10)));
            student.addBook(new Book("Think and Grow Rich", LocalDateTime.now()));
            student.addBook(new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

            StudentIdCard studentIdCard = new StudentIdCard("1234567889", student);

            studentIdCardRepository.save(studentIdCard);

        };
    }

    private static void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            Integer age = faker.number().numberBetween(15, 55);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    age);
            studentRepository.save(student);
        }
    }
}
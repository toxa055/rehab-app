package com.rehab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class that configures Spring Application Context.
 */
@SpringBootApplication
public class RehabApplication {

    /**
     * The point from where the application starts its execution.
     *
     * @param args given array of arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(RehabApplication.class, args);
    }
}

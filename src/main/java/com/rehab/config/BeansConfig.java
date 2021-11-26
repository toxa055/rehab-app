package com.rehab.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class that declares methods to configure all unnecessary beans.
 */
@Configuration
public class BeansConfig {

    /**
     * Method that initializes a new instance of ModelMapper.
     * It's used for converting objects from one type to another.
     *
     * @return new instance of ModelMapper;
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Method that initializes a new instance of Logger.
     * It's used for logging specific messages.
     *
     * @return new instance of Logger;
     */
    @Bean
    public Logger logger() {
        return LogManager.getLogger();
    }
}

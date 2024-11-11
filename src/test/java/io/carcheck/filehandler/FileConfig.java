package io.carcheck.filehandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class FileConfig {

    /**
     * Method to define file parser, we are passing the files from feature file for more reusablility
     * @param filePath
     * @return
     */
    @Bean
    @Scope(value = "prototype")
    public FileParser fileParser(String filePath) {
        return new FileParser(filePath);
    }
}

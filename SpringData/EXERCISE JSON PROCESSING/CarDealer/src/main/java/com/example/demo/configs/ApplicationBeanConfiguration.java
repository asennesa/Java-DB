
package com.example.demo.configs;

import com.example.demo.utils.FileUtil;
import com.example.demo.utils.FileUtilImpl;
import com.example.demo.utils.ValidationUtil;
import com.example.demo.utils.ValidationUtilImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public Gson gson(){
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public ValidationUtil validationUtil(){
        return new ValidationUtilImpl();
    }

    @Bean
    public FileUtil fileUtil(){
        return new FileUtilImpl();
    }

}
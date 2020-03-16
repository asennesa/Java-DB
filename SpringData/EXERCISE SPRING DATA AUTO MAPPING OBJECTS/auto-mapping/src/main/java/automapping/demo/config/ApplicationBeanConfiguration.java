package automapping.demo.config;

import automapping.demo.utils.ValidationUtil;
import automapping.demo.utils.ValidationUtilImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public ValidationUtil validationUtil(){
        return new ValidationUtilImpl();
    }
    @Bean
    public BufferedReader bufferedReader(){
        return new BufferedReader(new InputStreamReader(System.in));
    }
    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }
}

package automapping.demo.controllers;

import automapping.demo.dtos.UserLoginDto;
import automapping.demo.dtos.UserRegisterDto;
import automapping.demo.services.UserService;
import automapping.demo.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.io.BufferedReader;

@Component
public class AppController implements CommandLineRunner {
    private final BufferedReader bufferedReader;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    @Autowired
    public AppController(BufferedReader bufferedReader, ValidationUtil validationUtil, UserService userService) {
        this.bufferedReader = bufferedReader;

        this.validationUtil = validationUtil;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        while(true){
            System.out.println("Enter command");
            String[] input = this.bufferedReader.readLine().split("\\|");

            switch (input[0]){

                case"RegisterUser":
                    if(!input[2].equals(input[3])){
                        System.out.println("Passwords don't match");
                        break;
                    }
                    UserRegisterDto userRegisterDto = new UserRegisterDto(input[1],input[2],input[4]);
                    if(this.validationUtil.isValid(userRegisterDto)){
                        this.userService.registerUser(userRegisterDto);
                        System.out.printf("%s was registered%n",input[4]);


                    }else{
                        this.validationUtil.getViolations(userRegisterDto).stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                    break;
                case "LoginUser":
                    UserLoginDto userLoginDto = new UserLoginDto(input[1],input[2]);
                    this.userService.loginUser(userLoginDto);
                    break;
                case "Logout":
                    this.userService.logout();
                    break;

            }
        }

    }
}

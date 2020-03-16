package automapping.demo.services;

import automapping.demo.dtos.UserLoginDto;
import automapping.demo.dtos.UserRegisterDto;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);
    void loginUser (UserLoginDto userLoginDto);
    void logout();
}

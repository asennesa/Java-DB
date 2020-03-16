package automapping.demo.services;

import automapping.demo.dtos.UserDto;
import automapping.demo.dtos.UserLoginDto;
import automapping.demo.dtos.UserRegisterDto;
import automapping.demo.entities.Role;
import automapping.demo.entities.User;
import automapping.demo.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private UserDto userDto;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        User user = this.modelMapper.map(userRegisterDto,User.class);
        user.setRole(this.userRepository.count()==0 ? Role.ADMIN:Role.USER);
        this.userRepository.saveAndFlush(user);

    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        User user = this.userRepository.findByEmail(userLoginDto.getEmail());
        if(user ==null){
            System.out.println("Incorrect username / password");
        }else{
            //We add UserDto because the exercise has 1 user login slot.
            this.userDto=this.modelMapper.map(user,UserDto.class);
            System.out.printf("Successfully logged in %s%n",user.getFullName());


        }
    }

    @Override
    public void logout() {
        if(this.userDto==null){
            System.out.println("Cannot lot out.No user was logged in");
        }else{
            String name = this.userDto.getFullName();
            this.userDto = null;
            System.out.println(String.format("User %s successfully logged out",name));
        }
    }
}

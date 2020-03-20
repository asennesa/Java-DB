package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.models.dtos.UserSeedDto;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) {
        if(this.userRepository.count() != 0){
            return;
        }
        Arrays.stream(userSeedDtos).forEach(u->{
            if(this.validationUtil.isValid(u)){
                User user = this.modelMapper.map(u,User.class);
                this.userRepository.saveAndFlush(user);
            }else{
                this.validationUtil.getViolations(u).stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);

            }
        });

    }

    @Override
    @Transactional
    public User getRandomUser() {
        Random random = new Random();
        long randomId = random
                .nextInt((int) this.userRepository.count())+1;
        return this.userRepository.getOne(randomId);
    }
}

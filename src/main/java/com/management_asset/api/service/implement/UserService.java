package com.management_asset.api.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.User;
import com.management_asset.api.repository.UserRepository;
import com.management_asset.api.service.IUserService;

@Service
public class UserService implements IUserService{
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id){
        return userRepository.findById(id).get();
    }

    @Override
    public User save(User model){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

}

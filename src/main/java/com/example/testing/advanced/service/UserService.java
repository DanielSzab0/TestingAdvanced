package com.example.testing.advanced.service;

import com.example.testing.advanced.model.User;
import com.example.testing.advanced.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id)
    {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public User saveUser(User user)
    {
        return userRepository.save(user);
    }

    public void deleteUser(Long id)
    {
        getUserById(id);
        userRepository.deleteById(id);
    }

    public Optional<User> getUserByFullName(String name)
    {
        return Optional.ofNullable(userRepository.findByFullName(name)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

}

package com.fashioncloset.fashion_closet_organizer1.service;

import com.fashioncloset.fashion_closet_organizer1.model.User;
import com.fashioncloset.fashion_closet_organizer1.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        user.setRole("ROLE_USER"); // Set the user's role
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return (User) userRepository.findByUsername(username);
    }
}

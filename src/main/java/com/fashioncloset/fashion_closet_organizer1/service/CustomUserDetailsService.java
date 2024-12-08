package com.fashioncloset.fashion_closet_organizer1.service;

import com.fashioncloset.fashion_closet_organizer1.model.User;
import com.fashioncloset.fashion_closet_organizer1.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Dependency injection through constructor
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch a list of users based on the username
        List<User> users = userRepository.findByUsername(username);

        // If the list is empty, it means no user exists with this username
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // If the list contains more than one result, it's a data inconsistency issue
        if (users.size() > 1) {
            throw new IllegalStateException("Multiple users found with the same username: " + username);
        }

        // Retrieve the first (and assumed only) user
        User user = users.get(0);

        // Build and return a UserDetails object
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}

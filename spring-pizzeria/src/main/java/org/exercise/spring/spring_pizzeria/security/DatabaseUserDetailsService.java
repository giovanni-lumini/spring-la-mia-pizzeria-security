package org.exercise.spring.spring_pizzeria.security;

import java.util.Optional;

import org.exercise.spring.spring_pizzeria.model.User;
import org.exercise.spring.spring_pizzeria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Unimplemented method 'loadUserByUsername'");
        }

        return new DatabaseUserDetails(optionalUser.get());

    }

}

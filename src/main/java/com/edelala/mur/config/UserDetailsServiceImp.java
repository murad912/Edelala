package com.edelala.mur.config;



import com.edelala.mur.entity.User;
import com.edelala.mur.repo.UserRepository;
import com.edelala.mur.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

//@Service
//@RequiredArgsConstructor
//public class UserDetailsServiceImp implements UserDetailsService {
//
//    private final UserService userService;
//
////    public UserDetailsServiceImp(UserService userService) {
////        this.userService = userService;
////    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userService.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
//        );
//    }
//}

import com.edelala.mur.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Crucial for roles
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

//@Service // Mark this class as a Spring service component
//@RequiredArgsConstructor // Automatically injects UserService
//public class UserDetailsServiceImp implements UserDetailsService {
//
//    private final UserService userService; // Inject your UserService
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        // Fetch your custom User entity by email from the UserService
//        User user = userService.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//
//        // CRITICAL: Map your custom Role enum to Spring Security's GrantedAuthority.
//        // Spring Security expects roles to be prefixed with "ROLE_".
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(), // This is the 'username' (email) that Spring Security uses
//                user.getPassword(), // Encoded password
//                // Convert your custom Role to a SimpleGrantedAuthority with the "ROLE_" prefix
//                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
//        );
//    }
//}
//jun 13 11:00pm

//@Service // Mark this class as a Spring service component
//@RequiredArgsConstructor // Automatically injects dependencies via constructor
//public class UserDetailsServiceImp implements UserDetailsService {
//
//    // IMPORTANT: Inject UserRepository directly here to fetch your custom User entity.
//    // The previous code injected UserService, which then called findByEmail.
//    // For direct user loading, UserRepository is more common here.
//    private final UserRepository userRepository; // Assuming you have this repository
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        // Fetch your custom User entity by email from the UserRepository
//        User user = userRepository.findByEmail(email) // Using findByEmail as your username is email
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//
//        // CRITICAL FIX: Return your custom User entity directly,
//        // because it already implements the UserDetails interface.
//        return user;
//    }
//}
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}

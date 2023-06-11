package com.ltp.contacts.service;

import java.util.ArrayList;
import java.util.List;

import com.ltp.contacts.exception.UserNotFoundException;
import com.ltp.contacts.pojo.User;
import com.ltp.contacts.pojo.UserDescription;
import com.ltp.contacts.repository.UserDescriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.ltp.contacts.repository.UserRepository;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserDescriptionRepository userDescriptionRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserById(String id) {
        return userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void saveUser(User user) {
        UserDescription userDescription = new UserDescription();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDescriptionRepository.save(userDescription);
        user.setUserDescription(userDescription);
        userRepository.save(user);
    }

    @Override
    public void updateUserDescription(UserDescription userDescription) {
        User authenticatedUser = getAuthenticatedUser();
        UserDescription currentDescription = authenticatedUser.getUserDescription();
        currentDescription.setName(userDescription.getName());
        currentDescription.setSurname(userDescription.getSurname());
        currentDescription.setDescription(userDescription.getDescription());
        userDescriptionRepository.save(currentDescription);
        authenticatedUser.setUserDescription(currentDescription);
        userRepository.save(authenticatedUser);
    }

    @Override
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @Override
    public List<User> getUsers(List<Long> id) {
        return userRepository.findAllById(id);
    }
    @Override
    public List<User> getUsersWithoutAuthenticated() {
        List<User> users = new ArrayList<>(getUsers());
        users.removeIf(user -> getAuthenticatedUser().getUsername().equals(user.getUsername()));
        return users;
    }
    @Override
    public List<User> getUsersWithoutAuthenticatedForListUser(List<User> users){
        users.removeIf(user -> getAuthenticatedUser().getUsername().equals(user.getUsername()));
        return users;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public boolean unicityUser(User user) {
        String userName = user.getUsername();
        User existingUser = userRepository.findByUsername(userName).orElse(null);
        return existingUser == null || existingUser.getId().equals(user.getId());
    }

    @Override
    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}

package com.ltp.contacts.service;


import com.ltp.contacts.pojo.User;
import com.ltp.contacts.pojo.UserDescription;

import java.util.List;

public interface UserService {
    User getUserById(String id);

    void saveUser(User user);
    void updateUserDescription(UserDescription UserDescription);

    boolean verifyPassword(String rawPassword, String encodedPassword);

    List<User> getUsers();


    List<User> getUsersWithoutAuthenticatedForListUser(List<User> users);


    void deleteUser(String id);

    boolean unicityUser(User user);

    boolean isUserAuthenticated();
   User getAuthenticatedUser();


    List<User> getUsers(List<Long> id);

    List<User> getUsersWithoutAuthenticated();
}



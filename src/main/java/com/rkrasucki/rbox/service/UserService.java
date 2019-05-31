package com.rkrasucki.rbox.service;

import com.rkrasucki.rbox.model.User;

import javax.management.relation.RoleNotFoundException;

public interface UserService {

    User findByUsername(String username);
    User findByEmail(String email);
    void saveUser(User user) throws RoleNotFoundException;
    boolean checkIfValidOldPassword(User theUser, String oldPassword);
    void updateUserPassword(String username, String newPassword);
    void updateUserProfile(User theUser, String username);


}

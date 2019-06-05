package com.rkrasucki.rbox.service;

import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.model.UserDto;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface UserService {

    User findByUsername(String username);
    User findByEmail(String email);
    List<User>findAll();
    void saveUser(User user) throws RoleNotFoundException;
    boolean checkIfValidOldPassword(User theUser, String oldPassword);
    void updateUserPassword(String username, String newPassword);
    void updateUserProfile(UserDto userDto, String username);


}

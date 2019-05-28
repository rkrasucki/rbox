package com.rkrasucki.rbox.service;

import com.rkrasucki.rbox.model.User;

import javax.management.relation.RoleNotFoundException;

public interface UserService {

    User findByUsername(String username);

    User findByEmail(String email);

    void saveUser(User user) throws RoleNotFoundException;

}

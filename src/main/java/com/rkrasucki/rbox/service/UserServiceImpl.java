package com.rkrasucki.rbox.service;

import com.rkrasucki.rbox.model.Role;
import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.model.UserDto;
import com.rkrasucki.rbox.repository.RoleRepository;
import com.rkrasucki.rbox.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by rkrasucki 27.05.19
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) throws RoleNotFoundException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);

        Role role = roleRepository.findByRole("USER");
        ifRoleExistIsAddToUser(user, role);

        userRepository.save(user);
    }

    private void ifRoleExistIsAddToUser(User user, Role role) throws RoleNotFoundException {
        if(role !=null){
            user.setRoles(new HashSet<>(Arrays.asList(role)));
        }
        else {
            throw new RoleNotFoundException("Not found basic role: USER");
        }
    }

    @Override
    public boolean checkIfValidOldPassword(User theUser, String oldPassword) {
        return bCryptPasswordEncoder.matches(oldPassword, theUser.getPassword());
    }


    @Override
    @Transactional
    public void updateUserPassword(String username, String newPassword) {
        userRepository.updateUserPassword(username, bCryptPasswordEncoder.encode(newPassword));
    }

    @Override
    @Transactional
    public void updateUserProfile(UserDto userDto, String username) {
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        String email = userDto.getEmail();

        userRepository.updateUserProfile(username, firstName, lastName, email);

    }
}

package com.rkrasucki.rbox.service;

import com.rkrasucki.rbox.configuration.AppConstants;
import com.rkrasucki.rbox.model.Role;
import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.model.UserDto;
import com.rkrasucki.rbox.model.VerificationToken;
import com.rkrasucki.rbox.repository.RoleRepository;
import com.rkrasucki.rbox.repository.UserRepository;
import com.rkrasucki.rbox.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by rkrasucki 27.05.19
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private VerificationTokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, VerificationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
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
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User registerNewUserAccount(User user) throws RoleNotFoundException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRole("USER");
        ifRoleExistIsAddToUser(user, role);
        return userRepository.save(user);
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

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        VerificationToken newToken = new VerificationToken(token, user);
        tokenRepository.save(newToken);

    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if(verificationToken == null) {
            return AppConstants.TOKEN_INVALID;
        }

        User theUser = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0)){
            tokenRepository.delete(verificationToken);
            return AppConstants.TOKEN_EXPIRED;
        }

        theUser.setActive(1);
        userRepository.save(theUser);

        return AppConstants.TOKEN_VALID;
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingToken) {
        VerificationToken vt = tokenRepository.findByToken(existingToken);
        vt.updateToken(UUID.randomUUID().toString());
        tokenRepository.save(vt);
        return vt;
    }
}

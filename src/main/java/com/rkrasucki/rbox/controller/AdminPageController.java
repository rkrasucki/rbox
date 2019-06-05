package com.rkrasucki.rbox.controller;

import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.model.UserDto;
import com.rkrasucki.rbox.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class AdminPageController {

    private Logger logger = Logger.getLogger(getClass().getName());
    private UserService userService;

    public AdminPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAdminPage(Model theModel) {

        return "admin/admin";
    }

    @GetMapping("admin/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAdminAllUsersPage(Model theModel) {

        List<UserDto> usersDto = getAllUsers();
        theModel.addAttribute("users", usersDto);

        return "admin/users";
    }


    private List<UserDto> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserDto> usersDto = new ArrayList<>();

        for(User tmpUser: users) {
            UserDto userDto = new UserDto();
            userDto.setUsername(tmpUser.getUsername());
            userDto.setFirstName(tmpUser.getFirstName());
            userDto.setLastName(tmpUser.getLastName());
            userDto.setEmail(tmpUser.getEmail());
            userDto.setActive(tmpUser.getActive());

            boolean hasRoleNumber = tmpUser.getRoles().iterator().hasNext();
            if(hasRoleNumber) {
                int roleNumber = tmpUser.getRoles().iterator().next().getId();
                userDto.setRoleNumber(roleNumber);
            }

            usersDto.add(userDto);
        }

        return usersDto;
    }

}

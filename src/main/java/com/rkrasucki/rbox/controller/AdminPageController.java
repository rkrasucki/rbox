package com.rkrasucki.rbox.controller;

import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.service.AdminService;
import com.rkrasucki.rbox.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class AdminPageController {

    private Logger logger = Logger.getLogger(getClass().getName());
    private UserService userService;
    private AdminService adminService;

    public AdminPageController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAdminPage(Model theModel) {

        return "admin/admin";
    }

    @GetMapping("/admin/users/{page}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAdminAllUsersPage(@PathVariable("page") int page,  Model theModel) {
        Page<User> pages = getAllUsersPageable(page -1);
        int totalPages = pages.getTotalPages();
        int currentPage = pages.getNumber();
        List<User> usersList = pages.getContent();
        theModel.addAttribute("totalPages", totalPages);
        theModel.addAttribute("currentPage", currentPage +1);
        theModel.addAttribute("users", usersList);

        return "admin/users";
    }


    private Page<User> getAllUsersPageable(int page){
        int elements = 15;
        Page<User> pages = adminService.findAll(PageRequest.of(page,elements));
        for(User tmpUser:pages) {
            boolean hasRoleNumber = tmpUser.getRoles().iterator().hasNext();
            if(hasRoleNumber) {
                int roleNumber = tmpUser.getRoles().iterator().next().getId();
                tmpUser.setRoleNumber(roleNumber);
            }
        }

        return pages;
    }

}

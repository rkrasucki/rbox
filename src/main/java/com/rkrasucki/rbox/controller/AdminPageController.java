package com.rkrasucki.rbox.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAdminPage() {

        return "admin";
    }



}

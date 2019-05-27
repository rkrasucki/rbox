package com.rkrasucki.rbox.Controller;

import com.rkrasucki.rbox.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by rkrasucki 26.05.19
 */
@Controller
public class RegisterController {

    @GetMapping("/register")
    public String showRegistrationForm(Model theModel) {
        theModel.addAttribute("user", new User());
        return "registration-form";

    }
}

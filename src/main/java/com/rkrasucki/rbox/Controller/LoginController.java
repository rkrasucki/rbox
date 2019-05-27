package com.rkrasucki.rbox.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by rkrasucki 24.05.19
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage() {

        return "login";
    }
}

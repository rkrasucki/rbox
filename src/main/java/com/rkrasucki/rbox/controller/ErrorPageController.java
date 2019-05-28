package com.rkrasucki.rbox.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by rkrasucki 28.05.19
 */
@Controller
public class ErrorPageController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/error")
    public String showErrorPage(){
        return "error-page";
    }
}

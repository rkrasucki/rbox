package com.rkrasucki.rbox.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void shouldRegisterStatusOk() throws Exception {
        this.mockMvc.perform(get("/register")).andExpect(status().isOk());
    }

    @Test
    public void shouldShowRegistrationFormGet() throws Exception {
        this.mockMvc.perform(get("/register")).andExpect(view().name("registration-form"));
    }

    @Test
    @WithAnonymousUser
    public void shouldAddUserStatusOk() throws Exception {
        this.mockMvc.perform(post("/adduser")).andExpect(status().isOk());
    }


}
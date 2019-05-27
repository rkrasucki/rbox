package com.rkrasucki.rbox.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldMainStatusOk() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void shouldViewNameIndex() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(view().name("index"));
    }

    @Test
    @WithMockUser(username="admin",authorities={"EMPLOYEE","ADMIN"})
    public void shouldIndexStatusOkWithGodAuthorities() throws Exception {
        this.mockMvc.perform(get("/index")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void shouldIndexStatusNoOkWithBadAuthorities() throws Exception {
        this.mockMvc.perform(get("/index")).andExpect(status().isFound());
    }





}
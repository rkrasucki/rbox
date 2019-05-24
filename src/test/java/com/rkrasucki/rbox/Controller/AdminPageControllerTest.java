package com.rkrasucki.rbox.Controller;

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
public class AdminPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username="admin",authorities="ADMIN")
    public void shouldGetViewNameAdminWithAuthoriseRoleAdmin() throws Exception {
        this.mockMvc.perform(get("/admin")).andExpect(view().name("admin"));
    }

    @Test(expected = AssertionError.class)
    @WithAnonymousUser
    public void shouldNoGetViewNameAdminWithoutAuthorisation() throws Exception {
        this.mockMvc.perform(get("/admin")).andExpect(view().name("admin"));
    }

    @Test(expected = AssertionError.class)
    @WithMockUser(username = "user", authorities = "USER")
    public void shouldNotGetViewNAmeAdminWithOtherRoleThanAdmin() throws Exception {
        this.mockMvc.perform(get("/admin")).andExpect(view().name("admin"));
    }

    @Test
    @WithMockUser(username="admin",authorities="ADMIN")
    public void shouldAdminPageStatusIsOkWithAdminRole() throws Exception {
        this.mockMvc.perform(get("/admin")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="user",authorities="USER")
    public void shouldAdminPageStatusIsForbiddenWithUserRole() throws Exception {
        this.mockMvc.perform(get("/admin")).andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void shouldAdminPageStatusBadWithoutAuthorisation() throws Exception {
        this.mockMvc.perform(get("/admin")).andExpect(status().isFound());
    }



}
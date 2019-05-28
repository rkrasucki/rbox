package com.rkrasucki.rbox.service;

import com.rkrasucki.rbox.model.Role;
import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.repository.RoleRepository;
import com.rkrasucki.rbox.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    @Mock
    private RoleRepository mockRoleRepository;

    private UserServiceImpl userServiceUnderTest;
    private User theUser;
    private Role basicUserRole;

    @Before
    public void setUp() {
        initMocks(this);
        userServiceUnderTest = new UserServiceImpl(
                mockUserRepository,
                mockRoleRepository,
                mockBCryptPasswordEncoder);

        theUser = new User();
        theUser.setUsername("test_username");
        theUser.setFirstName("test_firstName");
        theUser.setLastName("test_lastName");
        theUser.setPassword("A1test_pass");
        theUser.setPasswordConfirm("A1test_pass");
        theUser.setEmail("test@email.com");

        basicUserRole = new Role();
        basicUserRole.setRole("USER");

        Mockito.when(mockUserRepository.findByUsername("test_username")).thenReturn(theUser);
        Mockito.when(mockUserRepository.findByEmail("test@email.com")).thenReturn(theUser);
        Mockito.when(mockUserRepository.save(theUser)).thenReturn(theUser);
        Mockito.when(mockRoleRepository.findByRole("USER")).thenReturn(basicUserRole);

    }

    @Test
    public void testFindByUsernameExist() {
        final String username = "test_username";
        final User found = userServiceUnderTest.findByUsername(username);

        assertEquals(username, found.getUsername());
    }

    @Test(expected = NullPointerException.class)
    public void testFindByUsernameNotExist() {
        final String username = "NoExistUser";
        final User found = userServiceUnderTest.findByUsername(username);

        found.getUsername();
    }

    @Test
    public void testFindByEmailExist() {
        final String email = "test@email.com";
        final User result = userServiceUnderTest.findByEmail(email);

        assertEquals(email, result.getEmail());
    }

    @Test(expected = NullPointerException.class)
    public void testFindByEmailNotExist() {
        final String email = "noexist@email.com";
        final User result = userServiceUnderTest.findByEmail(email);

        assertNull(result.getEmail());
    }

    @Test
    public void testSaveUserIsActive()  throws RoleNotFoundException {
        final int active = 1;
        userServiceUnderTest.saveUser(theUser);

        assertEquals(active, theUser.getActive());
    }

    @Test
    public void testSaveUserIfBasicRoleExist() throws RoleNotFoundException {
        Role roleTest = new Role();
        roleTest.setRole("USER");

        userServiceUnderTest.saveUser(theUser);
        List<Role> fromSavedUser = new ArrayList<>(theUser.getRoles());

        assertEquals(roleTest, fromSavedUser.get(0));
    }

    @Test
    public void testSaveUserIfBasicRoleNotExist() throws RoleNotFoundException {
        Role roleTest = new Role();
        roleTest.setRole("BROKEN_ROLE");

        userServiceUnderTest.saveUser(theUser);
        List<Role> fromSavedUser = new ArrayList<>(theUser.getRoles());

        assertNotEquals(roleTest, fromSavedUser.get(0));
    }

}
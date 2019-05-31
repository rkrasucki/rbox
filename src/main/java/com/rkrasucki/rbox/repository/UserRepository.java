package com.rkrasucki.rbox.repository;

import com.rkrasucki.rbox.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.username = :username")
    void updateUserPassword(@Param("username") String username, @Param("newPassword") String newPassword);

    @Modifying
    @Query("UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.email = :email " +
            "WHERE u.username =:username")
    void updateUserProfile(@Param("username") String username, @Param("firstName") String firstName,
                           @Param("lastName") String lastName, @Param("email") String email);
}

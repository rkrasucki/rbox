package com.rkrasucki.rbox.repository;

import com.rkrasucki.rbox.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by rkrasucki 10.06.19
 */

public interface AdminRepository extends JpaRepository<User, Integer> {


}

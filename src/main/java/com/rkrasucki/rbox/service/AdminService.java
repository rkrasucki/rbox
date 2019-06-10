package com.rkrasucki.rbox.service;

import com.rkrasucki.rbox.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by rkrasucki 10.06.19
 */

public interface AdminService {

    Page<User> findAll(Pageable pageable);

}

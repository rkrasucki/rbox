package com.rkrasucki.rbox.service;

import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.repository.AdminRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by rkrasucki 10.06.19
 */

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {

        Page<User> users = adminRepository.findAll(pageable);
        return users;
    }
}

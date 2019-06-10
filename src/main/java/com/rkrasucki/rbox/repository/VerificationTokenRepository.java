package com.rkrasucki.rbox.repository;

import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.stream.Stream;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser (User user);

    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    @Modifying
    @Query("DELETE FROM VerificationToken vt WHERE vt.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);

}

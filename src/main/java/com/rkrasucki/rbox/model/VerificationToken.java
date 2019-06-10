package com.rkrasucki.rbox.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Created by rkrasucki 06.06.19
 */
@Entity
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;
    @Transient
    private Logger logger = Logger.getLogger(getClass().getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private long id;

    @Column(name = "token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "FK_VERIFY_USER"))
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    public VerificationToken() {
    }

    public VerificationToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }


    public VerificationToken(final String token, final User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationToken that = (VerificationToken) o;
        return token.equals(that.token) &&
                user.equals(that.user) &&
                expiryDate.equals(that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, user, expiryDate);
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                '}';
    }
}

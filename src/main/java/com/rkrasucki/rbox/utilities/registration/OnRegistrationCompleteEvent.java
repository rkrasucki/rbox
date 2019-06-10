package com.rkrasucki.rbox.utilities.registration;

import com.rkrasucki.rbox.model.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Created by rkrasucki 06.06.19
 */

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final User user;

    public OnRegistrationCompleteEvent(String appUrl, Locale locale, User user) {
        super(user);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }
}

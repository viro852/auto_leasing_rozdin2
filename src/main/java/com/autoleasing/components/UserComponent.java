package com.autoleasing.components;

import com.autoleasing.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserComponent {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

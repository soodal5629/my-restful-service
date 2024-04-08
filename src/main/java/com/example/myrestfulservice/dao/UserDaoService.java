package com.example.myrestfulservice.dao;

import com.example.myrestfulservice.bean.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;
    static {
        users.add(new User(1, "Kenneth", LocalDateTime.now()));
        users.add(new User(2, "Alice", LocalDateTime.now()));
        users.add(new User(3, "Elena", LocalDateTime.now()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        if(user.getJoinDate() == null) {
            user.setJoinDate(LocalDateTime.now());
        }
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for(User user : users) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}

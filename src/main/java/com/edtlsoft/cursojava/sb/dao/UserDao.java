package com.edtlsoft.cursojava.sb.dao;

import com.edtlsoft.cursojava.sb.models.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();

    void delete(Long id);

    User store(User user);

    User login(User user);
}

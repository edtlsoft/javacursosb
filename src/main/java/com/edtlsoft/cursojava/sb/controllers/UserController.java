package com.edtlsoft.cursojava.sb.controllers;

import com.edtlsoft.cursojava.sb.dao.UserDao;
import com.edtlsoft.cursojava.sb.models.User;
import com.edtlsoft.cursojava.sb.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    private boolean validateJWTToken(String token) {
        return jwtUtil.getKey(token) != null;
    }

    @RequestMapping(value = "api/users")
    public List<User> index(@RequestHeader(value = "Authorization") String token) {
        if (! this.validateJWTToken(token)) {
            return new ArrayList<>();
        }
        return this.userDao.getAll();
    }

    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    public User store(@RequestBody User user) {
        Argon2 argon2 = Argon2Factory.create();
        String password = argon2.hash(10, 65536, 1, user.getPassword());
        user.setPassword(password);

        return this.userDao.store(user);
    }

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        this.userDao.delete(id);
    }

//    @RequestMapping(value = "user/{user}", method = "POST")
//    public User update() {
//        //
//    }
//
//    @RequestMapping(value = "user/{user}", method = "DELETE")
//    public User delete() {
//        //
//    }
}

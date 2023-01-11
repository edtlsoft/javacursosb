package com.edtlsoft.cursojava.sb.controllers;

import com.edtlsoft.cursojava.sb.dao.UserDao;
import com.edtlsoft.cursojava.sb.models.User;
import com.edtlsoft.cursojava.sb.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody User user) {
        User userDb = this.userDao.login(user);
        if (userDb != null) {
            String token = this.jwtUtil.create(String.valueOf(userDb.getId()), user.getEmail());
            return token;
        }
        return "FAIL";
    }
}

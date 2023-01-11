package com.edtlsoft.cursojava.sb.dao;

import com.edtlsoft.cursojava.sb.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAll() {
        String query = "FROM User";
        return this.entityManager.createQuery(query).getResultList();
    }

    @Override
    public void delete(Long id) {
        User user = this.entityManager.find(User.class, id);
        this.entityManager.remove(user);
    }

    @Override
    public User store(User user) {
        return entityManager.merge(user);
    }

    @Override
    public User login(User user) {
        String query = "FROM User WHERE email = :email";
        List<User> users = this.entityManager.createQuery(query)
                .setParameter("email", user.getEmail())
                .getResultList();

        if (users.isEmpty()) {
            return null;
        }

        User userDb = users.get(0);
        String passwordHashed = userDb.getPassword();
        Argon2 argon2 = Argon2Factory.create();

        if (argon2.verify(passwordHashed, user.getPassword())) {
            return user;
        }

        return null;
    }

}

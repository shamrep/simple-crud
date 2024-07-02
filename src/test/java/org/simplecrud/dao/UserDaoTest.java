package org.simplecrud.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simplecrud.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserDaoTest {
    private Dao<User> userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
    }

    @Test
    void testGetUser() {
        User user = new User(1, "test@gmail.com", "password_1");
        userDao.save(user);

        Optional<User> optionalUser = userDao.get(1L);
        User selectedUser = optionalUser.get();

        assertFalse(optionalUser.isEmpty());

        assertEquals(1, selectedUser.getId());
        assertEquals("test@gmail.com", selectedUser.getEmail());
        assertEquals("password_1", selectedUser.getPassword());

    }

}

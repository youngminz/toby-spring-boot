package springbook.user.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(locations = "/test-applicationContext.xml")
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void bean() {
        assertNotNull(userService);
    }
}
package springbook.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static springbook.user.service.UserService.MIN_LOGIN_FOR_SILVER;
import static springbook.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ContextConfiguration(locations = "/test-applicationContext.xml")
class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    PlatformTransactionManager transactionManager;

    List<User> users;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("bumjin", "박범진", "bumjin@spring.com", "p1", Level.BASIC, MIN_LOGIN_FOR_SILVER - 1, 0),
                new User("joytouch", "강명성", "bumjin@spring.com", "p2", Level.BASIC, MIN_LOGIN_FOR_SILVER, 0),
                new User("erwins", "신승한", "bumjin@spring.com", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1),
                new User("madnite1", "이상호", "bumjin@spring.com", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
                new User("green", "오민규", "bumjin@spring.com", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertEquals(userWithLevel.getLevel(), userWithLevelRead.getLevel());
        assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel());
        } else {
            assertEquals(userUpdate.getLevel(), user.getLevel());
        }
    }

    static class TestUserServiceException extends RuntimeException {
    }

    static class TestUserService extends UserService {
        private String id;

        private TestUserService(String id) {
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }
    }

    @Test
    public void upgradeAllOrNothing() {
        UserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(this.userDao);
        testUserService.setTransactionManager(this.transactionManager);
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        assertThrows(TestUserServiceException.class, testUserService::upgradeLevels);

        checkLevelUpgraded(users.get(1), false);
    }
}

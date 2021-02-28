package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.List;
import java.util.Map;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private Map<String, String> sqlMap;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    private RowMapper<User> userMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setLevel(Level.valueOf(rs.getInt("level")));
        user.setLogin(rs.getInt("login"));
        user.setRecommend(rs.getInt("recommend"));
        return user;
    };

    public void add(User user) {
        this.jdbcTemplate.update(
                this.sqlMap.get("add"),
                user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                this.sqlMap.get("get"),
                userMapper,
                id
        );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
                this.sqlMap.get("getAll"),
                userMapper
        );
    }

    public void update(User user) {
        this.jdbcTemplate.update(
                this.sqlMap.get("update"), user.getName(), user.getEmail(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
                user.getId()
        );
    }

    public void deleteAll() {
        this.jdbcTemplate.update(this.sqlMap.get("deleteAll"));
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject(this.sqlMap.get("getCount"), Integer.class);
    }
}

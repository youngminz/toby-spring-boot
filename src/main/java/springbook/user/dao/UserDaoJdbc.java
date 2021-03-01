package springbook.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.sqlservice.SqlService;

import javax.sql.DataSource;
import java.util.List;

@Component
public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SqlService sqlService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setSqlService(SqlService sqlService) {
        this.sqlService = sqlService;
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
                this.sqlService.getSql("add"),
                user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                this.sqlService.getSql("get"),
                userMapper,
                id
        );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
                this.sqlService.getSql("getAll"),
                userMapper
        );
    }

    public void update(User user) {
        this.jdbcTemplate.update(
                this.sqlService.getSql("update"), user.getName(), user.getEmail(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
                user.getId()
        );
    }

    public void deleteAll() {
        this.jdbcTemplate.update(this.sqlService.getSql("deleteAll"));
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("getCount"), Integer.class);
    }
}

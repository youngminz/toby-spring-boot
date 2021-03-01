package springbook.learningtest.embeddeddb;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class EmbeddedDbTest {
    EmbeddedDatabase db;
    JdbcTemplate template;

    @BeforeEach
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(HSQL)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();

        template = new JdbcTemplate(db);
    }

    @AfterEach
    public void tearDown() {
        db.shutdown();
    }

    @Test
    public void initData() {
        assertEquals(template.queryForObject("select count(*) from sqlmap", Integer.class), 2);

        List<Map<String, Object>> list = template.queryForList("select * from sqlmap order by key_");
        assertEquals(list.get(0).get("key_"), "KEY1");
        assertEquals(list.get(0).get("sql_"), "SQL1");
        assertEquals(list.get(1).get("key_"), "KEY2");
        assertEquals(list.get(1).get("sql_"), "SQL2");
    }

    @Test
    public void insert() {
        template.update("insert into sqlmap(key_, sql_) values(?,?)", "KEY3", "SQL3");
        assertEquals(template.queryForObject("select count(*) from sqlmap", Integer.class), 3);
    }
}

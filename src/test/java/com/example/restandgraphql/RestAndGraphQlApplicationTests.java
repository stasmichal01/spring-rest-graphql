package com.example.restandgraphql;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = RestAndGraphQlApplicationTests.Config.class)
@EnableAutoConfiguration
class RestAndGraphQlApplicationTests {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    static class Config {
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertNotNull(restTemplate);
        assertNotNull(jdbcTemplate);
        assertNotNull(dataSource);
    }

    @Test
    void h2Connection() throws SQLException {
        try (Connection conn = dataSource.getConnection()) { //DriverManager.getConnection("jdbc:h2:~/test", "sa", "")) {
            assertNotNull(conn);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT 1");
            assertThat(rs).isNotNull();
        }
    }
}

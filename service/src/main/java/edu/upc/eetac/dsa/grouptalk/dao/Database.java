package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by Hp on 2015-10-27.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Hp on 2015-10-05.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Database {
    /*private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {
    }*/

    private static Database instance = null;
    private DataSource ds;

    private Database() {
        HikariConfig config = new HikariConfig(Database.class.getClassLoader().getResource("hikari.properties").getFile());
        ds = new HikariDataSource(config);
    }

    private final static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    public final static Connection getConnection() throws SQLException {
        return getInstance().ds.getConnection();
    }
}

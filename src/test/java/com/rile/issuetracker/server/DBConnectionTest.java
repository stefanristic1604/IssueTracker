package com.rile.issuetracker.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stefan
 */
public class DBConnectionTest {

    private String name;
    private String url;
    private String password;

    public DBConnectionTest() {
        this.name = "root";
        this.password = "root";
        this.url = "jdbc:mysql://localhost:3306/issuetrackersystem";
    }

    public Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(url, name, password);
    }

    @Test
    public void testDBConnectionRunning() throws SQLException {
        boolean isRunning = true;
        boolean actual =  !getDBConnection().isClosed();
        assertEquals(isRunning, actual);
    }
    
}

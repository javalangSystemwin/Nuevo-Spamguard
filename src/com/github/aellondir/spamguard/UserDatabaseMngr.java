package com.github.aellondir.spamguard;

import java.sql.*;
import java.util.Properties;
import javax.sql.*;
import javax.sql.rowset.*;
import javax.sql.rowset.serial.*;

/**
 *
 * @author James Hull
 * @serial McMod JPGH.0001 v1
 * @version 0.01
 */
public class UserDatabaseMngr {

    private final static long serialVersionUID = 6789149501486571L;
    private final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private final String CONN_URL = "jdbc:detby:UsersDB";
    //only used on the first run;
    private final String CONN_URL_FR = "jdbc:derby:UsersDB;create=true";
    private final String TABLE_FORMAT =
            "create table USERS                            "
            + "    (NAME VARCHAR(32) NOT NULL PRIMARY KEY,   "
            + "     INFRACTIONS INTEGER NOT NULL,            ";
    private final String RS_QUERY = "SELECT * FROM UsersDB";
    private Statement stateM;
    private ResultSet rS;

    public UserDatabaseMngr() throws SQLException {
        try {
            Class.forName(DRIVER).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(CONN_URL);) {


            stateM = conn.createStatement();
        } catch (SQLException e) {
            if (e.getSQLState().equals("42Y55")) {
                this.firstRun();
            } else {
                e.printStackTrace();
            }
        }
    }

    private void firstRun() throws SQLException {
        try {
            Class.forName(DRIVER).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(CONN_URL_FR);) {


            stateM = conn.createStatement();

            stateM.executeUpdate(TABLE_FORMAT);
        } catch (SQLException e) {
            throw e;
        }
    }

    private void addOUpDPlayer() throws SQLException {
    }

    private boolean isNewPlayer() throws SQLException {
        return true;
    }

    public boolean addNPlayer(String name, int infractions) {
        try {
            if (isNewPlayer() == false) {
                return false;
            }

            this.addOUpDPlayer();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

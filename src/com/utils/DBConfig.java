package com.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * Project: alarmcodeimport
 * Package: com.utils
 * User: Administrator
 * Date: 2017-08-31 10:32
 * Author: Haiyangp
 */
public class DBConfig {

  private DBConfig() {
  }

  private static DBConfig dbConfig = new DBConfig();

  public static DBConfig _instance() {
    return dbConfig;
  }

  private Connection connection = null;

  public Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
    PropertiesLoad load = new PropertiesLoad("/db.properties");
    String driver = load.getProperty("driver");
    String user = load.getProperty("user");
    String password = load.getProperty("password");
    String url = load.getProperty("jdbcUrl");

    Class.forName(driver);

    this.connection = DriverManager.getConnection(url, user, password);
    return this.connection;
  }

  public static void closeResources(Connection connection) {
    closeResources(connection,null,null);
  }

  public static void closeResources(Connection connection, Statement statement) {
    closeResources(connection, statement,null);
  }

  public static void closeResources(Connection connection, Statement statement,
      ResultSet resultSet) {
    if (resultSet != null) {
      try {
        resultSet.close();
      } catch (SQLException e) {
        resultSet = null;
        e.printStackTrace();
      }
    }

    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        statement = null;
        e.printStackTrace();
      }
    }

    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        connection = null;
        e.printStackTrace();
      }
    }

  }

}

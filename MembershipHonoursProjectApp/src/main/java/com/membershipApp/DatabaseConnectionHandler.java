package com.membershipApp;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionHandler {
  private Connection conn;
  private Server server;

  public DatabaseConnectionHandler() {
  }

  public void dbServerStart() throws SQLException {
    server = Server.createTcpServer().start();
    try {
      Class.forName("org.h2.Driver");
      //System.out.println("driver found");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      conn = DriverManager.
              getConnection("jdbc:h2:tcp://localhost/~/MembershipDatabase", "admin", "Password");
      System.out.println("Server started");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // add application code here
    //conn.close();
  }

  public Connection getConn() {
    return conn;
  }

  public Server getServer() {
    return server;
  }
}
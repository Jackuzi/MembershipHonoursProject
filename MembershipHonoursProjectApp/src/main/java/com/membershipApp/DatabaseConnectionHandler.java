package com.membershipApp;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.StorageService;
import org.h2.tools.Server;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionHandler {
  private Connection conn;
  private Server server;
  private final static String DB_NAME = "MembershipDatabase.mv.db";


  public DatabaseConnectionHandler() {
  }

  public void dbServerStart() throws SQLException {
    //server = Server.createTcpServer("-tcpAllowOthers").start();
    // if (com.gluonhq.charm.down.Platform.isDesktop()) {
    if (Platform.isDesktop()) {
      try {
        Class.forName("org.h2.Driver");
        //System.out.println("driver found");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
      try {
        conn = DriverManager.
                getConnection("jdbc:h2:./src/main/resources/database/MembershipDatabase", "admin", "Password");
        System.out.println("Server started");

      } catch (SQLException e) {
        e.printStackTrace();

      }
      // add application code here
      //conn.close();
      // }
      // nie dziala
    } else if (Platform.isAndroid()) {
      File dir;
      try {
        dir = Services.get(StorageService.class)
                .map(s -> s.getPrivateStorage().get())
                .orElseThrow(() -> new IOException("Error: PrivateStorage not available"));
        File db = new File(dir, DB_NAME);
        System.out.println(DB_NAME);
        //if path not exists
        File path = new File("/data/data/com.membershipApp/files/MembershipDatabase.mv.db");
        if (!path.exists()) {
          DbUtils.copyDatabase("/database/", dir.getAbsolutePath(), DB_NAME);
        }
        String user = "admin";
        String pass = "Password";
        String dbUrl = "jdbc:h2:" + db.getAbsolutePath();
        System.out.println(db);
        System.out.println(dbUrl);
        dbUrl = dbUrl.substring(0, dbUrl.length() - 6);
        System.out.println(db);
        System.out.println(dbUrl);
        try {
          Class.forName("org.h2.Driver");
          conn = DriverManager.getConnection(dbUrl, user, pass);
          //System.out.println(conn.getSchema());
        } catch (ClassNotFoundException e) {
          //System.out.println(conn);
          e.printStackTrace();
        }


      } catch (IOException ex) {
        ex.printStackTrace();
        return;
      }
      /*String url = "jdbc:h2:/data/data/" +
              "com.membershi.hello" +
              "/data/hello" +
              ";FILE_LOCK=FS" +
              ";PAGE_SIZE=1024" +
              ";CACHE_SIZE=8192";*/
    }
  }

  public Connection getConn() {
    return conn;
  }

  public Server getServer() {
    return server;
  }
}
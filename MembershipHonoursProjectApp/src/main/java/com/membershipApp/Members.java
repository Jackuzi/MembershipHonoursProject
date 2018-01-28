package com.membershipApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

public class Members {
  private DatabaseConnectionHandler db;

  public ObservableList<MemberModel> getMemberData() {
    return memberData;
  }

  private ObservableList<MemberModel> memberData = FXCollections.observableArrayList();
  //public ObservableList<ReminderModel> getMembershipData() {return membershipData;}
  //private ObservableList<ReminderModel> membershipData = FXCollections.observableArrayList();


  public Members() {
    db = new DatabaseConnectionHandler();

  }

  public void retrieveData(int choice) {
    //choice 0 = customer
    //choice 1 = employee
    String sql = null;
    if (choice == 0) {
      sql = "SELECT * FROM INFORMATION_SCHEMA.CUSTOMERVIEW";
    } else if (choice == 1) {
      sql = "SELECT * FROM INFORMATION_SCHEMA.EMPLOYEEVIEW";
    }
    try {
      db.dbServerStart();
      db.getConn();
      //System.out.println(db.getConn().isClosed());
      //System.out.println(db.getConn().getSchema());
      Statement st = db.getConn().createStatement();
      ResultSet rs = st.executeQuery(sql);
      while (rs.next()) {
        int idDb = 0;
        Date dFrom = null;
        Date dTo = null;
        Date dCancel = null;
        boolean isExpired = false;
        String password = null;
        //Customer table
        if (choice == 0) {
          idDb = rs.getInt("customerId");//customer
          //membershipInfo
          dFrom = rs.getDate("dateFrom");//customer
          dTo = rs.getDate("dateTo");//customer
          dCancel = rs.getDate("cancellationDate");//customer
          isExpired = rs.getBoolean("expired");//customer
        } else if (choice == 1) {
          idDb = rs.getInt("employeeId");//employee
          password = rs.getString("password");//employee
        }
        String nDb = rs.getString("name");//shared
        String sDb = rs.getString("surname");//shared
        Date dobDb = rs.getDate("dob");//shared
        String eDb = rs.getString("email");//shared
        int tDb = rs.getInt("telephone");//shared
        //Address table
        String hDb = rs.getString("houseNumber");//shared
        String stDb = rs.getString("street");//shared
        String cDb = rs.getString("city");//shared
        String pDb = rs.getString("postcode");//shared
        String couDb = rs.getString("country");//shared
        if (choice == 0) {
          memberData.add(new MemberModel(idDb, nDb, sDb, stDb, hDb, tDb, eDb, pDb, cDb, dobDb, couDb, dFrom, dTo, dCancel, isExpired, null));
        } else if (choice == 1) {
          memberData.add(new MemberModel(idDb, nDb, sDb, stDb, hDb, tDb, eDb, pDb, cDb, dobDb, couDb, dFrom, dTo, dCancel, isExpired, password));
        }
        //System.out.println(dFrom.toString() + dTo + dCancel + isExpired);
        //System.out.println(idDb + nDb + sDb + stDb + hDb + tDb + eDb + pDb + cDb + dobDb + couDb);
        //System.out.println(memberData.toString());
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    } finally {
      System.out.println("Server stopped");
      //db.getServer().stop();
    }

  }

  public void insertData() {
  }

  public void updateData() {
  }

  public void deleteData() {
  }
}


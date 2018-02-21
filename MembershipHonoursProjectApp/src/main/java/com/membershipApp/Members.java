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

  public Members() {
    db = new DatabaseConnectionHandler();
  }

  public void retrieveData(int choice) {
    //choice 0 = customer
    //choice 1 = employee
    String sql = null;
    String memSQL = null;
    if (choice == 0) {
      memSQL = "SELECT *FROM INFORMATION_SCHEMA.MEMBERSHIPVIEW";
      sql = "SELECT * FROM INFORMATION_SCHEMA.CUSTOMERVIEW";
    } else if (choice == 1) {
      sql = "SELECT * FROM INFORMATION_SCHEMA.EMPLOYEEVIEW";
    }
    try {
      //System.out.println(db.getConn().getSchema());
      db.dbServerStart();
      db.getConn();
      Statement st = db.getConn().createStatement();
      ResultSet rs = st.executeQuery(sql);
      while (rs.next()) {
        int idDb = 0;
        Date dFrom = null;
        Date dTo = null;
        Date dCancel = null;
        int expiration = 0;
        String password = null;
        //Customer table
        if (choice == 0) {
          idDb = rs.getInt("customerId");//customer
        } else if (choice == 1) {
          idDb = rs.getInt("employeeId");//employee
          password = rs.getString("password");//employee
        }
        int aId = rs.getInt("addressId");
        String nDb = rs.getString("name");//shared
        String sDb = rs.getString("surname");//shared
        Date dobDb = rs.getDate("dob");//shared
        String eDb = rs.getString("email");//shared
        Long tDb = rs.getLong("telephone");//shared
        //Address table
        String hDb = rs.getString("houseNumber");//shared
        String stDb = rs.getString("street");//shared
        String cDb = rs.getString("city");//shared
        String pDb = rs.getString("postcode");//shared
        String couDb = rs.getString("country");//shared
        if (choice == 0) {
          // if customer has membership, else put null into object
          st = db.getConn().createStatement();
          ResultSet rs2 = st.executeQuery(memSQL);
          while (rs2.next()) {
            String custMembershipEmail = rs2.getString("email");
            if (custMembershipEmail.equals(eDb)) {
              dFrom = rs2.getDate("dateFrom");//customer
              dTo = rs2.getDate("dateTo");//customer
              dCancel = rs2.getDate("cancellationDate");//customer
              expiration = rs2.getInt("expiration");//customer
            }
          }
          memberData.add(new MemberModel(idDb, nDb, sDb, stDb, hDb, tDb, eDb, pDb, cDb, dobDb, couDb, dFrom, dTo, dCancel, expiration, null, aId));
        } else if (choice == 1) {
          memberData.add(new MemberModel(idDb, nDb, sDb, stDb, hDb, tDb, eDb, pDb, cDb, dobDb, couDb, dFrom, dTo, dCancel, expiration, password, aId));
        }
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    } finally {
      System.out.println("Server stopped");
    }
  }
}


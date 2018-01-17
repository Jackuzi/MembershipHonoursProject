package com.membershipApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Members {
  private DatabaseConnectionHandler db;

  public ObservableList<MemberModel> getMemberData() {
    return memberData;
  }

  private ObservableList<MemberModel> memberData = FXCollections.observableArrayList();

  public Members() throws SQLException {
  }

  public void retrieveData() throws SQLException {
    db = new DatabaseConnectionHandler();
    try {
      db.getConn();
      Statement st = db.getConn().createStatement();
      ResultSet rs = st.executeQuery("SELECT CUSTOMER.*, ADDRESS.* " +
              "FROM MEMBERSHIPDATABASE.PUBLIC.CUSTOMER , " +
              "MEMBERSHIPDATABASE.PUBLIC.ADDRESS " +
              "WHERE" +
              " CUSTOMER.ADDRESSID = ADDRESS.ADDRESSID");
      while (rs.next()) {
        //Customer table
        int idDb = rs.getInt("customerId");
        String nDb = rs.getString("name");
        String sDb = rs.getString("surname");
        Date dobDb = rs.getDate("dob");
        String eDb = rs.getString("email");
        int tDb = rs.getInt("telephone");
        //Address table
        String stDb = rs.getString("street");
        String cDb = rs.getString("city");
        String pDb = rs.getString("postcode");
        String couDb = rs.getString("country");
        String hDb = rs.getString("houseNumber");
        memberData.add(new MemberModel(idDb, nDb, sDb, stDb, hDb, tDb, eDb, pDb, cDb, dobDb, couDb));
        System.out.println(idDb + nDb + sDb + stDb + hDb + tDb + eDb + pDb + cDb + dobDb + couDb);
        //System.out.println(memberData.toString());
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    } finally {
      db.getConn().close();
    }
  }
}

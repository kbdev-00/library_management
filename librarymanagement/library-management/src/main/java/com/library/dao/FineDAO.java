package com.library.dao;

import com.library.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FineDAO {

  public boolean payFine(int fineId) {

    String sql = """
        UPDATE fines
        SET paid_status = 'PAID',
            paid_date = CURRENT_DATE
        WHERE fine_id = ?
          AND paid_status = 'UNPAID'
        """;

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setInt(1, fineId);

      return ps.executeUpdate() > 0;

    } catch (Exception e) {

      e.printStackTrace();
      return false;
    }
  }
}
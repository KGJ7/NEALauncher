package GUIs.preclient;

import DatabaseTools.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginModel {

    Connection connection;

    public loginModel(){
        try{
            this.connection = DBConnection.getConnection();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean isConnected() {
        try {
            Connection con = DBConnection.getConnection();
            return con != null;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLogin(String username, String password) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM UserClientData where DisplayName = ? and UserPassword = ?";
        try {
            ps = this.connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        } finally {
            assert ps != null;
            assert rs != null;
            ps.close();
            rs.close();
        }
    }

}

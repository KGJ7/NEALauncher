package GUIs.preclient;

import DatabaseTools.DBConnection;
import java.io.IOException;
import java.sql.Connection;
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

}

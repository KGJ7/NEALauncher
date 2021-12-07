package DatabaseTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLException;
public class DBConnection {

    public static final String CON = "";
    public static Connection getConnection() throws SQLException{
        try {
            Class.forName("org.sqlite.jdbc");
            return DriverManager.getConnection(CON);
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}

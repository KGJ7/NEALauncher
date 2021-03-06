package DatabaseTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String CON = "jdbc:sqlite:NEADB.sqlite";

    public static Connection getConnection() throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(CON);
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}

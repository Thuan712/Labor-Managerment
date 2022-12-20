package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author nmthu
 *
 */
public class ConnectDB {
    public static  Connection con = null;
    private static  ConnectDB instance= new ConnectDB();

    public static Connection getCon() {
        return con;
    }

    public static ConnectDB getInstance() {
        return instance;
    }

    public static void setInstance(ConnectDB instance) {
        ConnectDB.instance = instance;
    }
    public void connect() throws SQLException{
        String url= "jdbc:sqlserver://localhost:1433;databasename = QuanLyLaoDong";
        String user="sa";
        String password="sapassword";
        con=DriverManager.getConnection(url,user,password);
    }
    public void disconnect(){
        if(con!=null)
            try {
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

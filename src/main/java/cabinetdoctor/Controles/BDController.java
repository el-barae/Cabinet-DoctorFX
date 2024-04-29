package cabinetdoctor.Controles;

import java.sql.*;

public class BDController extends BDInfo{
    public static boolean login(String username, String pass){
        try{
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement p = con.prepareStatement("select * from users where username = ? and password = ?");
            p.setString(1, username);
            p.setString(2, pass);
            ResultSet res = p.executeQuery();
            return res.next();
        }catch (SQLException e){
            System.err.println(e);
            return false;
        }
    }

    public static boolean signup(String username, String pass, String email, String cin){
        try{
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement p = con.prepareStatement("insert into users(username, password, email, cin, tdc, ddc) values(?, ?, ?, ?, CURTIME(), CURDATE())");
            p.setString(1, username);
            p.setString(2, pass);
            p.setString(3, email);
            p.setString(4, cin);
            int res = p.executeUpdate();
            return res != 0;
        }catch (SQLException e){
            System.err.println(e);
            return false;
        }
    }
}

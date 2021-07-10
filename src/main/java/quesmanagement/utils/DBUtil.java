package quesmanagement.utils;

import java.sql.*;
import java.util.Date;

public class DBUtil {
    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private DBUtil(){}
    public static Connection getConnection() {
        Connection conn=null;
        try {
          conn=DriverManager.getConnection("****","root",
                    "******");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }
    /*public static Statement createStatement(String sql){
        Statement ps = null;
        Connection con = getConnection();
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ps;
    }*/

    public static void close(Connection conn, Statement ps , ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }
   // public static Date returnDate(java.sql.Date)
}

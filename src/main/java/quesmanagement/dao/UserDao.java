package quesmanagement.dao;

import quesmanagement.entity.User;
import quesmanagement.utils.DBUtil;
import quesmanagement.utils.ZeroException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    //-2: user exists
    public static int add(User user) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps=null;
        int result ;
        String sql;
        try {
            //language=MySQL
            sql= "select Count(*) from pidb.userr where userName=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,user.getUserName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                if (rs.getInt("Count(*)")!=0){
                    return -2;
                }
            }
            //language=MySQL
            sql = "insert into pidb.userr (userID,userName,userPass) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getUserID());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getUserPass());
            result = ps.executeUpdate();
        } catch (SQLException | ZeroException e) {
            e.printStackTrace();
            result=-1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static int changePass(User user) {
        Connection conn = DBUtil.getConnection();
        String sql ="update pidb.userr set userPass=? where userName=?";
        int result = 0;
        PreparedStatement ps = null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, user.getUserPass());
            ps.setInt(2, user.getUserID());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            result=-1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static User login(User user) {

        Connection conn = DBUtil.getConnection();
        String sql = "select * from pidb.userr where userName= ? and userPass= ? " ;
        PreparedStatement ps = null;
        ResultSet rs = null;

        User result = null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserPass());
            rs = ps.executeQuery();
            if (rs.next()) {
                result = new User(rs.getInt("userID"),
                        rs.getString("userName"), null);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    public static int maxID(){
        Connection conn = DBUtil.getConnection();
        //language=MySQL
        String sql = "select max(userID) from pidb.userr" ;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = 0;
        try {
            ps=conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt("max(userID)");
            }else {return 0;}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            result=-1;
        }
        return result;
    }
}

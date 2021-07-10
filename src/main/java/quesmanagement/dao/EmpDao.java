package quesmanagement.dao;

import quesmanagement.entity.Emp;
import quesmanagement.utils.DBUtil;
import quesmanagement.utils.ZeroException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpDao {
    public static int add(Emp emp) {
        if (emp.getEmpID() == null || emp.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        String sql = "insert into pidb.emp (empID,userID,empName,modTime) values(?,?,?,?)";
        int result = 0;
        PreparedStatement ps =null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, emp.getEmpID());
            ps.setInt(2, emp.getUserID());
            ps.setString(3, emp.getEmpName());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static int delete(Emp emp) {
        if (emp.getEmpID() == null || emp.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        String sql = "delete from pidb.emp where empID=? and userID=?";
        int result = 0;
        PreparedStatement ps = null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, emp.getEmpID());
            ps.setInt(2, emp.getUserID());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static List<Emp> search(Emp emp) {
        if (emp.getUserID() == null) {
            return null;
        }
        Connection conn = DBUtil.getConnection();
        //empID ,empName var,userID
        String sql = "call pidb.get_emp_by_conditions(?,?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Emp> emplist = new ArrayList<>();
        try {
            ps=conn.prepareStatement(sql);
            if (emp.getEmpID() != null) {
                ps.setString(1, emp.getEmpID());
            } else {
                ps.setNull(1, Types.CHAR);
            }
            if (emp.getEmpName() != null) {
                ps.setString(2, emp.getEmpName());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }
            if (emp.getUserID() != 0) {
                ps.setInt(3, emp.getUserID());
            } else {
                ps.setNull(3, Types.VARCHAR);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Emp empresult = new Emp();
                empresult.setEmpID(rs.getString("empID"));
                empresult.setEmpName(rs.getString("empName"));
                empresult.setUserID(rs.getInt("userID"));
                empresult.setModTime(new Date(rs.getTimestamp("modTime").getTime()));
                emplist.add(empresult);
            }
        } catch (SQLException | ZeroException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return emplist;
    }

    public static int modify(Emp emp) {
        if (emp.getEmpID() == null || emp.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        String sql = "";
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        int result = 0;
        try {
            if (emp.getEmpName() != null) {
                //language=MySQL
                sql = "update pidb.emp set empName=?, modTime=? where empID=? and userID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, emp.getEmpName());
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                ps.setString(3, emp.getEmpID());
                ps.setInt(4, emp.getUserID());
                result = ps.executeUpdate();
                conn.commit();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            result=-1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }
}


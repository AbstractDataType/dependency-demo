package quesmanagement.dao;

import quesmanagement.entity.Customer;
import quesmanagement.utils.DBUtil;
import quesmanagement.utils.ZeroException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDao {
    public static int add(Customer customer) {
        if (customer.getCustID() == null || customer.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        //language=MySQL
        String sql = "insert into pidb.customer " +
                "(custID,userID,custName,address,zip,email,modTime)" +
                " values(?,?,?,?,?,?,?)";
        int result = 0;
        PreparedStatement ps = null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, customer.getCustID());
            ps.setInt(2, customer.getUserID());
            ps.setString(3, customer.getCustName());
            ps.setString(4, customer.getAddress());
            ps.setInt(5, customer.getZip());
            ps.setString(6, customer.getEmail());
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static int delete(Customer customer) {
        if (customer.getCustID() == null || customer.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        String sql = "delete from pidb.customer where custID=? and userID=?";
        int result = 0;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getCustID());
            ps.setInt(2, customer.getUserID());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static List<Customer> search(Customer customer) {
        if (customer.getUserID() == null) {
            return null;
        }
        Connection conn = DBUtil.getConnection();
        String sql = "call pidb.get_cust_by_conditions(?,?,?,?,?,?)";
        PreparedStatement ps=null;
        ResultSet rs = null;
        List<Customer> custlist = new ArrayList<Customer>();
        try {
            ps = conn.prepareStatement(sql);
            if (customer.getCustID() != null) {
                ps.setString(1, customer.getCustID());
            } else {
                ps.setNull(1, Types.CHAR);
            }
            if (customer.getCustName() != null) {
                ps.setString(2, customer.getCustName());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }
            if (customer.getAddress() != null) {
                ps.setString(3, customer.getAddress());
            } else {
                ps.setNull(3, Types.VARCHAR);
            }
            if (customer.getZip() != null) {
                ps.setInt(4, customer.getZip());
            } else {
                ps.setNull(4, Types.VARCHAR);
            }
            if (customer.getEmail() != null) {
                ps.setString(5, customer.getEmail());
            } else {
                ps.setNull(5, Types.VARCHAR);
            }
            ps.setInt(6, customer.getUserID());

            rs = ps.executeQuery();
            while (rs.next()) {
                Customer custresult = new Customer();
                custresult.setCustID(rs.getString("custID"));
                custresult.setCustName(rs.getString("custName"));
                custresult.setAddress(rs.getString("address"));
                custresult.setEmail(rs.getString("email"));
                custresult.setZip(rs.getInt("zip"));
                custresult.setModtime(new Date(rs.getTimestamp("modTime").getTime()));
                custlist.add(custresult);
            }
        } catch (SQLException | ZeroException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return custlist;
    }

    public static int modify(Customer customer) {
        if (customer.getCustID() == null || customer.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        String sql = "";
        PreparedStatement ps = null;
        int result = 0;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        try {

            if (customer.getCustName() != null) {
                //language=MySQL
                sql = "update pidb.customer set custName=?, modTime=? where custID=? and userID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, customer.getCustName());
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                ps.setString(3, customer.getCustID());
                ps.setInt(4, customer.getUserID());
                result = ps.executeUpdate();
            }

            if (customer.getAddress() != null) {
                //language=MySQL
                sql = "update pidb.customer set address=?, modTime=? where custID=? and userID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, customer.getAddress());
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                ps.setString(3, customer.getCustID());
                ps.setInt(4, customer.getUserID());
                result = ps.executeUpdate();
            }

            if (customer.getEmail() != null) {
                //language=MySQL
                sql = "update pidb.customer set email=?, modTime=? where custID=? and userID=?";
               ps = conn.prepareStatement(sql);
                ps.setString(1, customer.getEmail());
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                ps.setString(3, customer.getCustID());
                ps.setInt(4, customer.getUserID());
                result = ps.executeUpdate();
            }
            if (customer.getZip() != null) {
                //language=MySQL
                sql = "update pidb.customer set zip=?, modTime=? where custID=? and userID=?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, customer.getZip());
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                ps.setString(3, customer.getCustID());
                ps.setInt(4, customer.getUserID());
                result = ps.executeUpdate();
            }
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }
}


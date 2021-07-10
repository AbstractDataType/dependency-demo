package quesmanagement.dao;

import quesmanagement.entity.Analy;
import quesmanagement.entity.Order;
import quesmanagement.entity.Pay;
import quesmanagement.utils.DBUtil;
import quesmanagement.utils.ZeroException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayDao {
    public static float[] add(Pay pay) {
        if (pay.getPayID() == null || pay.getUserID() == null) {
            return new float[]{-1};
        }
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new float[]{-1};
        }
        float[] result={0};

        try {
            //获取可支付金额
            BigDecimal avaliAmount = new BigDecimal(0);
            BigDecimal totalAmount = new BigDecimal(0);
            BigDecimal paidAmount = new BigDecimal(0);
            Order order = OrderDao.search(new Order(pay.getOrderID(), pay.getUserID())).get(0);
            totalAmount = totalAmount.add(
                    order.getUnitPrice().multiply(new BigDecimal(order.getUnit())));
            if (!order.getType()) {
                for (Analy analy : order.getAnalys()) {
                    totalAmount = totalAmount.add(
                            analy.getUnitPrice().multiply(new BigDecimal(analy.getUnit())));
                }
            }
            for (Pay payt : order.getPays()) {
                paidAmount =paidAmount.add(payt.getAmount());
            }
            avaliAmount = totalAmount .subtract(paidAmount) ;
            if (avaliAmount.compareTo(pay.getAmount()) < 0) {
                try {
                    conn.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                return new float[]{-2,avaliAmount.floatValue()};
            }

            //language=MySQL
            String sql = "insert into pidb.pay (payID,userID,accID,payTime,modTime,empID,orderID,amount) " +
                    "values(?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, pay.getPayID());
            ps.setInt(2, pay.getUserID());
            ps.setString(3, pay.getAccID());
            ps.setDate(4, new java.sql.Date(pay.getPayTime().getTime()));
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setString(6, pay.getEmpID());
            ps.setString(7, pay.getOrderID());
            ps.setFloat(8,pay.getAmount().floatValue());
            result[0] = ps.executeUpdate();

            //language=MySQL
            sql = "update pidb.account set balance= balance + ? " +
                    " where accID=? and userID=?";
            ps = conn.prepareStatement(sql);
            ps.setFloat(1, pay.getAmount().floatValue());
            ps.setString(2, pay.getAccID());
            ps.setInt(3, pay.getUserID());
            result[0]= ps.executeUpdate();

            conn.commit();
        } catch (SQLException | NullPointerException | IndexOutOfBoundsException e) {
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.printStackTrace();
            result[0]=-1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static int delete(Pay pay) {
        if (pay.getPayID() == null || pay.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        int result;
        try {
            //language=MySQL
            String sql = "update pidb.account set balance= balance - " +
                    "(select amount from pidb.pay where payID = ?  and pay.userID=? )" +
                    " and accID=(select pay.accID from pidb.pay where payID = ?  and pay.userID=? ) " +
                    "and userID=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, pay.getPayID());
            ps.setInt(2, pay.getUserID());
            ps.setString(3, pay.getPayID());
            ps.setInt(4, pay.getUserID());
            ps.setInt(5, pay.getUserID());
            result = ps.executeUpdate();

            sql = "delete from pidb.pay where payID=? and userID=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, pay.getPayID());
            ps.setInt(2, pay.getUserID());
            result = ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.printStackTrace();
            result = -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static List<Pay> search(Pay pay) {
        if (pay.getUserID() == null) {
            return null;
        }
        Connection conn = DBUtil.getConnection();
        //payID , orderID ,accID ,custID ,empID ,userID
        String sql = "call pidb.get_pay_by_conditions(?,?,?,?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Pay> resultlist = new ArrayList<Pay>();
        try {
            ps = conn.prepareStatement(sql);
            if (pay.getPayID() != null) {
                ps.setString(1, pay.getPayID());
            } else {
                ps.setNull(1, Types.CHAR);
            }
            if (pay.getOrderID() != null) {
                ps.setString(2, pay.getOrderID());
            } else {
                ps.setNull(2, Types.CHAR);
            }
            if (pay.getAccID() != null) {
                ps.setString(3, pay.getAccID());
            } else {
                ps.setNull(3, Types.CHAR);
            }
            if (pay.getEmpID() != null) {
                ps.setString(4, pay.getEmpID());
            } else {
                ps.setNull(4, Types.CHAR);
            }
            ps.setInt(5, pay.getUserID());
            rs = ps.executeQuery();
            while (rs.next()) {
                Pay result = new Pay();
                result.setPayID(rs.getString("payID"));
                result.setOrderID(rs.getString("orderID"));
                result.setUserID(rs.getInt("userID"));
                result.setAccID(rs.getString("accID"));
                result.setEmpName(rs.getString("empName"));
                result.setEmpID(rs.getString("empID"));
                result.setAmount(new BigDecimal(rs.getString("amount")));
                result.setPayTime(rs.getTimestamp("payTime"));
                result.setModTime(rs.getTimestamp("modTime"));
                resultlist.add(result);
            }
        } catch (SQLException | ZeroException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return resultlist;
    }
}
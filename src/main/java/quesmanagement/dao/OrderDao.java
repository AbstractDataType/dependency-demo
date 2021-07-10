package quesmanagement.dao;

import quesmanagement.entity.*;
import quesmanagement.utils.DBUtil;
import quesmanagement.utils.ZeroException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    public static int add(Order order) {
        if (order.getOrderID() == null || order.getUserID() == null) {
            return -1;
        }
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        int result = 0;

        try {
            //language=MySQL
            String sql = "insert into pidb.orderr (orderID, custID , userID, empID, " +
                    "orderTime,unit,unitPrice, type,modTime) values(?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, order.getOrderID());
            ps.setString(2, order.getCustID());
            ps.setInt(3, order.getUserID());
            ps.setString(4, order.getEmpID());
            ps.setDate(5, new java.sql.Date(order.getOrderTime().getTime()));
            ps.setInt(6, order.getQuestionnaires().size());
            ps.setFloat(7, order.getUnitPrice().floatValue());
            ps.setBoolean(8, order.getType());
            ps.setTimestamp(9, ts);
            result = ps.executeUpdate();

            for (Questionnaire questionnaire : order.getQuestionnaires()) {
                //language=MySQL
                sql = "update pidb.questionnaires set orderID=? , modTime=? where questionnarieID=? and userID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, order.getOrderID());
                ps.setTimestamp(2, ts);
                ps.setString(3, questionnaire.getQuestionnarieID());
                ps.setInt(4, order.getUserID());
                ps.executeUpdate();
            }
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            result = -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    //return -2 => 订单被后续环节占用。
    public static int delete(Order order) {
        if (order.getOrderID() == null || order.getUserID() == null) {
            return -1;
        }

        List<Questionnaire> questionnaires = QuestionnaireDao.search(
                new Questionnaire(null, order.getOrderID(), null, null,
                        null, order.getUserID())
        );

        if (questionnaires != null) {
            for (Questionnaire questionnaire : questionnaires) {
                try {
                    if (!questionnaire.getDistID().equals("") ||
                            !questionnaire.getAnalyID().equals("") ||
                            !questionnaire.getRecvID().equals("")
                    ) {
                        return -2;
                    }
                } catch (NullPointerException ignored) {
                }
            }
        }

        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = null;
        String sql = null;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        int result = 0;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        try {
            if (questionnaires != null) {
                for (Questionnaire questionnaire : questionnaires) {
                    //language=MySQL
                    sql = "update pidb.questionnaires questionnaires set orderID=NULL, recvID=NULL, analyID=NULL," +
                            " distID=NULL, fillTime=NULL, filler=NULL, analyResult=NULL," +
                            " answer=NULL, modTime=? where questionnarieID=? and userID=?";
                    ps = conn.prepareStatement(sql);
                    ps.setTimestamp(1, ts);
                    ps.setString(2, questionnaire.getQuestionnarieID());
                    ps.setInt(3, questionnaire.getUserID());
                    ps.executeUpdate();
                }
            }
            //language=MySQL
            sql = "delete from pidb.orderr where orderID=? and userID=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, order.getOrderID());
            ps.setInt(2, order.getUserID());
            result = ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            result = -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static List<Order> search(Order order) {
        if (order.getUserID() == null) {
            return null;
        }
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Order> acclist = new ArrayList<Order>();
        try {
            //orderID, custID,empID,userID
            //language=MySQL
            String sql = "call pidb.get_order_by_conditions(?,?,?,?)";
            ps = conn.prepareStatement(sql);
            if (order.getOrderID() != null) {
                ps.setString(1, order.getOrderID());
            } else {
                ps.setNull(1, Types.CHAR);
            }
            if (order.getCustID() != null) {
                ps.setString(2, order.getCustID());
            } else {
                ps.setNull(2, Types.CHAR);
            }
            if (order.getEmpID() != null) {
                ps.setString(3, order.getEmpID());
            } else {
                ps.setNull(3, Types.CHAR);
            }
            ps.setInt(4, order.getUserID());
            rs = ps.executeQuery();
            while (rs.next()) {
                Order result = new Order();
                result.setOrderID(rs.getString("orderID"));
                result.setCustID(rs.getString("custID"));
                result.setUserID(rs.getInt("userID"));
                result.setCustName(rs.getString("custName"));
                result.setUnit(rs.getInt("unit"));
                result.setEmpID(rs.getString("empID"));
                result.setEmpname(rs.getString("empName"));
                result.setOrderTime(rs.getDate("orderTime"));
                result.setUnitPrice(BigDecimal.valueOf(rs.getFloat("unitPrice")));
                result.setType(rs.getBoolean("type"));
                result.setModTime(rs.getTimestamp("modTime"));
                result.setQuestionnaires(QuestionnaireDao.search(
                        new Questionnaire(null, order.getOrderID(), null, null,
                                null, order.getUserID())
                ));
                result.setPays(PayDao.search(
                        new Pay(null, order.getOrderID(), order.getUserID())
                ));
                result.setDists(DistDao.search(
                        new Dist(null, order.getOrderID(), order.getUserID())
                ));
                result.setRecvs(RecvDao.search(
                        new Recv(null, order.getOrderID(), order.getUserID())
                ));
                result.setAnalys(AnalyDao.search(
                        new Analy(null, order.getOrderID(), order.getUserID())
                ));
                acclist.add(result);
            }
        } catch (SQLException | ZeroException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return acclist;
    }

}

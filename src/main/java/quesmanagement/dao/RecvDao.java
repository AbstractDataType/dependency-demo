package quesmanagement.dao;

import quesmanagement.entity.Questionnaire;
import quesmanagement.entity.Recv;
import quesmanagement.utils.DBUtil;
import quesmanagement.utils.ZeroException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecvDao {
    public static int add(Recv recv) {
        if (recv.getRecvID() == null || recv.getUserID() == null) {
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
            String sql = "insert into pidb.recv (recvID, orderID , userID, empID, " +
                    "recvTime,modTime) values(?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, recv.getRecvID());
            ps.setString(2, recv.getOrderID());
            ps.setInt(3, recv.getUserID());
            ps.setString(4, recv.getEmpID());
            ps.setDate(5, new java.sql.Date(recv.getRecvTime().getTime()));
            ps.setTimestamp(6, ts);
            result = ps.executeUpdate();

            Questionnaire questionnaire = recv.getQuestionnaire();
            sql = "update pidb.questionnaires set recvID=?, fillTime=?, filler=?, modTime=?, answer=? where questionnarieID=? and userID=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, recv.getRecvID());
            ps.setDate(2, new java.sql.Date(questionnaire.getFillTime().getTime()));
            ps.setString(3, questionnaire.getFiller());
            ps.setTimestamp(4, ts);
            ps.setString(5, questionnaire.getAnswer());
            ps.setString(6, questionnaire.getQuestionnarieID());
            ps.setInt(7, recv.getUserID());
            ps.executeUpdate();

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

    //return -2 => 订单被后续环节占用。
    public static int delete(Recv recv) {
        if (recv.getRecvID() == null || recv.getUserID() == null) {
            return -1;
        }

        List<Questionnaire> questionnaires = QuestionnaireDao.search(
                new Questionnaire(null, recv.getOrderID(), null,
                        recv.getRecvID(), null, recv.getUserID())
        );
        if (questionnaires != null) {
            for (Questionnaire questionnaire : questionnaires) {
                try {
                    if (!questionnaire.getAnalyID().equals("")) {
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
                    sql = "update pidb.questionnaires questionnaires set recvID=NULL, analyID=NULL," +
                            " fillTime=NULL, filler=NULL, analyResult=NULL," +
                            " answer=NULL, modTime=? where questionnarieID=? and userID=?";
                    ps = conn.prepareStatement(sql);
                    ps.setTimestamp(1, ts);
                    ps.setString(2, questionnaire.getQuestionnarieID());
                    ps.setInt(3, questionnaire.getUserID());
                    ps.executeUpdate();
                }
            }
            //language=MySQL
            sql = "delete from pidb.recv where recvID=? and userID=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, recv.getRecvID());
            ps.setInt(2, recv.getUserID());
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

    public static List<Recv> search(Recv recv) {
        if (recv.getUserID() == null) {
            return null;
        }
        Connection conn = DBUtil.getConnection();
        //recvID ,orderID ,empID ,userID
        //language=MySQL
        String sql = "call pidb.get_recv_by_conditions(?,?,?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Recv> resultlist = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            if (recv.getRecvID() != null) {
                ps.setString(1, recv.getRecvID());
            } else {
                ps.setNull(1, Types.CHAR);
            }
            if (recv.getOrderID() != null) {
                ps.setString(2, recv.getOrderID());
            } else {
                ps.setNull(2, Types.CHAR);
            }
            if (recv.getEmpID() != null) {
                ps.setString(3, recv.getEmpID());
            } else {
                ps.setNull(3, Types.CHAR);
            }
            ps.setInt(4, recv.getUserID());
            rs = ps.executeQuery();
            while (rs.next()) {
                Recv result = new Recv();
                result.setRecvID(rs.getString("recvID"));
                result.setOrderID(rs.getString("orderID"));
                result.setUserID(rs.getInt("userID"));
                result.setEmpID(rs.getString("empID"));
                result.setRecvTime(rs.getDate("recvTime"));
                result.setModTime(rs.getTimestamp("modTime"));
                result.setEmpName(rs.getString("empName"));
                result.setQuestionnaire(Objects.requireNonNull(QuestionnaireDao.search(
                        new Questionnaire(null, null, null,
                                result.getRecvID(), null, recv.getUserID())
                )).get(0));
                resultlist.add(result);
            }
        } catch (SQLException | ZeroException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return resultlist;
    }

}

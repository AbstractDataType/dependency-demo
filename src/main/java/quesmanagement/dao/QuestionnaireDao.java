package quesmanagement.dao;

import quesmanagement.entity.Questionnaire;
import quesmanagement.utils.DBUtil;
import quesmanagement.utils.ZeroException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionnaireDao {
    public static int add(Questionnaire questionnaire) {
        if (questionnaire.getQuestionnarieID() == null || questionnaire.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        //language=MySQL
        String sql = "insert into pidb.questionnaires (questionnarieID,userID,title,questionTitle,modTime) values(?,?,?,?,?)";
        int result = 0;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, questionnaire.getQuestionnarieID());
            ps.setInt(2, questionnaire.getUserID());
            ps.setString(3, questionnaire.getTitle());
            ps.setString(4, questionnaire.getQuestionTitle());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static int delete(Questionnaire questionnaire) {
        if (questionnaire.getQuestionnarieID() == null || questionnaire.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        //language=MySQL
        String sql = "delete from pidb.questionnaires where questionnarieID=? and userID=? " +
                "and (orderID is null) and (recvID is null) and (analyID is null)";
        PreparedStatement ps = null;
        int result = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, questionnaire.getQuestionnarieID());
            ps.setInt(2, questionnaire.getUserID());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }


    public static List<Questionnaire> search(Questionnaire questionnaire) {
        if (questionnaire.getUserID() == null) {
            return null;
        }
        Connection conn = DBUtil.getConnection();
        //questionnarieID , orderID , recvID , analyID , distID , userID
        //language=MySQL
        String sql = "call pidb.get_ques_by_conditions(?,?,?,?,?,?)";
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Questionnaire> resultlist = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            if (questionnaire.getQuestionnarieID() != null) {
                ps.setString(1, questionnaire.getQuestionnarieID());
            } else {
                ps.setNull(1, Types.CHAR);
            }
            if (questionnaire.getOrderID() != null) {
                ps.setString(2, questionnaire.getOrderID());
            } else {
                ps.setNull(2, Types.CHAR);
            }
            if (questionnaire.getRecvID() != null) {
                ps.setString(3, questionnaire.getRecvID());
            } else {
                ps.setNull(3, Types.CHAR);
            }
            if (questionnaire.getAnalyID() != null) {
                ps.setString(4, questionnaire.getAnalyID());
            } else {
                ps.setNull(4, Types.CHAR);
            }
            if (questionnaire.getDistID() != null) {
                ps.setString(5, questionnaire.getDistID());
            } else {
                ps.setNull(5, Types.CHAR);
            }
            ps.setInt(6, questionnaire.getUserID());
            rs = ps.executeQuery();
            while (rs.next()) {
                Questionnaire result = new Questionnaire();
                result.setQuestionnarieID(rs.getString("questionnarieID"));
                result.setOrderID(rs.getString("orderID"));
                result.setRecvID(rs.getString("recvID"));
                result.setAnalyID(rs.getString("analyID"));
                result.setDistID(rs.getString("distID"));
                result.setFillTime(rs.getDate("fillTime"));
                result.setFiller(rs.getString("filler"));
                result.setTitle(rs.getString("title"));
                result.setAnalyResult(rs.getString("analyResult"));
                result.setQuestionTitle(rs.getString("questionTitle"));
                result.setAnswer(rs.getString("answer"));
                result.setUserID(rs.getInt("userID"));
                result.setModTime(rs.getTimestamp("modTime"));
                resultlist.add(result);
            }
        } catch (SQLException | ZeroException | NullPointerException e) {
            resultlist = null;
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return resultlist;
    }

    public static int modify(Questionnaire questionnaire) {
        if (questionnaire.getQuestionnarieID() == null || questionnaire.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        String sql;
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        int result = 0;

        try {
            if (questionnaire.getTitle() != null) {
                //language=MySQL
                sql = "update pidb.questionnaires set title=?, modTime=? where questionnarieID=? and userID=?" +
                        "and (orderID is null) and (recvID is null) and (analyID is null)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, questionnaire.getTitle());
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                ps.setString(3, questionnaire.getQuestionnarieID());
                ps.setInt(4, questionnaire.getUserID());
                result = ps.executeUpdate();
            }

            if (questionnaire.getQuestionTitle() != null) {
                //language=MySQL
                sql = "update pidb.questionnaires set questionTitle=?, modTime=? where questionnarieID=? and userID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, questionnaire.getQuestionTitle());
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                ps.setString(3, questionnaire.getQuestionnarieID());
                ps.setInt(4, questionnaire.getUserID());
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
            result = -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static List<Questionnaire> searchNotOrderd(Questionnaire questionnaire) {
        if (questionnaire.getUserID() == null) {
            return null;
        }
        Connection conn = DBUtil.getConnection();
        //questionnarieID , orderID , recvID , analyID , distID , userID
        //language=MySQL
        String sql = "select * from pidb.questionnaires where (orderID is null)" +
                " and (distID is null ) and (recvID is null ) and (analyID is null)" +
                " and userID=?";
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Questionnaire> resultlist = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, questionnaire.getUserID());
            rs = ps.executeQuery();
            while (rs.next()) {
                Questionnaire result = new Questionnaire();
                result.setQuestionnarieID(rs.getString("questionnarieID"));
                result.setOrderID(rs.getString("orderID"));
                result.setRecvID(rs.getString("recvID"));
                result.setAnalyID(rs.getString("analyID"));
                result.setDistID(rs.getString("distID"));
                result.setFillTime(rs.getDate("fillTime"));
                result.setFiller(rs.getString("filler"));
                result.setTitle(rs.getString("title"));
                result.setAnalyResult(rs.getString("analyResult"));
                result.setQuestionTitle(rs.getString("questionTitle"));
                result.setAnswer(rs.getString("answer"));
                result.setUserID(rs.getInt("userID"));
                result.setModTime(rs.getTimestamp("modTime"));
                resultlist.add(result);
            }
        } catch (SQLException | ZeroException | NullPointerException e) {
            resultlist = null;
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return resultlist;
    }

    public static List<Questionnaire> searchNotDisted(Questionnaire questionnaire) {
        if (questionnaire.getUserID() == null || questionnaire.getOrderID() == null) {
            return null;
        }
        Connection conn = DBUtil.getConnection();
        //questionnarieID , orderID , recvID , analyID , distID , userID
        //language=MySQL
        String sql = "select * from pidb.questionnaires where orderID =? " +
                " and (distID is null ) and (recvID is null ) and (analyID is null)" +
                " and userID=?";
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Questionnaire> resultlist = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, questionnaire.getOrderID());
            ps.setInt(2, questionnaire.getUserID());
            rs = ps.executeQuery();
            while (rs.next()) {
                Questionnaire result = new Questionnaire();
                result.setQuestionnarieID(rs.getString("questionnarieID"));
                result.setOrderID(rs.getString("orderID"));
                result.setRecvID(rs.getString("recvID"));
                result.setAnalyID(rs.getString("analyID"));
                result.setDistID(rs.getString("distID"));
                result.setFillTime(rs.getDate("fillTime"));
                result.setFiller(rs.getString("filler"));
                result.setTitle(rs.getString("title"));
                result.setAnalyResult(rs.getString("analyResult"));
                result.setQuestionTitle(rs.getString("questionTitle"));
                result.setAnswer(rs.getString("answer"));
                result.setUserID(rs.getInt("userID"));
                result.setModTime(rs.getTimestamp("modTime"));
                resultlist.add(result);
            }
        } catch (SQLException | ZeroException | NullPointerException e) {
            resultlist = null;
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return resultlist;
    }

    public static List<Questionnaire> searchNotRecved(Questionnaire questionnaire) {
        if (questionnaire.getUserID() == null || questionnaire.getOrderID() == null) {
            return null;
        }
        Connection conn = DBUtil.getConnection();
        //questionnarieID , orderID , recvID , analyID , distID , userID
        //language=MySQL
        String sql = "select * from pidb.questionnaires where orderID =? " +
                " and (distID is not null ) and (recvID is null ) and (analyID is null)" +
                " and userID=?";
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Questionnaire> resultlist = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, questionnaire.getOrderID());
            ps.setInt(2, questionnaire.getUserID());
            rs = ps.executeQuery();
            while (rs.next()) {
                Questionnaire result = new Questionnaire();
                result.setQuestionnarieID(rs.getString("questionnarieID"));
                result.setOrderID(rs.getString("orderID"));
                result.setRecvID(rs.getString("recvID"));
                result.setAnalyID(rs.getString("analyID"));
                result.setDistID(rs.getString("distID"));
                result.setFillTime(rs.getDate("fillTime"));
                result.setFiller(rs.getString("filler"));
                result.setTitle(rs.getString("title"));
                result.setAnalyResult(rs.getString("analyResult"));
                result.setQuestionTitle(rs.getString("questionTitle"));
                result.setAnswer(rs.getString("answer"));
                result.setUserID(rs.getInt("userID"));
                result.setModTime(rs.getTimestamp("modTime"));
                resultlist.add(result);
            }
        } catch (SQLException | ZeroException | NullPointerException e) {
            resultlist = null;
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return resultlist;
    }

    public static List<Questionnaire> searchNotAnalyed(Questionnaire questionnaire) {
        if (questionnaire.getUserID() == null || questionnaire.getOrderID() == null) {
            return null;
        }
        Connection conn = DBUtil.getConnection();
        //questionnarieID , orderID , recvID , analyID , distID , userID
        //language=MySQL
        String sql = "select * from pidb.questionnaires where orderID =? " +
                " and (distID is not null ) and (recvID is not null) and (analyID is null)" +
                " and userID=?";
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Questionnaire> resultlist = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, questionnaire.getOrderID());
            ps.setInt(2, questionnaire.getUserID());
            rs = ps.executeQuery();
            while (rs.next()) {
                Questionnaire result = new Questionnaire();
                result.setQuestionnarieID(rs.getString("questionnarieID"));
                result.setOrderID(rs.getString("orderID"));
                result.setRecvID(rs.getString("recvID"));
                result.setAnalyID(rs.getString("analyID"));
                result.setDistID(rs.getString("distID"));
                result.setFillTime(rs.getDate("fillTime"));
                result.setFiller(rs.getString("filler"));
                result.setTitle(rs.getString("title"));
                result.setAnalyResult(rs.getString("analyResult"));
                result.setQuestionTitle(rs.getString("questionTitle"));
                result.setAnswer(rs.getString("answer"));
                result.setUserID(rs.getInt("userID"));
                result.setModTime(rs.getTimestamp("modTime"));
                resultlist.add(result);
            }
        } catch (SQLException | ZeroException | NullPointerException e) {
            resultlist = null;
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return resultlist;
    }
}




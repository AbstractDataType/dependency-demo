package quesmanagement.dao;

import quesmanagement.entity.Questionnaire;
import quesmanagement.entity.Analy;
import quesmanagement.utils.DBUtil;
import quesmanagement.utils.ZeroException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnalyDao {
    public static int add(Analy analy) {
        if (analy.getAnalyID() == null || analy.getUserID()==null) {
            return -1;
        }
        Timestamp ts= new Timestamp(System.currentTimeMillis());
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps=null;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        int result = 0;

        try {
            //language=MySQL
            String sql = "insert into pidb.analy (analyID, orderID , userID, empID, " +
                    " analyTime, unit , unitPrice,modTime) values(?,?,?,?,?,?,?,?)";
            ps =conn.prepareStatement(sql);
            ps.setString(1,analy.getAnalyID());
            ps.setString(2, analy.getOrderID());
            ps.setInt(3, analy.getUserID());
            ps.setString(4, analy.getEmpID());
            ps.setDate(5, new java.sql.Date(analy.getAnalyTime().getTime()));
            ps.setInt(6, analy.getQuestionnaires().size());
            if (analy.getUnitPrice()==null){
                ps.setNull(7,Types.FLOAT);
            }else {
                ps.setFloat(7, analy.getUnitPrice().floatValue());
            }
            ps.setTimestamp(8, ts );
            result = ps.executeUpdate();

            for (Questionnaire questionnaire : analy.getQuestionnaires()){
                sql="update pidb.questionnaires set analyID=? , analyResult=?, modTime=? where questionnarieID=? and userID=?";
                ps =conn.prepareStatement(sql);
                ps.setString(1,analy.getAnalyID());
                ps.setString(2,questionnaire.getAnalyResult());
                ps.setTimestamp(3,ts);
                ps.setString(4,questionnaire.getQuestionnarieID());
                ps.setInt(5,analy.getUserID());
                ps.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            result=-1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static int delete(Analy analy) {
        if (analy.getAnalyID() == null || analy.getUserID()==null) {
            return -1;
        }

        List<Questionnaire> questionnaires=QuestionnaireDao.search(
                new Questionnaire(null,null,null,null,
                        analy.getAnalyID(), analy.getUserID())
        );
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps= null;
        String sql=null;
        Timestamp ts= new Timestamp(System.currentTimeMillis());
        int result = 0;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        try {
            if (questionnaires!=null) {
                for (Questionnaire questionnaire : questionnaires) {
                    //language=MySQL
                   sql = "update pidb.questionnaires questionnaires set analyID=NULL," +
                            " analyResult=NULL, modTime=? where questionnarieID=? and userID=?";
                    ps =conn.prepareStatement(sql);
                    ps.setTimestamp(1, ts);
                    ps.setString(2, questionnaire.getQuestionnarieID());
                    ps.setInt(3,questionnaire.getUserID());
                    ps.executeUpdate();
                }
            }
             //language=MySQL
            sql = "delete from pidb.analy where analyID=? and userID=?";
            ps =conn.prepareStatement(sql);
            ps.setString(1, analy.getAnalyID());
            ps.setInt(2, analy.getUserID());
            result = ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            result=-1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static List<Analy> search(Analy analy) {
        if ( analy.getUserID()==null) {
            return null;
        }
        Connection conn = DBUtil.getConnection();
        //analyID ,orderID ,empID ,userID
         //language=MySQL
        String sql = "call pidb.get_analy_by_conditions(?,?,?,?)";
        PreparedStatement ps =null;
        ResultSet rs = null;
        List<Analy> resultlist = new ArrayList<>();
        try {
            ps=conn.prepareStatement(sql);
            if (analy.getAnalyID()!= null) {
                ps.setString(1,analy.getAnalyID());
            } else {
                ps.setNull(1, Types.CHAR);
            }
            if (analy.getOrderID() != null) {
                ps.setString(2, analy.getOrderID());
            } else {
                ps.setNull(2, Types.CHAR);
            }
            if (analy.getEmpID() != null) {
                ps.setString(3,analy.getEmpID());
            } else {
                ps.setNull(3, Types.CHAR);
            }
            ps.setInt(4, analy.getUserID());
            rs = ps.executeQuery();
            while (rs.next()) {
                Analy result = new Analy();
                result.setAnalyID(rs.getString("analyID"));
                result.setOrderID(rs.getString("orderID"));
                result.setEmpName(rs.getString("empName"));
                result.setUserID(rs.getInt("userID"));
                result.setEmpID(rs.getString("empID"));
                result.setUnit(rs.getInt("unit"));
                result.setUnitPrice(BigDecimal.valueOf(rs.getFloat("unitPrice")));
                result.setAnalyTime(rs.getDate("analyTime"));
                result.setModTime(rs.getTimestamp("modTime"));
                result.setQuestionnaires(QuestionnaireDao.search(
                        new Questionnaire(null,null,null,
                                null, result.getAnalyID(), analy.getUserID())
                ));
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

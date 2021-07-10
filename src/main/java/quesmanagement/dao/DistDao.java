package quesmanagement.dao;

import quesmanagement.entity.Dist;
import quesmanagement.entity.Questionnaire;
import quesmanagement.utils.DBUtil;
import quesmanagement.utils.ZeroException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DistDao {
    public static int add(Dist dist) {
        if (dist.getDistID() == null || dist.getUserID() == null) {
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
            String sql = "insert into pidb.dist (distID, orderID , userID, empID, " +
                    "distTime, unit ,modTime) values(?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dist.getDistID());
            ps.setString(2, dist.getOrderID());
            ps.setInt(3, dist.getUserID());
            ps.setString(4, dist.getEmpID());
            ps.setDate(5, new java.sql.Date(dist.getDistTime().getTime()));
            ps.setInt(6, dist.getQuestionnaires().size());
            ps.setTimestamp(7, ts);
            result = ps.executeUpdate();

            for (Questionnaire questionnaire : dist.getQuestionnaires()) {
                sql = "update pidb.questionnaires set distID=? , modTime=? where questionnarieID=? and userID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, dist.getDistID());
                ps.setTimestamp(2, ts);
                ps.setString(3, questionnaire.getQuestionnarieID());
                ps.setInt(4, dist.getUserID());
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
    public static int delete(Dist dist) {
        if (dist.getDistID() == null || dist.getUserID() == null) {
            return -1;
        }

        List<Questionnaire> questionnaires = QuestionnaireDao.search(
                new Questionnaire(null, null, dist.getDistID(), null,
                        null, dist.getUserID())
        );

        if (questionnaires != null) {
            for (Questionnaire questionnaire : questionnaires) {
                try {
                    if (!questionnaire.getRecvID().equals("") ||
                            !questionnaire.getAnalyID().equals("")
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
                        sql = "update pidb.questionnaires questionnaires set recvID=NULL, analyID=NULL," +
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
                sql = "delete from pidb.dist where distID=? and userID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, dist.getDistID());
                ps.setInt(2, dist.getUserID());
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

        public static List<Dist> search (Dist dist){
            if (dist.getUserID() == null) {
                return null;
            }
            Connection conn = DBUtil.getConnection();
            //distID ,orderID ,empID ,userID
            //language=MySQL
            String sql = "call pidb.get_dist_by_conditions(?,?,?,?)";
            PreparedStatement ps = null;
            ResultSet rs = null;
            List<Dist> acclist = new ArrayList<>();
            try {
                ps = conn.prepareStatement(sql);
                if (dist.getDistID() != null) {
                    ps.setString(1, dist.getDistID());
                } else {
                    ps.setNull(1, Types.CHAR);
                }
                if (dist.getOrderID() != null) {
                    ps.setString(2, dist.getOrderID());
                } else {
                    ps.setNull(2, Types.CHAR);
                }
                if (dist.getEmpID() != null) {
                    ps.setString(3, dist.getEmpID());
                } else {
                    ps.setNull(3, Types.CHAR);
                }
                ps.setInt(4, dist.getUserID());
                rs = ps.executeQuery();
                while (rs.next()) {
                    Dist result = new Dist();
                    result.setDistID(rs.getString("distID"));
                    result.setOrderID(rs.getString("orderID"));
                    result.setEmpName(rs.getString("empName"));
                    result.setUserID(rs.getInt("userID"));
                    result.setEmpID(rs.getString("empID"));
                    result.setUnit(rs.getInt("unit"));
                    result.setDistTime(rs.getDate("distTime"));
                    result.setModTime(rs.getTimestamp("modTime"));
                    result.setQuestionnaires(QuestionnaireDao.search(
                            new Questionnaire(
                                    null,
                                    null,
                                    result.getDistID(),
                                    null,
                                    null,
                                    dist.getUserID())
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

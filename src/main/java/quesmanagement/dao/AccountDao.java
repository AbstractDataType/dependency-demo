package quesmanagement.dao;

import quesmanagement.entity.Account;
import quesmanagement.utils.DBUtil;
import quesmanagement.utils.ZeroException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountDao {
    public static int add(Account account) {
        if (account.getAccID() == null || account.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        String sql = "insert into pidb.account (accID, userID , bank, bankAccountID, " +
                "balance, modTime) values(?,?,?,?,?,?)";
        int result = 0;
        PreparedStatement ps=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, account.getAccID());
            ps.setInt(2, account.getUserID());
            ps.setString(3, account.getBank());
            ps.setLong(4, account.getBankAccountID());
            ps.setFloat(5, account.getBalance().floatValue());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static int delete(Account account) {
        if (account.getAccID() == null || account.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        String sql = "delete from pidb.account where accID=? and userID=?";
        int result = 0;
        PreparedStatement ps=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, account.getAccID());
            ps.setInt(2, account.getUserID());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }

    public static List<Account> search(Account account) {
        if (account.getUserID() == null) {
            return null;
        }
        Connection conn = DBUtil.getConnection();
        //accID , bank varchar(50),bankAccountID ,userID 
        String sql = "call pidb.get_acc_by_conditions(?,?,?,?)";
        PreparedStatement ps=null;
        ResultSet rs = null;
        List<Account> acclist = new ArrayList<Account>();
        try {
            ps=conn.prepareStatement(sql);
            if (account.getAccID() != null) {
                ps.setString(1, account.getAccID());
            } else {
                ps.setNull(1, Types.CHAR);
            }
            if (account.getBank() != null) {
                ps.setString(2, account.getBank());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }
            if (account.getBankAccountID() != null) {
                ps.setLong(3, account.getBankAccountID());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setInt(4, account.getUserID());

            rs = ps.executeQuery();
            while (rs.next()) {
                Account accresult = new Account();
                accresult.setAccID(rs.getString("accID"));
                accresult.setBank(rs.getString("bank"));
                accresult.setBankAccountID(rs.getLong("bankAccountID"));
                accresult.setUserID(rs.getInt("userID"));
                accresult.setBalance(BigDecimal.valueOf(rs.getFloat("balance")));
                accresult.setModTime(new Date(rs.getTimestamp("modTime").getTime()));
                acclist.add(accresult);
            }
        } catch (SQLException | ZeroException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return acclist;
    }

    public static int modify(Account account) {
        if (account.getAccID() == null || account.getUserID() == null) {
            return -1;
        }
        Connection conn = DBUtil.getConnection();
        String sql = "";
        int result = 0;
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        try {

            if (account.getBank() != null) {
                //language=MySQL
                sql = "update pidb.account set bank=?, modTime=? where accID=? and userID=?";
                ps=conn.prepareStatement(sql);
                ps.setString(1, account.getBank());
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                ps.setString(3, account.getAccID());
                ps.setInt(4, account.getUserID());
                result = ps.executeUpdate();
            }

            if (account.getBankAccountID() != null) {
                //language=MySQL
                sql = "update pidb.account set bankAccountID=?, modTime=? where accID=? and userID=?";
                ps=conn.prepareStatement(sql);

                ps.setLong(1, account.getBankAccountID());
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                ps.setString(3, account.getAccID());
                ps.setInt(4, account.getUserID());
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
            result=-1;
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return result;
    }
}


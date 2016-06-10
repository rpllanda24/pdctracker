package com.accenture.aswhcm.tracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.accenture.aswhcm.tracker.domain.TimeTracker;
import com.accenture.aswhcm.tracker.domain.User;

@Repository("timeTrackerDao")
public class TimeTrackerDaoImpl
    implements TimeTrackerDao {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public List<TimeTracker> getTimeTrackerList(String userId, Date startDate, Date endDate) {

        String sql;
        if (startDate != null && endDate != null) {
            sql =
                "SELECT * FROM timetracker t inner join user u on u.username = t.userid where t.userId = ? and t.login between ? and ? order by t.id";
        } else {
            sql =
                "SELECT * FROM timetracker t inner join user u on u.username = t.userid where t.userId = ? order by t.id";
        }

        Connection conn = null;
        List<TimeTracker> result = new ArrayList<TimeTracker>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            if (startDate != null && endDate != null) {
                ps.setDate(1, new java.sql.Date(startDate.getTime()));
                ps.setDate(2, new java.sql.Date(endDate.getTime()));
            } else {
                ps.setString(1, userId);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("userId"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                TimeTracker timeTracker = new TimeTracker(rs.getInt("id"), rs.getTimestamp("login"),
                    rs.getTimestamp("logout"), rs.getTimestamp("logincorrection"), rs.getTimestamp("logoutcorrection"),
                    rs.getString("comment"), user);

                result.add(timeTracker);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return result;
    }

    public boolean createTimeTracker(TimeTracker timeTracker) {

        boolean result = false;
        String sql = "INSERT INTO TIMETRACKER (userid,login) VALUES (?,?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, timeTracker.getUser().getUserId());
            ps.setObject(2, new java.sql.Timestamp(timeTracker.getLogin().getTime()));
            int number = ps.executeUpdate();
            if (number != 0) {
                result = true;
            }
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return result;
    }

    public boolean updateTimeTracker(TimeTracker timeTracker) {

        String sql =
            "UPDATE TIMETRACKER SET USERID = ?, LOGIN = ?, LOGOUT = ?, LOGINCORRECTION = ?, LOGOUTCORRECTION=?, COMMENT = ? WHERE ID = ?";
        Connection conn = null;
        boolean result = false;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, timeTracker.getUser().getUserId());
            Date login = timeTracker.getLogin();
            if (login == null) {
                ps.setObject(2, null);
            } else {
                ps.setObject(2, new java.sql.Timestamp(login.getTime()));
            }

            Date logout = timeTracker.getLogout();
            if (logout == null) {
                ps.setObject(3, null);
            } else {
                ps.setObject(3, new java.sql.Timestamp(logout.getTime()));
            }
            Date loginCorrection = timeTracker.getLoginCorrection();
            if (loginCorrection == null) {
                ps.setObject(4, null);
            } else {

                ps.setObject(4, new java.sql.Timestamp(loginCorrection.getTime()));
            }

            Date logoutCorrection = timeTracker.getLogoutCorrection();
            if (logoutCorrection == null) {
                ps.setObject(5, null);
            } else {
                ps.setObject(5, new java.sql.Timestamp(logoutCorrection.getTime()));
            }

            ps.setString(6, timeTracker.getComment());
            ps.setInt(7, timeTracker.getId());
            int number = ps.executeUpdate();
            if (number != 0) {
                result = true;
            }

            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return result;
    }

    public TimeTracker getLastTimeTracker(String userId) {

        String sql = "SELECT * FROM TIMETRACKER WHERE USERID = ? ORDER BY ID DESC LIMIT 1";

        Connection conn = null;
        TimeTracker timeTracker = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                timeTracker = new TimeTracker(rs.getInt("id"), rs.getTimestamp("login"), rs.getTimestamp("logout"),
                    rs.getTimestamp("logincorrection"), rs.getTimestamp("logoutcorrection"), rs.getString("comment"),
                    new User(userId));

            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return timeTracker;
    }

    public TimeTracker getTimeTracker(int id) {

        String sql = "select * from timetracker where id = ?";

        Connection conn = null;
        TimeTracker timeTracker = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                timeTracker = new TimeTracker(rs.getInt("id"), rs.getTimestamp("login"), rs.getTimestamp("logout"),
                    rs.getTimestamp("logincorrection"), rs.getTimestamp("logoutcorrection"), rs.getString("comment"),
                    new User(rs.getString("userId")));

            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return timeTracker;
    }

}

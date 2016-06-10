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

import com.accenture.aswhcm.tracker.domain.LeaveRequest;
import com.accenture.aswhcm.tracker.domain.LeaveTracker;
import com.accenture.aswhcm.tracker.domain.LeaveType;
import com.accenture.aswhcm.tracker.domain.Request;
import com.accenture.aswhcm.tracker.domain.User;

@Repository("leaveTrackerDao")
public class LeaveTrackerDaoImpl
    implements LeaveTrackerDao {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public List<LeaveTracker> getLeaveTrackerList(String userId, Date start, Date end) {

        String sql;
        if (start != null && end != null) {
            sql =
                "SELECT l.*,lt.leave_type as leave_type, u.lastName as emp_lastName, u.firstName as emp_firstName, a.lastName as appr_lastName, a.firstName as app_firstName FROM leavetracker l inner join user u on u.username = l.userId inner join user a on a.username = l.approverId inner join leave_type lt on lt.id = l.leaveType where l.userId = ? and l.startDate between ? and ? order by id";
        } else {
            sql =
                "SELECT l.*,lt.leave_type as leave_type, u.lastName as emp_lastName, u.firstName as emp_firstName, a.lastName as appr_lastName, a.firstName as app_firstName FROM leavetracker l inner join user u on u.username = l.userId inner join user a on a.username = l.approverId inner join leave_type lt on lt.id = l.leaveType where l.userId = ?";
        }

        Connection conn = null;
        List<LeaveTracker> result = new ArrayList<LeaveTracker>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            if (start != null && end != null) {
                ps.setDate(1, new java.sql.Date(start.getTime()));
                ps.setDate(2, new java.sql.Date(end.getTime()));
            } else {
                ps.setString(1, userId);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User emp = new User();
                emp.setFirstName(rs.getString("emp_firstName"));
                emp.setLastName(rs.getString("emp_lastName"));

                User appr = new User();
                appr.setFirstName(rs.getString("app_firstName"));
                appr.setLastName(rs.getString("appr_lastName"));

                LeaveTracker leaveTracker =
                    new LeaveTracker(rs.getInt("id"), rs.getString("leave_type"), rs.getTimestamp("startDate"),
                        rs.getTimestamp("endDate"), rs.getString("status"), emp, appr, rs.getInt("leaveType"));

                result.add(leaveTracker);
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

    public List<LeaveType> getLeaveTypes() {

        String sql = "select * from leave_type";
        Connection conn = null;
        List<LeaveType> result = new ArrayList<LeaveType>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveType leavetype =
                    new LeaveType(rs.getInt("id"), rs.getString("leave_type"), rs.getBoolean("for_approval"));
                result.add(leavetype);
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

    public boolean saveLeave(LeaveTracker leave) throws SQLException {

        boolean result = false;
        String sql =
            "INSERT INTO leavetracker (userid,leaveType,startDate,endDate,status,approverId,requestId) VALUES (?,?,?,?,?,?,?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, leave.getUser().getUserId());
            ps.setInt(2, leave.getLeaveTypeId());
            ps.setDate(3, new java.sql.Date(leave.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(leave.getEndDate().getTime()));
            ps.setString(5, leave.getStatus());
            ps.setString(6, leave.getApprover().getUserId());
            ps.setInt(7, leave.getRequestId());

            int number = ps.executeUpdate();
            if (number != 0) {
                result = true;
            }
            ps.close();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return result;
    }

    public int saveRequest(Request request) throws SQLException {

        int number;
        String sql = "INSERT INTO request (userId,approverId,requestType,requestDate,status) VALUES (?,?,?,?,?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, request.getUserId());
            ps.setString(2, request.getApproverId());
            ps.setString(3, request.getRequestType());
            ps.setDate(4, new java.sql.Date(request.getRequestDate().getTime()));
            ps.setString(5, request.getStatus());

            number = ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return number;
    }

    public int lastRequestId(String userId) throws SQLException {

        String sql = "select id from request where userId = ? order by id desc limit 1";
        Connection conn = null;
        int result = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getInt("id");
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return result;
    }

    public List<LeaveRequest> getLeaveRequestsByApprover(String approverId, String status) throws SQLException {

        String sql =
            "select r.id,r.requestDate, r.status, l.id as leavetrackerId, l.startDate,l.endDate,u.firstName, u.lastName,u.username, lt.leave_type from request r inner join leavetracker l on r.id = l.requestId inner join user u on u.username = r.userId inner join leave_type lt on lt.id = l.leaveType where requestType = 'Leave' and l.approverId = ? and r.status = ? order by r.id desc";
        Connection conn = null;

        List<LeaveRequest> leaveRequests = new ArrayList<LeaveRequest>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, approverId);
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveRequest leaveRequest = new LeaveRequest();
                Request request = new Request();
                request.setId(rs.getInt("id"));
                request.setRequestDate(rs.getTimestamp("requestDate"));
                request.setStatus(rs.getString("status"));

                leaveRequest.setRequest(request);

                LeaveTracker leave = new LeaveTracker();
                leave.setId(rs.getInt("leavetrackerId"));
                leave.setStartDate(rs.getTimestamp("startDate"));
                leave.setEndDate(rs.getTimestamp("endDate"));
                leave.setLeaveType(rs.getString("leave_type"));

                User user = new User();
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));

                leave.setUser(user);

                leaveRequest.setLeave(leave);

                leaveRequests.add(leaveRequest);

            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return leaveRequests;
    }

    public boolean updateRequestStatus(int id, String status) throws SQLException {

        boolean result = false;
        String sql = "update request set status = ? where id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);

            int number = ps.executeUpdate();
            if (number != 0) {
                result = true;
            }
            ps.close();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return result;
    }

    public LeaveRequest getLeaveRequestById(String approverId, int id) throws SQLException {

        String sql =
            "select r.id,r.requestDate, r.status, l.id as leavetrackerId, l.startDate,l.endDate,u.firstName, u.lastName,u.username, lt.leave_type from request r inner join leavetracker l on r.id = l.requestId inner join user u on u.username = r.userId inner join leave_type lt on lt.id = l.leaveType where requestType = 'Leave' and l.approverId = ? and r.id = ? order by r.id desc";
        Connection conn = null;

        LeaveRequest leaveRequest = new LeaveRequest();
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, approverId);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Request request = new Request();
                request.setId(rs.getInt("id"));
                request.setRequestDate(rs.getTimestamp("requestDate"));
                request.setStatus(rs.getString("status"));

                leaveRequest.setRequest(request);

                LeaveTracker leave = new LeaveTracker();
                leave.setId(rs.getInt("leavetrackerId"));
                leave.setStartDate(rs.getTimestamp("startDate"));
                leave.setEndDate(rs.getTimestamp("endDate"));
                leave.setLeaveType(rs.getString("leave_type"));

                User user = new User();
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));

                leave.setUser(user);

                leaveRequest.setLeave(leave);

            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return leaveRequest;
    }

    public boolean updateLeaveStatus(int id, String status) throws SQLException {

        boolean result = false;
        String sql = "update leavetracker set status = ? where id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);

            int number = ps.executeUpdate();
            if (number != 0) {
                result = true;
            }
            ps.close();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return result;
    }

}

package com.accenture.aswhcm.tracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.accenture.aswhcm.tracker.domain.User;

@Repository("userDao")
public class UserDaoImpl
    implements UserDao {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public List<User> getUsers() {

        String sql = "SELECT * FROM USER";

        Connection conn = null;
        List<User> users = new ArrayList<User>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
                    rs.getInt("role"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("team"));
                users.add(user);
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
        return users;
    }

    public boolean createUser(User user) {

        String sql = "INSERT INTO USER (username,password,role,firstName,lastName,team) VALUES (?,?,?,?,?,?)";

        Connection conn = null;
        boolean result = false;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassw());
            ps.setInt(3, user.getRole());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getTeam());
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

    public boolean login(User user) {

        String sql = "SELECT * FROM USER where username = ? and password = ?";

        Connection conn = null;
        boolean flag = false;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassw());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                flag = true;
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
        return flag;
    }

    public boolean deleteUser(int id) {

        String sql = "DELETE FROM USER where id = ?";
        Connection conn = null;
        boolean result = false;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
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

    public boolean resetPassword(int id, String password) {

        String sql = "UPDATE USER SET PASSWORD = ? WHERE ID = ?";
        Connection conn = null;
        boolean result = false;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, password);
            ps.setInt(2, id);
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

    public User getUser(int id) {

        String sql = "SELECT * FROM USER  WHERE ID = ?";

        Connection conn = null;
        User user = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getInt("role"),
                    rs.getString("firstName"), rs.getString("lastName"), rs.getString("team"));
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
        return user;
    }

    @Override
    public boolean updateUser(User user) {

        String sql = "UPDATE USER SET username=?,password=?,role=?,firstName=?,lastName=?,team=? WHERE id=?";

        Connection conn = null;
        boolean result = false;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassw());
            ps.setInt(3, user.getRole());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getTeam());
            ps.setLong(7, user.getId());
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

}

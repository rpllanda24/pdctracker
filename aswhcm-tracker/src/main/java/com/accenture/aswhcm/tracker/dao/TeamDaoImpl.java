package com.accenture.aswhcm.tracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.accenture.aswhcm.tracker.domain.Team;

@Repository("teamDao")
public class TeamDaoImpl
    implements TeamDao {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public List<Team> getTeams() {

        String sql = "SELECT * FROM TEAM";

        Connection conn = null;
        List<Team> teams = new ArrayList<Team>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Team team = new Team(rs.getInt("id"), rs.getString("team_code"), rs.getString("team_desc"));
                teams.add(team);
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
        return teams;
    }

    public Team getTeam(int id) {

        String sql = "SELECT * FROM TEAM  WHERE ID = ?";

        Connection conn = null;
        Team team = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                team = new Team(rs.getInt("id"), rs.getString("team_code"), rs.getString("team_code"));
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
        return team;
    }

    public boolean createTeam(Team team) {

        String sql = "INSERT INTO ASWHCMTRACKER.TEAM (team_code,team_desc) VALUES (?,?)";

        Connection conn = null;
        boolean result = false;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, team.getTeamCode());
            ps.setString(2, team.getTeamDesc());
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

    @Override
    public boolean updateTeam(Team team) {

        String sql = "UPDATE ASWHCMTRACKER.TEAM SET team_code=?,team_desc=? WHERE id=?";
        Connection conn = null;
        boolean result = false;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, team.getTeamCode());
            ps.setString(2, team.getTeamDesc());
            ps.setLong(3, team.getId());
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

    public boolean deleteTeam(int id) {

        String sql = "DELETE FROM ASWHCMTRACKER.TEAM where id = ?";
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

}

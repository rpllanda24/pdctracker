package com.accenture.aswhcm.tracker.domain;

public class Team {

    private int id;

    private String teamCode;

    private String teamDesc;

    public Team(int id, String teamCode, String teamDesc) {
        super();
        this.id = id;
        this.teamCode = teamCode;
        this.teamDesc = teamDesc;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getTeamCode() {

        return teamCode;
    }

    public void setTeamCode(String teamCode) {

        this.teamCode = teamCode;
    }

    public String getTeamDesc() {

        return teamDesc;
    }

    public void setTeamDesc(String teamDesc) {

        this.teamDesc = teamDesc;
    }

}

package com.accenture.aswhcm.tracker.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Team {

    private int id;

    private String teamCode;

    private String teamDesc;

    public Team() {
        super();
    }

    public Team(String teamCode, String teamDesc) {
        super();
        this.teamCode = teamCode;
        this.teamDesc = teamDesc;
    }

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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj, false);
    }

    /**
     * Converts the CopyConfig to String object.
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this);
    }

}

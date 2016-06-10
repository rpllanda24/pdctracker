package com.accenture.aswhcm.tracker.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class User {

    private int id;

    private String userId;

    private String passw;

    private int role;

    private String firstName;

    private String lastName;

    private String team;

    public User() {
        super();
    }

    public User(String userId) {
        super();
        this.userId = userId;
    }

    public User(int id, String userId, String passw, int role, String firstName, String lastName, String team) {
        super();
        this.id = id;
        this.userId = userId;
        this.passw = passw;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

    public String getPassw() {

        return passw;
    }

    public void setPassw(String passw) {

        this.passw = passw;
    }

    public int getRole() {

        return role;
    }

    public void setRole(int role) {

        this.role = role;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public String getTeam() {

        return team;
    }

    public void setTeam(String team) {

        this.team = team;
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

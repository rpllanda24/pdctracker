package com.accenture.aswhcm.tracker.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TimeTracker {

    private int id;

    private Date login;

    private Date logout;

    private Date loginCorrection;

    private Date logoutCorrection;

    private String comment;

    private User user;

    public TimeTracker() {
        super();
    }

    public TimeTracker(Date login, String user) {
        super();
        this.login = login;
        this.user = new User(user);
    }

    public TimeTracker(int id, Date login, Date logout, Date loginCorrection, Date logoutCorrection, String comment,
                       User user) {
        super();
        this.id = id;
        this.login = login;
        this.logout = logout;
        this.loginCorrection = loginCorrection;
        this.logoutCorrection = logoutCorrection;
        this.comment = comment;
        this.user = user;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public Date getLogin() {

        return login;
    }

    public void setLogin(Date login) {

        this.login = login;
    }

    public Date getLogout() {

        return logout;
    }

    public void setLogout(Date logout) {

        this.logout = logout;
    }

    public Date getLoginCorrection() {

        return loginCorrection;
    }

    public void setLoginCorrection(Date loginCorrection) {

        this.loginCorrection = loginCorrection;
    }

    public Date getLogoutCorrection() {

        return logoutCorrection;
    }

    public void setLogoutCorrection(Date logoutCorrection) {

        this.logoutCorrection = logoutCorrection;
    }

    public String getComment() {

        return comment;
    }

    public void setComment(String comment) {

        this.comment = comment;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
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

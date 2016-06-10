package com.accenture.aswhcm.tracker.web.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CorrectionData {

    private int id;

    private String loginCorrection;

    private String logoutCorrection;

    private String comment;

    public CorrectionData() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CorrectionData(int id, String loginCorrection, String logoutCorrection, String comment) {
        super();
        this.id = id;
        this.loginCorrection = loginCorrection;
        this.logoutCorrection = logoutCorrection;
        this.comment = comment;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getLoginCorrection() {

        return loginCorrection;
    }

    public void setLoginCorrection(String loginCorrection) {

        this.loginCorrection = loginCorrection;
    }

    public String getLogoutCorrection() {

        return logoutCorrection;
    }

    public void setLogoutCorrection(String logoutCorrection) {

        this.logoutCorrection = logoutCorrection;
    }

    public String getComment() {

        return comment;
    }

    public void setComment(String comment) {

        this.comment = comment;
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

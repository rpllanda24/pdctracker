package com.accenture.aswhcm.tracker.web;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The <code>FaultDetails</code> as "results" part of web service fault response holds information
 * about error code and its string description. It provides also ErrorCodes constants.
 * <p/>
 * The data object is intended to be immutable.
 */
public class FaultDetails {

    private String cause;

    public FaultDetails(String cause) {

        super();
        this.cause = cause;
    }

    public String getCause() {

        return cause;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return new HashCodeBuilder(31, 1).append(cause).toHashCode();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FaultDetails other = (FaultDetails) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(cause, other.cause).isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this);
    }

}

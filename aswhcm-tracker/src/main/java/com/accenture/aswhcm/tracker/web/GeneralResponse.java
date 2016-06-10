package com.accenture.aswhcm.tracker.web;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * GeneralResponse is the object that represent the general response for any RESTful WebService
 * method.
 * <p/>
 * The data object is intended to be immutable.
 * 
 * @param <E> the type of results in this object
 */
public class GeneralResponse<E> {

    private Boolean success;

    private E results;

    private long total;

    public GeneralResponse(Boolean success, E results, long total) {

        super();
        this.success = success;
        this.results = results;
        this.total = total;
    }

    public GeneralResponse(Boolean success, E results) {

        super();
        this.success = success;
        this.results = results;
    }

    public Boolean getSuccess() {

        return success;
    }

    public E getResults() {

        return results;
    }

    public long getTotal() {

        return total;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return new HashCodeBuilder(31, 1).append(success).append(results).toHashCode();
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

        @SuppressWarnings("unchecked")
        GeneralResponse<E> other = (GeneralResponse<E>) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(success, other.success)
            .append(results, other.results).isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this);
    }

}

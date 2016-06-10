package com.accenture.aswhcm.tracker.web;

/**
 * FaultResponse is the object that represent fault response for any RESTful WebService method.
 * <p/>
 * The data object is intended to be immutable.
 */
public class FaultResponse
    extends GeneralResponse<FaultDetails> {

    public FaultResponse(Boolean success, FaultDetails results) {

        super(success, results);
    }

}

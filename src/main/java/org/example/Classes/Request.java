package org.example.Classes;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private String requestType;
    private Object requestData;

    public Request(String requestType, Object requestData) {
        this.requestType = requestType;
        this.requestData = requestData;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Object getRequestData() {
        return requestData;
    }

    public void setRequestData(Object requestData) {
        this.requestData = requestData;
    }
}


package com.model;

import com.enums.RequestType;
import com.enums.WhoRequest;

import java.util.List;

/**
 * Created by Artemie on 04.10.2016.
 */
public class Model {
    private String nodeId;
    private WhoRequest whoRequest;
    private RequestType requestType;
    private int fromPort;
    private int toPort;//5051
    private String fromHostname;
    private String toHostname;//233.0.0.1
    private String message;
    private List<Empl> emplList;
    private int countConnections;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public WhoRequest getWhoRequest() {
        return whoRequest;
    }

    public void setWhoRequest(WhoRequest whoRequest) {
        this.whoRequest = whoRequest;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getFromHostname() {
        return fromHostname;
    }

    public void setFromHostname(String fromHostname) {
        this.fromHostname = fromHostname;
    }

    public List<Empl> getEmplList() {
        return emplList;
    }

    public void setEmplList(List<Empl> emplList) {
        this.emplList = emplList;
    }

    public int getFromPort() {
        return fromPort;
    }

    public void setFromPort(int fromPort) {
        this.fromPort = fromPort;
    }

    public int getToPort() {
        return toPort;
    }

    public void setToPort(int toPort) {
        this.toPort = toPort;
    }

    public String getToHostname() {
        return toHostname;
    }

    public void setToHostname(String toHostname) {
        this.toHostname = toHostname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCountConnections() {
        return countConnections;
    }

    public void setCountConnections(int countConnections) {
        this.countConnections = countConnections;
    }
}

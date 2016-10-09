package com.model;

import com.enums.RequestType;
import com.enums.WhoRequest;

import java.util.Date;

/**
 * Created by Artemie on 04.10.2016.
 */
public class Model {
    private String nodeId;
    private WhoRequest whoRequest;
    private RequestType requestType;
    private String hostname;
    private String port;
    //
    private Integer id;
    private String firstname;
    private String lastname;
    private int age;
    private int salary;
    private Date createdDate;

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

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}

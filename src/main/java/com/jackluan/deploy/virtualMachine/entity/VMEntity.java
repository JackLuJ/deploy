package com.jackluan.deploy.virtualMachine.entity;

public class VMEntity {

    private String id;

    private String ip;

    private String port;

    private Integer status;

    public VMEntity(){

    }

    public VMEntity(String id, String ip, String port, Integer status) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VMEntity{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", status=" + status +
                '}';
    }
}

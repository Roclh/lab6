package com.classes;

import java.net.InetAddress;

public class Connection implements Comparable<Connection>{
    public String userName;
    public String password;
    public int PORT;
    public InetAddress address;

    public Connection(String userName, String password, int PORT, InetAddress address){
        this.userName = userName;
        this.password = password;
        this.PORT = PORT;
        this.address = address;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int compareTo(Connection o) {
        return 0;
    }
}

package com.classes;

import java.net.InetAddress;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Consumer;

public class AllConnections {
    public static Queue<Connection> currentConnetions = new PriorityQueue<>();


    public static void connect(Connection connection){
        if(!currentConnetions.contains(connection)){
            currentConnetions.offer(connection);
        } else{
            System.out.println("Такое соединение уже существует");
        }
    }
    public static Connection getConnectionByAddressAndPort(InetAddress inetAddress, int PORT){
        return currentConnetions.stream().filter(connection -> connection.getAddress().equals(inetAddress)&&connection.getPORT()==PORT).findFirst().get();
    }
    public static Connection getConnectionByUserName(String UserName){
        return currentConnetions.stream().filter(connection -> connection.getUserName().equals(UserName)).findFirst().get();
    }

    public static boolean isDistinct(String userName){
            return JDBCConnection.checkLogin(userName);
    }


    public static void disconnect(Connection connection){
        currentConnetions.remove(connection);
    }

    public static void doWithAll(Consumer<Connection> func){
        currentConnetions.forEach(func);
    }


}

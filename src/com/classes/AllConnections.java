package com.classes;

import java.net.InetAddress;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AllConnections {
    public static Queue<Connection> currentConnections = new PriorityBlockingQueue<>();

    public static boolean contains(Predicate<Connection> predicate){
        try {
            currentConnections.stream().filter(predicate).findAny().get();
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }



    public static boolean connect(Connection connection){
        if(!AllConnections.contains(connection1 -> connection1.getUserName().equals(connection.getUserName()))){
            if(JDBCConnection.logIn(connection.getUserName(), connection.getPassword())){
                currentConnections.offer(connection);
                return true;
            }
        } else{
            System.out.println("Такое соединение уже существует");
            return false;
        }
        return false;
    }
    public static Connection getConnectionByAddressAndPort(InetAddress inetAddress, int PORT){
        return currentConnections.stream().filter(connection -> connection.getAddress().equals(inetAddress)&&connection.getPORT()==PORT).findFirst().get();
    }
    public static Connection getConnectionByUserName(String UserName){
        return currentConnections.stream().filter(connection -> connection.getUserName().equals(UserName)).findFirst().get();
    }

    public static boolean isDistinct(String userName){
            return JDBCConnection.checkLogin(userName);
    }


    public static void disconnect(Connection connection){
        currentConnections.remove(connection);
    }

    public static void doWithAll(Consumer<Connection> func){
        currentConnections.forEach(func);
    }


}

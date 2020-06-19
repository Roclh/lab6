package com.classes.serverSide;

import com.classes.AllConnections;
import com.classes.Connection;
import com.classes.JDBCConnection;
import com.classes.serverSide.answers.*;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Listener extends Thread {
    private final static Logger logger = Logger.getLogger(Listener.class.getName());
    private ExecutorService pool;
    private int poolSize;
    private DatagramSocket datagramSocket;
    private Sender sender;
    private CommandProcessingModule commandProcessingModule;

    public static ExecutorService newFixedPoolThread(){
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }

    public Listener(DatagramSocket datagramSocket, Sender sender, CommandProcessingModule commandProcessingModule) {
        pool = newFixedPoolThread();
        poolSize = 8;
        this.datagramSocket = datagramSocket;
        this.sender = sender;
        this.commandProcessingModule = commandProcessingModule;
    }

    @Override
    public void run() {
        for(int i = 0; i < poolSize; i++){
            ListenerThread task = new ListenerThread(datagramSocket, sender, commandProcessingModule);
            pool.execute(task);
        }
    }

}

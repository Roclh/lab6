package com.classes.serverSide;


import com.classes.serverSide.answers.Answer;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Sender extends Thread {
    private DatagramSocket socket;
    private ExecutorService pool;

    private static ExecutorService newFixedThreadPool(int nThreads){
        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

    }

    public Sender(DatagramSocket datagramSocket) {
        pool = newFixedThreadPool(8);
        this.socket = datagramSocket;
    }

    @Override
    public void run() {
        while (!isInterrupted()) ;
    }

    public void send(Answer answer, InetAddress inetAddress, int PORT) {
        SenderThread task = new SenderThread(answer, inetAddress, PORT, socket);
        pool.execute(task);
    }

}

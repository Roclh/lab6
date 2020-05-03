package com;

import com.classes.WorkSpace;
import com.classes.serverSide.CommandProcessingModule;
import com.classes.serverSide.Listener;
import com.classes.serverSide.Sender;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static final int PORT = 62222;
    private static final Logger logger = Logger.getLogger("Server");


    public static void main(String[] args) {
        ServerStart();
        new WorkSpace();
    }

    public static void ServerStart(){
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if(datagramSocket!=null){
            logger.info("Сервер запущен.");
            CommandProcessingModule commandProcessingModule = new CommandProcessingModule();
            Sender sender = new Sender(datagramSocket);
            Listener listener = new Listener(datagramSocket, sender, commandProcessingModule);

            sender.setDaemon(true);
            listener.setDaemon(true);

            sender.start();
            listener.start();

        }
    }
}

package com;

import com.classes.Terminal;
import com.classes.WorkSpace;
import com.classes.serverSide.CommandProcessingModule;
import com.classes.serverSide.Listener;
import com.classes.serverSide.Sender;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static int PORT = 62222;
    private static final Logger logger = Logger.getLogger("Server");


    public static void main(String[] args) {
        ServerStart();
        new WorkSpace();
    }

    public static void ServerStart(){
        DatagramSocket datagramSocket = null;
        while(true){
            try {
                int port = Integer.parseInt(Terminal.readLine("Введите порт:"));
                datagramSocket = new DatagramSocket(port);
                PORT = port;
                break;
            } catch (SocketException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        if(datagramSocket.isBound()){
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

package com.classes.serverSide;

import com.classes.serverSide.answers.*;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class Listener extends Thread {
    private final static Logger logger = Logger.getLogger(Listener.class.getName());
    private DatagramSocket datagramSocket;
    private Sender sender;
    private CommandProcessingModule commandProcessingModule;

    public Listener(DatagramSocket datagramSocket, Sender sender, CommandProcessingModule commandProcessingModule) {
        this.datagramSocket = datagramSocket;
        this.sender = sender;
        this.commandProcessingModule = commandProcessingModule;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                byte[] bytes = new byte[16384];
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                datagramSocket.receive(datagramPacket);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

                Request request = (Request) objectInputStream.readObject();
                logger.info("Получен запрос от " + datagramPacket.getAddress() + ":" +
                        datagramPacket.getPort() + " - " + request.getCommand() + " " + request.getArg1() + " " + request.getArg2());

                try {
                    Answer answer = commandProcessingModule.handle(request);
                    sender.send(answer, datagramPacket.getAddress(), datagramPacket.getPort());
                } catch (NullPointerException e) {
                    sender.send(new ErrAnswer("Сервер не смог выполнить команду"), datagramPacket.getAddress(), datagramPacket.getPort());
                    e.printStackTrace();
                }

                byteArrayInputStream.close();
                objectInputStream.close();
            } catch (IOException | ClassNotFoundException e) {
               System.out.println(e.getMessage());
            }


        }
    }
//
//    public static boolean portAvailable(int port) {
//        DatagramSocket datagramSocket;
//        try {
//            datagramSocket = new DatagramSocket(port);
//            InetSocketAddress inetSocketAddress = new InetSocketAddress(address, port);
//            DatagramPacket datagramPacket = new DatagramPacket("Проверка".getBytes(), "Проверка".getBytes().length, inetSocketAddress);
//            datagramSocket.send(datagramPacket);
//            return true;
//        } catch (IOException e) {
//            return false;
//        }
//    }

}

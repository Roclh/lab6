package com.classes.serverSide;

import com.classes.AllConnections;
import com.classes.Connection;
import com.classes.JDBCConnection;
import com.classes.serverSide.answers.Answer;
import com.classes.serverSide.answers.ErrAnswer;
import com.classes.serverSide.answers.FineAnswer;
import com.classes.serverSide.answers.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Logger;

public class ListenerThread extends Thread{
    private final static Logger logger = Logger.getLogger(Listener.class.getName());
    private DatagramSocket datagramSocket;
    private Sender sender;
    private CommandProcessingModule commandProcessingModule;

    public ListenerThread(DatagramSocket datagramSocket, Sender sender, CommandProcessingModule commandProcessingModule) {
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
                logger.info("Получен запрос от " + request.getUserName()+datagramPacket.getAddress() + ":" +
                        datagramPacket.getPort() + " - " + request.getCommand() + " " + request.getArg1() + " " + request.getArg2());

                if(request.getCommand().equals("login")){
                    if(JDBCConnection.logIn(request.getArg1(), request.getArg2())){
                        Connection connection = new Connection(request.getArg1(), request.getArg2(), datagramPacket.getPort(), datagramPacket.getAddress());
                        AllConnections.connect(connection);
                        sender.send(new FineAnswer("AllFine"), connection.address, connection.PORT);
                    }else{
                        sender.send(new ErrAnswer("WrongPassword"), datagramPacket.getAddress(), datagramPacket.getPort());
                    }
                }else if (request.getCommand().equals("auth")&&AllConnections.isDistinct(request.getUserName())) {
                    Connection connection = new Connection(request.getArg1(), request.getArg2(), datagramPacket.getPort(), datagramPacket.getAddress());
                    AllConnections.connect(connection);
                    JDBCConnection.savePassword(connection);
                    sender.send(new FineAnswer("AllFine"), connection.address, connection.PORT);
                } else if (request.getCommand().equals("auth")&&!AllConnections.isDistinct(request.getUserName())){
                    sender.send(new ErrAnswer("AlreadyInUse"), datagramPacket.getAddress(), datagramPacket.getPort());
                }else if(request.getCommand().equals("exit")){
                    AllConnections.disconnect(AllConnections.getConnectionByUserName(request.getUserName()));
                }else{
                    commandProcessingModule.handle(request, AllConnections.getConnectionByUserName(request.getUserName()), sender);
                    byteArrayInputStream.close();
                    objectInputStream.close();
                }
            } catch(IOException | ClassNotFoundException e){
                System.out.println(e.getMessage());
            }
        }
    }
}

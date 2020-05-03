package com.classes.serverSide;


import com.classes.serverSide.answers.Answer;
import com.classes.serverSide.answers.BigDataAnswer;
import com.classes.serverSide.answers.FineAnswer;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Logger;

public class Sender extends Thread {
    private final static int BIG_DATA_CONST = 7000;
    private static final Logger logger = Logger.getLogger(Sender.class.getName());
    private DatagramSocket socket;

    public Sender(DatagramSocket datagramSocket) {
        this.socket = datagramSocket;
    }

    @Override
    public void run() {
        while (!isInterrupted()) ;
    }

    public void send(Answer answer, InetAddress inetAddress, int PORT) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(answer);
            objectOutputStream.flush();

            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();

            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, inetAddress, PORT);
            try {
                if (bytes.length > (BIG_DATA_CONST * 2)) sendBigDataAnswer(bytes, inetAddress, PORT, answer);
                else {
                    socket.send(datagramPacket);
                    logger.info("Отправлен ответ на " + datagramPacket.getAddress() + ":" + datagramPacket.getPort());
                    answer.logAnswer();
                }
            } catch (SocketException | IllegalStateException e) {
                sendBigDataAnswer(bytes, inetAddress, PORT, answer);
            }
        } catch (IOException e) {
            logger.warning("Не получилось отправить ответ.");
            e.printStackTrace();
        }

    }

    //TODO: Убрать массив bytes когда все заработает
    private void sendBigDataAnswer(byte[] bytes, InetAddress inetAddress, int PORT, Answer answer) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        logger.info("Слишком большой объем переданных данных");
        Answer bigDataAnswer = new BigDataAnswer();
        partSend(bigDataAnswer, inetAddress, PORT, objectOutputStream, byteArrayOutputStream);

        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        logger.info("Количество байтов: " + answer.getValue().getBytes().length);
        int count = (answer.getValue().getBytes().length / BIG_DATA_CONST + 1);
        logger.info("Количество переданных пакетов составляет " + count);
        Answer countAnswer = new FineAnswer(Integer.toString(count));
        partSend(countAnswer, inetAddress, PORT, objectOutputStream, byteArrayOutputStream);

        for (int i = 0; i < count; i++) {
            Answer newAnswer;
            if (i == count - 1) {
                logger.info("Количество байтов: " + answer.getValue().substring(i * BIG_DATA_CONST).getBytes().length);
                newAnswer = new FineAnswer(answer.getValue().substring(i * BIG_DATA_CONST));
            } else {
                logger.info("Количество байтов: " + answer.getValue().substring(i * BIG_DATA_CONST, (i + 1) * BIG_DATA_CONST).getBytes().length);
                newAnswer = new FineAnswer(answer.getValue().substring(i * BIG_DATA_CONST, (i + 1) * BIG_DATA_CONST));
            }
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            partSend(newAnswer, inetAddress, PORT, objectOutputStream, byteArrayOutputStream);

        }

    }

    private void partSend(Answer answer, InetAddress address, int port, ObjectOutputStream objectOutputStream, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        objectOutputStream.writeObject(answer);
        objectOutputStream.flush();
        byte[] newBytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.flush();
        DatagramPacket datagramPacket = new DatagramPacket(newBytes, newBytes.length, address, port);
        socket.send(datagramPacket);
        logger.info("Отправлен ответ на " + datagramPacket.getAddress() + ":" + datagramPacket.getPort());
        answer.logAnswer();
        objectOutputStream.reset();
        byteArrayOutputStream.reset();
    }

}

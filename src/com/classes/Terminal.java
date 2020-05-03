package com.classes;



import com.classes.serverSide.Listener;

import java.util.Scanner;
import java.util.logging.*;

public class Terminal {
    private static final Logger logger = Logger.getLogger(Listener.class.getName());
    private static Scanner sc = new Scanner(System.in);

    public static String readLine(String message){
        logger.info(message);
        return sc.nextLine();
    }
}

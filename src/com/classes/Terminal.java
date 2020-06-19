package com.classes;


import com.classes.serverSide.Listener;

import java.util.Scanner;
import java.util.logging.*;

public class Terminal {
    private static final Logger logger = Logger.getLogger(Listener.class.getName());
    private static Scanner sc = new Scanner(System.in);

    public static String readLine(String message) {
        logger.info(message);
        return sc.nextLine();
    }



    public static int choose(String... args) {
        int kol = args.length;


        if (kol <= 10) {
            System.out.println("Выберите вариант ответа.");
            for (int i = 1; i <= kol; i++) {
                System.out.println(i + ".: " + args[i]);
            }

            while (true)
                try {
                    switch (Integer.parseInt(readLine(""))) {
                        case 1:
                            return 1;
                        case 2:
                            return 2;
                        case 3:
                            return 3;
                        case 4:
                            return 4;
                        case 5:
                            return 5;
                        case 6:
                            return 6;
                        case 7:
                            return 7;
                        case 8:
                            return 8;
                        case 9:
                            return 9;
                        case 10:
                            return 10;
                        default:
                            System.out.println("Такого варианта не существует.");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Неверный ввод числа.");
                }
        } else {
            System.out.println("Введено неверное количество аргументов.");
            return 0;
        }
    }
}

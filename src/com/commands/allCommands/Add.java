package com.commands.allCommands;

import com.classes.CommandTranslator;
import com.classes.QueueController;
import com.classes.Terminal;
import com.classes.WorkSpace;
import com.classes.serverSide.CommandProcessingModule;
import com.commands.Command;
import com.enums.Country;
import com.enums.EyeColor;
import com.enums.HairColor;
import com.exceptions.SavePeopleException;
import com.wrappers.Person;
import com.wrappers.UserCommand;

public class Add extends Command {
    public Add() {
        super("add");
    }

    @Override
    public void execute(){
        UserCommand userCommand = WorkSpace.getUserCommand();
        if(!userCommand.getArg1().equals("")){
            try {
                Person person = CommandTranslator.translateArg(userCommand.getArg1());
                if(QueueController.getQueue().offer(person)) System.out.println("Человек добавлен");
                else System.out.println("Возникла ошибка добавления человека");
            } catch (SavePeopleException e) {
                System.out.println(e.getMessage());
            }
        }else {
            Person person = new Person();
            String buf;
            while (true) {
                buf = Terminal.readLine("Введите имя");
                if (!buf.equals("")) {
                    person.setName(buf);
                    break;
                } else System.out.println("Имя введено неверно");
            }
            while (true) {
                try {
                    buf = Terminal.readLine("Введите координату X");
                    if (!buf.equals("") && (Long.parseLong(buf) < Long.MAX_VALUE && Long.parseLong(buf) > Long.MIN_VALUE)) {
                        person.getCoordinates().setX(Long.parseLong(buf));
                        break;
                    } else System.out.println("Координата X введена неверно");
                } catch (IllegalArgumentException e) {
                    System.out.println("Координата X введена неверно");
                }
            }
            while (true) {
                try {
                    buf = Terminal.readLine("Введите координату Y");
                    if (!buf.equals("") && (Float.parseFloat(buf) < Float.MAX_VALUE || Float.parseFloat(buf) > Float.MIN_VALUE)) {
                        person.getCoordinates().setY(Float.parseFloat(buf));
                        break;
                    } else System.out.println("Координата Y введена неверно");
                } catch (IllegalArgumentException e) {
                    System.out.println("Координата Y введена неверно");
                }
            }
            while (true) {
                try {
                    buf = Terminal.readLine("Введите рост");
                    if (!buf.equals("") && (Float.parseFloat(buf) > 0 || Float.parseFloat(buf) < Float.MAX_VALUE)) {
                        person.setHeight(Float.parseFloat(buf));
                        break;
                    } else System.out.println("Рост введен неверно");
                } catch (IllegalArgumentException e) {
                    System.out.println("Рост введена неверно");
                }
            }
            while (true) {
                try {
                    buf = Terminal.readLine("Введите цвет глаз (Возможные цвета: RED, BLUE, YELLOW)");
                    if (!buf.equals("")) {
                        person.setEyeColor(EyeColor.valueOf(buf.toUpperCase()));
                        break;
                    } else System.out.println("Цвет глаз введен неверно");
                } catch (IllegalArgumentException e) {
                    System.out.println("Цвет глаз введен неверно");
                }
            }
            while (true) {
                try {
                    buf = Terminal.readLine("Введите цвет волос (Возможные цвета: GREEN, BLACK, PINK, YELLOW, ORANGE, WHITE)");
                    if (!buf.equals("")) {
                        person.setHairColor(HairColor.valueOf(buf.toUpperCase()));
                        break;
                    } else System.out.println("Цвет волос введен неверно");
                } catch (IllegalArgumentException e) {
                    System.out.println("Цвет волос введен неверно");
                }
            }
            while (true) {
                try {
                    buf = Terminal.readLine("Введите национальность (Возможные национальности: INDIA, VATICAN, NORTH_AMERICA, JAPAN)");
                    if (!buf.equals("")) {
                        person.setNationality(Country.valueOf(buf.toUpperCase()));
                        break;
                    } else System.out.println("Национальность введена неверно");
                } catch (IllegalArgumentException e) {
                    System.out.println("Национальность введена неверно");
                }
            }
            while (true) {
                try {
                    buf = Terminal.readLine("Введите координату локации X");
                    if (!buf.equals("") && (Integer.parseInt(buf) < Integer.MAX_VALUE || Integer.parseInt(buf) > Integer.MIN_VALUE)) {
                        person.getLocation().setX(Integer.parseInt(buf));
                        break;
                    } else System.out.println("Координата X введена неверно");
                } catch (IllegalArgumentException e) {
                    System.out.println("Координата X введена неверно");
                }
            }
            while (true) {
                try {
                    buf = Terminal.readLine("Введите координату локации Y");
                    if (!buf.equals("") && (Float.parseFloat(buf) < Float.MAX_VALUE || Float.parseFloat(buf) > Float.MIN_VALUE)) {
                        person.getLocation().setY(Float.parseFloat(buf));
                        break;
                    } else System.out.println("Координата Y введена неверно");
                } catch (IllegalArgumentException e) {
                    System.out.println("Координата Y введена неверно");
                }
            }
            while (true) {
                try {
                    buf = Terminal.readLine("Введите координату локации Z");
                    if (!buf.equals("") && (Float.parseFloat(buf) < Float.MAX_VALUE || Float.parseFloat(buf) > Float.MIN_VALUE)) {
                        person.getLocation().setY(Float.parseFloat(buf));
                        break;
                    } else System.out.println("Координата Z введена неверно");
                } catch (IllegalArgumentException e) {
                    System.out.println("Координата Z введена неверно");
                }
            }
            if (QueueController.getQueue().offer(person)) System.out.println("Персонаж добавлен");
            else System.out.println("Произошла ошибка");

        }
    }

    @Override
    public String serverExecute(){
        UserCommand userCommand = CommandProcessingModule.getCPMCommand();
            try {
                Person person = CommandTranslator.translateArg(userCommand.getArg1());
                if(QueueController.getQueue().offer(person)) return ("Человек добавлен");
                else return ("Возникла ошибка добавления человека");
            } catch (SavePeopleException e) {
                return (e.getMessage());
            }
        }
}

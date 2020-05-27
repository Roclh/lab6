package com.classes;


import com.commands.AllCommands;
import com.exceptions.NoSuchCommandException;
import com.wrappers.UserCommand;


/**
 * Данный класс реализует основной цикл работы программы.
 */

public class WorkSpace {
    private static UserCommand userCommand;


    public WorkSpace(){
        QueueController.initiate();
        AllCommands.initiate();
        while(true){
            commandCycle(Terminal.readLine("Ожидание комманды"));
        }

    }

    /**
     * Цикл комманды реализует одиночное считывание комманды пользователя,
     * @param command
     */
    private void commandCycle(String command){
        userCommand = CommandTranslator.translateCommand(command);
        try{
            AllCommands.getCommand(userCommand.getCommand()).execute();
        }catch (NoSuchCommandException e){
            System.out.println(e.getMessage());
        }
    }

    public static UserCommand getUserCommand() {
        return userCommand;
    }

    public static void setUserCommand(UserCommand userCommand) {
        WorkSpace.userCommand = userCommand;
    }
}

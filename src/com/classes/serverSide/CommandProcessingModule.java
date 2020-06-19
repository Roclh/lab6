package com.classes.serverSide;

import com.classes.CommandTranslator;
import com.classes.serverSide.answers.Answer;
import com.classes.serverSide.answers.ErrAnswer;
import com.classes.serverSide.answers.FineAnswer;
import com.classes.serverSide.answers.Request;
import com.commands.AllCommands;
import com.exceptions.NoSuchCommandException;
import com.wrappers.UserCommand;

import java.sql.Connection;

/**
 * Данный класс реализует обработку комманд, полученных с Listener
 * @see Listener
 */

public class CommandProcessingModule extends Thread{
    private static volatile UserCommand CPMCommand;
    private static volatile com.classes.Connection currentConnection;

    @Override
    public void run() {
        while (!isInterrupted());
    }

    public void handle(Request request, com.classes.Connection connection, Sender sender) {
        CPMCommand = CommandTranslator.translateCommand(request.getCommand() +" " + request.getArg1() + " " + request.getArg2());
        currentConnection = connection;
        CommandExecutionThread thread = new CommandExecutionThread(request, connection, sender);
        thread.start();
    }

    private Answer commandCycle(UserCommand userCommand) {
        try {
            return new FineAnswer(AllCommands.getCommand(userCommand.getCommand()).serverExecute());
        } catch (NoSuchCommandException e) {
            return new ErrAnswer(e.getMessage());
        }
    }

    public static UserCommand getCPMCommand() {
        return CPMCommand;
    }

    public static com.classes.Connection getCPMConnection(){return currentConnection;}
}

package com.classes.serverSide;

import com.classes.Connection;
import com.classes.serverSide.answers.Answer;
import com.classes.serverSide.answers.ErrAnswer;
import com.classes.serverSide.answers.FineAnswer;
import com.classes.serverSide.answers.Request;
import com.commands.AllCommands;
import com.commands.Command;
import com.exceptions.NoSuchCommandException;
import com.wrappers.UserCommand;

public class CommandExecutionThread extends Thread{
    private UserCommand request;
    private Connection connection;
    private Sender sender;


    public CommandExecutionThread(Request request, Connection connection, Sender sender){
        this.request = new UserCommand(request.getCommand(), request.getArg1(), request.getArg2());
        this.connection = connection;
        this.sender = sender;
    }

    @Override
    public void run(){
        sender.send(commandCycle(request), connection.address, connection.PORT);
    }

    private Answer commandCycle(UserCommand userCommand) {
        try {
            return new FineAnswer(AllCommands.getCommand(userCommand.getCommand()).serverExecute());
        } catch (NoSuchCommandException e) {
            return new ErrAnswer(e.getMessage());
        }
    }
}

package com.classes.serverSide;

import com.classes.serverSide.answers.Answer;
import com.classes.serverSide.answers.ErrAnswer;
import com.classes.serverSide.answers.FineAnswer;
import com.classes.serverSide.answers.Request;
import com.commands.AllCommands;
import com.exceptions.NoSuchCommandException;
import com.wrappers.UserCommand;

public class CommandProcessingModule {
    private static UserCommand CPMCommand;


    public Answer handle(Request request) {
        CPMCommand = new UserCommand(request.getCommand(), request.getArg1(), request.getArg2());
        return commandCycle(CPMCommand);
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
}

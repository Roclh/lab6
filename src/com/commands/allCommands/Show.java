package com.commands.allCommands;

import com.classes.JDBCConnection;
import com.classes.QueueController;
import com.classes.serverSide.CommandProcessingModule;
import com.commands.Command;
import com.exceptions.NoAnyActivityYetException;

public class Show extends Command{
    public Show() {
        super("show");
    }

    @Override
    public void execute() {
             System.out.println(QueueController.showQueue());
    }

    @Override
    public String serverExecute() {
        if(CommandProcessingModule.getCPMCommand().getArg1().equals("my")){
            try {
                return JDBCConnection.showQueue(CommandProcessingModule.getCPMConnection());
            } catch (NoAnyActivityYetException e) {
                return e.getMessage();
            }
        }
        return QueueController.showQueue();
    }
}

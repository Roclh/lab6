package com.commands.allCommands;

import com.classes.QueueController;
import com.commands.Command;

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
        return QueueController.showQueue();
    }
}

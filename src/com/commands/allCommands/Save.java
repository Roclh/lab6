package com.commands.allCommands;

import com.classes.*;
import com.classes.serverSide.CommandProcessingModule;
import com.commands.Command;
import com.wrappers.Person;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

public class Save extends Command {
    public Save() {
        super("save");
    }

    @Override
    public void execute() {
        QueueController.doWithAll(person -> JDBCConnection.insertPerson(person, CommandProcessingModule.getCPMConnection()));
    }

    @Override
    public String serverExecute() {
        return "Такой команды не существует";
    }
}

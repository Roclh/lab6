package com.commands.allCommands;

import com.classes.Connection;
import com.classes.JDBCConnection;
import com.classes.QueueController;
import com.classes.WorkSpace;
import com.commands.Command;
import com.exceptions.NoSuchPersonException;
import com.wrappers.Person;

import java.net.InetAddress;

public class InsertInDatabase extends Command {
    public InsertInDatabase(){
        super("insert");
    }

    @Override
    public void execute() {

    }
}

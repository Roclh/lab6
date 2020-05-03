package com.commands.allCommands;


import com.commands.Command;
import com.wrappers.HistoryWrapper;
import com.classes.serverSide.Listener;

import java.util.logging.Logger;

public class History extends Command {
    public History() {
        super("history");
    }

    @Override
    public void execute() {
       System.out.println(HistoryWrapper.printHistory());
    }

    @Override
    public String serverExecute() {
        return HistoryWrapper.printHistory();
    }
}

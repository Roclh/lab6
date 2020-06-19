package com.commands.allCommands;

import com.classes.JDBCConnection;
import com.classes.QueueController;
import com.classes.serverSide.CommandProcessingModule;
import com.commands.Command;
import com.exceptions.NoAnyActivityYetException;
import com.wrappers.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Clear extends Command {
    public Clear() {
        super("clear");
    }

    @Override
    public void execute() {
        Person person;
        String ans = "";
        while ((person = QueueController.poll()) != null) {
            ans = ans + "Был удален объект с id " + person.getId() + "\n";
        }
        System.out.println(ans);
    }

    @Override
    public String serverExecute() {
        List<Long> availableId;
        try {
            availableId = JDBCConnection.getAvailableId(CommandProcessingModule.getCPMConnection());
            AtomicReference<String> ans = new AtomicReference<>("");
            List<Long> finalAvailableId = availableId;
            for (Long id: finalAvailableId) {
                if(QueueController.removeIf(person -> person.getId() == id)){
                    JDBCConnection.deleteById(id);
                    ans.set(ans + "Был удален объект с id " + id + "\n");
                }
            }

            return (ans.get());
        } catch (NoAnyActivityYetException e) {
            return e.getMessage();
        }

    }
}

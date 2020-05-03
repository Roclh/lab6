package com.commands.allCommands;

import com.classes.QueueController;
import com.commands.Command;
import com.wrappers.Person;

public class Clear extends Command {
    public Clear() {
        super("clear");
    }

    @Override
    public void execute() {
        Person person;
        String ans = "";
        while ((person = QueueController.getQueue().poll()) != null) {
            ans = ans + "Был удален объект с id " + person.getId() + "\n";
        }
        System.out.println(ans);
    }

    @Override
    public String serverExecute() {
        Person person;
        String ans = "";
        while ((person = QueueController.getQueue().poll()) != null) {
            ans = ans + "Был удален объект с id " + person.getId() + "\n";
        }
        return (ans);
    }
}

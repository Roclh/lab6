package com.commands.allCommands;

import com.classes.CommandTranslator;
import com.classes.QueueController;
import com.classes.WorkSpace;
import com.commands.Command;
import com.exceptions.SavePeopleException;
import com.wrappers.Person;
import com.wrappers.UserCommand;

public class RemoveLower extends Command {
    public RemoveLower() {
        super("remove_lower");
    }

    @Override
    public void execute() {
        UserCommand userCommand = WorkSpace.getUserCommand();
        try {
            Person p = CommandTranslator.translateArg(userCommand.getArg1());
            QueueController.getQueue().removeIf(person -> person.compareTo(p) < 0);
        } catch (SavePeopleException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String serverExecute() {
        UserCommand userCommand = WorkSpace.getUserCommand();
        try {
            Person p = CommandTranslator.translateArg(userCommand.getArg1());
            long size = QueueController.getQueue().size();
            QueueController.getQueue().removeIf(person -> person.compareTo(p) < 0);
            if(size> QueueController.getQueue().size())
                return "Объект был удален";
                else return "Объект не был удален";
        } catch (SavePeopleException e) {
            return (e.getMessage());
        }
    }
}

package com.commands.allCommands;

import com.classes.CommandTranslator;
import com.classes.QueueController;
import com.classes.WorkSpace;
import com.classes.serverSide.CommandProcessingModule;
import com.commands.Command;
import com.exceptions.NoSuchPersonException;
import com.exceptions.SavePeopleException;
import com.wrappers.Person;
import com.wrappers.UserCommand;

import javax.jws.soap.SOAPBinding;

public class RemoveById extends Command {
    public RemoveById() {
        super("remove_by_id");
    }

    @Override
    public void execute() {
        UserCommand userCommand = WorkSpace.getUserCommand();
        long kol = QueueController.getQueue().size();
        QueueController.getQueue().removeIf(person -> person.getId() == Long.parseLong(userCommand.getArg1()));
        if (kol > (long) QueueController.getQueue().size())
            System.out.println("Объект был удален\n");
        else System.out.println("Объект не был удален\n");
    }

    @Override
    public String serverExecute() {
        UserCommand userCommand = CommandProcessingModule.getCPMCommand();
        long kol = QueueController.getQueue().size();
        QueueController.getQueue().removeIf(person -> person.getId() == Long.parseLong(userCommand.getArg1()));
        if (kol > (long) QueueController.getQueue().size())
            return ("Объект был удален\n");
        else return ("Объект не был удален\n");
    }
}

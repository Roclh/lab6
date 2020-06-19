package com.commands.allCommands;

import com.classes.CommandTranslator;
import com.classes.JDBCConnection;
import com.classes.QueueController;
import com.classes.WorkSpace;
import com.classes.serverSide.CommandProcessingModule;
import com.commands.Command;
import com.exceptions.NoAnyActivityYetException;
import com.exceptions.NoSuchPersonException;
import com.exceptions.SavePeopleException;
import com.wrappers.Person;
import com.wrappers.UserCommand;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public class RemoveById extends Command {
    public RemoveById() {
        super("remove_by_id");
    }

    @Override
    public void execute() {
        UserCommand userCommand = WorkSpace.getUserCommand();
        long kol = QueueController.size();
        QueueController.removeIf(person -> person.getId() == Long.parseLong(userCommand.getArg1()));
        if (kol > QueueController.size())
            System.out.println("Объект был удален\n");
        else System.out.println("Объект не был удален\n");
    }

    @Override
    public String serverExecute() {
        try {
            UserCommand userCommand = CommandProcessingModule.getCPMCommand();
            List<Long> list = JDBCConnection.getAvailableId(CommandProcessingModule.getCPMConnection());
            if (QueueController.removeIf(person -> person.getId() == Long.parseLong(userCommand.getArg1()) && list.contains(person.getId()))) {
                JDBCConnection.deleteById(Long.parseLong(userCommand.getArg1()));
                return ("Объект был удален\n");
            } else return ("Объект не был удален\n");
        } catch (NoAnyActivityYetException e) {
            e.printStackTrace();
            return ("Объект не был удален\n");
        }
    }
}

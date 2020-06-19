package com.commands.allCommands;

import com.classes.CommandTranslator;
import com.classes.JDBCConnection;
import com.classes.QueueController;
import com.classes.WorkSpace;
import com.classes.serverSide.CommandProcessingModule;
import com.commands.Command;
import com.exceptions.NoAnyActivityYetException;
import com.exceptions.SavePeopleException;
import com.wrappers.Person;
import com.wrappers.UserCommand;

import java.util.List;

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
        try {
            UserCommand userCommand = CommandProcessingModule.getCPMCommand();
            List<Long> list = JDBCConnection.getAvailableId(CommandProcessingModule.getCPMConnection());
            Person p = CommandTranslator.translateArg(userCommand.getArg1());
            if(QueueController.removeIf(person -> person.compareTo(p) < 0 && list.contains(person.getId()))){
                return "Объект был удален";
            }else return "Объект не был удален";
        } catch (SavePeopleException | NoAnyActivityYetException e) {
            return (e.getMessage());
        }
    }
}

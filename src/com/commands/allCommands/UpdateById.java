package com.commands.allCommands;

import com.classes.CommandTranslator;
import com.classes.QueueController;
import com.classes.WorkSpace;
import com.commands.Command;
import com.exceptions.NoSuchPersonException;
import com.exceptions.SavePeopleException;
import com.wrappers.Person;
import com.wrappers.UserCommand;

public class UpdateById extends Command {
    public UpdateById() {
        super("update_by_id");
    }

    @Override
    public void execute() {
        try {
            UserCommand userCommand = WorkSpace.getUserCommand();
            Person bufPerson = QueueController.getPerson(Long.parseLong(userCommand.getArg1()));
            QueueController.getQueue().removeIf(person -> person.getId() == Long.parseLong(userCommand.getArg1()));
            if (QueueController.getQueue().offer(CommandTranslator.translateArg(userCommand.getArg2(), bufPerson)))
                System.out.println("Элемент с id " + userCommand.getArg1() + " был обновлен\n");
            else System.out.println("Ошибка");
        }catch(NoSuchPersonException | SavePeopleException e){
            System.out.println("Введен неверный Id");
        }
    }

    @Override
    public String serverExecute() {
        try {
            UserCommand userCommand = WorkSpace.getUserCommand();
            Person bufPerson = QueueController.getPerson(Long.parseLong(userCommand.getArg1()));
            QueueController.getQueue().removeIf(person -> person.getId() == Long.parseLong(userCommand.getArg1()));
            if (QueueController.getQueue().offer(CommandTranslator.translateArg(userCommand.getArg2(), bufPerson)))
                return ("Элемент с id " + userCommand.getArg1() + " был обновлен\n");
            else return ("Ошибка");
        }catch(NoSuchPersonException | SavePeopleException e){
            return ("Введен неверный Id");
        }
    }
}

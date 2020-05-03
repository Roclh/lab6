package com.commands.allCommands;

import com.classes.QueueController;
import com.classes.WorkSpace;
import com.classes.serverSide.CommandProcessingModule;
import com.commands.Command;
import com.enums.HairColor;
import com.wrappers.UserCommand;

import java.io.IOException;

public class CountByHairColor extends Command {
    public CountByHairColor() {
        super("count_by_hair_color");
    }

    @Override
    public void execute() {
        UserCommand userCommand = WorkSpace.getUserCommand();
        System.out.println( "В коллекции содержится " +
                QueueController.getQueue().stream().filter(person -> person.getHairColor().equals(HairColor.valueOf(userCommand.getArg1().toUpperCase()))).count() +
                " элементов с данным цветом волос\n");

    }

    @Override
    public String serverExecute() {
        UserCommand userCommand = CommandProcessingModule.getCPMCommand();
        return ( "В коллекции содержится " +
                QueueController.getQueue().stream().filter(person -> person.getHairColor().equals(HairColor.valueOf(userCommand.getArg1().toUpperCase()))).count() +
                " элементов с данным цветом волос\n");
    }
}

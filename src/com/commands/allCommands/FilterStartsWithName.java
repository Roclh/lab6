package com.commands.allCommands;

import com.classes.QueueController;
import com.classes.WorkSpace;
import com.classes.serverSide.CommandProcessingModule;
import com.commands.Command;
import com.wrappers.Person;
import com.wrappers.UserCommand;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

public class FilterStartsWithName extends Command {
    public FilterStartsWithName() {
        super("filter_starts_with_name");
    }

    @Override
    public void execute() {
        UserCommand userCommand = WorkSpace.getUserCommand();
        AtomicReference<String> ans = new AtomicReference<>("");
        Queue<Person> bufQueue = new PriorityQueue<>(QueueController.getQueue());
        bufQueue.stream().filter(person -> person.getName().substring(0, userCommand.getArg1().length()).equals(userCommand.getArg1())).forEach(person ->
                ans.set(ans +"Имя: " + person.getName() + ", id: " + person.getId() + "\n"));
        if (bufQueue.stream().filter(person -> person.getName().substring(0, userCommand.getArg1().length()).equals(userCommand.getArg1())).count() <= 0) {
            System.out.println("В данной коллекции нету объектов, начинающихся с данной подстроки\n");
        }
        System.out.println(ans.get());
    }

    @Override
    public String serverExecute() {
        UserCommand userCommand = CommandProcessingModule.getCPMCommand();
        AtomicReference<String> ans = new AtomicReference<>("");
        Queue<Person> bufQueue = new PriorityQueue<>(QueueController.getQueue());
        bufQueue.stream().filter(person -> person.getName().substring(0, userCommand.getArg1().length()).equals(userCommand.getArg1())).forEach(person ->
                ans.set(ans +"Имя: " + person.getName() + ", id: " + person.getId() + "\n"));
        if (bufQueue.stream().filter(person -> person.getName().substring(0, userCommand.getArg1().length()).equals(userCommand.getArg1())).count() <= 0) {
            return ("В данной коллекции нету объектов, начинающихся с данной подстроки\n");
        }
        return (ans.get());
    }
}

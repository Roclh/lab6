package com.commands.allCommands;

import com.classes.QueueController;
import com.commands.Command;
import com.wrappers.Person;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

public class PrintUniqueHeight extends Command {
    public PrintUniqueHeight() {
        super("print_unique_height");
    }

    @Override
    public void execute() {
        AtomicReference<String> ans = new AtomicReference<>("");
        ans.set(ans + "Уникальные значения height: \n");
        Queue<Person> buf = new PriorityQueue<>(QueueController.getQueue());
        if (QueueController.getQueue().size() > 0) {
            Queue<Float> allHeights = new PriorityQueue<>();
            QueueController.getQueue().stream().forEach(person -> allHeights.add(person.getHeight()));
            allHeights.stream().distinct().forEach(height -> ans.set(ans.get() + height + "\n"));
            System.out.println(ans.get());
        } else {
            System.out.println("Коллекция пуста");
        }
    }

    @Override
    public String serverExecute() {
        AtomicReference<String> ans = new AtomicReference<>("");
        ans.set(ans + "Уникальные значения height: \n");
        Queue<Person> buf = new PriorityQueue<>(QueueController.getQueue());
        if (QueueController.getQueue().size() > 0) {
            Queue<Float> allHeights = new PriorityQueue<>();
            QueueController.getQueue().stream().forEach(person -> allHeights.add(person.getHeight()));
            allHeights.stream().distinct().forEach(height -> ans.set(ans.get() + height + "\n"));
            return (ans.get());
        } else {
            return ("Коллекция пуста");
        }
    }
}

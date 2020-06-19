package com.commands.allCommands;

import com.classes.QueueController;
import com.commands.Command;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class Info extends Command {
    public Info() {
        super("info");
    }

    @Override
    public void execute(){

        AtomicReference<String> ans = new AtomicReference<>("");
        ans.set(ans + "Информация о коллекции: \r\n");
        ans.set(ans + "В коллекции содержаться объекты типа Person\r\n");
        if (QueueController.getQueue().size() > 0) {
            ans.set(ans + "В коллекции сейчас находится " + QueueController.getQueue().size() + " элементов. Они приведены ниже\r\n");
            QueueController.doWithAll(person -> ans.set(ans + "Name: " + person.getName() + ", Id:" + person.getId() + "\r\n"));
            ans.set(ans + "================================================\r\n");
            System.out.println(ans.get());
        } else {
            System.out.println("Коллекция пуста");
        }
    }

    @Override
    public String serverExecute() {
        AtomicReference<String> ans = new AtomicReference<>("");
        ans.set(ans + "Информация о коллекции: \r\n");
        ans.set(ans + "В коллекции содержаться объекты типа Person\r\n");
        if (QueueController.getQueue().size() > 0) {
            ans.set(ans + "В коллекции сейчас находится " + QueueController.getQueue().size() + " элементов. Они приведены ниже\r\n");
            QueueController.doWithAll(person -> ans.set(ans + "Name: " + person.getName() + ", Id:" + person.getId() + "\r\n"));
            ans.set(ans + "================================================\r\n");
            return (ans.get());
        } else {
            return ("Коллекция пуста");
        }
    }
}

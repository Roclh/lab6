package com.commands.allCommands;

import com.classes.QueueController;
import com.classes.WorkSpace;
import com.classes.serverSide.CommandProcessingModule;
import com.commands.Command;
import com.exceptions.SavePeopleException;
import com.wrappers.Person;
import com.classes.CommandTranslator;
import com.wrappers.UserCommand;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author Roclh
 * @see Command
 *
 * Данный класс реализует работу комманды add_if_min, которая добавляет объект с заданными пользователем параметрами если объект меньше остальных.
 */

public class AddIfMin extends Command {
    public AddIfMin() {
        super("add_if_min");
    }

    @Override
    public void execute() {
        UserCommand userCommand = WorkSpace.getUserCommand();
        String ans = "";
        if (QueueController.getQueue().size() > 0) {
            try {
            Person p = CommandTranslator.translateArg(userCommand.getArg1());
            Queue<Person> buf = new PriorityQueue<>(QueueController.getQueue());
            Person min = buf.poll();
            for (Person person : buf) {
                assert min != null;
                if (person.compareTo(min) < 0) min = person;
            }
            assert min != null;
            if (p.compareTo(min) < 0) {
                QueueController.getQueue().offer(p);
                ans = ans + "Объект с именем " + p.getName() + " добавлен\n";
                System.out.println(ans);
            } else System.out.println("Данный элемент не является минимальным\n");
            } catch (SavePeopleException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Данная коллекция пуста");
        }
    }

    @Override
    public String serverExecute() {
        UserCommand userCommand = CommandProcessingModule.getCPMCommand();
        String ans = "";
        if (QueueController.getQueue().size() > 0) {
            try {
                Person p = CommandTranslator.translateArg(userCommand.getArg1());
                Queue<Person> buf = new PriorityQueue<>(QueueController.getQueue());
                Person min = buf.poll();
                for (Person person : buf) {
                    assert min != null;
                    if (person.compareTo(min) < 0) min = person;
                }
                assert min != null;
                if (p.compareTo(min) < 0) {
                    QueueController.getQueue().offer(p);
                    ans = ans + "Объект с именем " + p.getName() + " добавлен\n";
                    return (ans);
                } else return ("Данный элемент не является минимальным\n");
            } catch (SavePeopleException e) {
                e.printStackTrace();
            }
        } else {
            return ("Данная коллекция пуста");
        }
        return ans;
    }
}

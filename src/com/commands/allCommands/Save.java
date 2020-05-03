package com.commands.allCommands;

import com.classes.JsonParser;
import com.classes.QueueController;
import com.classes.Terminal;
import com.commands.Command;
import com.wrappers.Person;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

public class Save extends Command {
    public Save() {
        super("save");
    }

    @Override
    public void execute() {
        try {
            if (JsonParser.isWritable()) {

                if (QueueController.getQueue().size() > 0) {
                    Queue<Person> bufQueue = new PriorityQueue<>(QueueController.getQueue());
                    JsonParser.resetSaveFile();
                    bufQueue.forEach(person -> {
                        try {
                            JsonParser.savePerson(person);
                        } catch (IOException ex) {
                            System.out.println("Поеш гавна");
                        }
                    });
                    System.out.println("Коллекция сохранена.");
                } else {
                    boolean isWorking = true;
                    String question = Terminal.readLine("Ваша коллекция пуста. Вы хотите удалить содержимое файла? Y/N");
                    while (isWorking) {
                        if (question.equals("Y") || question.equals("y")) {
                            isWorking = false;
                            JsonParser.resetSaveFile();
                            System.out.println("Вы очистили содержимое файла");
                        } else if (question.equals("N") || question.equals("n")) {
                            isWorking = false;
                            System.out.println("Вы решили сохранить содержимое");
                        } else System.out.println("Неверная комманда. Введите Y или N");
                    }
                }
            } else System.out.println("Недостаточно прав для изменения коллекции");
        } catch (NullPointerException e) {
            System.out.println("Шото случилось");
        }
    }

    @Override
    public String serverExecute() {
        return "Такой команды не существует";
    }
}

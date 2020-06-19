package com.commands;

import com.commands.allCommands.*;
import com.exceptions.NoSuchCommandException;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Данный класс релализует хранение и обработку коллекции комманд
 * @see Command
 *
 */

public class AllCommands {
    private static boolean isInitiated = false;
    private static Queue<Command> allCommands = new PriorityQueue<>();


    /**
     * Метод создает коллекцию комманд для дальнейшей работы с ними.
     * Метод может быть вызван лишь один раз за всю работу программы.
     */
    public static void initiate(){
        if(!isInitiated) {

            allCommands.add(new Add());
            allCommands.add(new AddIfMin());
            allCommands.add(new Clear());
            allCommands.add(new CountByHairColor());
            allCommands.add(new ExecuteScript());
            allCommands.add(new Exit());
            allCommands.add(new FilterStartsWithName());
            allCommands.add(new Help());
            allCommands.add(new History());
            allCommands.add(new Info());
            allCommands.add(new PrintUniqueHeight());
            allCommands.add(new RemoveById());
            allCommands.add(new RemoveLower());
            allCommands.add(new Show());
            allCommands.add(new UpdateById());
            isInitiated = true;
        }else System.out.println("Комманды уже были добавлены в коллекцию.");
    }


    /**
     * Метод возвращает комманду по её id.
     * @param id - id комманды.
     * @return Метод возвращает объект класса Command
     * @throws NoSuchCommandException - в случае, если была введена неверная комманда, выбрасывает NoSuchCommandException.
     */
    public static Command getCommand(String id) throws NoSuchCommandException {
        if (allCommands.stream().anyMatch(command -> command.getId().equals(id))){
            return allCommands.stream().filter(command -> command.getId().equals(id)).findFirst().get();
        }else {
            throw new NoSuchCommandException();
        }

    }
}

package com.commands.allCommands;

import com.classes.CommandTranslator;
import com.classes.Terminal;
import com.classes.WorkSpace;
import com.commands.AllCommands;
import com.commands.Command;
import com.exceptions.NoSuchCommandException;
import com.wrappers.HistoryWrapper;
import com.wrappers.UserCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExecuteScript extends Command {
    public ExecuteScript() {
        super("execute_script");
    }
    private static int depth = 0;
    private static UserCommand executerCommand;

    @Override
    public void execute() {
        String scriptPath = "";
        executerCommand = WorkSpace.getUserCommand();
        scriptPath = executerCommand.getArg1();

        if (!HistoryWrapper.addScriptPath(executerCommand.getArg1())) {
            System.out.println("Скрипт вызывает сам себя, что приведет к бесконечному циклу. Комманда будет прервана.");
            return;
        }
        try {
            File file = new File(executerCommand.getArg1().trim());
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                commandCycle(sc.nextLine());
            }
            depth--;
            HistoryWrapper.popScriptPath(scriptPath);
        } catch (FileNotFoundException e) {
            System.out.println("Такого файла не существует");
        }
    }

    private void commandCycle(String command) {
        executerCommand = CommandTranslator.translateCommand(command);
        WorkSpace.setUserCommand(executerCommand);
        try {
            AllCommands.getCommand(executerCommand.getCommand()).execute();
        } catch (NoSuchCommandException e) {
            System.out.println(e.getMessage());
        }
    }
}

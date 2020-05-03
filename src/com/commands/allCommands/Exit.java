package com.commands.allCommands;

import com.commands.Command;

public class Exit extends Command {
    public Exit() {
        super("exit");
    }

    @Override
    public void execute(){
        System.out.println("Завершение работы сервера");
        System.exit(111);
    }

    @Override
    public String serverExecute() {
        return "Такой команды не существует";
    }
}

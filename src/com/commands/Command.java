package com.commands;

/**
 *  Данный класс является шаблоном для всех реализованных комманд
 *
 */

public class Command implements Comparable<Command>{
    private String id;



    public Command(String id){
        this.id = id;
    }

    /**
     * Метод execute реализует выполнение комманд от сервера, выводящий ответ напрямую на командную строку сервера.
     */

    public void execute(){

    }

    /**
     * Метод serverExecute реализует выполнение комманд от клиента, отправляющий ответ обратно клиенту, который отправил запрос.
     * @return
     */
    public String serverExecute(){
        return "null";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(Command o) {
        return 0;
    }
}

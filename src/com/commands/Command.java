package com.commands;

public class Command implements Comparable<Command>{
    private String id;

    public Command(String id){
        this.id = id;
    }


    public void execute(){

    }

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

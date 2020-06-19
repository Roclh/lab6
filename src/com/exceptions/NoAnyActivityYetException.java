package com.exceptions;

import sun.reflect.annotation.ExceptionProxy;

public class NoAnyActivityYetException extends Exception {
    private String message = "В данный момент не сущетсвует каких-либо доступных id. Создайте собственных людей для дальнейшей работы с ними.";

    @Override
    public String getMessage(){
        return message;
    }
}

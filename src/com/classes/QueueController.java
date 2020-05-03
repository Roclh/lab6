package com.classes;

import com.exceptions.NoSuchCommandException;
import com.exceptions.NoSuchPersonException;
import com.exceptions.SavePeopleException;
import com.wrappers.Person;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

public class QueueController {
    private static Queue<Person> allPerson = new PriorityQueue<>();
    private static boolean isInitiated = false;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static void initiate(){
        if(!isInitiated){
            while(true) {
                try {
                    if(JsonParser.setFile(Terminal.readLine("Введите путь к файлу"))){
                        break;
                    }
                }catch (IOException e){
                    System.out.println("Такого файла не существует");
                }
            }

            PriorityQueue<Person> buf = new PriorityQueue<>();
            int kolEl = 0;
            try {
                kolEl = JsonParser.getKolEl();
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден\n" + e.getMessage());
            }
            boolean allOk = true;
            long[] elementsId = new long[kolEl];
            try {
                elementsId = JsonParser.getId();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                allOk = false;
            }
            if (allOk) {
                for (int i = 0; i < kolEl; i++) {
                    try {
                        buf.offer(JsonParser.getSavedPerson(elementsId[i]));
                    } catch (IOException | SavePeopleException e) {
                        System.out.println(e.getMessage());
                    }
                }
                allPerson = buf;
                if (allPerson.size() > 0) System.out.println("Файл загружен в коллекцию");
                else System.out.println("Файл пустой");
            }
        } else System.out.println("коллекция уже инициализированна");
    }

    public static Person getPerson(long id) throws NoSuchPersonException {
        if (allPerson.stream().anyMatch(person -> person.getId() == id)){
            return allPerson.stream().filter(command -> command.getId() == id).findFirst().get();
        }else {
            throw new NoSuchPersonException();
        }
    }

    public static String showQueue(){
            AtomicReference<String> answer = new AtomicReference<>("");
            allPerson.forEach(person -> {
                answer.set(answer + ("{\"name\":"+ person.getName() + "\";" +
                        "\"id\":\"" + person.getId() + "\";" +
                        "\"coordinates\"{\"X\":\"" + person.getCoordinates().getX().toString() + "\";\"Y\":\"" +
                        person.getCoordinates().getY() + "\"};" +
                        "\"creationDate\":\"" + dtf.format(person.getCreationDate()) + "\";\n    " +
                        "\"height\":\"" + person.getHeight().toString() + "\";" +
                        "\"eyeColor\":\"" + person.getEyeColor().toString() + "\";" +
                        "\"hairColor\":\"" + person.getHairColor().toString() + "\";" +
                        "\"nationality\":\"" + person.getNationality().toString() + "\";\n    " +
                        "\"location\"{\"X\":" + person.getLocation().getX().toString() + "\";\"Y\":" +
                        person.getLocation().getY().toString() + "\";\"Z\":\"" +
                        person.getLocation().getZ().toString() + "\"}\n"));
            });
            return answer.get();
        }
//    }

    public static Queue<Person> getQueue(){
        return allPerson;
    }

}

package com.classes;

import com.enums.Country;
import com.enums.EyeColor;
import com.enums.HairColor;
import com.exceptions.NoAnyActivityYetException;
import com.wrappers.Person;
import sun.security.rsa.RSASignature;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class JDBCConnection {

    public static long getMaxId(){
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/PersonInfo";
            try (java.sql.Connection con = DriverManager.getConnection(url, "postgres", "Nikita444super")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT max(persons.id) from persons");
                long id = 0;
                while(rs.next()){
                    if((id = rs.getInt("max"))!=0 ){
                        return id;
                    }
                }
                rs.close();
                stmt.close();
                return id;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static void deleteById(long id){
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/PersonInfo";
            try (java.sql.Connection con = DriverManager.getConnection(url, "postgres", "Nikita444super")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("DELETE FROM persons WHERE id = "+id);
                rs.close();
                stmt.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void dropTable(){
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/PersonInfo";
            try (java.sql.Connection con = DriverManager.getConnection(url, "postgres", "Nikita444super")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("DELETE FROM persons");
                rs.close();
                stmt.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void savePassword(Connection connection){
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/PersonInfo";
            try (java.sql.Connection con = DriverManager.getConnection(url, "postgres", "Nikita444super")) {
                Statement stmt = con.createStatement();
                MessageDigest md = MessageDigest.getInstance("SHA-224");

                ResultSet rs = stmt.executeQuery("INSERT INTO logs(login, password)\n VALUES('"+ connection.getUserName() +"','"+ Decoder.encrypt(connection.getPassword()) +"')");
                rs.close();
                stmt.close();
                while(rs.next());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean checkLogin(String login){
        boolean isWorked = true;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/PersonInfo";
            try (java.sql.Connection con = DriverManager.getConnection(url, "postgres", "Nikita444super")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM logs WHERE Login = '" + login +"'");
                if(rs.next()){
                    if(rs.getString("login").equals(login)){
                        isWorked = false;
                    }
                }
                rs.close();
                stmt.close();
                return isWorked;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean logIn(String login, String password){
        boolean isWorked = false;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/PersonInfo";
            try (java.sql.Connection con = DriverManager.getConnection(url, "postgres", "Nikita444super")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM logs WHERE Login = '" + login +"'");
                if(rs.next()){
                    if(rs.getString("login").equals(login) && rs.getString("password").equals(Decoder.encrypt(password))){
                        isWorked = true;
                    }else{
                        System.out.println("Неверный пароль");
                        isWorked = false;
                    }
                }
                rs.close();
                stmt.close();
                return isWorked;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void insertPerson(Person person, Connection connection) {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/PersonInfo";
            try (java.sql.Connection con = DriverManager.getConnection(url, "postgres", "Nikita444super")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("INSERT INTO persons(UserName,name,coordinatesx,coordinatesy,datetime,height,eyecolor,haircolor,country,locationx,locationy, locationz)\n VALUES ('" + connection.getUserName() + "','" + person.getName() + "'," + person.getCoordinates().getX() + "," + person.getCoordinates().getY() +
                        ",'" + person.getCreationDate().toString() + "'," + person.getHeight() + ",'" + person.getEyeColor().toString() + "','" + person.getHairColor() + "','" + person.getNationality().toString() + "'," + person.getLocation().getX().toString() + "," +
                        person.getLocation().getY().toString() + ", " + person.getLocation().getZ().toString() + ");");
                while(rs.next());
                rs.close();
                stmt.close();

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getUserCollection(String userId) {
        Queue<Person> allPerson = new PriorityQueue<>();
        if (userId.equals("any")) {
            try {
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://localhost:5432/PersonInfo";
                String login = "postgres";
                String password = "Nikita444super";
                try (java.sql.Connection con = DriverManager.getConnection(url, login, password)) {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM public.persons\n ORDER BY id");
                    while (rs.next()) {
                        Person person = new Person();
                        person.setId(rs.getLong("id"));
                        person.setName(rs.getString("name"));
                        person.getCoordinates().setX(rs.getLong("coordinatesx"));
                        person.getCoordinates().setY(rs.getLong("coordinatesy"));
                        person.setCreationDate(LocalDateTime.parse(rs.getString("datetime")));
                        person.setHeight(rs.getFloat("height"));
                        person.setEyeColor(EyeColor.valueOf(rs.getString("eyecolor")));
                        person.setHairColor(HairColor.valueOf(rs.getString("haircolor")));
                        person.setNationality(Country.valueOf(rs.getString("country")));
                        person.getLocation().setX(rs.getInt("locationx"));
                        person.getLocation().setY(rs.getFloat("locationy"));
                        person.getLocation().setZ(rs.getFloat("locationz"));
                        if (allPerson.offer(person)) {
                            System.out.println("Человек с id " + person.getId() + " был добавлен в коллекцию.");
                        } else System.out.println("Произошла ошибка");
                    }
                    QueueController.setQueue(allPerson);
                    rs.close();
                    stmt.close();

                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else { //TODO: Сделать метод нормальным
            System.out.println("Пока не сделана");

        }
    }

    public static List<Long> getAvailableId(Connection connection) throws NoAnyActivityYetException {
        List<Long> id = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/PersonInfo";
            try (java.sql.Connection con = DriverManager.getConnection(url, "postgres", "Nikita444super")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM public.persons\nWHERE username ='"+connection.getUserName()+"'");
                while(rs.next()){
                    id.add(rs.getLong("id"));
                }
                rs.close();
                stmt.close();
                return id;

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new NoAnyActivityYetException();
    }
}

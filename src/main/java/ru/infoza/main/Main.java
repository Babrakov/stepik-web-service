package ru.infoza.main;


import ru.infoza.dbService.DBException;
import ru.infoza.dbService.DBService;
import ru.infoza.dbService.dataSets.UsersDataSet;

public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBService();
        dbService.printConnectInfo();
        try {
            long userId = dbService.addUser("noob");
            System.out.println("Added user id: " + userId);

            UsersDataSet dataSet = dbService.getUser(userId);
            System.out.println("User data set: " + dataSet);

//            dbService.cleanUp();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}

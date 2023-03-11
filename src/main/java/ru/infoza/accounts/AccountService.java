package ru.infoza.accounts;

import ru.infoza.dbService.DBException;
import ru.infoza.dbService.DBService;
import ru.infoza.dbService.dao.UsersDAO;
import ru.infoza.dbService.dataSets.UsersDataSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;
    private final DBService dbService;

    public AccountService() {
        dbService = new DBService();
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) throws DBException {
        dbService.addUser(userProfile.getLogin(),userProfile.getPass());
//        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        UsersDataSet dataSet = null;
        try {
            dataSet = dbService.getUser(login);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

//        return Optional.ofNullable(dataSet)
//                .map(d -> new UserProfile(d.getLogin(), d.getPass(), d.getEmail()))
//                .orElse(null);

        if (dataSet == null) return null;
        return new UserProfile(dataSet.getLogin(),dataSet.getPass(), dataSet.getEmail());
//        return loginToProfile.get(login);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}

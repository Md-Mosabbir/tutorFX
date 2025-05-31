package tutor.dsafx.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private static final String USERS_FILE = "user.json";
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(User.class, new UserAdapter())
            .create();
    private List<User> users;

    public AuthService() {
        loadUsers();
    }

    private void loadUsers() {
        try {
            if (!Files.exists(Paths.get(USERS_FILE))) {
                users = new ArrayList<>();
                saveUsers();
            }

            Type userListType = new TypeToken<ArrayList<BaseUser>>(){}.getType();
            users = gson.fromJson(new FileReader(USERS_FILE), userListType);

            if (users == null) {
                users = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            users = new ArrayList<>();
        }
    }

    private void saveUsers() {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean register(User user) {
        if (findByUsername(user.getUsername()) != null) {
            return false;
        }
        users.add(user);
        saveUsers();
        return true;
    }

    public User login(String username, String password) {
        User user = findByUsername(username);
        if (user != null && user.verifyPassword(password)) {
            return user;
        }
        return null;
    }

    private User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
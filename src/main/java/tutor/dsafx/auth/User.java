package tutor.dsafx.auth;

import org.mindrot.jbcrypt.BCrypt;

public interface User {
    String getUsername();
    void setUsername(String username);
    String getPasswordHash();
    void setPasswordHash(String passwordHash);
    String getEmail();
    void setEmail(String email);

    // Default method for password verification
    default boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, getPasswordHash());
    }
}
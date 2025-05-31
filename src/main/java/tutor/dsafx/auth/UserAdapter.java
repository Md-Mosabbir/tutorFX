package tutor.dsafx.auth;

import com.google.gson.*;

import java.lang.reflect.Type;

public class UserAdapter implements JsonSerializer<User>, JsonDeserializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("username", user.getUsername());
        obj.addProperty("passwordHash", user.getPasswordHash());
        obj.addProperty("email", user.getEmail());
        return obj;
    }

    @Override
    public User deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        BaseUser user = new BaseUser();
        user.setUsername(obj.get("username").getAsString());
        user.setPasswordHash(obj.get("passwordHash").getAsString());
        user.setEmail(obj.get("email").getAsString());
        return user;
    }
}
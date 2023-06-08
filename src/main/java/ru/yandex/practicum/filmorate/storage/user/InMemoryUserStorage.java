package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Validator;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long idCounter = 0;

    @Override
    public List<User> getUsers() {
        return List.copyOf(users.values());
    }

    @Override
    public User createUser(User user) {
        Validator.validationCheck(user);
        idCounter++;
        user.setId(idCounter);
        users.put(idCounter, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        Validator.validationCheck(user);
        long filmId = user.getId();
        if (users.containsKey(filmId)) {
            users.put(filmId, user);
            return user;
        }
        throw new ValidationException("Такого idCounter не существует");
    }

    @Override
    public User findUserById(long id) {
        return users.get(id);
    }

    @Override
    public void deleteUsers() {
        users.clear();
    }
}

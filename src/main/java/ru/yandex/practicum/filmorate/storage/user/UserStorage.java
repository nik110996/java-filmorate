package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserStorage {

    List<User> getUsers();

    User createUser(User user);

    User updateUser(User user);

    User findUserById(long id);

    void addFriend(long id, long friendId);

    void deleteFriend(long id, long friendId);

    List<User> getFriendsList(long id);
    List<User> getCommonFriends(long id, long otherId);

    void deleteUsers();
}

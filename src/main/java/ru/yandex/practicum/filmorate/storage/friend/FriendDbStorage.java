package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("friendDbStorage")
public class FriendDbStorage implements FriendStorage {

    private final JdbcTemplate jdbcTemplate;

    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(long id, long friendId) {
        checkUserExisting(id);
        checkUserExisting(friendId);
        String sqlInsert = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sqlInsert, id, friendId);
    }

    @Override
    public void deleteFriend(long id, long friendId) {
        String sqlInsert = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlInsert, id, friendId);
        jdbcTemplate.update(sqlInsert, friendId, id);
    }

    @Override
    public List<User> getFriendsList(long id) {
        String sqlQuery = "SELECT u.id, u.login, u.name, u.birthday, u.email FROM users u " +
                "JOIN friends f ON f.friend_id = u.id WHERE user_id = ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser, id);
    }

    @Override
    public List<User> getCommonFriends(long id, long otherId) {
        List<User> userFriends = getFriendsList(id);
        List<User> friendFriends = getFriendsList(otherId);
        List<User> commonFriends = new ArrayList<>();
        for (User user : userFriends) {
            if (friendFriends.contains(user)) {
                commonFriends.add(user);
            }
        }
        return commonFriends;
    }

    private void checkUserExisting(long id) {
        try {
            new UserDbStorage(jdbcTemplate).findUserById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Такого idCounter не существует");
        }
    }

    public User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }
}
